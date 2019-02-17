package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;
import java.awt.geom.Line2D;

public class Line extends AbstractFigure {

    private static final int LINE_DISTANCE = 5;

    private final Line2D.Double line;

    public Line(int x1, int y1, int x2, int y2) {
        this.line = new Line2D.Double(x1, y1, x2, y2);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.draw(line);
    }

    @Override
    public void move(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return;
        }

        line.x1 += dx;
        line.y1 += dy;
        line.x2 += dx;
        line.y2 += dy;

        notifyListeners();
    }

    @Override
    public boolean contains(int x, int y) {
        return line.ptSegDistSq(x, y) < LINE_DISTANCE;
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        line.x1 = origin.x;
        line.y1 = origin.y;
        line.x2 = corner.x;
        line.y2 = corner.y;

        notifyListeners();    // XXX should only be invoked if the figure changed due to this operation. Here you could compare the start and end point of the line with ghe given origin and corner points.
    }

    @Override
    public Rectangle getBounds() {
        return line.getBounds();
    }

    @Override
    public Figure clone() {
        return new Line((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
    }
}
