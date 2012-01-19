/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.algorytmy;

import algorytmytsp.grafy.Graf;
import algorytmytsp.grafy.GrafDowolny;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import algorytmytsp.Test;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Tomek
 */
public class TwiceAroundTree implements IAlgorytmTSP {

    private WezelDFS wezlyDFS[];
    private List<Integer> listaDFS;

    @Override
    public List<Integer> rozwiazTSP(Graf graf) {

        Graf grafMST = mstPrim(graf);

        dfs(grafMST);

        boolean bezPowtorzen[] = new boolean[graf.getRozmiar()];

        for (Iterator<Integer> it = listaDFS.iterator(); it.hasNext();) {
            Integer i = it.next();

            if (!bezPowtorzen[i]) {
                bezPowtorzen[i] = true;
            } else {
                it.remove();
            }
        }

        if (listaDFS.size() > 0) {
            listaDFS.add(listaDFS.get(0));
        }

        return listaDFS;
    }

    private Graf mstPrim(Graf graf) {

        PriorityQueue<WezelMST> kolejka = new PriorityQueue();
        WezelMST wezlyMST[] = new WezelMST[graf.getRozmiar()];

        for (int i = 0; i < wezlyMST.length; i++) {
            WezelMST wezel = new WezelMST(i);

            if (i == 0) {
                wezel.setKlucz(0);
            } else {
                wezel.setKlucz(Double.POSITIVE_INFINITY);
            }

            wezel.setPi(null);

            wezlyMST[i] = wezel;
            kolejka.offer(wezel);
        }

        boolean pozaKolejka[] = new boolean[graf.getRozmiar()];

        while (!kolejka.isEmpty()) {

            WezelMST r = kolejka.poll();
            pozaKolejka[r.getI()] = true;

            for (Integer i : graf.sasiedzi(r.getI())) {
                WezelMST v = wezlyMST[i];

                if (!pozaKolejka[i]
                        && graf.wagaKrawedzi(r.getI(), i) < v.getKlucz()) {

                    kolejka.remove(v);

                    v.setKlucz(graf.wagaKrawedzi(r.getI(), i));
                    v.setPi(r);

                    kolejka.offer(v);
                }
            }
        }

        GrafDowolny mst = new GrafDowolny(graf.getRozmiar());

        for (WezelMST wezel : wezlyMST) {
            int w1 = wezel.getI();
            int w2;
            if (wezel.getPi() != null) {
                w2 = wezel.getPi().getI();
            } else {
                continue;
            }
            mst.ustawWageKrawedzi(w1, w2, graf.wagaKrawedzi(w1, w2));
        }

        return mst;
    }

    private void dfs(Graf graf) {

        wezlyDFS = new WezelDFS[graf.getRozmiar()];

        for (int i = 0; i < wezlyDFS.length; i++) {
            WezelDFS wezel = new WezelDFS(i);
            wezel.setKolor(WezelDFS.BIALY);
            wezel.setPi(null);

            wezlyDFS[i] = wezel;
        }

        listaDFS = new ArrayList<Integer>();

        for (int i = 0; i < wezlyDFS.length; i++) {
            WezelDFS u = wezlyDFS[i];
            if (u.getKolor() == WezelDFS.BIALY) {
                listaDFS.add(u.getI());
                dfsVisit(graf, u);
            }
        }
    }

    private void dfsVisit(Graf graf, WezelDFS u) {
        u.setKolor(WezelDFS.SZARY);

        for (Integer i : graf.sasiedzi(u.getI())) {
            WezelDFS v = wezlyDFS[i];

            if (v.getKolor() == WezelDFS.BIALY) {
                v.setPi(u);

                listaDFS.add(v.getI());
                dfsVisit(graf, v);
                listaDFS.add(u.getI());
            }
        }

        u.setKolor(WezelDFS.CZARNY);
    }

    private class WezelDFS {

        public final static int BIALY = 0;
        public final static int SZARY = 1;
        public final static int CZARNY = 2;
        private int i;
        private int kolor;
        private WezelDFS pi;

        public WezelDFS(int i) {
            this.i = i;
        }

        public int getI() {
            return i;
        }

        public int getKolor() {
            return kolor;
        }

        public void setKolor(int kolor) {
            this.kolor = kolor;
        }

        public WezelDFS getPi() {
            return pi;
        }

        public void setPi(WezelDFS pi) {
            this.pi = pi;
        }
    }

    private class WezelMST implements Comparable<WezelMST> {

        private int i;
        private WezelMST pi;
        private double klucz;

        public WezelMST(int i) {
            this.i = i;
        }

        @Override
        public int compareTo(WezelMST o) {
            if (o == null) {
                return 1;
            }

            if (this.getKlucz() > o.getKlucz()) {
                return 1;
            }
            if (this.getKlucz() < o.getKlucz()) {
                return -1;
            } else {
                return 0;
            }
        }

        public WezelMST getPi() {
            return pi;
        }

        public void setPi(WezelMST pi) {
            this.pi = pi;
        }

        public double getKlucz() {
            return klucz;
        }

        public void setKlucz(double klucz) {
            this.klucz = klucz;
        }

        public int getI() {
            return i;
        }
    }
}