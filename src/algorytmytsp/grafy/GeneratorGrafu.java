/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.grafy;

import algorytmytsp.grafy.GrafDowolny;
import algorytmytsp.grafy.GrafXY;
import java.util.Random;

/**
 * Klasa udostępniająca narzędzia służące do generowania grafów.
 * @author Tomek
 */
public class GeneratorGrafu {
    
    private Random generator = new Random();
    
    public GrafDowolny losowyGraf(int liczbaWierzcholkow, boolean skierowanie, double pokrycie) {
       
        GrafDowolny graf = new GrafDowolny(liczbaWierzcholkow, skierowanie);

        for (int i = 0; i < liczbaWierzcholkow; i++) {
            for (int j = 0; j < (skierowanie ? liczbaWierzcholkow : i); j++) {
               graf.ustawWageKrawedzi(i, j, generator.nextDouble());
            }
            
            graf.usunKrawedz(i, i);
        }
        
        return graf;
    }
    
    public void losowyGraf(int liczbaWierzcholkow, boolean skierowanie) {
        losowyGraf(liczbaWierzcholkow, skierowanie, 1.0);
    }
    
    public GrafXY losowyGrafXY(int liczbaWierzcholkow) {
        
        GrafXY graf = new GrafXY(liczbaWierzcholkow);
        
        for (int i = 0; i < graf.getRozmiar(); i++) {
            graf.ustawXYWierzcholka(i, generator.nextDouble(), generator.nextDouble(), false);
        }
        
        graf.aktualizujWagi();
        
        return graf;
    }

}
