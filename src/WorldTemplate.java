import java.io.File;

public class WorldTemplate {
	
	private final File parentDirectory;
	
	private final File mainJS;
	
	private final String name;
	
	private final String description;
	
	public WorldTemplate(File parentDirectory)
	{
		if(!parentDirectory.isDirectory())
		{
			throw new IllegalArgumentException("Not a directory");
		}
		this.parentDirectory = parentDirectory;
		name = new TextFile(parentDirectory.getAbsolutePath()+"/name").getContent();
		description = new TextFile(parentDirectory.getAbsolutePath()+"/description").getContent();;
		mainJS = new File(parentDirectory.getAbsolutePath()+"/main.js");
	}
	
	public String getPath()
	{
		return parentDirectory.getAbsolutePath();
	}

	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public File getMain()
	{
		return mainJS;
	}
	
	public static boolean isTemplate(File f)
	{
		//I'm a genius, aren't I?
		try
		{
			new WorldTemplate(f);
			return true;
		}
		catch(Exception e)//God knows what could happen
		{
			return false;
		}
	}
	
}
