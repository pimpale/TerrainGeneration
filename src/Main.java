import java.io.File;

@SuppressWarnings("serial")
public class Main
{
	
	public static void main(String[] args) throws InterruptedException 
	{
		WorldTemplateChooser w = new WorldTemplateChooser(new File("./Resources/Templates"));
		w.run();
		WorldTemplate selected = w.getSelected();
		
		
	}

}

