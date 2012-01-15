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

    public GrafXY(GrafXY grafWzorcowy, double wspolrzednaX, double wspolrzednaY) {
        super(0);

        liczbaWierzcholkow = grafWzorcowy.getLiczbaWierzcholkow() + 1;
        skierowany = grafWzorcowy.czyJestSkierowany();

        macierzKosztow = new double[liczbaWierzcholkow][liczbaWierzcholkow];
        wspolrzedneX = new double[liczbaWierzcholkow];
        wspolrzedneY = new double[liczbaWierzcholkow];

        for (int i = 0; i < grafWzorcowy.getLiczbaWierzcholkow(); i++) {
            for (int j = 0; j < grafWzorcowy.getLiczbaWierzcholkow(); j++) {
                macierzKosztow[i][j] = grafWzorcowy.getWagaKrawedzi(i, j);
            }
        }

        for (int i = 0; i < grafWzorcowy.getLiczbaWierzcholkow(); i++) {
            setWspolrzedneWierzcholka(i,
                    grafWzorcowy.getXWierzcholka(i),
                    grafWzorcowy.getYWierzcholka(i), false);
        }

        setWspolrzedneWierzcholka(liczbaWierzcholkow - 1, wspolrzednaX, wspolrzednaY, true);
    }

    public final void setWspolrzedneWierzcholka(int wierzcholek, double wspolrzednaX, double wspolrzednaY, boolean aktualizujWagi) {

        wspolrzedneX[wierzcholek] = wspolrzednaX;
        wspolrzedneY[wierzcholek] = wspolrzednaY;

        if (aktualizujWagi) {
            for (int i = 0; i < liczbaWierzcholkow; i++) {
                macierzKosztow[wierzcholek][i] = obliczWageKrawedzi(wierzcholek, i);
                macierzKosztow[i][wierzcholek] = macierzKosztow[wierzcholek][i];
            }
        }
    }

    public void setXYWierzcholka(int wierzcholek, double x, double y) {
        setWspolrzedneWierzcholka(wierzcholek, x, y, true);
    }

    public double getXWierzcholka(int wierzcholek) {
        return wspolrzedneX[wierzcholek];
    }

    public double getYWierzcholka(int wierzcholek) {
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
