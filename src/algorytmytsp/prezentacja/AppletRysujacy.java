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
import javax.swing.JApplet;

/**
 *
 * @author Tomek
 */
public class AppletRysujacy extends JApplet {

    private GrafXY graf;
    private StylPrezentacji styl;
    private PrezentacjaGrafu prezentacja;
    
    public AppletRysujacy() {
        super();
        
        addMouseListener(new MouseHandler());
    }

    @Override
    public void paint(Graphics g) {
        
        if (graf == null) {
            return;
        }

        if (prezentacja == null) {
            prezentacja = new PrezentacjaGrafu(graf);
        }

        if (styl == null) {
            styl = new StylPrezentacji();
        }

        Graphics2D g2 = (Graphics2D) g;

        int wysokosc = getSize().height;
        int szerokosc = getSize().width;

        g2.setBackground(styl.getKolorRGBTla());
        g2.clearRect(0, 0, szerokosc, wysokosc);
        g2.setStroke(new BasicStroke((float) styl.getGruboscLinii()));
        g2.setFont(styl.getCzcionka());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Obliczanie punktow w których maja zostac wyswietlone wierzcholki
        Point2D.Double punkty[] = new Point2D.Double[graf.getLiczbaWierzcholkow()];

        for (int i = 0; i < graf.getLiczbaWierzcholkow(); i++) {
            punkty[i] = polozenieWierzcholka(i);
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
                    str = str.substring(0, Math.min(str.length(), styl.getMaksDlugoscWag()));

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
            double rw = styl.getRozmiarWierzcholka();
            Shape okrag = new Ellipse2D.Double(punkty[i].x - rw * 0.5,
                    punkty[i].y - rw * 0.5,
                    rw, rw);
            g2.setPaint(styl.getKolorRGBTla());
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
        return styl.getKolorRGB(prezentacja.getKolorWierzcholka(w));
    }

    private Color kolorRGBKrawedzi(int w1, int w2) {
        return styl.getKolorRGB(prezentacja.getKolorKrawedzi(w1, w2));
    }

    private Point2D.Double polozenieWierzcholka(int w) {
        Point2D.Double punkt = new Point2D.Double();

        punkt.x = graf.getWspolrzednaXWierzcholka(w) * getSize().width;
        punkt.y = graf.getWspolrzednaYWierzcholka(w) * getSize().height;

        return punkt;
    }

    @Override
    public void init() {
        
    }

    public GrafXY getGraf() {
        return graf;
    }

    public void setGraf(GrafXY graf) {
        this.graf = graf;
    }

    public void setPrezentacja(PrezentacjaGrafu prezentacja) {
        this.prezentacja = prezentacja;
    }

    public void setStyl(StylPrezentacji styl) {
        this.styl = styl;
    }
    
    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Klik!");
            
            
        }
    }
    // TODO overwrite start(), stop() and destroy() methods
}
