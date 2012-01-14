/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.prezentacja;

import algorytmytsp.grafy.GrafXY;
import java.util.List;

/**
 *
 * @author Tomek
 */
public class MapaKolorow {

    private KoloryElementow koloryWierzcholkow[];
    private KoloryElementow koloryKrawedzi[][];
    private GrafXY graf;

    public MapaKolorow(GrafXY graf) {
        this.graf = graf;

        if (this.graf != null) {
            int n = graf.getLiczbaWierzcholkow();
            koloryWierzcholkow = new KoloryElementow[n];
            koloryKrawedzi = new KoloryElementow[n][n];

            wyczyscKolory();
        }
    }
    
    final public void wyczyscKolory() {
        kolorujGraf(KoloryElementow.DOMYSLNY);
    }

    final public void kolorujGraf(KoloryElementow kolor) {
        for (int i = 0; i < graf.getLiczbaWierzcholkow(); i++) {
            kolorujWierzcholek(i, kolor);
        }

        for (int i = 0; i < graf.getLiczbaWierzcholkow(); i++) {
            for (int j = 0; j < i; j++) {
                kolorujKrawedz(i, j, kolor);
            }
            
            kolorujKrawedz(i, i, kolor);
        }
    }

    public void kolorujKrawedz(int wierzcholek1, int wierzcholek2, KoloryElementow kolor, boolean kolorujWierzcholki) {
        koloryKrawedzi[wierzcholek1][wierzcholek2] = kolor;

        if (!graf.czyJestSkierowany()) {
            koloryKrawedzi[wierzcholek2][wierzcholek1] = kolor;
        }

        if (kolorujWierzcholki) {
            kolorujWierzcholek(wierzcholek1, kolor, false);
            kolorujWierzcholek(wierzcholek2, kolor, false);
        }
    }

    public void kolorujKrawedz(int wierzcholek1, int wierzcholek2, KoloryElementow kolor) {
        kolorujKrawedz(wierzcholek1, wierzcholek2, kolor, false);
    }

    public void kolorujWierzcholek(int wierzcholek, KoloryElementow kolor, boolean kolorujKrawedzie) {
        koloryWierzcholkow[wierzcholek] = kolor;

        if (kolorujKrawedzie) {
            for (int i = 0; i < graf.getLiczbaWierzcholkow(); i++) {
                kolorujKrawedz(wierzcholek, i, kolor, false);
            }
        }
    }

    public void kolorujWierzcholek(int wierzcholek, KoloryElementow kolor) {
        kolorujWierzcholek(wierzcholek, kolor, false);
    }

    public void kolorujSciezke(List<Integer> sciezka, KoloryElementow kolor) {
        for (int i = 0; i < sciezka.size() - 1; i++) {
            kolorujKrawedz(sciezka.get(i), sciezka.get(i + 1), kolor, true);
        }
        kolorujWierzcholek(sciezka.get(sciezka.size() - 1), kolor, false);
    }

    public KoloryElementow getKolorWierzcholka(int wierzcholek) {
        return koloryWierzcholkow[wierzcholek];
    }

    public KoloryElementow getKolorKrawedzi(int wierzcholek1, int wierzcholek2) {
        return koloryKrawedzi[wierzcholek1][wierzcholek2];
    }
 
    public GrafXY getGraf() {
        return graf;
    }
}
