/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.prezentacja;

import algorytmytsp.algorytmy.AlgorytmTSP;
import algorytmytsp.grafy.GrafXY;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomek
 */
public class WatekAlgorytmu extends Thread {

    private PanelRysujacy panelRysujacy;
    private AlgorytmTSP algorytm;
    private long opoznienie;
    
    public WatekAlgorytmu(PanelRysujacy panelRysujacy, AlgorytmTSP algorytm, long opoznienie) {
        this.panelRysujacy = panelRysujacy;
        this.algorytm = algorytm;
        this.opoznienie = opoznienie;
        this.setDaemon(true);
    }
    
    public WatekAlgorytmu(PanelRysujacy panelRysujacy, AlgorytmTSP algorytm) {
        this(panelRysujacy, algorytm, 1000);
    }
    
    @Override
    public void run() {
        algorytm.setIteracyjnie(true);
        algorytm.setMapaKolorow(panelRysujacy.getMapaKolorow());
        algorytm.setWatek(this);
        
        algorytm.rozwiazTSPIteracyjnie(panelRysujacy.getGraf());
        
        this.stop();
    }
    
    public void koniecIteracji() {
        try {
            panelRysujacy.repaint();
            
            Thread.sleep(opoznienie);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(WatekAlgorytmu.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Przerwanie");
        }
    }
    
    public void setOpoznienie(long opoznienie) {
        this.opoznienie = opoznienie;
    }
}
