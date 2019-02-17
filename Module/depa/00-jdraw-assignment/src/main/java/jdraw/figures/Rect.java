/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

public class Rect extends AbstractFigure {

    private java.awt.Rectangle rectangle;
    // XXX at least for rectangle based figures (like rectangle, oval, ....) the bounding box could be stored in an abstract base class and move/setBounds could then be implemented in the base class as well.
    // XXX I would declare this field as final (I know, in the sources it was not final, just changed that).

    public Rect(int x, int y, int w, int h) {
        rectangle = new java.awt.Rectangle(x, y, w, h);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(Color.BLACK);
        g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        rectangle.setFrameFromDiagonal(origin, corner);
        notifyListeners();    // XXX notification should only be made if the figure really changed. For the rectangle this could be implemented by
        //     storing a copy of the original rectangle before invoking setFrameFromDiagonal and then to compare the original
        //     rectangle with the new one.
    }

    @Override
    public void move(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return;
        }

        rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
        notifyListeners();
    }

    @Override
    public boolean contains(int x, int y) {
        return rectangle.contains(x, y);
    }

    @Override
    public Rectangle getBounds() {
        return rectangle.getBounds();
    }

    @Override
    public Figure clone() {
        return null;
    }
}
