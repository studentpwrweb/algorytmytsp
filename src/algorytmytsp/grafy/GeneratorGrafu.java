/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.grafy;

import java.util.Random;

/**
 * Klasa udostępniająca narzędzia służące do generowania grafów.
 * @author Tomek
 */
public class GeneratorGrafu {
    
    private Random generator = new Random();
    
    public GrafDowolny losowyGraf(int rozmiar, boolean skierowanie, double pokrycie) {
       
        GrafDowolny graf = new GrafDowolny(rozmiar, skierowanie);

        for (int i = 0; i < graf.getRozmiar(); i++) {
            for (int j = 0; j < (skierowanie ? graf.getRozmiar() : i); j++) {
               graf.ustawWageKrawedzi(i, j, generator.nextDouble());
            }
            
            graf.usunKrawedz(i, i);
        }
        
        return graf;
    }
    
    public GrafDowolny losowyGraf(int rozmiar, boolean skierowanie) {
        return losowyGraf(rozmiar, skierowanie, 1.0);
    }
    
    public GrafDowolny losowyGraf(int rozmiar) {
        return losowyGraf(rozmiar, false);
    }
    
    public GrafXY losowyGrafXY(int rozmiar) {
        
        GrafXY graf = new GrafXY(rozmiar);
        
        for (int i = 0; i < graf.getRozmiar(); i++) {
            graf.ustawXYWierzcholka(i, generator.nextDouble(), generator.nextDouble(), false);
        }
        
        graf.aktualizujWagi();
        
        return graf;
    }

}
