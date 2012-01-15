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
public abstract class AlgorytmTSP implements IAlgorytmTSP {
    protected WatekAlgorytmu watek;
    protected MapaKolorow mapaKolorow;
    protected boolean iteracyjnie = false;
    
    @Override
    public abstract List<Integer> rozwiazTSP(Graf graf);
    
    public abstract List<Integer> rozwiazTSPIteracyjnie(Graf graf);
    
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
