package algorytmytsp.algorytmy;

import algorytmytsp.grafy.Graf;
import algorytmytsp.prezentacja.KoloryElementow;
import algorytmytsp.prezentacja.MapaKolorow;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Zachlanny extends AlgorytmIteracyjnyTSP implements IAlgorytmTSP {

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

    public List<Integer> rozwiaz(Graf graf, MapaKolorow mapaKolorow) throws InterruptedException {


        double[][] macierz = graf.getMacierzKosztow();

        // Waga najkrótszej ścieżki
        double min = Double.POSITIVE_INFINITY;

        // Najkrótsza znaleziona do tej pory ścieżka robocza
        LinkedList<Integer> najkrotszaSciezka = null;

        // r to wierzchołek od którego zaczynamy
        for (int r = 0; r < macierz.length; r++) {

            if (koloruj) {
                mapaKolorow.wyczyscKolory();
                mapaKolorow.kolorujWierzcholek(r, KoloryElementow.NIEBIESKI);  
                koniecIteracji();
            }

            // Waga ścieżki roboczej
            double dlugosc = 0;

            // Ścieżka robocza
            LinkedList<Integer> sciezka = new LinkedList<Integer>();

            // Informuje o tym czy wierzchołek znajduje się już na ścieżce
            boolean naSciezce[] = new boolean[macierz.length];

            // Dodaj wierzchołek początkowy do ścieżki roboczej
            sciezka.add(r);
            naSciezce[r] = true;

            for (int x = 0; x < macierz.length - 1; x++) {

                int najblizszy = -1; // Wierzchołek, do którego mamy najbliżej
                int ostatni = sciezka.getLast(); // Ostatni wierzchołek ze ścieżki roboczej

                // Odległość między ostatnim a najbliższym wierzchołkiem
                double km = Double.POSITIVE_INFINITY;

                for (int y = 0; y < macierz[ostatni].length; y++) {

                    // Jeśli na ścieżce nie ma wierzchołka y
                    // i odległość do y jest mniejsza od km...
                    if (!naSciezce[y] && macierz[ostatni][y] < km) {

                        // ...to znaczy, że y jest najbliższym znalezionym do tej pory wierzchołkiem
                        najblizszy = y;
                        km = macierz[ostatni][y];
                    }
                }

                // Jeśli z wierzchołka ostatni nie można było się nigdzie dostać, 
                // przrwij pętle i zacznij od innego wierzchołka początkowego r ...
                if (najblizszy == -1) {
                    break;
                } else {

                    // .. w przeciwnym wypadku, dodaj najbliższy wierzchołek do ścieżki
                    sciezka.add(najblizszy);
                    naSciezce[najblizszy] = true;
                    dlugosc += km;

                    if (koloruj) {
                        mapaKolorow.kolorujKrawedz(ostatni, najblizszy, KoloryElementow.ZIELONY, true);
                        mapaKolorow.kolorujWierzcholek(najblizszy, KoloryElementow.NIEBIESKI);
                        koniecIteracji();
                    }

                }
            }

            // Dodawanie wierzchołka r do ścieżki aby zamknąć cykl,
            // pod warunkiem, że na ścieżce znajdują się już wszystkie wierzchołki
            if (sciezka.size() == macierz.length
                    && graf.istnienieKrawedzi(sciezka.getLast(), r)) {
                dlugosc += macierz[sciezka.getLast()][r];
                sciezka.add(r);

                if (koloruj) {
                    mapaKolorow.kolorujSciezke(sciezka, KoloryElementow.CZERWONY);
                    mapaKolorow.kolorujWierzcholek(r, KoloryElementow.NIEBIESKI);
                    koniecIteracji();
                }

            } else {
                // Jeśli nie istnieje ostatnia krawędź przerwij pętle
                continue;
            }

            if (dlugosc < min) {
                najkrotszaSciezka = sciezka;
                min = dlugosc;
            }

        }

        if (koloruj) {
            mapaKolorow.kolorujSciezke(najkrotszaSciezka, KoloryElementow.CZERWONY);
            koniecIteracji();
        }

        return najkrotszaSciezka;
    }

    @Override
    public void rozwiazTSPIteracyjnie(Graf graf, MapaKolorow mapaKolorow) throws InterruptedException {

        if (mapaKolorow != null) {
            koloruj = true;
        }

        rozwiaz(graf, mapaKolorow);
    }
}
