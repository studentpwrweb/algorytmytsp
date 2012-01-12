/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.algorytmy;

import algorytmytsp.grafy.Graf;
import java.util.List;

/**
 *
 * @author Tomek
 */
public interface IAlgorytmTSP {
    public List<Integer> rozwiazTSP(Graf graf);
}
