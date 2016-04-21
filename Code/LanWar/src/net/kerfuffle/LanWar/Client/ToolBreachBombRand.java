package net.kerfuffle.LanWar.Client;

import static net.kerfuffle.LanWar.Client.Default.*;

import net.kerfuffle.Utilities.Quad;
import net.kerfuffle.Utilities.RGB;

public class ToolBreachBombRand implements Tool{

	private final String name = "BreachBombRand";
	private final int id = BREACH_BOMB_RAND;
	private final String desc = "Creates a breach to a random position within the map. (No limit)";
	private final Quad icon = new Quad(0, 0, 50, 50, new RGB(1,1,1));
	
	public ToolBreachBombRand()
	{
		icon.setTexture(ICON_BREACH_BOMB_RAND);
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
	}
}
