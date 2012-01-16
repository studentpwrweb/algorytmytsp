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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Tomek
 */
public class LosowyTSP extends AlgorytmIteracyjnyTSP implements IAlgorytmTSP {

    public LosowyTSP(WatekAlgorytmu watek, MapaKolorow mapa) {
        super(watek, mapa);
    }

    public LosowyTSP() {
        super(null, null);
    }
    private boolean koloruj;
    private LinkedList<Integer> sciezka;

    @Override
    public List<Integer> rozwiazTSP(Graf graf) {

        koloruj = false;

        try {
            rozwiaz(graf);
        } catch (InterruptedException ex) {
            System.out.println("Nieoczekiwane przerwanie");
        }

        return sciezka;
    }

    private void rozwiaz(Graf graf) throws InterruptedException {
        Random generator = new Random();

        sciezka = new LinkedList<Integer>();

        ArrayList<Integer> wierzcholki = new ArrayList<Integer>();
        for (int i = 0; i < graf.getLiczbaWierzcholkow(); i++) {
            wierzcholki.add(i);
        }

        if (koloruj) {
            mapaKolorow.wyczyscKolory();
        }

        while (sciezka.size() < graf.getLiczbaWierzcholkow()) {

            int indeksWierzcholka = generator.nextInt(wierzcholki.size());
            int wierzcholek = wierzcholki.remove(indeksWierzcholka);

            sciezka.add(wierzcholek);

            if (koloruj) {
                mapaKolorow.kolorujSciezke(sciezka, KoloryElementow.WYROZNIONY1);
                mapaKolorow.kolorujWierzcholek(wierzcholek, KoloryElementow.ANALIZOWANY);
            }

            koniecIteracji();
        }

        sciezka.add(sciezka.getFirst());
        mapaKolorow.kolorujSciezke(sciezka, KoloryElementow.WYROZNIONY1);
    }

    @Override
    public void rozwiazTSPIteracyjnie(Graf graf) throws InterruptedException {

        if (mapaKolorow != null) {
            koloruj = true;
        }

        rozwiaz(graf);
    }
}
