package com.moac.android.kolotone.model;

public class Overlap {

    AbstractShape s1;
    AbstractShape s2;
    double area;

    public Overlap(AbstractShape s1, AbstractShape s2, double area) {
        super();
        this.s1 = s1;
        this.s2 = s2;
        this.area = area;
    }

    public AbstractShape getShape1() {
        return s1;
    }

    public AbstractShape getShape2() {
        return s2;
    }

    public double getArea() {
        return area;
    }

    public boolean involves(AbstractShape sa, AbstractShape sb) {
        return involves(sa) && involves(sb);
    }

    public boolean involves(AbstractShape s) {
        return (s1 == s) || (s2 == s);
    }
}
