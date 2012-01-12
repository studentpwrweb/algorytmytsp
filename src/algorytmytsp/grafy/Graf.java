/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.grafy;

/**
 *
 * @author Tomek
 */
public abstract class Graf {

    protected double macierzKosztow[][];
    protected int liczbaWierzcholkow;
    protected boolean skierowany;

    public Graf(int liczbaWierzcholkow, boolean skierowany) {

        this.liczbaWierzcholkow = liczbaWierzcholkow;
        this.skierowany = skierowany;

        macierzKosztow = new double[liczbaWierzcholkow][liczbaWierzcholkow];
    }

    public Graf(int liczbaWierzcholkow) {
        this(liczbaWierzcholkow, false);
    }

    public Graf(Graf grafWzorcowy) {
        this.liczbaWierzcholkow = grafWzorcowy.liczbaWierzcholkow;
        this.skierowany = grafWzorcowy.skierowany;
        
        this.macierzKosztow = grafWzorcowy.macierzKosztow.clone();
        for (int i = 0; i < this.macierzKosztow.length; i++) {
            this.macierzKosztow[i] = grafWzorcowy.macierzKosztow[i].clone();
        }
    }

    public boolean czyIstniejeKrawedz(int wierzcholek1, int wierzcholek2) {
        if (Double.isInfinite(getWagaKrawedzi(wierzcholek1, wierzcholek2))) {
            return false;
        } else {
            return true;
        }
    }
    
    public double[][] getMacierzKosztow() {
        double[][] macierz = macierzKosztow.clone();
        
        for (int i = 0; i < macierz.length; i++) {
            macierz[i] = macierzKosztow[i].clone();
        }
        
        return macierz;
    }

    public boolean czyJestSkierowany() {
        return skierowany;
    }

    public double getWagaKrawedzi(int wierzcholek1, int wierzcholek2) {          
        return macierzKosztow[wierzcholek1][wierzcholek2];
    }

    public int getLiczbaWierzcholkow() {
        return liczbaWierzcholkow;
    }

    @Override
    public String toString() {

        String str = new String();

        for (int i = 0; i < liczbaWierzcholkow; i++) {
            for (int j = 0; j < liczbaWierzcholkow; j++) {
                str += Double.toString(getWagaKrawedzi(i, j)) + '\t';
            }
            
            if (i < liczbaWierzcholkow - 1) {
                str += '\n';
            }
        }

        return str;
    }
}
