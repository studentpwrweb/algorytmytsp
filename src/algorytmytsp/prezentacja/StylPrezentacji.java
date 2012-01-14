/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.prezentacja;

import algorytmytsp.prezentacja.KolorElementu;
import java.util.EnumMap;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Tomek
 */
public class StylPrezentacji {
    private EnumMap<KolorElementu, Color> mapaKolorow = new EnumMap<KolorElementu, Color>(KolorElementu.class);
    
    private Color kolorTla = Color.WHITE;
    private double rozmiarWierzcholka  = 32;
    private double gruboscLinii = 2;
    private Font czcionka = new Font("Dialog", Font.PLAIN, 16);
    private int maksDlugoscWag = 4;
    
    public StylPrezentacji() {
        /*for (KolorElementu kolorElementu : KolorElementu.values()) {
            mapaKolorow.put(kolorElementu, Color.BLACK);
        }*/ 
        mapaKolorow.put(KolorElementu.DOMYSLNY, Color.BLACK);
        mapaKolorow.put(KolorElementu.WYROZNIONY, Color.RED);
        mapaKolorow.put(KolorElementu.NIEODWIEDZONY, Color.BLACK);
        mapaKolorow.put(KolorElementu.ODWIEDZONY, Color.GRAY);
        mapaKolorow.put(KolorElementu.ANALIZOWANY, Color.GREEN);
        mapaKolorow.put(KolorElementu.NIEWIDOCZNY, new Color(0, true));
    }
    
    public void mapujKolor(KolorElementu kolorElementu, Color kolorRGB) {
        mapaKolorow.put(kolorElementu, kolorRGB);
    }
    
    public Color getKolorRGB(KolorElementu kolorElementu) {
        return mapaKolorow.get(kolorElementu);
    }
    
    public int getMaksDlugoscWag() {
        return maksDlugoscWag;
    }

    public void setMaksDlugoscWag(int maksDlugoscWag) {
        this.maksDlugoscWag = maksDlugoscWag;
    }
    
    public Font getCzcionka() {
        return czcionka;
    }

    public void setCzcionka(Font czcionka) {
        this.czcionka = czcionka;
    }

    public double getGruboscLinii() {
        return gruboscLinii;
    }

    public void setGruboscLinii(double gruboscLinii) {
        this.gruboscLinii = gruboscLinii;
    }

    public Color getKolorRGBTla() {
        return kolorTla;
    }

    public void setKolorRGBTla(Color kolorTla) {
        this.kolorTla = kolorTla;
    }

    public double getRozmiarWierzcholka() {
        return rozmiarWierzcholka;
    }

    public void setRozmiarWierzcholka(double rozmiarWierzcholka) {
        this.rozmiarWierzcholka = rozmiarWierzcholka;
    }
    
}
