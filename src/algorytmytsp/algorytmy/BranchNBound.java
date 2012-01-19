/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.algorytmy;

import algorytmytsp.grafy.Graf;
import java.util.*;

/**
 *
 * @author Tomek
 */
public class BranchNBound implements IAlgorytmTSP {

    private PriorityQueue<WezelRozw> kolejkaRozw;
    private MacierzPrzestawna macierz;
    private HashSet<LinkedList<Integer>> czescioweRozw;
    private LinkedList<Integer> najlepszeRozw;
    private Graf graf;
    private double gorneOgr;

    @Override
    public List<Integer> rozwiazTSP(Graf g) {
        kolejkaRozw = new PriorityQueue<WezelRozw>();
        macierz = new MacierzPrzestawna(g);
        czescioweRozw = new LinkedHashSet<LinkedList<Integer>>();
        gorneOgr = Double.POSITIVE_INFINITY;
        graf = g;

        rozwiazDlaWezla(new WezelRozw(null));
        
        return najlepszeRozw;
    }

    private void rozwiazDlaWezla(WezelRozw wezel) {

        redukujMacierz(wezel);

        blokujPodcykle(wezel);

        normalizujMacierz(wezel);

        if (macierz.getLiczbaWierszy() > 2 && macierz.getLiczbaKolumn() > 2) {
            utworzPodrozwiazania(wezel);
            
            kolejkaRozw.offer(wezel.getLewy());
            
            rozwiazDlaWezla(wezel.getPrawy());
        } else {
            
            int w0 = macierz.getNrWiersza(0);
            int w1 = macierz.getNrWiersza(1);
            int k0 = macierz.getNrKolumny(0);
            int k1 = macierz.getNrKolumny(1);
            
            double sum0 = macierz.getElement(w0, k0) + macierz.getElement(w1, k1);
            double sum1 = macierz.getElement(w0, k1) + macierz.getElement(w1, k0);
            double minSum;
                    
            if (sum0 < sum1) {
                dodajDoRozw(w0, k0);
                dodajDoRozw(w1, k1);
            } else {
                dodajDoRozw(w0, k1);
                dodajDoRozw(w1, k0);
            }
            
            double dolneOgr = wezel.getDolneOgr() + Math.min(sum0, sum1);
            
            if (dolneOgr < gorneOgr) {
                gorneOgr = dolneOgr;
                najlepszeRozw = (LinkedList<Integer>) czescioweRozw.toArray()[0];
            }
        }
    }

    // Znajduje krawedz podzialu i tworzy węzły podrozwiązań
    private void utworzPodrozwiazania(WezelRozw wezel) {
        // Podział według 'najdroższej' krawedzi
        ObslugaPodzialu oP = new ObslugaPodzialu();
        iterujPoMacierzy(oP);

        wezel.setI(oP.getI());
        wezel.setJ(oP.getJ());

        // Tworzenie węzłów podrozwiązań
        wezel.setLewy(new WezelRozw(wezel,
                wezel.getDolneOgr() + oP.getPrzyrostOgr()));
        wezel.setPrawy(new WezelRozw(wezel));
    }

    private void normalizujMacierz(WezelRozw wezel) {
        ObslugaNormalizacji oN = new ObslugaNormalizacji();
        iterujPoMacierzy(oN);

        if (Double.isNaN(wezel.getDolneOgr())) {
            wezel.setDolneOgr(oN.getDolneOgr());
        } else {
            wezel.setDolneOgr(wezel.getDolneOgr() + oN.getDolneOgr());
        }

    }

    private void blokujPodcykle(WezelRozw wezel) {

        WezelRozw rodzic = wezel.getRodzic();

        if (rodzic != null) {
            LinkedList<Integer> sciezka = dodajDoRozw(rodzic.getI(), rodzic.getJ());
            macierz.usunElement(sciezka.getLast(), sciezka.getFirst());
        }
    }

    private LinkedList<Integer> dodajDoRozw(int i, int j) {

        LinkedList<Integer> nowaSciezka = new LinkedList<Integer>();
        nowaSciezka.add(i);
        nowaSciezka.add(j);

        for (Iterator<LinkedList<Integer>> it = czescioweRozw.iterator(); it.hasNext();) {
            LinkedList<Integer> sciezka = it.next();

            if (sciezka.getLast() == nowaSciezka.getFirst()) {
                nowaSciezka.removeFirst();
                nowaSciezka.addAll(0, sciezka);
                it.remove();
                break;
            }
        }

        for (Iterator<LinkedList<Integer>> it = czescioweRozw.iterator(); it.hasNext();) {
            LinkedList<Integer> sciezka = it.next();

            if (sciezka.getFirst() == nowaSciezka.getLast()) {
                nowaSciezka.removeLast();
                nowaSciezka.addAll(sciezka);
                it.remove();
                break;
            }
        }

        czescioweRozw.add(nowaSciezka);

        return nowaSciezka;
    }

