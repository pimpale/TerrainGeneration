package worldBuilder;

import java.io.PrintStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScript {
	private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("javscript");
	private String script;
	private final PrintStream out;
	public JavaScript(String code, PrintStream out)
	{
		script = code;
		this.out = out;
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
