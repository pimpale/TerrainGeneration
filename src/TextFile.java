import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class TextFile {
	final String fileName;
	
	public TextFile(String filename)
	{
		fileName = filename;
	}
	
	
	public void SetFile(String[] file)
	{
		erase();
		
		try(BufferedWriter w = new BufferedWriter(new FileWriter(new File(fileName))))
		{
			for(int i = 0; i < file.length; i++)
			{
				w.write(file[i]);
				w.newLine();
			}
			w.close();
		}
		catch(Throwable t)
		{
			
		}	
	}

	public String[] getFile()
	{
		try(Scanner sc= new Scanner(new File(fileName))) {
			ArrayList<String> s = new ArrayList<String>();
			while(sc.hasNextLine())
			{
				s.add(sc.nextLine());
			}
			return s.toArray(new String[]{});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void erase()
	{
		try(RandomAccessFile f = new RandomAccessFile(fileName, "rw")) {
			f.setLength(0);
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
