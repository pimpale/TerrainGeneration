package worldBuilder;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;

import javax.script.*;

public class WorldScript implements Runnable 
{
	private final JavaScript script;
	private Canvas window;
	private PrintStream out;
	private int seed;
	
	public WorldScript(JavaScript script, Canvas window, int seed)
	{
		this.script = script;
		this.window = window;
		
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
			ScriptEngineManager factory = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName("javascript");
			engine.put("seed", seed);
			engine.put("window", window);
			engine.put("out", out);
			engine.eval(script);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
