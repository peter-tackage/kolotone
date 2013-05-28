package com.moac.android.kolotone.model;

public class ElasticAnchor extends Anchor {

    private int kFactor = 1;

    public ElasticAnchor(float xpos, float ypos, int k) {
        super(xpos, ypos);
        this.kFactor = k;
    }

    public int getKFactor() {
        return kFactor;
    }

    public void setKFactor(int k) {
        kFactor = k;
    }
}
