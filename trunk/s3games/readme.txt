S3Games - Software for S3 summer school of science 2013
s3.sci.hr, Pozega-Croatia, July/August 2013

S3 project title: The art to play games
Project leader: Zuzana Koysova, zuzycka@gmail.com
Technical support: Pavel Petrovic, ppetrovic@acm.org
Group members: Mislav Unger, Marko Cvijovic, Jan Corazza

Open-source project under MIT License (http://opensource.org/licenses/mit-license.php)
Project repository and documentation: https://code.google.com/p/s3games/
Implementation languages: Java (JDK 7) and C++ for the CameraBoard application
Development environment: Netbeans 7.2/7.3, MSVS C++ Express 10/12


Requirements:

* RXTX lib for communicating with the SSC-32 servo controller, not needed for simulated games
  download and docs: http://rxtx.qbang.org/wiki/index.php/Main_Page 
  we used version 2.1-7r2 (2006-10-29)
* pthreads library for WIN32: http://www.sourceware.org/pthreads-win32/ 
  needed for running and compiling the cameraBoard application 
  we used version 2.9.1 (2012-05-27)
* OpenCV for running and compiling the cameraBoard application (http://opencv.org/)
  we used version 2.46 (2013-07-03)


See also: 

* SSC-32 docs: http://www.lynxmotion.com/images/html/build136.htm
* Lynxmotion AL5D arm: http://www.lynxmotion.com/c-130-al5d.aspx


Notes for 64-bit platforms:

* RXTX is normally available only for 32-bit platform. If you are on a 64-bit platform,
  download JDK7 for 32-bit and use it to compile and run the program (see Manage platforms
  in Netbeans project settings)


If you like to contribute to the project, please contact us.
