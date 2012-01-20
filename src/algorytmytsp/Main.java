/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp;

import algorytmytsp.algorytmy.*;
import algorytmytsp.grafy.GeneratorGrafu;
import algorytmytsp.grafy.Graf;
import java.util.List;

/**
 *
 * @author student
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        // Algorytmy, które zostaną wykonane
        // Algorytm z którym będziemy porównywać rozwiązania musi być pierwszy
        IAlgorytmTSP algorytmy[] = {new Zachlanny(), new TwiceAroundTree(), new BranchNBound()};

        // Ustawienia początkowe
        int liczbaWierzcholkow = 100;

        // Ustawienia zmian liczby wierzcholkow
        int stalyPrzyrost = 10;
        double wspolczynnikPrzyrostu = 1.0;

        // Ustawienia przetwarzania
        int liczbaIteracji = 100;

        // Generator grafu
        GeneratorGrafu generator = new GeneratorGrafu();

        // Alokacja pamięci
        int rozmiaryGrafow[] = new int[liczbaIteracji];
        long czasyObliczen[][] = new long[liczbaIteracji][algorytmy.length]; // nanosekundy
        double jakoscRozwiazan[][] = new double[liczbaIteracji][algorytmy.length]; // waga / waga referencyjna

        // Przetwarzanie
        for (int i = 0; i < liczbaIteracji; i++) {

            int rozmiar = (int) (liczbaWierzcholkow * Math.pow(wspolczynnikPrzyrostu, i)
                    + stalyPrzyrost * i);

            // Zapis obliczonego rozmiaru
            rozmiaryGrafow[i] = rozmiar;

            // Wypisywanie rozmiaru
            System.out.print(rozmiar);

            Graf graf = generator.losowyGrafXY(rozmiar);

            double wagaReferencyjna = Double.POSITIVE_INFINITY;

            for (int j = 0; j < algorytmy.length; j++) {

                // Obliczenia dla i-tego algorytmu
                long poczatek = System.nanoTime();
                List<Integer> rozwiazanie = algorytmy[j].rozwiazTSP(graf);
                long koniec = System.nanoTime();

                double waga = wagaSciezki(rozwiazanie, graf);

                if (j == 0) {
                    wagaReferencyjna = waga;
                }

                // Zapis wynikow
                czasyObliczen[i][j] = koniec - poczatek;
                jakoscRozwiazan[i][j] = waga / wagaReferencyjna;

                // Wypisywanie wyników
                System.out.print('\t' + Long.toString(czasyObliczen[i][j]));
                System.out.print('\t' + Double.toString(jakoscRozwiazan[i][j]));
            }

            System.out.println();
        }

    }

    private static double wagaSciezki(List<Integer> sciezka, Graf graf) {
        double waga = 0;
        for (int i = 0; i < sciezka.size() - 1; i++) {
            waga += graf.wagaKrawedzi(sciezka.get(i), sciezka.get(i + 1));
        }

        return waga;
    }
}
