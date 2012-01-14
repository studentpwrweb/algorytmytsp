/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.algorytmy;

import algorytmytsp.grafy.Graf;
import algorytmytsp.grafy.GrafXY;
import algorytmytsp.prezentacja.KoloryElementow;
import algorytmytsp.prezentacja.MapaKolorow;
import algorytmytsp.prezentacja.WatekAlgorytmu;

/**
 *
 * @author Tomek
 */
public class PrzykladowyTSP extends AlgorytmTSP {

    private int nrIteracji = 0;

    @Override
    public void rozwiazTSP(Graf graf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void rozwiazTSPIteracyjnie(Graf graf) {
        
        nrIteracji = 0;
        
        while (nrIteracji < graf.getLiczbaWierzcholkow()) {
            
            // Koloruje wierzcholek i wszystkie incydentne krawedzie
            mapaKolorow.kolorujWierzcholek(nrIteracji, KoloryElementow.ODWIEDZONY, true);
            
            // Koloruje tylko wierzcholek
            mapaKolorow.kolorujWierzcholek(nrIteracji, KoloryElementow.ANALIZOWANY);
            
            if (nrIteracji > 0) {
                // Koloruje tylko wierzcholek
                mapaKolorow.kolorujWierzcholek(nrIteracji - 1, KoloryElementow.ODWIEDZONY);
            }
            
            nrIteracji++;
  
            zakonczIteracje();
        }
    }
}
