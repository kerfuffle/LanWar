package net.kerfuffle.Utilities;

public class Size {

	private float w, h, d, r;
	
	public Size(float w, float h)
	{
		this.w = w;
		this.h = h;
	}
	
	public void setWidth(float w)
	{
		this.w = w;
	}
	public void setHeight(float h)
	{
		this.h = h;
	}
	public void setRadius(float r)
	{
		this.r = r;
	}
	public void setDepth(float d)
	{
		this.d = d;
	}
	
	public float getDepth()
	{
		return d;
	}
	public float getHeight()
	{
		return h;
	}
	public float getWidth()
	{
		return w;
	}
	public float getRadius()
	{
		return r;
	}
}
