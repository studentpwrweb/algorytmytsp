/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.prezentacja;

import java.awt.Color;
import java.awt.Font;
import java.util.EnumMap;
import java.text.DecimalFormat;

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
    private DecimalFormat formatWag = new DecimalFormat("#.##");
    
    public SchematKolorow() {
        /*for (KolorElementu kolorElementu : KolorElementu.values()) {
            mapaKolorow.put(kolorElementu, Color.BLACK);
        }*/ 
        mapaKolorow.put(KoloryElementow.DOMYSLNY, Color.BLACK);
        mapaKolorow.put(KoloryElementow.WYROZNIONY1, Color.RED);
        mapaKolorow.put(KoloryElementow.WYROZNIONY2, Color.BLUE);
        mapaKolorow.put(KoloryElementow.NIEODWIEDZONY, Color.BLACK);
        mapaKolorow.put(KoloryElementow.ODWIEDZONY, Color.LIGHT_GRAY);
        mapaKolorow.put(KoloryElementow.ANALIZOWANY, Color.GREEN);
        mapaKolorow.put(KoloryElementow.NIEWIDOCZNY, new Color(0, true));
    }
    
    public void mapujKolor(KoloryElementow kolorElementu, Color kolorRGB) {
        mapaKolorow.put(kolorElementu, kolorRGB);
    }
    
    public Color getKolorRGB(KoloryElementow kolorElementu) {
        return mapaKolorow.get(kolorElementu);
    }
    
    public DecimalFormat getFormatWag() {
        return formatWag;
    }

    public void setFormatWag(DecimalFormat formatWag) {
        this.formatWag = formatWag;
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
