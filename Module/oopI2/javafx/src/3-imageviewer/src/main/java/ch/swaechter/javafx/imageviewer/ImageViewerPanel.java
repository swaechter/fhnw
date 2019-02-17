package ch.swaechter.javafx.imageviewer;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;

public class ImageViewerPanel extends StackPane {

    private Image currentimage;

    private ImageView imageview = new ImageView();

    private Rectangle border = new Rectangle();

    private Text text = new Text();

    public ImageViewerPanel() {
        setMinSize(0, 0);

        imageview.setPreserveRatio(true);
        imageview.fitWidthProperty().bind(widthProperty().subtract(10));
        imageview.fitHeightProperty().bind(heightProperty().subtract(10));
        imageview.setEffect(new DropShadow(10, 5, 5, new Color(0, 0, 0, 0.3)));

        border.setFill(null);
        border.setStroke(Color.BLACK);

        text.setFont(new Font(24));
        text.setFontSmoothingType(FontSmoothingType.LCD);

        getChildren().addAll(imageview, border, text);

        widthProperty().addListener(event -> {
            drawBorder();
        });

        heightProperty().addListener(event -> {
            drawBorder();
        });
    }

    public void setPicture(File file) {
        try {
            currentimage = new Image(file.toURI().toURL().toString());
            imageview.setImage(currentimage);
            if (currentimage.getHeight() == 0) {
                text.setText(file.getName());
            } else {
                text.setText(new String());
            }
            drawBorder();
        } catch (MalformedURLException e) {
        }
    }


    public void drawBorder() {
        if (currentimage == null) return;
        double originalratio = currentimage.getWidth() / currentimage.getHeight();
        double displayedratio = imageview.getFitWidth() / imageview.getFitHeight();
        if (originalratio < displayedratio) {
            border.setWidth(imageview.getFitHeight() * originalratio + 3);
            border.setHeight(imageview.getFitHeight() + 3);
        } else {
            border.setWidth(imageview.getFitWidth() + 3);
            border.setHeight(imageview.getFitWidth() / originalratio + 3);
        }
    }
}
