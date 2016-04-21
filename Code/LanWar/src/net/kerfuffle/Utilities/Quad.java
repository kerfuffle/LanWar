package net.kerfuffle.Utilities;

import org.newdawn.slick.opengl.Texture;
import static net.kerfuffle.Utilities.Util.*;

import java.util.Random;

public class Quad {

	public float x, y, w, h;
	public RGB color;
	public Texture tex;
	private float originX, originY;
	//public Path path = new Path();
	//public Quad pieces[][];
	//public int piecesW, piecesH;
	public int texType;
	
	public Quad(float x, float y, float w, float h, RGB color)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
	}
	
	public void setOrigin(float x, float y)
	{
		originX = x;
		originY = y;
	}
	
	public void lockToOrigin()
	{
		x = originX+(x-originX);
		y = originY+(y-originY);
	}
	
	public void setTexture(Texture tex)
	{
		this.tex = tex;
	}
	
	public void draw()
	{
		color(color);
		if (tex != null)
		{
			quadTex(x, y, w, h, tex);
		}
		else
		{
			quad(x, y, w, h);
		}
	}
}
