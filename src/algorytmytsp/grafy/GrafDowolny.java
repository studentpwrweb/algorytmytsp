/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.grafy;

/**
 *
 * @author Tomek
 */
public class GrafDowolny extends Graf {
    
    protected int mapaWierzcholkow[];
    
    public GrafDowolny(int liczbaWierzcholkow, boolean skierowany) {
        super(liczbaWierzcholkow, skierowany);
    }
    
    public GrafDowolny(int liczbaWierzcholkow) {
        this(liczbaWierzcholkow, false);
    }
    
    public GrafDowolny(Graf grafWzorcowy) {
        super(grafWzorcowy);
    }
    
    public void setWagaKrawedzi(int wierzcholek1, int wierzcholek2, double waga) {
        macierzKosztow[wierzcholek1][wierzcholek2] = waga;

        if (!skierowany) {
            macierzKosztow[wierzcholek2][wierzcholek1] = waga;
        }
    }
    
    public void usunKrawedz(int wierzcholek1, int wierzcholek2) {
        setWagaKrawedzi(wierzcholek1, wierzcholek2, Double.POSITIVE_INFINITY);
    }
}