    private void redukujMacierz(WezelRozw wezel) {
        WezelRozw rodzic = wezel.getRodzic();

        if (rodzic != null) {
            if (rodzic.getPrawy() == wezel) {
                macierz.usunWiersz(rodzic.getI());
                macierz.usunKolumne(rodzic.getJ());

            } else {
                macierz.usunElement(rodzic.getI(), rodzic.getJ());
            }
        }
    }

    private void iterujPoMacierzy(ObslugaIteracji oI) {

        oI.rozpoczecie();

        for (int k = 0; k < oI.getLiczbaIteracji(); k++) {

            Iterator<Integer> itI;

            boolean parzysta = (k % 2 == 0 ? true : false);

            if (parzysta) {
                itI = macierz.iteratorWierszy();
            } else {
                itI = macierz.iteratorKolumn();
            }

            oI.przedWierKol(k);

            while (itI.hasNext()) {

                Iterator<Integer> itJ;
                if (parzysta) {
                    itJ = macierz.iteratorKolumn();
                } else {
                    itJ = macierz.iteratorWierszy();
                }

                int i = itI.next();

                oI.poczatekWierKol(i, k);

                while (itJ.hasNext()) {

                    int j = itJ.next();

                    if (parzysta) {
                        oI.naElemencie(i, j, k);
                    } else {
                        oI.naElemencie(j, i, k);
                    }
                }

                oI.koniecWierKol(i, k);
            }

            oI.poWierKol(k);
        }

        oI.zakonczenie();
    }

    // Interfejs obiektu 
    private abstract class ObslugaIteracji {

        protected int liczbaIteracji = 2;

        public int getLiczbaIteracji() {
            return liczbaIteracji;
        }

        public void rozpoczecie() {
        }

        public void zakonczenie() {
        }

        public void przedWierKol(int k) {
        }

        public void poWierKol(int k) {
        }

        public void poczatekWierKol(int i, int k) {
        }

        public void koniecWierKol(int i, int k) {
        }

        public void naElemencie(int i, int j, int k) {
        }
    }

    private class ObslugaPodzialu extends ObslugaIteracji {

        private ArrayList<Integer> iZer;
        private ArrayList<Integer> jZer;
        private HashMap<Integer, Double> minWierszy;
        private HashMap<Integer, Double> minKolumn;
        private boolean znalezionoZero;
        private double min;
        private double przyrostOgr;
        private int i;
        private int j;

        @Override
        public void rozpoczecie() {
            minWierszy = new HashMap<Integer, Double>();
            minKolumn = new HashMap<Integer, Double>();

            iZer = new ArrayList<Integer>();
            jZer = new ArrayList<Integer>();
        }

        @Override
        public void poczatekWierKol(int i, int k) {
            znalezionoZero = false;
            min = Double.POSITIVE_INFINITY;
        }

        @Override
        public void naElemencie(int i, int j, int k) {
            double element = macierz.getElement(i, j);

            if (element == 0) {
                iZer.add(i);
                jZer.add(j);

                if (!znalezionoZero) {
                    znalezionoZero = true;
                } else {
                    min = Math.min(min, element);
                }
            } else {
                min = Math.min(min, element);
            }
        }

        @Override
        public void koniecWierKol(int i, int k) {
            if (k == 0) {
                minWierszy.put(i, min);
            } else {
                minKolumn.put(i, min);
            }
        }

        @Override
        public void zakonczenie() {
            przyrostOgr = Double.NEGATIVE_INFINITY;

            for (int k = 0; k < iZer.size(); k++) {
                double sumaMin = minWierszy.get(iZer.get(k)) + minKolumn.get(jZer.get(k));

                if (sumaMin > przyrostOgr) {
                    przyrostOgr = sumaMin;
                    i = iZer.get(k);
                    j = jZer.get(k);
                }
            }
        }

        public double getPrzyrostOgr() {
            return przyrostOgr;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }
    }

    private class ObslugaNormalizacji extends ObslugaIteracji {

        private double min;
        private double dolneOgr;

        @Override
        public void rozpoczecie() {
            dolneOgr = 0;
        }

        @Override
        public void poczatekWierKol(int i, int k) {
            min = Double.POSITIVE_INFINITY;
        }

        @Override
        public void naElemencie(int i, int j, int k) {
            min = Math.min(min, macierz.getElement(i, j));
        }

        @Override
        public void koniecWierKol(int i, int k) {
            if (min > 0) {

                Iterator<Integer> it;
                if (k == 0) {
                    it = macierz.iteratorKolumn();
                } else {
                    it = macierz.iteratorWierszy();
                }


                while (it.hasNext()) {
                    int l = it.next();
                    if (k == 0) {
                        macierz.setElement(i, l, macierz.getElement(i, l) - min);
                    } else {
                        macierz.setElement(l, i, macierz.getElement(l, i) - min);
                    }
                }

                dolneOgr += min;
            }
        }

