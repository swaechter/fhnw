package ch.swaechter.javafx.imageviewer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;
import java.util.function.IntUnaryOperator;

public class ImagePanel extends StackPane {

    private boolean isgrayscale;

    private File currentfile;

    private Image currentimage;

    private ImageView imageview = new ImageView();

    private Rectangle border = new Rectangle();

    private Text text = new Text();

    public ImagePanel() {
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
        if (file == null)
            return;
        try {
            currentfile = file;
            currentimage = new Image(file.toURI().toURL().toString());
            text.setText("");
            if (currentimage.getHeight() != 0) {
                if (isgrayscale) {
                    Image tempimage = currentimage;
                    currentimage = new WritableImage(tempimage.getPixelReader(), (int) tempimage.getWidth(), (int) tempimage.getHeight());
                    transformImage((WritableImage) currentimage, this::colorToGray);
                }
                imageview.setImage(currentimage);
            } else {
                imageview.setImage(currentimage);
                text.setText(file.getName());
            }
            drawBorder();
        } catch (MalformedURLException e) {
            System.out.println("exception in ImagePanel::setPicture() : malformed URL");
        }
    }

    public void setMode(boolean gray) {
        isgrayscale = gray;
        setPicture(currentfile);
    }

    public void showCurrentHistogram() {
        if (currentfile != null && currentimage.getHeight() != 0) {
            int[] histo = new int[64];
            int brightness;
            int max = 0;
            PixelReader pixelreader = currentimage.getPixelReader();
            int h = (int) currentimage.getHeight();
            int w = (int) currentimage.getWidth();
            for (int row = 0; row < h; ++row) {
                for (int col = 0; col < w; ++col) {
                    brightness = (colorToGray(pixelreader.getArgb(col, row)) & 0x000000FF) / 4;
                    ++histo[brightness];
                }
            }
            for (int i : histo) {
                if (i > max) max = i;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Canvas canvas = new Canvas(512, 200);
            GraphicsContext context = canvas.getGraphicsContext2D();
            alert.setGraphic(canvas);
            alert.setTitle("Current Image");
            alert.setHeaderText("Helligkeit");
            context.setFill(Color.AZURE);
            context.fillRect(0, 0, 512, 200);
            context.strokeRect(0, 0, 512, 200);
            for (int i = 0; i < 64; ++i) {
                context.setFill(new Color(i / 64.0, i / 64.0, i / 64.0, 1));
                int y = histo[i] * 199 / max;
                context.fillRect(8 * i, 200 - y, 7, y);
                context.strokeRect(8 * i, 200 - y, 7, y);
            }
            alert.showAndWait();
        }
    }

    public void drawBorder() {
        if (currentimage == null)
            return;
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

    private void transformImage(WritableImage image, IntUnaryOperator operator) {
        int h = (int) image.getHeight();
        int w = (int) image.getWidth();
        PixelReader pixelreader = image.getPixelReader();
        PixelWriter pixelwriter = image.getPixelWriter();
        for (int row = 0; row < h; ++row) {
            for (int col = 0; col < w; ++col) {
                pixelwriter.setArgb(col, row, operator.applyAsInt(pixelreader.getArgb(col, row)));
            }
        }
    }

    private int colorToGray(int color) {
        int r = ((color >>> 16) & 0xFF);
        int g = ((color >>> 8) & 0xFF);
        int b = (color & 0xFF);
        int brt = (21 * r + 72 * g + 7 * b) / 100;
        return (color & 0xFF000000) | brt << 16 | brt << 8 | brt;
    }
}
