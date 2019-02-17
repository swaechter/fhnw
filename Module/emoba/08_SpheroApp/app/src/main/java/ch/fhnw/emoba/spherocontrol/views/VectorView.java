package ch.fhnw.emoba.spherocontrol.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import ch.fhnw.emoba.spherocontrol.models.SpheroMath;

public class VectorView extends View {

    public enum DrawStrategy {
        AIM,
        TOUCH,
        SENSOR
    }

    private final VectorViewListener vectorViewListener;

    private final DrawStrategy drawStrategy;

    private final Paint outerZonePaint;

    private final Paint innerZonePaint;

    private final Paint linePaint;

    private final Point centerPoint;

    private final Point touchPoint;

    private final Point circlePoint;

    private int canvasRadius;

    public VectorView(Context context, VectorViewListener vectorViewListener, DrawStrategy drawStrategy) {
        super(context);

        this.vectorViewListener = vectorViewListener;
        this.drawStrategy = drawStrategy;

        outerZonePaint = new Paint();
        outerZonePaint.setColor(Color.DKGRAY);

        innerZonePaint = new Paint();
        innerZonePaint.set(outerZonePaint);
        innerZonePaint.setAlpha(100);

        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);

        centerPoint = new Point();

        touchPoint = new Point();

        circlePoint = new Point();
    }

    @Override
    public void onDraw(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        canvasRadius = (int) (((canvasHeight > canvasWidth) ? canvasWidth : canvasHeight) * 0.9) / 2;

        centerPoint.x = canvasWidth / 2;
        centerPoint.y = canvasHeight / 2;

        linePaint.setStrokeWidth((float) (canvasRadius * 0.05));

        canvas.drawCircle(centerPoint.x, centerPoint.y, canvasRadius, innerZonePaint);
        canvas.drawCircle(centerPoint.x, centerPoint.y, canvasRadius * 0.1f, outerZonePaint);
        if (touchPoint.x != 0 && touchPoint.y != 0) {
            if (drawStrategy == DrawStrategy.AIM) {
                canvas.drawCircle(circlePoint.x, circlePoint.y, canvasRadius * 0.1f, linePaint);
            } else if (drawStrategy == DrawStrategy.TOUCH) {
                canvas.drawLine(centerPoint.x, centerPoint.y, touchPoint.x, touchPoint.y, linePaint);
                canvas.drawCircle(touchPoint.x, touchPoint.y, canvasRadius * 0.1f, linePaint);
            } else {
                canvas.drawLine(centerPoint.x, centerPoint.y, touchPoint.x, touchPoint.y, linePaint);
                canvas.drawCircle(touchPoint.x, touchPoint.y, canvasRadius * 0.1f, linePaint);
            }
        }
        canvas.drawCircle(centerPoint.x, centerPoint.y, canvasRadius * 0.02f, linePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            float initialX = event.getX();
            float initialY = event.getY();

            float relativeX = initialX - centerPoint.x;
            float relativeY = (initialY - centerPoint.y) * -1;

            float relativeAngle = SpheroMath.calculateTouchAngle(relativeX, relativeY);
            float spheroAngle = SpheroMath.calculateSpheroAngle(relativeX, relativeY);

            double relativeVelocity = SpheroMath.calculateVelocity(relativeX, relativeY);
            float fixedX = SpheroMath.calculateX(relativeAngle) * canvasRadius;
            float fixedY = SpheroMath.calculateY(relativeAngle) * canvasRadius;

            if (relativeVelocity > canvasRadius) {
                relativeX = fixedX;
                relativeY = fixedY;
            }

            float circleX = relativeX / canvasRadius;
            float circleY = relativeY / canvasRadius;
            float circleVelocity = SpheroMath.calculateVelocity(circleX, circleY);

            touchPoint.x = (int) relativeX + centerPoint.x;
            touchPoint.y = (int) (relativeY * -1) + centerPoint.y;
            circlePoint.x = (int) fixedX + centerPoint.x;
            circlePoint.y = (int) (fixedY * -1) + centerPoint.y;

            vectorViewListener.onMove(spheroAngle, circleVelocity);
        } else {
            touchPoint.x = 0;
            touchPoint.y = 0;
            circlePoint.x = 0;
            circlePoint.y = 0;
            vectorViewListener.onRelease();
        }

        invalidate();
        return true;
    }

    public Point calculateDisplayPoint(float angle, float velocity) {
        if (velocity > 1.0) {
            velocity = 1.0f;
        }

        float circleX = SpheroMath.calculateX(angle) * velocity;
        float circleY = SpheroMath.calculateY(angle) * velocity;

        float centerX = centerPoint.x;
        float centerY = centerPoint.y;

        float relativeX = canvasRadius * circleX;
        float relativeY = canvasRadius * circleY;

        float absoluteX = centerX - relativeX;
        float absoluteY = centerY + relativeY;

        return new Point((int) absoluteX, (int) absoluteY);
    }
}