        public double getDolneOgr() {
            return dolneOgr;
        }
    }

    private class MacierzPrzestawna {

        private double macierz[][];
        private ArrayList<Integer> kolumny;
        private ArrayList<Integer> wiersze;
        private boolean[] istnKolumny;
        private boolean[] istnWiersze;

        public MacierzPrzestawna(Graf graf) {
            macierz = new double[graf.getRozmiar()][];
            for (int i = 0; i < macierz.length; i++) {
                macierz[i] = graf.getMacierzKosztow()[i].clone();
            }

            kolumny = new ArrayList<Integer>();
            wiersze = new ArrayList<Integer>();

            istnKolumny = new boolean[graf.getRozmiar()];
            istnWiersze = new boolean[graf.getRozmiar()];

            for (int i = 0; i < graf.getRozmiar(); i++) {
                kolumny.add(i);
                wiersze.add(i);

                istnKolumny[i] = true;
                istnWiersze[i] = true;
            }
        }

        public MacierzPrzestawna(MacierzPrzestawna macierzWzorcowa) {
            this.macierz = macierzWzorcowa.macierz.clone();
            kolumny = (ArrayList<Integer>) macierzWzorcowa.kolumny.clone();
            wiersze = (ArrayList<Integer>) macierzWzorcowa.wiersze.clone();
            istnKolumny = macierzWzorcowa.istnKolumny.clone();
            istnWiersze = macierzWzorcowa.istnWiersze.clone();
        }

        public void usunWiersz(int i) {
            if (istnWiersze[i]) {
                wiersze.remove(wiersze.indexOf(i));
                istnWiersze[i] = false;
            }
        }

        public void usunKolumne(int i) {
            if (istnKolumny[i]) {
                kolumny.remove(kolumny.indexOf(i));
                istnKolumny[i] = false;
            }
        }

        public void usunElement(int i, int j) {
            setElement(i, j, Double.POSITIVE_INFINITY);
        }

        public double getElement(int i, int j) {
            if (istnWiersze[i] && istnKolumny[j]) {
                return macierz[i][j];
            } else {
                return Double.NaN;
            }
        }

        public void setElement(int i, int j, double wartosc) {
            if (istnWiersze[i] && istnKolumny[j]) {
                macierz[i][j] = wartosc;
            }
        }

        public int getLiczbaKolumn() {
            return kolumny.size();
        }

        public int getLiczbaWierszy() {
            return wiersze.size();
        }

        public int getNrKolumny(int i) {
            return kolumny.get(i);
        }

        public int getNrWiersza(int i) {
            return wiersze.get(i);
        }

        public Iterator<Integer> iteratorKolumn() {
            return kolumny.iterator();
        }

        public Iterator<Integer> iteratorWierszy() {
            return wiersze.iterator();
        }

        @Override
        public String toString() {

            String str = new String();

            for (int i : kolumny) {
                str += '\t' + Integer.toString(i);
            }

            str += '\n';

            for (int i : wiersze) {
                str += Integer.toString(i);

                for (int j : kolumny) {
                    String numStr = Double.toString(getElement(i, j));
                    str += '\t' + numStr.substring(0, Math.min(numStr.length(), 4));
                }
                str += '\n';
            }

            return str;
        }
    }

    private class WezelRozw implements Comparable<WezelRozw> {

        private double dolneOgr;
        private int i;
        private int j;
        private WezelRozw lewy = null;
        private WezelRozw prawy = null;
        private WezelRozw rodzic;

        public WezelRozw(WezelRozw rodzicRozw) {
            this(rodzicRozw, Double.NaN);
        }

        public WezelRozw(WezelRozw rodzic, double doleOgr) {
            this.rodzic = rodzic;
            this.dolneOgr = doleOgr;
        }
        
        @Override
        public int compareTo(WezelRozw o) {
            if (o == null) {
                return 1;
            }
            
            if (this.getDolneOgr() > o.getDolneOgr()) {
                return 1;
            } else if (this.getDolneOgr() < o.getDolneOgr()) {
                return -1;
            } else {
                return 0;
            }
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }

        public double getDolneOgr() {
            return dolneOgr;
        }

        public void setDolneOgr(double dolneOgr) {
            this.dolneOgr = dolneOgr;
        }

        public WezelRozw getRodzic() {
            return rodzic;
        }

        public void setRodzic(WezelRozw rodzic) {
            this.rodzic = rodzic;
        }

        public WezelRozw getLewy() {
            return lewy;
        }

        public void setLewy(WezelRozw lewy) {
            this.lewy = lewy;
        }

        public WezelRozw getPrawy() {
            return prawy;
        }

        public void setPrawy(WezelRozw prawy) {
            this.prawy = prawy;
        }
        
    }
}
