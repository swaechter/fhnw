package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

import java.awt.*;

public class OvalDrawTool extends AbstractDrawTool {

    public OvalDrawTool(DrawContext context) {
        super(context, "Oval", "oval.png", Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    protected Figure createFigure(int x, int y) {
        return new Oval(x, y, 0, 0);
    }
}
