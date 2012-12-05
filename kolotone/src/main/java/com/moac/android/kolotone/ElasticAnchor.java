package com.moac.android.kolotone;

public class ElasticAnchor extends Anchor {

	private int kFactor = 1;
	
	public ElasticAnchor(int k)
	{
		this.kFactor = k;
	}
	
	public int getKFactor()
	{
		return kFactor;
	}
	
	public void setKFactor(int k)
	{
		kFactor = k;
	}
}
