import org.junit.Before;
import org.junit.Test;
import org.xewn.jaxb.Factory;
import org.xewn.jaxb.LexicalResource;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class Tests
{
	private String TEST_FILE = "wn-verb.body.xml";

	private final String ewnHome = System.getenv("XEWNHOME") + File.separator + "xsrc";

	private LexicalResource lexicalResource;

	@Before public void init() throws JAXBException, IOException, XMLStreamException
	{
		final File xmlFile = new File(ewnHome, TEST_FILE);
		this.lexicalResource = Factory.make(xmlFile);
	}

	@Test public void scanSenses()
	{
		assertNotNull(this.lexicalResource);
		new Scan(true).scanSenses(this.lexicalResource);
	}

	@Test public void scanSynsets()
	{
		assertNotNull(this.lexicalResource);
		new Scan(true).scanSynsets(this.lexicalResource);
	}
}