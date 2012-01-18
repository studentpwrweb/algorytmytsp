/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.algorytmy;

import algorytmytsp.grafy.Graf;
import algorytmytsp.prezentacja.KoloryElementow;
import algorytmytsp.prezentacja.MapaKolorow;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SylwiaBłach
 */
public class BruteForce extends AlgorytmIteracyjnyTSP implements IAlgorytmTSP {

    private Graf graf;
    private LinkedList<Integer> kolejneWierzcholki;
    private LinkedList<Integer> kolejneWierzcholkiBiezaca;
    private double wartoscWag;
    private double wartoscWagBiezaca;
    private boolean[] odwiedzone;
    private boolean koloruj;
    private MapaKolorow mapaKolorow;

    @Override
    public List<Integer> rozwiazTSP(Graf graf) {
        koloruj = false;

        List<Integer> sciezka = null;

        try {
            sciezka = rozwiaz(graf);
        } catch (InterruptedException ex) {
            Logger.getLogger(BruteForce.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sciezka;
    }

    public List<Integer> rozwiaz(Graf graf) throws InterruptedException {

        this.graf = graf;

        kolejneWierzcholki = new LinkedList<Integer>();
        kolejneWierzcholkiBiezaca = new LinkedList<Integer>();

        wartoscWag = Integer.MAX_VALUE;
        wartoscWagBiezaca = 0;
        odwiedzone = new boolean[graf.getRozmiar()];

        brutal(0);

        return kolejneWierzcholki;
    }

    private void brutal(int v) throws InterruptedException {

        kolejneWierzcholkiBiezaca.add(v); // Dodaj wierzchołek v do bieżącej listy wierzchołków

        if (kolejneWierzcholkiBiezaca.size() == graf.getRozmiar()) {

            // Sprawdź czy istnieje krawędź (0, v), jeśli istnieje, to istnieje cykl
            if (graf.istnienieKrawedzi(v, 0)) {

                // Sprawdź czy znaleziony cykl, jest najkrótszym ze znalezionych do tej pory
                if (wartoscWagBiezaca + graf.wagaKrawedzi(v, 0) < wartoscWag) {

                    wartoscWag = wartoscWagBiezaca + graf.wagaKrawedzi(v, 0);
                    kolejneWierzcholki = (LinkedList) kolejneWierzcholkiBiezaca.clone();
                    kolejneWierzcholki.add(0);
                }
                
            }

        } else {
            odwiedzone[v] = true;
            // Tutaj zablokuj wierzchołek przypisując true w tablicy odwiedzonych

            for (int s : graf.sasiedzi(v)) {

                // Sprawdź czy wierzchołek był juz odwiedzony
                if (odwiedzone[s]) {
                    continue; // Przejdź od razu do kolejnej iteracji pętli
                }
                wartoscWagBiezaca += graf.wagaKrawedzi(v, s);  
                
                brutal(s);
                wartoscWagBiezaca -= graf.wagaKrawedzi(v, s);
                // Tutaj będzie m.in. rekurencyjne wywołanie funkcji brutal
                
            }
            odwiedzone[v] = false;
            // Tutaj Odblokuj wierzchołek
        }

        //   Usuń wierzchołek v z bieżącej listy wierzchołków
        kolejneWierzcholkiBiezaca.removeLast();
    }

    @Override
    public void rozwiazTSPIteracyjnie(Graf graf, MapaKolorow mapaKolorow) throws InterruptedException {
        if (mapaKolorow != null) {
            koloruj = true;
        }

        this.mapaKolorow = mapaKolorow;

        rozwiaz(graf);
    }
}
