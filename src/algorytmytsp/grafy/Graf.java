/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.grafy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tomek
 */
public class Graf {

    protected boolean[][] macierzSasiedztwa;
    protected List<List<Integer>> listaSasiedztwa;
    protected double[][] macierzKosztow;
    protected int rozmiar;
    protected boolean skierowany;

    public Graf(int rozmiar, boolean skierowany) {
        this.rozmiar = rozmiar;
        this.skierowany = skierowany;

        macierzSasiedztwa = new boolean[getRozmiar()][getRozmiar()];
        listaSasiedztwa = new ArrayList<List<Integer>>(getRozmiar());
        for (int i = 0; i < getRozmiar(); i++) {
            listaSasiedztwa.add(new ArrayList<Integer>());
        }

        macierzKosztow = new double[getRozmiar()][getRozmiar()];
        for (int i = 0; i < getRozmiar(); i++) {
            for (int j = 0; j < getRozmiar(); j++) {
                macierzKosztow[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    public Graf(int rozmiar) {
        this(rozmiar, false);
    }
    
    public Graf() {
        this(0);
    }

    protected void ustawWageKrawedzi(int w1, int w2, double waga) {
        if (Double.isInfinite(waga)) {
            if (istnienieKrawedzi(w1, w2)) {
                macierzSasiedztwa[w1][w2] = false;
                macierzKosztow[w1][w2] = Double.POSITIVE_INFINITY;

                for (Iterator<Integer> it = listaSasiedztwa.get(w1).iterator(); it.hasNext();) {
                    int w = it.next();
                    if (w == w2) {
                        it.remove();
                        break;
                    }
                }

                if (!isSkierowany()) {
                    macierzSasiedztwa[w2][w1] = false;
                    macierzKosztow[w2][w1] = Double.POSITIVE_INFINITY;

                    for (Iterator<Integer> it = listaSasiedztwa.get(w2).iterator(); it.hasNext();) {
                        int w = it.next();
                        if (w == w1) {
                            it.remove();
                            break;
                        }
                    }
                }
            }
        } else {
            if (!istnienieKrawedzi(w1, w2)) {
                macierzSasiedztwa[w1][w2] = true;
                listaSasiedztwa.get(w1).add(w2);
                
                if (!isSkierowany()) {
                    macierzSasiedztwa[w2][w1] = true;
                    listaSasiedztwa.get(w2).add(w1);
                }

            }

            macierzKosztow[w1][w2] = waga;

            if (!isSkierowany()) {
                macierzKosztow[w2][w1] = waga;
            }
        }
    }

    public boolean istnienieKrawedzi(int w1, int w2) {
        return macierzSasiedztwa[w1][w2];
    }

    public double wagaKrawedzi(int w1, int w2) {
        return macierzKosztow[w1][w2];
    }

    public List<Integer> sasiedzi(int w) {
        return listaSasiedztwa.get(w);
    }

    public List<List<Integer>> getListaSasiedztwa() {
        return listaSasiedztwa;
    }

    public double[][] getMacierzKosztow() {
        return macierzKosztow;
    }

    public boolean[][] getMacierzSasiedztwa() {
        return macierzSasiedztwa;
    }

    public final int getRozmiar() {
        return rozmiar;
    }

    public boolean isSkierowany() {
        return skierowany;
    }
}
