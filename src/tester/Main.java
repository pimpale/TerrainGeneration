package tester;
import java.io.File;

import com.flowpowered.noise.module.source.Perlin;

import worldUtils.ShortMap;
import worldUtils.WorldUtils;

@SuppressWarnings("serial")
public class Main
{
	
	public static void main(String[] args) throws InterruptedException 
	{		
		
		Perlin p = new Perlin();
		
		ShortMap sh = WorldUtils.constantValue(0.5, 300, 300);
		//WorldUtils.scale(sh, 50, 0, 0, 300, 300);
		sh.Export("/home/fidgetsinner/img.png");
	}

}

