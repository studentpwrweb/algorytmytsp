/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.algorytmy;

import algorytmytsp.grafy.Graf;
import java.util.LinkedList;

/**
 *
 * @author Tomek
 */
public interface IAlgorytmTSP {
    public LinkedList<Integer> rozwiazTSP(Graf graf);
}
