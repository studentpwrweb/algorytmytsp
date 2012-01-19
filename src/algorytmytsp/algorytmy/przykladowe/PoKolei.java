/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.algorytmy.przykladowe;

import algorytmytsp.algorytmy.AlgorytmIteracyjnyTSP;
import algorytmytsp.algorytmy.IAlgorytmTSP;
import algorytmytsp.grafy.Graf;
import algorytmytsp.prezentacja.KoloryElementow;
import algorytmytsp.prezentacja.MapaKolorow;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tomek
 */
public class PoKolei extends AlgorytmIteracyjnyTSP implements IAlgorytmTSP {

    @Override
    public List<Integer> rozwiazTSP(Graf graf) {
        int wierzcholek = 0;

        LinkedList<Integer> sciezka = new LinkedList<Integer>();

        while (wierzcholek < graf.getRozmiar()) {

            sciezka.add(wierzcholek);

            wierzcholek++;
        }

        sciezka.add(0);

        return sciezka;
    }

    @Override
    public void rozwiazTSPIteracyjnie(Graf graf, MapaKolorow mapaKolorow) throws InterruptedException {
        int wierzcholek = 0;

        LinkedList<Integer> sciezka = new LinkedList<Integer>();

        mapaKolorow.wyczyscKolory();

        while (wierzcholek < graf.getRozmiar()) {

            sciezka.add(wierzcholek);

            // Koloruje wierzcholek i wszystkie incydentne krawedzie
            mapaKolorow.kolorujWierzcholek(wierzcholek, KoloryElementow.SZARY, true);

            // Koloruje sciezke (wierzcholki i krawedzie)
            mapaKolorow.kolorujSciezke(sciezka, KoloryElementow.CZERWONY);

            wierzcholek++;

            koniecIteracji();
        }
        
        // Zamknij cykl
        if (!sciezka.isEmpty()) {
            sciezka.add(sciezka.getFirst());
        }
        
        mapaKolorow.kolorujSciezke(sciezka, KoloryElementow.CZERWONY);

    }
}
