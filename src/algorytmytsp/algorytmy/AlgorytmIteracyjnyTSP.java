/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.algorytmy;

import algorytmytsp.grafy.Graf;
import algorytmytsp.prezentacja.MapaKolorow;
import algorytmytsp.prezentacja.WatekAlgorytmu;

/**
 *
 * @author Tomek
 */
public abstract class AlgorytmIteracyjnyTSP {
    protected WatekAlgorytmu watek;
    
    public abstract void rozwiazTSPIteracyjnie(Graf graf, MapaKolorow mapaKolorow) throws InterruptedException;
    
    protected void koniecIteracji() throws InterruptedException {
        if (watek != null) {
            watek.koniecIteracji();
        }
    }

    public void setWatek(WatekAlgorytmu watek) {
        this.watek = watek;
    }
    
}
