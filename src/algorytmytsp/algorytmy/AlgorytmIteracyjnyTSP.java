/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.algorytmy;

import algorytmytsp.grafy.Graf;
import algorytmytsp.grafy.GrafXY;
import algorytmytsp.prezentacja.MapaKolorow;
import algorytmytsp.prezentacja.WatekAlgorytmu;
import java.util.List;

/**
 *
 * @author Tomek
 */
public abstract class AlgorytmIteracyjnyTSP {
    protected WatekAlgorytmu watek;
    protected MapaKolorow mapaKolorow;
    
    public abstract void rozwiazTSPIteracyjnie(Graf graf) throws InterruptedException;
    
    protected void koniecIteracji() throws InterruptedException {
        if (watek != null) {
            watek.koniecIteracji();
        }
    }

    public void setMapaKolorow(MapaKolorow mapaKolorow) {
        this.mapaKolorow = mapaKolorow;
    }

    public void setWatek(WatekAlgorytmu watek) {
        this.watek = watek;
    }
    
}
