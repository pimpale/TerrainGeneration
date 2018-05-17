package worldBuilder;

import java.io.PrintStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScript {
	private final ScriptEngine engine; 
	private String script;
	private final PrintStream out;
	public JavaScript(String code, PrintStream out)
	{
		engine = new ScriptEngineManager().getEngineByName("javascript");
		setScript(code);
		this.out = out;
		put("out",out);
	}
	
	public String getScript()
	{
		return script;
	}
	
	public void setScript(String script)
	{
		this.script = script;
	}
	public void put(String key, Object value)
	{
		engine.put(key, value);
	}
	
	public void eval() throws ScriptException
	{
		engine.eval(script);
	}
	
	
}
