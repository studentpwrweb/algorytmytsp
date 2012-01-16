/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.grafy;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tomek
 */
public class GrafDowolny extends Graf {
    public GrafDowolny(int rozmiar, boolean skierowany) {
        super(rozmiar, skierowany);
    }
    
    public GrafDowolny(int rozmiar) {
        this(rozmiar, false);
    }
    
    @Override
    public void ustawWageKrawedzi(int w1, int w2, double waga) {
        super.ustawWageKrawedzi(w1, w2, waga);
    }
    
    public void usunKrawedz(int w1, int w2) {
        ustawWageKrawedzi(w1, w2, Double.POSITIVE_INFINITY);
    }
}
