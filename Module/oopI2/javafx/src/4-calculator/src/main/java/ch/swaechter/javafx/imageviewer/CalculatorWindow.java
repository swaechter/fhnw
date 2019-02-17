package ch.swaechter.javafx.imageviewer;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class CalculatorWindow extends GridPane {

    public CalculatorWindow() {
        setId("gridpane");

        ColumnConstraints columnconstraincs = new ColumnConstraints();
        columnconstraincs.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(columnconstraincs, columnconstraincs, columnconstraincs, columnconstraincs);

        RowConstraints rowconstraints = new RowConstraints();
        rowconstraints.setVgrow(Priority.ALWAYS);
        getRowConstraints().addAll(new RowConstraints(), rowconstraints, rowconstraints, rowconstraints, rowconstraints, rowconstraints);

        TextField lcdpanel = new TextField();
        lcdpanel.setEditable(false);
        lcdpanel.setFocusTraversable(false);

        Button buttonclear = new Button("C");
        buttonclear.setMaxWidth(Double.MAX_VALUE);
        buttonclear.setMaxHeight(Double.MAX_VALUE);

        Button buttonplusminus = new Button("+/-");
        buttonplusminus.setMaxWidth(Double.MAX_VALUE);
        buttonplusminus.setMaxHeight(Double.MAX_VALUE);

        Button buttondivide = new Button("/");
        buttondivide.setMaxWidth(Double.MAX_VALUE);
        buttondivide.setMaxHeight(Double.MAX_VALUE);

        Button buttonmultiply = new Button("*");
        buttonmultiply.setMaxWidth(Double.MAX_VALUE);
        buttonmultiply.setMaxHeight(Double.MAX_VALUE);

        Button buttonseven = new Button("7");
        buttonseven.setMaxWidth(Double.MAX_VALUE);
        buttonseven.setMaxHeight(Double.MAX_VALUE);

        Button buttoneight = new Button("8");
        buttoneight.setMaxWidth(Double.MAX_VALUE);
        buttoneight.setMaxHeight(Double.MAX_VALUE);

        Button buttonnine = new Button("9");
        buttonnine.setMaxWidth(Double.MAX_VALUE);
        buttonnine.setMaxHeight(Double.MAX_VALUE);

        Button buttonsubstract = new Button("-");
        buttonsubstract.setMaxWidth(Double.MAX_VALUE);
        buttonsubstract.setMaxHeight(Double.MAX_VALUE);

        Button buttonfour = new Button("4");
        buttonfour.setMaxWidth(Double.MAX_VALUE);
        buttonfour.setMaxHeight(Double.MAX_VALUE);

        Button buttonfive = new Button("5");
        buttonfive.setMaxWidth(Double.MAX_VALUE);
        buttonfive.setMaxHeight(Double.MAX_VALUE);

        Button buttonsix = new Button("6");
        buttonsix.setMaxWidth(Double.MAX_VALUE);
        buttonsix.setMaxHeight(Double.MAX_VALUE);

        Button buttonadd = new Button("+");
        buttonadd.setMaxWidth(Double.MAX_VALUE);
        buttonadd.setMaxHeight(Double.MAX_VALUE);

        Button buttonone = new Button("1");
        buttonone.setMaxWidth(Double.MAX_VALUE);
        buttonone.setMaxHeight(Double.MAX_VALUE);

        Button buttontwo = new Button("2");
        buttontwo.setMaxWidth(Double.MAX_VALUE);
        buttontwo.setMaxHeight(Double.MAX_VALUE);

        Button buttonthree = new Button("3");
        buttonthree.setMaxWidth(Double.MAX_VALUE);
        buttonthree.setMaxHeight(Double.MAX_VALUE);

        Button buttonequal = new Button("=");
        buttonequal.setMaxWidth(Double.MAX_VALUE);
        buttonequal.setMaxHeight(Double.MAX_VALUE);
        buttonequal.setId("equal");

        Button buttonzero = new Button("0");
        buttonzero.setMaxWidth(Double.MAX_VALUE);
        buttonzero.setMaxHeight(Double.MAX_VALUE);

        Button buttondot = new Button(".");
        buttondot.setMaxWidth(Double.MAX_VALUE);
        buttondot.setMaxHeight(Double.MAX_VALUE);

        add(lcdpanel, 0, 0, 4, 1);

        add(buttonclear, 0, 1, 1, 1);
        add(buttonplusminus, 1, 1, 1, 1);
        add(buttondivide, 2, 1, 1, 1);
        add(buttonmultiply, 3, 1, 1, 1);

        add(buttonseven, 0, 2, 1, 1);
        add(buttoneight, 1, 2, 1, 1);
        add(buttonnine, 2, 2, 1, 1);
        add(buttonsubstract, 3, 2, 1, 1);

        add(buttonfour, 0, 3, 1, 1);
        add(buttonfive, 1, 3, 1, 1);
        add(buttonsix, 2, 3, 1, 1);
        add(buttonadd, 3, 3, 1, 1);

        add(buttonone, 0, 4, 1, 1);
        add(buttontwo, 1, 4, 1, 1);
        add(buttonthree, 2, 4, 1, 1);
        add(buttonequal, 3, 4, 1, 2);

        add(buttonzero, 0, 5, 2, 1);
        add(buttondot, 2, 5, 1, 1);
    }
}
