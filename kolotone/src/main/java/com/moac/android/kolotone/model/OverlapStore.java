package com.moac.android.kolotone.model;

import java.util.ArrayList;
import java.util.List;

public class OverlapStore {

    private List<Overlap> overlaps = new ArrayList<Overlap>();

    public Overlap getOverlap(AbstractShape s1, AbstractShape s2) {
        for(Overlap o : overlaps) {
            if(o.involves(s1, s2)) {
                return o;
            }
        }
        return null;
    }

    public List<Overlap> getOverlaps(AbstractShape s) {
        List<Overlap> result = new ArrayList<Overlap>();

        for(Overlap o : overlaps) {
            if(o.involves(s)) {
                result.add(o);
            }
        }
        return result;
    }
}
