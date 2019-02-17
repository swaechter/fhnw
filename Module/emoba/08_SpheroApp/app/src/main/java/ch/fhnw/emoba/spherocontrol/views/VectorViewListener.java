package ch.fhnw.emoba.spherocontrol.views;

public interface VectorViewListener {

    void onMove(float angle, float velocity);

    void onRelease();
}
