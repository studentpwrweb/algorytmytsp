/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp;

import algorytmytsp.algorytmy.AlgorytmBB;
import algorytmytsp.algorytmy.IAlgorytmTSP;
import algorytmytsp.grafy.GeneratorGrafu;
import algorytmytsp.grafy.Graf;
import java.util.LinkedList;
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
        // Algorytm z którym będziemy porównywać rozwiązania (wyczerpujący) musi być pierwszy
        IAlgorytmTSP algorytmy[] = {new AlgorytmBB()};

        // Ustawienia początkowe
        int liczbaWierzcholkow = 10;
        boolean skierowanie = false;
        double pokrycie = 1.0; // Ta zmienna póki co nie ma wpływu na faktyczne pokrycie

        // Ustawienia zmian liczby wierzcholkow
        int stalyPrzyrost = 0;
        double wspolczynnikPrzyrostu = 1.5;

        // Ustawienia przetwarzania
        int liczbaIteracji = 10;

        // Generator grafu
        GeneratorGrafu generator = new GeneratorGrafu();

        // Alokacja pamięci
        int rozmiaryGrafow[] = new int[liczbaIteracji];
        long czasyObliczen[][] = new long[liczbaIteracji][algorytmy.length]; // nanosekundy
        double jakosciRozwiazan[][] = new double[liczbaIteracji][algorytmy.length]; // obliczone rozwiazanie / optymale rozwiazanie

        // Przetwarzanie
        for (int i = 0; i < liczbaIteracji; i++) {

            int rozmiar = (int) (liczbaWierzcholkow * Math.pow(wspolczynnikPrzyrostu, i)
                    + stalyPrzyrost * i);
            
            // Zapis obliczonego rozmiaru
            rozmiaryGrafow[i] = rozmiar;
            
            // Wypisywanie rozmiaru
            //System.out.print(rozmiar + '\t');

            Graf graf = generator.losowyGraf(rozmiar, skierowanie, pokrycie);

            double wagaReferencyjna = Double.POSITIVE_INFINITY;

            for (int j = 0; j < algorytmy.length; j++) {

                // Obliczenia dla i-tego algorytmu
                long poczatek = System.nanoTime();
                double waga = wagaSciezki(algorytmy[j].rozwiazTSP(graf), graf);
                long koniec = System.nanoTime();

                if (j == 0) {
                    wagaReferencyjna = waga;
                }

                // Zapis wynikow
                czasyObliczen[i][j] = koniec - poczatek;
                jakosciRozwiazan[i][j] = waga / wagaReferencyjna;
                
                // Wypisywanie wyników
                System.out.println(czasyObliczen[i][j]);
            }
        }

    }
    
    private static double wagaSciezki(List<Integer> sciezka, Graf graf) {
        double waga = 0;
        for (int i = 0; i < sciezka.size() - 1; i++) {
            waga += graf.getWagaKrawedzi(sciezka.get(i), sciezka.get(i + 1));
        }
        
        return waga;
    }
}
