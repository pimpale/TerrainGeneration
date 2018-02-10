import java.io.File;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class RawObject {
	
	public abstract String getName();

	public static Element getXmlRootElement(String filename) throws Exception
	{
		return getXmlDocument(filename).getDocumentElement();
	}
	
	public static Document getXmlDocument(String filename) throws Exception
	{
		DocumentBuilderFactory dbFactory 
		= DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new File(filename));
		doc.getDocumentElement().normalize();
		return doc;
	}
}
