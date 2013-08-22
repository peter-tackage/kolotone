package com.moac.android.kolotone.model;

public class ElasticAnchor extends Anchor {

    private int kFactor = 1;

    public ElasticAnchor(float _xpos, float _ypos, int _kFactor) {
        super(_xpos, _ypos);
        this.kFactor = _kFactor;
    }

    public int getKFactor() {
        return kFactor;
    }

    public void setKFactor(int k) {
        kFactor = k;
    }
}
