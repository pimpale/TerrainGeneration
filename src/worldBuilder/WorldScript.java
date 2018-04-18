package worldBuilder;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;

import javax.script.*;

public class WorldScript implements Runnable{

	private final String script;
	private Canvas window;
	private PrintStream out;
	
	public WorldScript(File script, Canvas window, PrintStream out)
	{
		this(new TextFile(script).getContent(), window, out);
	}
	
	public WorldScript(String script, Canvas window, PrintStream out)
	{
		this.out = out;
		this.script = script;
		this.window = window;
	}
	
	@Override
	public void run() {
	try {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("javascript");
		engine.put("window", window);
		engine.put("out", out);
		engine.eval(script);
	} catch (Exception ex) {
		ex.printStackTrace();
	}
}



}
