package net.kerfuffle.Utilities;

public class Coord {

	private float x, y, z;
	
	public Coord(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	public void setY(float y)
	{
		this.y = y;
	}
	public void setZ(float z)
	{
		this.z = z;
	}
	
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}
	public float getZ()
	{
		return z;
	}
	
}
