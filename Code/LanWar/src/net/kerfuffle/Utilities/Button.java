package net.kerfuffle.Utilities;

import static net.kerfuffle.LanWar.Client.Default.*;
import static net.kerfuffle.Utilities.Util.*;

import org.lwjgl.input.Mouse;

public class Button{

	public Quad box;
	private String text;
	private RGB hoverColor = new RGB(1, 0, 0);
	private RGB oldColor;
	private boolean usable = true;
	private float x, y;
	
	public Button(String text, Quad box)
	{
		this.box = box;
		this.text = text;
		oldColor = box.color;
		
		x = box.x + (box.w/2) - (text.length()*3.7f);
		y = box.y + (box.h/2) - 5;
	}
	
	public void setText(String str)
	{
		text = str;
		
		x = box.x + (box.w/2) - (text.length()*3.7f);
		y = box.y + (box.h/2) - 5;
	}
	
	public boolean isClick()
	{
		if (usable)
		{
			if (onClick(box.x, box.y, box.w, box.h, originX + Mouse.getX(), originY + Mouse.getY()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void setUsable(boolean b)
	{
		usable = b;
		if (b)
		{
			box.color = oldColor;
		}
		else
		{
			box.color = new RGB(0.5f, 0.5f, 0.5f);
		}
	}
	
	public void update()
	{
		if (usable)
		{
			if (onHover(box.x, box.y, box.w, box.h, originX + Mouse.getX(), originY + Mouse.getY()))
			{
				box.color = hoverColor;
			}
			else
			{
				box.color = oldColor;
			}
		}
		
		box.draw();
		color(new RGB(0, 0, 0));
		drawString(text, x, y);
	}
}
