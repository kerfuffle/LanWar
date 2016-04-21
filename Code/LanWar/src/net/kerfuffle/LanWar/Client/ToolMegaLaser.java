package net.kerfuffle.LanWar.Client;

import static net.kerfuffle.LanWar.Client.Default.*;

import net.kerfuffle.Utilities.Quad;
import net.kerfuffle.Utilities.RGB;

public class ToolMegaLaser implements Tool{

	private final String name = "MegaLaser";
	private final int id = MEGA_LASER;
	private final String desc = "Creates an impenetrable laser beam from the player's current position, infinitely expanding in the direction of the player's orientation. (5 secs)";
	private final Quad icon = new Quad(0, 0, 50, 50, new RGB(1,1,1));
	
	public ToolMegaLaser()
	{
		icon.setTexture(ICON_MEGA_LASER);
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
