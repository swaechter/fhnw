package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Oval extends AbstractFigure {

    private final Ellipse2D ellipse;

    public Oval(int x, int y, int w, int h) {
        ellipse = new Ellipse2D.Double(x, y, w, h);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) ellipse.getX(), (int) ellipse.getY(), (int) ellipse.getWidth(), (int) ellipse.getHeight());
        g.setColor(Color.BLACK);
        g.drawOval((int) ellipse.getX(), (int) ellipse.getY(), (int) ellipse.getWidth(), (int) ellipse.getHeight());
    }

    @Override
    public void move(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return;
        }

        ellipse.setFrame(ellipse.getX() + dx, ellipse.getY() + dy, ellipse.getWidth(), ellipse.getHeight());

        notifyListeners();
    }

    @Override
    public boolean contains(int x, int y) {
        return ellipse.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        ellipse.setFrameFromCenter(origin.x, origin.y, corner.x, corner.y);

        notifyListeners();    // XXX same holds for the oval, i.e. you could compare the bounding boxes.
    }

    @Override
    public Rectangle getBounds() {
        return ellipse.getBounds();
    }

    @Override
    public Figure clone() {
        return new Oval((int) ellipse.getX(), (int) ellipse.getY(), (int) ellipse.getWidth(), (int) ellipse.getHeight());
    }
}
