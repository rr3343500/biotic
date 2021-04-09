package com.example.bioticclasses.piechart;

import im.dacer.androidcharts.PieHelper;

public class PiechartHelper {
    int velocity = 5;
    private float startDegree;
    private float endDegree;
    private float targetStartDegree;
    private float targetEndDegree;
    private String title;
    private int color;
    private float sweepDegree;

    /**
     * @param percent from 0 to 100
     */
    public PiechartHelper(float percent ) {
        this(percent, null, 0);
    }

    public PiechartHelper(float percent, int color) {
        this(percent, null, color);
    }

    /**
     * @param percent from 0 to 100
     */
    PiechartHelper(float percent, String title) {
        this(percent, title, 0);
    }

    /**
     * @param percent from 0 to 100
     */
    public PiechartHelper(float percent, String title, int color) {
        this.sweepDegree = percent * 360 / 100;
        this.title = title;
        this.color = color;
    }

    PiechartHelper(float startDegree, float endDegree, PiechartHelper targetPie) {
        this.startDegree = startDegree;
        this.endDegree = endDegree;
        targetStartDegree = targetPie.getStartDegree();
        targetEndDegree = targetPie.getEndDegree();
        this.sweepDegree = targetPie.getSweep();
        this.title = targetPie.getTitle();
        this.color = targetPie.getColor();
    }



    PiechartHelper setTarget(PieHelper targetPie) {
        this.targetStartDegree = targetPie.getStartDegree();
        this.targetEndDegree = targetPie.getEndDegree();
        this.title = targetPie.getTitle();
        this.color = targetPie.getColor();
        this.sweepDegree = targetPie.getSweep();
        return this;
    }

    void setDegree(float startDegree, float endDegree) {
        this.startDegree = startDegree;
        this.endDegree = endDegree;
    }

    boolean isColorSetted() {
        return color != 0;
    }

    boolean isAtRest() {
        return (startDegree == targetStartDegree) && (endDegree == targetEndDegree);
    }

    void update() {
        this.startDegree = updateSelf(startDegree, targetStartDegree, velocity);
        this.endDegree = updateSelf(endDegree, targetEndDegree, velocity);
        this.sweepDegree = endDegree - startDegree;
    }

    String getPercentStr() {
        float percent = sweepDegree / 360 * 100;
        return String.valueOf((int) percent) + "%";
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public float getSweep() {
        return sweepDegree;
    }

    public float getStartDegree() {
        return startDegree;
    }

    public float getEndDegree() {
        return endDegree;
    }

    private float updateSelf(float origin, float target, int velocity) {
        if (origin < target) {
            origin += velocity;
        } else if (origin > target) {
            origin -= velocity;
        }
        if (Math.abs(target - origin) < velocity) {
            origin = target;
        }
        return origin;
    }
}
