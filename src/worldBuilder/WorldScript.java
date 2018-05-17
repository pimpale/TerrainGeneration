package worldBuilder;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;

import javax.script.*;

public class WorldScript implements Runnable 
{
	private JavaScript script;
	private Canvas window;
	private int seed;
	
	public WorldScript(JavaScript script, Canvas window, int seed)
	{
		System.out.println(script.getScript());
		setScript(script);
		setCanvas(window);
		setSeed(seed);
		script.put("window", window);
		script.put("seed", seed);
	}
	
	public void setScript(JavaScript script)
	{
		this.script = script; 
	}

	public JavaScript getScript()
	{
		return script;
	}
	
	public void setCanvas(Canvas c)
	{
		window = c;
	}
	
	public Canvas getCanvas()
	{
		return window;
	}
	
	public void setSeed(int seed)
	{
		this.seed = seed;
	}
	
	public int getSeed()
	{
		return seed;
	}
	
	@Override
	public void run() {
		try {
			script.eval();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
