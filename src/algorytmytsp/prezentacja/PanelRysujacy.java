/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.prezentacja;

import algorytmytsp.grafy.GrafXY;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.EnumMap;
import javax.swing.JPanel;

/**
 *
 * @author Tomek
 */
public class PanelRysujacy extends JPanel {

    private GrafXY graf;
    private SchematKolorow schemat;
    private MapaKolorow mapa;

    public PanelRysujacy() {
        super();
        
        graf = new GrafXY(0);
        mapa = new MapaKolorow(graf);
        schemat = new SchematKolorow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (schemat == null) {
            schemat = new SchematKolorow();
        }

        Graphics2D g2 = (Graphics2D) g;

        int wysokosc = getSize().height;
        int szerokosc = getSize().width;

        g2.setBackground(schemat.getKolorRGBTla());
        g2.clearRect(0, 0, szerokosc, wysokosc);
        g2.setStroke(new BasicStroke((float) schemat.getGruboscLinii()));
        g2.setFont(schemat.getCzcionka());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (graf == null) {
            return;
        }

        if (mapa == null) {
            mapa = new MapaKolorow(graf);
        }

        // Obliczanie punktow w których maja zostac wyswietlone wierzcholki
        Point2D.Double punkty[] = new Point2D.Double[graf.getRozmiar()];

        for (int i = 0; i < graf.getRozmiar(); i++) {
            punkty[i] = wierzcholekNaPanel(
                    new Point2D.Double(graf.xWierzcholka(i), graf.yWierzcholka(i)));
        }

        // Rysowanie krawedzi 
        for (int i = 0; i < graf.getRozmiar() - 1; i++) {
            for (int j = i; j < graf.getRozmiar(); j++) {
                if (graf.istnienieKrawedzi(i, j)) {
                    Shape linia = new Line2D.Double(punkty[i], punkty[j]);

                    g2.setPaint(kolorRGBKrawedzi(i, j));
                    g2.draw(linia);
                }
            }
        }

        // Rysowanie wag krawedzi 
        for (int i = 0; i < graf.getRozmiar() - 1; i++) {
            for (int j = i; j < graf.getRozmiar(); j++) {
                if (graf.istnienieKrawedzi(i, j)) {
                    String str = schemat.getFormatWag().format(graf.wagaKrawedzi(i, j));

                    double strSzer = g2.getFontMetrics().stringWidth(str);
                    double strWys = g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent();

                    double alfa = Math.atan2(punkty[j].y - punkty[i].y, punkty[i].x - punkty[j].x);

                    boolean rozwarty = false;

                    if ((alfa > Math.PI * 0.5) || ((alfa < 0) && (alfa > -Math.PI * 0.5))) {
                        rozwarty = true;
                    }

                    double sina = Math.sin(alfa);
                    double cosa = Math.cos(alfa);

                    if (rozwarty) {
                        cosa *= -1;
                    }

                    double dxy = cosa * strSzer * 0.5 - sina * strWys * 0.5;
                    double dx = cosa * dxy;
                    double dy = sina * dxy;

                    if (rozwarty) {
                        dx = -dx + strSzer;
                    }

                    double srodekX = punkty[i].x - (punkty[i].x - punkty[j].x) / 2;
                    double srodekY = punkty[i].y - (punkty[i].y - punkty[j].y) / 2;

                    g2.setPaint(kolorRGBKrawedzi(i, j));
                    g2.drawString(str, (float) (srodekX - dx), (float) (srodekY + dy + strWys));

                }

            }
        }

        // Rysowanie wierzcholkow
        for (int i = 0; i < graf.getRozmiar(); i++) {
            double rw = schemat.getRozmiarWierzcholka();
            Shape okrag = new Ellipse2D.Double(punkty[i].x - rw * 0.5,
                    punkty[i].y - rw * 0.5,
                    rw, rw);
            g2.setPaint(schemat.getKolorRGBTla());
            g2.fill(okrag);

            g2.setPaint(kolorRGBWierzcholka(i));
            g2.draw(okrag);

            double strSzer = g2.getFontMetrics().stringWidth(Integer.toString(i));
            double strWys = g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent();

            g2.drawString(Integer.toString(i),
                    (float) (punkty[i].x - strSzer * 0.5),
                    (float) (punkty[i].y + strWys * 0.5));
        }

    }

    private Color kolorRGBWierzcholka(int w) {
        return schemat.getKolorRGB(mapa.getKolorWierzcholka(w));
    }

    private Color kolorRGBKrawedzi(int w1, int w2) {
        return schemat.getKolorRGB(mapa.getKolorKrawedzi(w1, w2));
    }

    private Point2D.Double wierzcholekNaPanel(Point2D.Double w) {
        Point2D.Double p = new Point2D.Double();

        double rw = schemat.getRozmiarWierzcholka();

        p.x = w.getX() * (getWidth() - rw) + 0.5 * rw;
        p.y = w.getY() * (getHeight() - rw) + 0.5 * rw;

        return p;
    }

    private Point2D.Double panelNaWierzcholek(Point2D.Double p) {
        Point2D.Double w = new Point2D.Double();

        double rw = schemat.getRozmiarWierzcholka();

        w.x = (p.getX() - 0.5 * rw) / (getWidth() - rw);
        w.y = (p.getY() - 0.5 * rw) / (getHeight() - rw);

        w.x = Math.max(Math.min(w.getX(), 1), 0);
        w.y = Math.max(Math.min(w.getY(), 1), 0);

        return w;
    }
    
    public void dodajWierzcholek(double x, double y) { 
        Point2D.Double xy = panelNaWierzcholek(new Point2D.Double(x, y));

        setGraf(new GrafXY(getGraf(), xy.getX(), xy.getY()));
    }
        
    public GrafXY getGraf() {
        return graf;
    }

    public void setGraf(GrafXY graf) {
        this.graf = (graf != null ? graf : new GrafXY(0));
        this.mapa = new MapaKolorow(this.graf);

        this.repaint();
    }
    
    public void setMapaKolorow(MapaKolorow mapa) {
        this.mapa = mapa;
    }

    public MapaKolorow getMapaKolorow() {
        return mapa;
    }
    
    private class SchematKolorow {
        private EnumMap<KoloryElementow, Color> mapaKolorow = new EnumMap<KoloryElementow, Color>(KoloryElementow.class);
        private Color kolorTla = Color.WHITE;
        private double rozmiarWierzcholka  = 32;
        private double gruboscLinii = 2;
        private Font czcionka = new Font("Dialog", Font.PLAIN, 16);
        private DecimalFormat formatWag = new DecimalFormat("#.##");

        public SchematKolorow() {
            mapaKolorow.put(KoloryElementow.DOMYSLNY, Color.BLACK);
            mapaKolorow.put(KoloryElementow.CZERWONY, Color.RED);
            mapaKolorow.put(KoloryElementow.NIEBIESKI, Color.BLUE);
            mapaKolorow.put(KoloryElementow.SZARY, Color.BLACK);
            mapaKolorow.put(KoloryElementow.CZARNY, Color.LIGHT_GRAY);
            mapaKolorow.put(KoloryElementow.ZIELONY, Color.GREEN);
            mapaKolorow.put(KoloryElementow.PRZEZROCZYSTY, new Color(0, true));
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
}
