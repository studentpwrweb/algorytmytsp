/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.grafy;

/**
 *
 * @author Tomek
 */
public class GrafXY extends Graf {
    
    private double wspolrzedneX[];
    private double wspolrzedneY[];

    public GrafXY(int rozmiar) {
        super(rozmiar, false);

        wspolrzedneX = new double[getRozmiar()];
        wspolrzedneY = new double[getRozmiar()];
    }
    
    public GrafXY(GrafXY grafWzorcowy, double x, double y) {
        this(grafWzorcowy.getRozmiar() + 1);
        
        for (int i = 0; i < grafWzorcowy.getRozmiar(); i++) {
            ustawXYWierzcholka(i, grafWzorcowy.xWierzcholka(i), grafWzorcowy.yWierzcholka(i), false);
        }

        for (int i = 0; i < grafWzorcowy.getRozmiar(); i++) {
            for (int j = 0; j < i; j++) {
                ustawWageKrawedzi(i, j, grafWzorcowy.wagaKrawedzi(i, j));
            }
        }
        
        ustawXYWierzcholka(getRozmiar() - 1, x, y, true);
        
    }

    public final void ustawXYWierzcholka(int w, double x, double y, boolean aktualizujWagi) {

        wspolrzedneX[w] = x;
        wspolrzedneY[w] = y;

        if (aktualizujWagi) {
            for (int i = 0; i < getRozmiar(); i++) {    
                ustawWageKrawedzi(w, i, obliczWage(w, i));
            }
        }
    }

    public void ustawXYWierzcholka(int w, double x, double y) {
        ustawXYWierzcholka(w, x, y, true);
    }

    public double xWierzcholka(int w) {
        return wspolrzedneX[w];
    }

    public double yWierzcholka(int w) {
        return wspolrzedneY[w];
    }

    public void aktualizujWagi() {

        for (int i = 0; i < getRozmiar(); i++) {
            for (int j = 0; j < i; j++) {
                ustawWageKrawedzi(i, j, obliczWage(i, j));
            }
        }
    }

    private double obliczWage(int w1, int w2) {
        if (w1 == w2) {
            return Double.POSITIVE_INFINITY;
        } else {
            double dx = wspolrzedneX[w1] - wspolrzedneX[w2];
            double dy = wspolrzedneY[w1] - wspolrzedneY[w2];

            return Math.sqrt(dy * dy + dx * dx);
        }
    }
}
