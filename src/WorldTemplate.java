import java.io.File;

public class WorldTemplate {
	
	private final File parentDirectory;
	
	private File mainJS;
	
	private String name;
	
	private String description = "";
	
	public WorldTemplate(File parentDirectory)
	{
		this.parentDirectory = parentDirectory;
		name = parentDirectory.getName();
		File[] children = parentDirectory.listFiles();
		for(int i = 0; i < 0; i++)
		{
			File child = children[i];
			if(child.isFile())
			{
				String fileName = child.getName();
				if(fileName.equals("main.js"))
				{
					mainJS = child;
				}
				else if(fileName.equals("name"))
				{
					this.name = new TextFile(child).getContent();
				}
				else if(fileName.equals("name"))
				{
					this.description = new TextFile(child).getContent();
				}
			}
		}
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
