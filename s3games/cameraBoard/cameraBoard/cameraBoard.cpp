#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

#include <iostream>

using namespace cv;
using namespace std;

class ElementType
{
private:
	double hueMin, hueMax;
	double satMin, satMax;
	double valueMin, valueMax;
	int sizeMin, sizeMax;

public:
	char *elementTypeName;
	int elementTypeState;

	ElementType(double hueMin, double hueMax, double satMin, double satMax, double valueMin, double valueMax, int sizeMin, int sizeMax, char *elementTypeName, int elementTypeState)
	{
		this->hueMin = hueMin;
		this->hueMax = hueMax;
		this->satMin = satMin;
		this->satMax = satMax;
		this->valueMin = valueMin;
		this->valueMax = valueMax;
		this->sizeMin = sizeMin;
		this->sizeMax = sizeMax;
		this->elementTypeName = elementTypeName;
		this->elementTypeState = elementTypeState;
	}

	bool matches(double hue, double sat, double value)
	{		
		return  (hue >= hueMin) && (hue <= hueMax) &&
				(sat >= satMin) && (sat <= satMax) &&
				(value >= valueMin) && (value <= valueMax);
	}

	bool matchesSize(int size)
	{				
		return (size >= sizeMin) && (size <= sizeMax);
	}
};

class Location 
{
public:
	int x, y;
	char *elementType;
	int elementState;

	Location(int x, int y, char *elementType, int elementState)
	{
		this->x = x;
		this->y = y;
		this->elementType = elementType;
		this->elementState = elementState;
	}
};

class ElementMatcher
{

public:
	ElementMatcher(ElementType &etype, Mat &img, int x, int y) 
		: findingImg(img), et(etype)
	{		
		fsize = -1;
		fwidth = img.size().width;
		fheight = img.size().height;
		fx = x;
		fy = y;
	}

	long size()
	{
		if (fsize == -1)	
			follow();		
		return fsize;
	}

private:	
	Mat &findingImg;
	ElementType &et;
	int fx, fy;
	int	fwidth, fheight;
	double fhue, fsat, fval;
	int fsize;

	inline void getHSV()
	{
		fhue = findingImg.at<Vec3f>(fy, fx)[0];
		fsat = findingImg.at<Vec3f>(fy, fx)[1];
		fval = findingImg.at<Vec3f>(fy, fx)[2];
	}

	inline void follow()
	{
		getHSV();
		if (et.matches(fhue, fsat, fval)) getSize();
	}

	void getSize()
	{		
		findingImg.at<Vec3f>(fy, fx)[0] = -1;
		findingImg.at<Vec3f>(fy, fx)[1] = -1;
		findingImg.at<Vec3f>(fy, fx)[2] = -1;
		fsize++;
		if (fx > 0)
		{
			fx--;
			follow();	
			fx++;
		}
		if (fy > 0)
		{
			fy--;
			follow();
			fy++;
		}
		if (fx < fwidth - 1)
		{
			fx++;
			follow();
			fx--;
		}
		if (fy < fheight - 1)
		{
			fy++;
			follow();
			fy--;
		}
	}
};

class ObjectLocator
{
public:
	ObjectLocator(Mat &img, vector<ElementType *> &elTypes, vector<Location *> &locs)
		: locations(locs), elementTypes(elTypes), findingImg(img)
	{
	}

	void findObjects()
	{
		int fwidth = findingImg.size().width; 
		int fheight = findingImg.size().height;

		for (int x = 0; x < fwidth; x++)
			for (int y = 0; y < fheight; y++)		
				for (vector<ElementType *>::iterator it = elementTypes.begin(); it < elementTypes.end(); it++)
				{
					ElementMatcher elo(**it, findingImg, x, y);
					if ((*it)->matchesSize(elo.size()))
						locations.push_back(new Location(x, y, (*it)->elementTypeName, (*it)->elementTypeState));
				}
	}

private:
	vector<Location *> &locations;
	vector<ElementType *> &elementTypes;
	Mat &findingImg;
};

static void onMouse( int event, int x, int y, int, void* mouse )
{
	if( event != EVENT_LBUTTONDOWN ) return;
	int *mouseData = (int *)mouse;
	mouseData[0] = 1;
	mouseData[1] = x;
	mouseData[2] = y;
}

int main( int argc, char** argv )
{
	vector<Location *> locations;
	vector<ElementType *> elementTypes;

    VideoCapture *cap = new VideoCapture(0);
	Mat image;

	//ElementType t1(82.0, 167.0, 0.3, 1.0, 76.0, 230.0, 100, 5000, "green", 1);
	ElementType t1(12.0, 347.0, 0.3, 1.0, 76.0, 230.0, 100, 5000, "green", 1);
	ElementType t2(250.0, 330.0, 0.3, 0.5, 100.0, 255.0, 100, 5000, "red", 1);

	elementTypes.push_back(&t2);

	if (!cap->isOpened())
	{
		cerr << "could not open video capture device.";
		return 0;
	}

	char *camera = "Camera - SPACE to capture the board state";
	namedWindow( camera, CV_WINDOW_AUTOSIZE );// Create a window for display.
	
	int mouseData[3];
	int &mouseClicked = mouseData[0];
	int &mouseX = mouseData[1];
	int &mouseY = mouseData[2];
	mouseClicked = 0;
	setMouseCallback(camera, onMouse, mouseData);

	if (!cap->read(image))
	{
		cerr << "could not read frame from camera.";
		return 0;
	}
	Mat image32(image.size().width, image.size().height, CV_32FC3);
	Mat hsv32(image.size().width, image.size().height, CV_32FC3);

	char key;

	do
	{
		if (!cap->read(image))
		{
			cerr << "could not read frame from camera.";
			return 0;
		}

		imshow( camera, image );

		key = waitKey(1);

		if (mouseClicked)
		{
			image.convertTo(image32, CV_32FC3);
			cvtColor(image32, hsv32, CV_BGR2HSV);
			cout << "hue=" << hsv32.at<cv::Vec3f>(mouseY,mouseX)[0] << " ";
			cout << "sat=" << hsv32.at<cv::Vec3f>(mouseY,mouseX)[1] << " ";
			cout << "val=" << hsv32.at<cv::Vec3f>(mouseY,mouseX)[2] << endl;
			mouseClicked = false;
			key = -1;
		}

		if (key == ' ')
		{
			image.convertTo(image32, CV_32FC3);
			cvtColor(image32, hsv32, CV_BGR2HSV);
			ObjectLocator ol(hsv32, elementTypes, locations);
			ol.findObjects();
			cout << "objects:" << endl;
			for (vector<Location *>::iterator it = locations.begin(); it < locations.end(); it++)
				cout << (*it)->elementType << "(" << (*it)->elementState << ") at " << (*it)->x << ", " << (*it)->y << endl;
			key = -1;
		}

	} while ( key == -1);
	
	delete cap;

    exit(0);
}