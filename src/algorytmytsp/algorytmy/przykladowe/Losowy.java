/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.algorytmy.przykladowe;

import algorytmytsp.algorytmy.AlgorytmIteracyjnyTSP;
import algorytmytsp.algorytmy.IAlgorytmTSP;
import algorytmytsp.algorytmy.Zachlanny;
import algorytmytsp.grafy.Graf;
import algorytmytsp.prezentacja.KoloryElementow;
import algorytmytsp.prezentacja.MapaKolorow;
import algorytmytsp.prezentacja.WatekAlgorytmu;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomek
 */
public class Losowy extends AlgorytmIteracyjnyTSP implements IAlgorytmTSP {

    private boolean koloruj;

    @Override
    public List<Integer> rozwiazTSP(Graf graf) {

        koloruj = false;
        
        List<Integer> sciezka = null;

        try {
             sciezka = rozwiaz(graf, null);
        } catch (InterruptedException ex) {
            Logger.getLogger(Zachlanny.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sciezka;
    }

    private List<Integer> rozwiaz(Graf graf, MapaKolorow mapaKolorow) throws InterruptedException {
        Random generator = new Random();

        LinkedList<Integer> sciezka = new LinkedList<Integer>();

        ArrayList<Integer> wierzcholki = new ArrayList<Integer>();
        for (int i = 0; i < graf.getRozmiar(); i++) {
            wierzcholki.add(i);
        }

        if (koloruj) {
            mapaKolorow.wyczyscKolory();
        }

        while (sciezka.size() < graf.getRozmiar()) {

            int indeksWierzcholka = generator.nextInt(wierzcholki.size());
            int wierzcholek = wierzcholki.remove(indeksWierzcholka);

            sciezka.add(wierzcholek);

            if (koloruj) {
                mapaKolorow.kolorujSciezke(sciezka, KoloryElementow.CZERWONY);
                mapaKolorow.kolorujWierzcholek(wierzcholek, KoloryElementow.ZIELONY);
                
                koniecIteracji();
            } 
        }

        // Zamknij cykl
        if (!sciezka.isEmpty()) {
            sciezka.add(sciezka.getFirst());
        }

        if (koloruj) {
            mapaKolorow.kolorujSciezke(sciezka, KoloryElementow.CZERWONY);
        }

        return sciezka;
    }

    @Override
    public void rozwiazTSPIteracyjnie(Graf graf, MapaKolorow mapaKolorow) throws InterruptedException {

        if (mapaKolorow != null) {
            koloruj = true;
        }

        rozwiaz(graf, mapaKolorow);
    }
}
