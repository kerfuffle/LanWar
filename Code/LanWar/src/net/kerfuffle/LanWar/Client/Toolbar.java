package net.kerfuffle.LanWar.Client;

import java.util.ArrayList;

import net.kerfuffle.Utilities.RGB;

import static net.kerfuffle.LanWar.Client.Default.*;

public class Toolbar {

	private int baseX, baseY;
	
	private ArrayList <Tool> tools = new ArrayList<Tool>();
	
	public Toolbar()
	{
		
	}
	
	public void update()
	{
		
	}
	
	/**
	 * Player picks up tool
	 * Set by server (after server confirms that player did pick up item)
	 * @param t		tool to be added
	 */
	public void addTool(Tool t)
	{
		tools.add(t);
	}
	
	/**
	 * Player drops tool
	 * Player uses tool
	 * Set by server (after server confirms that player can drop/use tool)
	 * @param t		tool to be removed
	 */
	public void removeTool(Tool t)
	{
		tools.remove(t);
	}
	public void removeTool(int i)
	{
		tools.remove(i);
	}
	
}
