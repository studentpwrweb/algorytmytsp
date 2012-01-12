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

    public GrafXY(int liczbaWierzcholkow) {

        super(liczbaWierzcholkow, false);

        wspolrzedneX = new double[liczbaWierzcholkow];
        wspolrzedneY = new double[liczbaWierzcholkow];
    }

    public GrafXY(GrafXY grafWzorcowy) {
        super(grafWzorcowy);

        wspolrzedneX = grafWzorcowy.wspolrzedneX.clone();
        wspolrzedneY = grafWzorcowy.wspolrzedneY.clone();
    }

    public void setWspolrzedneWierzcholka(int wierzcholek, double wspolrzednaX, double wspolrzednaY, boolean aktualizujWagi) {

        wspolrzedneX[wierzcholek] = wspolrzednaX;
        wspolrzedneY[wierzcholek] = wspolrzednaY;

        if (aktualizujWagi) {
            for (int i = 0; i < liczbaWierzcholkow; i++) {
                macierzKosztow[wierzcholek][i] = obliczWageKrawedzi(wierzcholek, i);
                macierzKosztow[i][wierzcholek] = macierzKosztow[wierzcholek][i];
            }
        }
    }

    public void setWspolrzedneWierzcholka(int wierzcholek, double x, double y) {
        setWspolrzedneWierzcholka(wierzcholek, x, y, true);
    }
    
    public double getWspolrzednaXWierzcholka(int wierzcholek) {
        return wspolrzedneX[wierzcholek];
    }
    
    public double getWspolrzednaYWierzcholka(int wierzcholek) {
        return wspolrzedneY[wierzcholek];
    }

    public void aktualizujWszystkieWagi() {

        for (int i = 0; i < liczbaWierzcholkow; i++) {
            for (int j = i; j < liczbaWierzcholkow; j++) {
                macierzKosztow[i][j] = obliczWageKrawedzi(i, j);
                macierzKosztow[j][i] = macierzKosztow[i][j];
            }
        }
    }

    private double obliczWageKrawedzi(int wierzcholek1, int wierzcholek2) {
        if (wierzcholek1 == wierzcholek2) {
            return Double.POSITIVE_INFINITY;
        } else {
            double dx = wspolrzedneX[wierzcholek1] - wspolrzedneX[wierzcholek2];
            double dy = wspolrzedneY[wierzcholek1] - wspolrzedneY[wierzcholek2];

            return Math.sqrt(dy * dy + dx * dx);
        }
    }
}
