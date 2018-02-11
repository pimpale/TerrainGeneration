import java.io.File;

public class WorldTemplate {
	
	private final File parentDirectory;
	
	private final File mainJS;
	
	private final String name;
	
	private final String description;
	
	public WorldTemplate(String parentDirectory)
	{
		this(new File(parentDirectory));
	}
	
	public WorldTemplate(File parentDirectory)
	{
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

}
