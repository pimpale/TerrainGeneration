import org.w3c.dom.Element;

public class Biome extends RawObject{

	final String Name;
	final int RainMin;
	final int RainMax;
	final int TempMin;
	final int TempMax;


	public Biome(Element e) throws Exception
	{
		Name = getText("name", e);
		RainMin = Integer.parseInt(getText("minimum_rainfall", e));
		RainMax = Integer.parseInt(getText("maximum_rainfall", e));
		TempMin = Integer.parseInt(getText("minimum_temperature",e));
		TempMax = Integer.parseInt(getText("maximum_temperature",e));
	}

	private String getText(String name, Element e)
	{
		String st = null;
		try
		{
			 st = e.getElementsByTagName(name)
				.item(0)
				.getTextContent();
		}
		catch(NullPointerException ex)
		{
		}
		return st;
	}

	@Override
	public String getName() {
		return Name;
	}
}
