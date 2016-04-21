package net.kerfuffle.LanWar.Client;

import static net.kerfuffle.LanWar.Client.Default.*;

import net.kerfuffle.Utilities.Quad;
import net.kerfuffle.Utilities.RGB;

public class ToolDimensionalDrift implements Tool{

	private final String name = "DimensionalDrift";
	private final int id = DIM_DRIFT;
	private final String desc = "Drifts player into another dimension for a few seconds and drifts them back. (10 secs)";
	private final Quad icon = new Quad(0, 0, 50, 50, new RGB(1,1,1));
	
	public ToolDimensionalDrift()
	{
		icon.setTexture(ICON_DIM_DRIFT);
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
