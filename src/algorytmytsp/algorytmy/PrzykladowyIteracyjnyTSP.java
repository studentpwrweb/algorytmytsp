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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Tomek
 */
public class PrzykladowyIteracyjnyTSP extends AlgorytmIteracyjnyTSP implements IAlgorytmTSP {

    private int nrIteracji = 0;

    @Override
    public List<Integer> rozwiazTSP(Graf graf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Integer> rozwiazTSPIteracyjnie(Graf graf) {
        
        nrIteracji = 0;
        
        LinkedList<Integer> sciezka = new LinkedList<Integer>();
        
        while (nrIteracji < graf.getLiczbaWierzcholkow()) {
            
            // Koloruje wierzcholek i wszystkie incydentne krawedzie
            mapaKolorow.kolorujWierzcholek(nrIteracji, KoloryElementow.ODWIEDZONY, true);
            
            sciezka.add(nrIteracji);
            
            // Koloruje sciezke (wierzcholki i krawedzie)
            mapaKolorow.kolorujSciezke(sciezka, KoloryElementow.WYROZNIONY);
            
            nrIteracji++;
  
            zakonczIteracje();
        }
        
        return sciezka;
    }
}
