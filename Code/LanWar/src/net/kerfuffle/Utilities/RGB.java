package net.kerfuffle.Utilities;

public class RGB {

	private float r, g, b, a = -1;
	
	public RGB(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void setColor(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void setA(float a)
	{
		this.a = a;
	}
	
	public float A()
	{
		return a;
	}
	
	public float R()
	{
		return r;
	}
	public float G()
	{
		return g;
	}
	public float B()
	{
		return b;
	}
}
