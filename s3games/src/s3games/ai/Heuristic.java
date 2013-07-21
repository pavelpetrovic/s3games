/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.ai;

import s3games.engine.GameState;

/**
 *
 * @author Zuzka
 */
public abstract class Heuristic {
    public abstract double heuristic(GameState gameState);
}
