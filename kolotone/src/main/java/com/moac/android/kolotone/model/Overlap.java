package com.moac.android.kolotone.model;

public class Overlap {

    private AbstractShape mShape1;
    private AbstractShape mShape2;
    private double mArea;

    public Overlap(AbstractShape _s1, AbstractShape _s2, double _area) {
        mShape1 = _s1;
        mShape2 = _s2;
        mArea = _area;
    }

    public AbstractShape getShape1() {
        return mShape1;
    }

    public AbstractShape getShape2() {
        return mShape2;
    }

    public double getArea() {
        return mArea;
    }

    public boolean involves(AbstractShape _s1, AbstractShape _s2) {
        return involves(_s1) && involves(_s2);
    }

    public boolean involves(AbstractShape _s) {
        return (mShape1 == _s) || (mShape2 == _s);
    }
}
