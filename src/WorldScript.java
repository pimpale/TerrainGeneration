import java.awt.image.BufferedImage;
import java.io.File;
import javax.script.*;

public class WorldScript implements Runnable{

	private final String script;
	private BufferedImage window;
	private int seed;
	
	public WorldScript(String script, BufferedImage window, int seed)
	{
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
		engine.eval(script);
	} catch (Exception ex) {
		ex.printStackTrace();
	}
}



}
