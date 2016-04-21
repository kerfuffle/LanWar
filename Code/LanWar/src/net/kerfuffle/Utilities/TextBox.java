package net.kerfuffle.Utilities;

import org.lwjgl.input.Mouse;
import static net.kerfuffle.Utilities.Util.*;
import static net.kerfuffle.LanWar.Client.Default.*;

public class TextBox {

	public Quad box;
	private Quad background;
	private String text = "";
	private int maxChars;
	private RGB highlightColor = new RGB(1, 1, 0);
	private RGB oldColor;
	private boolean selected = false, multiLine = false, editable = true;
	
	public TextBox(Quad box, RGB backgroundColor, int maxChars)
	{
		this.box = box;
		oldColor = backgroundColor;
		this.maxChars = maxChars;
		background = new Quad(box.x -5, box.y - 5, box.w+10, box.h+10, backgroundColor);
	}
	
	public void setEditable(boolean b)
	{
		editable = b;
	}
	public void setMultiLine(boolean b)
	{
		multiLine = b;
	}
	
	public void setHighlightColor(RGB color)
	{
		highlightColor = color;
	}
	
	public void setText(String str)
	{
		text = str;
	}
	
	public void update()
	{
		background.draw();
		box.draw();
		
		color(new RGB(0, 0, 0));
		if (multiLine)
		{
			drawString(text, box.x + 5, box.y + box.h-15);
		}
		else
		{
			drawString(text, box.x + 5, box.y + (box.h/2)-5);
		}
		
		
		if (isClick())
		{
			selected = true;
			background.color = highlightColor;
		}
		
		if (!onHover(background.x, background.y, background.w, background.h, originX + Mouse.getX(), originY + Mouse.getY()))
		{
			selected = false;
			background.color = oldColor;
		}
		
		if (selected && editable)
		{
			if (multiLine)
			{
				text = scanKeys(text, maxChars);
			}
			else
			{
				text = removeNewLine(scanKeys(text, maxChars));
			}
		}
	}
	
	public boolean isClick()
	{
		if (onClick(background, originX + Mouse.getX(), originY + Mouse.getY()))
		{
			return true;
		}
		return false;
	}
	
	public String getText()
	{
		return text;
	}
	
	
	private String removeNewLine(String str)
	{
		String ret = "";
		
		String sp[] = str.split("\n");
		
		for (int i = 0; i < sp.length; i++)
		{
			ret+=sp[i];
		}
		
		return ret;
	}
}