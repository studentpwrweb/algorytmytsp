/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.prezentacja;

import algorytmytsp.prezentacja.KoloryElementow;
import java.util.EnumMap;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Tomek
 */
public class SchematKolorow {
    private EnumMap<KoloryElementow, Color> mapaKolorow = new EnumMap<KoloryElementow, Color>(KoloryElementow.class);
    
    private Color kolorTla = Color.WHITE;
    private double rozmiarWierzcholka  = 32;
    private double gruboscLinii = 2;
    private Font czcionka = new Font("Dialog", Font.PLAIN, 16);
    private int maksDlugoscWag = 4;
    
    public SchematKolorow() {
        /*for (KolorElementu kolorElementu : KolorElementu.values()) {
            mapaKolorow.put(kolorElementu, Color.BLACK);
        }*/ 
        mapaKolorow.put(KoloryElementow.DOMYSLNY, Color.BLACK);
        mapaKolorow.put(KoloryElementow.WYROZNIONY, Color.RED);
        mapaKolorow.put(KoloryElementow.NIEODWIEDZONY, Color.BLACK);
        mapaKolorow.put(KoloryElementow.ODWIEDZONY, Color.GRAY);
        mapaKolorow.put(KoloryElementow.ANALIZOWANY, Color.GREEN);
        mapaKolorow.put(KoloryElementow.NIEWIDOCZNY, new Color(0, true));
    }
    
    public void mapujKolor(KoloryElementow kolorElementu, Color kolorRGB) {
        mapaKolorow.put(kolorElementu, kolorRGB);
    }
    
    public Color getKolorRGB(KoloryElementow kolorElementu) {
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
