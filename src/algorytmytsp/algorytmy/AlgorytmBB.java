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
public class AlgorytmBB implements IAlgorytmTSP {

    private MacierzPrzestawna macierz;
    private HashSet<LinkedList<Integer>> czescioweRozw;
    private LinkedList<Integer> najlepszeRozw;
    private Graf graf;
    private double gorneOgr;

    @Override
    public List<Integer> rozwiazTSP(Graf g) {
        macierz = new MacierzPrzestawna(g);
        czescioweRozw = new HashSet<LinkedList<Integer>>();
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

            rozwiazDlaWezla(wezel.getPraweDziecko());
        } else {
            int aI, aJ;
            int bI, bJ;
            
            if (macierz.getElementRel(0, 0) + macierz.getElementRel(1, 1)
                    < macierz.getElementRel(0, 1) + macierz.getElementRel(1, 0)) {
                aI = 0; aJ = 0;
                bI = 1; bJ = 1;
            } else {
                aI = 0; aJ = 1;
                bI = 1; bJ = 0;
            }
            
            dodajDoRozw(macierz.getNrWiersza(aI), macierz.getNrKolumny(aJ));
            dodajDoRozw(macierz.getNrWiersza(bI), macierz.getNrKolumny(bJ));
            
            najlepszeRozw = (LinkedList<Integer>)czescioweRozw.toArray()[0];
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
        wezel.setLeweDziecko(new WezelRozw(wezel,
                wezel.getDolneOgr() + oP.getPrzyrostOgr()));
        wezel.setPraweDziecko(new WezelRozw(wezel));
    }

    private void normalizujMacierz(WezelRozw wezel) {
        ObslugaNormalizacji oN = new ObslugaNormalizacji();
        iterujPoMacierzy(oN);

        if (Double.isNaN(wezel.getDolneOgr())) {
            wezel.setDolneOgr(oN.getDolneOgr());
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
            if (rodzic.getPraweDziecko() == wezel) {
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
            macierz = graf.getMacierzKosztow();

            kolumny = new ArrayList<Integer>();
            wiersze = new ArrayList<Integer>();

            int n = graf.getRozmiar();

            istnKolumny = new boolean[n];
            istnWiersze = new boolean[n];

            for (int i = 0; i < n; i++) {
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

        public double getElementRel(int i, int j) {
            return getElement(getNrWiersza(i), getNrKolumny(j));
        }

        public void setElementRel(int i, int j, double wartosc) {
            setElement(getNrWiersza(i), getNrKolumny(j), wartosc);
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

    private class WezelRozw {

        private double dolneOgr;
        private int i;
        private int j;
        private WezelRozw lewyWezel = null;
        private WezelRozw prawyWezel = null;
        private WezelRozw rodzic;

        public WezelRozw(WezelRozw rodzicRozw) {
            this(rodzicRozw, Double.NaN);
        }

        public WezelRozw(WezelRozw rodzicRozw, double dolneOgraniczenie) {
            this.rodzic = rodzicRozw;
            this.dolneOgr = dolneOgraniczenie;
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

        public void setRodzic(WezelRozw rodzicRozw) {
            this.rodzic = rodzicRozw;
        }

        public WezelRozw getLeweDziecko() {
            return lewyWezel;
        }

        public void setLeweDziecko(WezelRozw rozwBezKrawedzi) {
            this.lewyWezel = rozwBezKrawedzi;
        }

        public WezelRozw getPraweDziecko() {
            return prawyWezel;
        }

        public void setPraweDziecko(WezelRozw rozwZKrawedzia) {
            this.prawyWezel = rozwZKrawedzia;
        }
    }
}
