package deprecatedButIStillWantToKeep;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import WorldBuilder.WorldTemplate;

public class WorldTemplateChooser implements Runnable, ListSelectionListener{
	
	private JFrame frame;
	private WorldTemplate[] templates;
	private JList<String> templateList;
	private JScrollPane listScrollPane;
	private JButton runButton;
	private JSplitPane leftSplitPane;
	private JTextArea descriptionArea;
	private JScrollPane descriptionScrollPane;
	private JSplitPane splitPane;
	
	private int selectedIndex = 0;
	
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
	
	private String[] getNames(WorldTemplate[] temps)
	{
		String[] s = new String[temps.length];
		for(int i = 0; i < temps.length; i++)
		{
			s[i] = temps[i].getName();
		}
		return s;
	}
	
	private JList<String> getJList(WorldTemplate[] temps)
	{
		JList<String> templateList = new JList<String>(getNames(temps));
		
		templateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		templateList.setSelectedIndex(0);
		templateList.addListSelectionListener(this);
		return templateList;
	}
	
	public WorldTemplate getSelected()
	{
		return templates[selectedIndex];
	}
	
	
	
	public WorldTemplateChooser(File parentDirectory)
	{
		templates = getWorldTemplates(parentDirectory);
		templateList = getJList(templates);
		listScrollPane = new JScrollPane(templateList);
		runButton = new JButton("Select World");
		runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	return;
            }
        });
		leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, listScrollPane, runButton);
		descriptionArea = new JTextArea(templates[0].getDescription());
		descriptionArea.setEditable(false);
		descriptionScrollPane = new JScrollPane(descriptionArea);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftSplitPane, descriptionScrollPane);
	}
	
	public void destroy()
	{
		frame.dispose();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		selectedIndex = arg0.getFirstIndex();
		WorldTemplate selectedTemplate = templates[selectedIndex];
		descriptionArea.setText(selectedTemplate.getDescription());
	}




	@Override
	public void run() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(splitPane);
		frame.pack();
		frame.setVisible(true);
	}
	
	
}
