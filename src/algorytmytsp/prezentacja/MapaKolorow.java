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

    /**
     * Tworzy mapę kolorów dla grafu, ustawiając kolor wszystkich wierzchołków
     * i krawędzi na domyślny.
     * @param graf graf, na podstawie którego zostanie utworzona mapa kolorów
     */
    public MapaKolorow(GrafXY graf) {
        this.graf = graf;

        if (this.graf != null) {
            int n = graf.getRozmiar();
            koloryWierzcholkow = new KoloryElementow[n];
            koloryKrawedzi = new KoloryElementow[n][n];

            wyczyscKolory();
        }
    }
    
    /**
     * Ustawia kolor wszystkich krawędzi i wierzchołków na domyślny.
     */
    final public void wyczyscKolory() {
        kolorujGraf(KoloryElementow.DOMYSLNY);
    }
    
    /**
     * Ustawia kolor wszystkich krawędzi i wierzchołków na wybrany.
     * @param kolor kolor na jaki mają być pokolorowane wierzchołki i krawędzie
     */
    public void kolorujGraf(KoloryElementow kolor) {
        for (int i = 0; i < graf.getRozmiar(); i++) {
            kolorujWierzcholek(i, kolor);
        }

        for (int i = 0; i < graf.getRozmiar(); i++) {
            for (int j = 0; j < i; j++) {
                kolorujKrawedz(i, j, kolor);
            }
            
            kolorujKrawedz(i, i, kolor);
        }
    }
    
    /**
     * Ustawia kolor krawędzi (w1, w2) na wybrany.
     * @param w1 wierzchołek wyjściowy
     * @param w2 wierzchołek wejściowy
     * @param kolor kolor krawędzi
     * @param kolorujWierzcholki true - koloruj wierzchołki w1 i w2, false - w przeciwnym wypadku
     */
    public void kolorujKrawedz(int w1, int w2, KoloryElementow kolor, boolean kolorujWierzcholki) {
        koloryKrawedzi[w1][w2] = kolor;

        if (!graf.isSkierowany()) {
            koloryKrawedzi[w2][w1] = kolor;
        }

        if (kolorujWierzcholki) {
            kolorujWierzcholek(w1, kolor, false);
            kolorujWierzcholek(w2, kolor, false);
        }
    }

    /**
     * Ustawia kolor krawędzi (w1, w2) na wybrany.
     * @param w1 wierzchołek wyjściowy
     * @param w2 wierzchołek wejściowy
     * @param kolor kolor krawędzi
     */
    public void kolorujKrawedz(int w1, int w2, KoloryElementow kolor) {
        kolorujKrawedz(w1, w2, kolor, false);
    }

    /**
     * Ustawia kolor wierzchołka w na wybrany.
     * @param w wierzchołek 
     * @param kolor wybrany kolor
     * @param kolorujKrawedzie true - koloruj wszystkie incydentne krawędzie, false - w przeciwnym wypadku
     */
    public void kolorujWierzcholek(int w, KoloryElementow kolor, boolean kolorujKrawedzie) {
        koloryWierzcholkow[w] = kolor;

        if (kolorujKrawedzie) {
            for (int i = 0; i < graf.getRozmiar(); i++) {
                kolorujKrawedz(w, i, kolor, false);
            }
        }
    }

    /**
     * Ustawia kolor wierzchołka w na wybrany.
     * @param w wierzchołek 
     * @param kolor wybrany kolor
     */
    public void kolorujWierzcholek(int w, KoloryElementow kolor) {
        kolorujWierzcholek(w, kolor, false);
    }

    /**
     * Ustawia kolor wszystkich wierzchołków i krawędzi wchodzących w skład ścieżki na wybrany.
     * @param sciezka scieżka (lista wierzchołków)
     * @param kolor wybrany kolor
     */
    public void kolorujSciezke(List<Integer> sciezka, KoloryElementow kolor) {
        if (sciezka.isEmpty()) {
            return;
        }
        
        for (int i = 0; i < sciezka.size() - 1; i++) {
            kolorujKrawedz(sciezka.get(i), sciezka.get(i + 1), kolor, true);
        }
        kolorujWierzcholek(sciezka.get(sciezka.size() - 1), kolor, false);
    }

    /**
     * Zwraca kolor wierzchołka w.
     * @param w wierzchołek
     * @return kolor wierzchołka w
     */
    public KoloryElementow getKolorWierzcholka(int w) {
        return koloryWierzcholkow[w];
    }

    /**
     * Zwraca kolor krawędzi (w1, w2).
     * @param w1 wierzchołek wyjściowy
     * @param w2 wierzchołek wejściowy
     * @return kolor krawędzi (w1, w2)
     */
    public KoloryElementow getKolorKrawedzi(int w1, int w2) {
        return koloryKrawedzi[w1][w2];
    }
 
    /**
     * Zwraca graf powiązany z tą mapą kolorów.
     * @return graf powiązany z tą mapą kolorów
     */
    public GrafXY getGraf() {
        return graf;
    }
}
