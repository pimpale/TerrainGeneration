import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

public class WorldLauncher {
	
	private JFrame frame;
	
	private static WorldTemplate[] getWorldTemplates(File parentDirectory)
	{
		ArrayList<File> possibleTemplates = new ArrayList<File>();
		
		if(!parentDirectory.isDirectory())
		{
			throw new IllegalArgumentException("Directory not valid");
		}
		
		possibleTemplates.add(parentDirectory);
		
		File[] children = parentDirectory.listFiles();
		for(int i = 0; i < children.length; i++)
		{
			File child = children[i];
			if(child.isDirectory())
			{
				possibleTemplates.add(child);	
			}
		}
		
		ArrayList<WorldTemplate> templates = new ArrayList<WorldTemplate>();
		
		for(int i = 0; i < possibleTemplates.size(); i++)
		{
			if(WorldTemplate.isTemplate(possibleTemplates.get(i)))
			{
				templates.add(new WorldTemplate(possibleTemplates.get(i)));
			}
		}
		
		return templates.toArray(new WorldTemplate[templates.size()]);
	}
	
	
	
	public WorldLauncher(File parentDirectory)
	{
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String[] s = new String[] {"hola", "hi", "ok"};
		
		JList<String> list = new JList<String>(s);
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		
		JScrollPane listScrollPane = new JScrollPane(list);
		JLabel picture = new JLabel();
		picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
		picture.setHorizontalAlignment(JLabel.CENTER);

		Canvas c = new Canvas();
		
		c.setBackground(Color.BLACK);
		
		JScrollPane pictureScrollPane = new JScrollPane(c);

		//Create a split pane with the two scroll panes in it.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				listScrollPane, pictureScrollPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);

		//Provide minimum sizes for the two components in the split pane.
		listScrollPane.setMinimumSize(new Dimension(100, 50));
		pictureScrollPane.setMinimumSize(new Dimension(100, 50));

		//Provide a preferred size for the split pane.
		splitPane.setPreferredSize(new Dimension(400, 200));
		
		frame.getContentPane().add(splitPane);
		frame.pack();
		frame.setVisible(true);
	}
}
