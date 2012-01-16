/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.prezentacja;

import algorytmytsp.algorytmy.AlgorytmIteracyjnyTSP;
import algorytmytsp.grafy.GrafXY;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomek
 */
public class WatekAlgorytmu extends Thread {

    private PanelRysujacy panelRysujacy;
    private OknoProgramu oknoProgramu;
    private AlgorytmIteracyjnyTSP algorytm;
    private long przerwa = 0;
    private boolean kontynuuj = false;

    public WatekAlgorytmu(AlgorytmIteracyjnyTSP algorytm) {
        this.algorytm = algorytm;

        this.setDaemon(true);
    }

    @Override
    public void run() {
        algorytm.setIteracyjnie(true);
        algorytm.setMapaKolorow(panelRysujacy.getMapaKolorow());
        algorytm.setWatek(this);

        if (oknoProgramu != null) {
            oknoProgramu.watekRozpoczety();
        }
        
        boolean przerwany = false;

        try {
            algorytm.rozwiazTSPIteracyjnie(panelRysujacy.getGraf());
        } catch (InterruptedException ex) {
            przerwany = true;
        } finally {
            if (panelRysujacy != null && !przerwany) {
                panelRysujacy.repaint();
            }

            if (oknoProgramu != null) {
                oknoProgramu.watekZakonczony();
            }
        }
    }

    public void koniecIteracji() throws InterruptedException {

        if (panelRysujacy != null) {
            panelRysujacy.repaint();
        }

        synchronized (this) {
            if (kontynuuj) {
                wait(przerwa);
            } else {
                wait();
            }
        }
    }
    
    public OknoProgramu getOknoProgramu() {
        return oknoProgramu;
    }

    public void setOknoProgramu(OknoProgramu oknoProgramu) {
        this.oknoProgramu = oknoProgramu;
    }

    public PanelRysujacy getPanelRysujacy() {
        return panelRysujacy;
    }

    public void setPanelRysujacy(PanelRysujacy panelRysujacy) {
        this.panelRysujacy = panelRysujacy;
    }

    public long getPrzerwa() {
        return przerwa;
    }

    public void setPrzerwa(long przerwa) {
        this.przerwa = przerwa;
    }

    public boolean isKontynuuj() {
        return kontynuuj;
    }

    public void setKontynuuj(boolean kontynuuj) {
        this.kontynuuj = kontynuuj;
    }
}
