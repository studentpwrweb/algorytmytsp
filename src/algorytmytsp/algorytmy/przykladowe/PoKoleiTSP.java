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
import algorytmytsp.prezentacja.WatekAlgorytmu;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tomek
 */
public class PoKoleiTSP extends AlgorytmIteracyjnyTSP implements IAlgorytmTSP {

    public PoKoleiTSP(WatekAlgorytmu watek, MapaKolorow mapa) {
        super(watek, mapa);
    }

    public PoKoleiTSP() {
        super(null, null);
    }

    @Override
    public List<Integer> rozwiazTSP(Graf graf) {
        int wierzcholek = 0;

        LinkedList<Integer> sciezka = new LinkedList<Integer>();

        while (wierzcholek < graf.getLiczbaWierzcholkow()) {

            sciezka.add(wierzcholek);

            wierzcholek++;
        }

        sciezka.add(0);

        return sciezka;
    }

    @Override
    public void rozwiazTSPIteracyjnie(Graf graf) throws InterruptedException {
        int wierzcholek = 0;

        LinkedList<Integer> sciezka = new LinkedList<Integer>();
        
        mapaKolorow.wyczyscKolory();

        while (wierzcholek < graf.getLiczbaWierzcholkow()) {

            sciezka.add(wierzcholek);

            // Koloruje wierzcholek i wszystkie incydentne krawedzie
            mapaKolorow.kolorujWierzcholek(wierzcholek, KoloryElementow.ODWIEDZONY, true);

            // Koloruje sciezke (wierzcholki i krawedzie)
            mapaKolorow.kolorujSciezke(sciezka, KoloryElementow.WYROZNIONY1);

            wierzcholek++;

            koniecIteracji();
        }
        
        sciezka.add(0);
        mapaKolorow.kolorujSciezke(sciezka, KoloryElementow.WYROZNIONY1);
        
    }
}
