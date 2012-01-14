/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.algorytmy;

import algorytmytsp.grafy.Graf;
import algorytmytsp.grafy.GrafXY;
import algorytmytsp.prezentacja.MapaKolorow;
import algorytmytsp.prezentacja.WatekAlgorytmu;

/**
 *
 * @author Tomek
 */
public abstract class AlgorytmTSP {
    protected WatekAlgorytmu watek;
    protected MapaKolorow mapaKolorow;
    protected boolean iteracyjnie = false;
    
    public abstract void rozwiazTSP(Graf graf);
    
    public abstract void rozwiazTSPIteracyjnie(Graf graf);
    
    protected void zakonczIteracje() {
        if (iteracyjnie && watek != null) {
            watek.koniecIteracji();
        }
    }
    
    public void setIteracyjnie(boolean iteracyjnie) {
        this.iteracyjnie = iteracyjnie;
    }

    public void setMapaKolorow(MapaKolorow mapaKolorow) {
        this.mapaKolorow = mapaKolorow;
        this.mapaKolorow.wyczyscKolory();
    }

    public void setWatek(WatekAlgorytmu watek) {
        this.watek = watek;
    }
    
}
