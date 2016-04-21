package net.kerfuffle.LanWar.Client;

public interface Tool {

	public String getName();
	
	public int getId();
	
	public String getDesc();
	
	public void execute(int victimId);
	
	public void drawIcon();
	
	public void setIconPos(int x, int y);
}
