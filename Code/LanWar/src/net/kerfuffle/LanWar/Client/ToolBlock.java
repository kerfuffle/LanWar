package net.kerfuffle.LanWar.Client;

import static net.kerfuffle.LanWar.Client.Default.*;

import net.kerfuffle.Utilities.Quad;
import net.kerfuffle.Utilities.RGB;

public class ToolBlock implements Tool{

	private final String name = "Block";
	private final int id = BLOCK;
	private final String desc = "Creates a block. (No limit)";
	private final Quad icon = new Quad(0, 0, 50, 50, new RGB(1,1,1));
	
	public ToolBlock()
	{
		icon.setTexture(ICON_BLOCK);
	}
	
	/**
	 * 
	 */
	public void execute(int victimId)
	{
		
	}
	
	/**
	 * Refer to the implemented interface for further documentation of the following functions:
	 */
	public String getName()
	{
		return name;
	}
	public int getId()
	{
		return id;
	}
	public String getDesc()
	{
		return desc;
	}
	public void setIconPos(int x, int y)
	{
		icon.x = x;
		icon.y = y;
	}
	public void drawIcon()
	{
		icon.draw();
		//TODO
		//draw number of blocks left
	}
}
