package tester;
import java.io.File;

import com.flowpowered.noise.module.source.Billow;

import worldUtils.ShortMap;
import worldUtils.WorldUtils;

@SuppressWarnings("serial")
public class Main
{
	
	public static void main(String[] args) throws InterruptedException 
	{		
		System.out.println(System.getProperty("java.class.path"));
		
		ShortMap sh = WorldUtils.noise(new Billow(), 300, 300);
		sh.Export("/home/fidgetsinner/img.png");
	}

}

