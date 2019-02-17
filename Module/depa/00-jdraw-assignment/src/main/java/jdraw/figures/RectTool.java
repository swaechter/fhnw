/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

import java.awt.*;

public class RectTool extends AbstractDrawTool {

    public RectTool(DrawContext context) {
        super(context, "Rect", "rectangle.png", Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    protected Figure createFigure(int x, int y) {
        return new Rect(x, y, 0, 0);
    }
}
