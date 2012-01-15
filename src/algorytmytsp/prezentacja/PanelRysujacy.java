/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmytsp.prezentacja;

import algorytmytsp.grafy.GrafXY;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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
        Point2D.Double punkty[] = new Point2D.Double[graf.getLiczbaWierzcholkow()];

        for (int i = 0; i < graf.getLiczbaWierzcholkow(); i++) {
            punkty[i] = wierzcholekNaPanel(
                    new Point2D.Double(graf.getXWierzcholka(i), graf.getYWierzcholka(i)));
        }

        // Rysowanie krawedzi 
        for (int i = 0; i < graf.getLiczbaWierzcholkow() - 1; i++) {
            for (int j = i; j < graf.getLiczbaWierzcholkow(); j++) {
                if (graf.czyIstniejeKrawedz(i, j)) {
                    Shape linia = new Line2D.Double(punkty[i], punkty[j]);

                    g2.setPaint(kolorRGBKrawedzi(i, j));
                    g2.draw(linia);
                }
            }
        }

        // Rysowanie wag krawedzi 
        for (int i = 0; i < graf.getLiczbaWierzcholkow() - 1; i++) {
            for (int j = i; j < graf.getLiczbaWierzcholkow(); j++) {
                if (graf.czyIstniejeKrawedz(i, j)) {
                    String str = Double.toString(graf.getWagaKrawedzi(i, j));
                    str = str.substring(0, Math.min(str.length(), schemat.getMaksDlugoscWag()));

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
        for (int i = 0; i < graf.getLiczbaWierzcholkow(); i++) {
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

        double rw = getSchematKolorow().getRozmiarWierzcholka();

        p.x = w.getX() * (getWidth() - rw) + 0.5 * rw;
        p.y = w.getY() * (getHeight() - rw) + 0.5 * rw;

        return p;
    }

    private Point2D.Double panelNaWierzcholek(Point2D.Double p) {
        Point2D.Double w = new Point2D.Double();

        double rw = getSchematKolorow().getRozmiarWierzcholka();

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

    public void setSchematKolorow(SchematKolorow schemat) {
        this.schemat = schemat;

        this.repaint();
    }

    public SchematKolorow getSchematKolorow() {
        return schemat;
    }
}
