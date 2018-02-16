import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextFile {
	private final String filePath;
	private final File file;
	
	public TextFile(String filename)
	{
		filePath = filename;
		file = new File(filePath);
	}
	
	public TextFile(File file)
	{
		if(file.is)
		this.file = file;
		filePath = file.getAbsolutePath();
	}
	
	public void append(String s)
	{	
		try {
			FileWriter w = new FileWriter(file);
			w.append(s);
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getContent()
	{
		try {
			byte[] content = Files.readAllBytes(Paths.get(filePath));
			return new String(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void erase()
	{
		try(RandomAccessFile f = new RandomAccessFile(filePath, "rw")) {
			f.setLength(0);
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
