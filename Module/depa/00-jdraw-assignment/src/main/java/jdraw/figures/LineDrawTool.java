package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

import java.awt.*;

public class LineDrawTool extends AbstractDrawTool {

    public LineDrawTool(DrawContext context) {
        super(context, "Line", "line.png", Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    protected Figure createFigure(int x, int y) {
        return new Line(x, y, x, y);
    }
}
