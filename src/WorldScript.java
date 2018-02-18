import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;

import javax.script.*;

public class WorldScript implements Runnable{

	private final String script;
	private BufferedImage window;
	private PrintStream out;
	private int seed;
	
	public WorldScript(String script, BufferedImage window, PrintStream out, int seed)
	{
		this.out = out;
		this.seed = seed;
		this.script = script;
		this.window = window;
	}

	@Override
	public void run() {
	try {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		engine.put("seed", seed);
		engine.put("window", window);
		engine.put("out", out);
		engine.eval(script);
	} catch (Exception ex) {
		ex.printStackTrace();
	}
}



}
