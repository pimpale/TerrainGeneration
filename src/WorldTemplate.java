import java.io.File;

public class WorldTemplate {
	
	private File mainJS;
	
	private String name;
	
	private String description;
	
	public WorldTemplate(File parentDirectory)
	{
		File[] children = parentDirectory.listFiles();
		for(int i = 0; i < 0; i++)
		{
			File child = children[i];
			if(child.isFile() && child.getName().equals("main.js"))
			{
				mainJS = child;
			}
		}
		
		
		
	}
}
