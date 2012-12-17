package com.moac.android.kolotone.model;

import android.util.SparseArray;

public class ColorMap {
	
	// Want to create a map between Colors and Frequencies
	private SparseArray<Float> map = new SparseArray<Float>();
	
	public float getNote(int color)
	{
		return map.get(color);
	}
	
	public void setNote(int color, float note)
	{
		map.put(color, note);
	}

}
