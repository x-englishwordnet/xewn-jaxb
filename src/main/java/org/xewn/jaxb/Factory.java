package org.xewn.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

public class Factory
{
	private Factory()
	{
	}

	public static LexicalResource make(final File xmlFile) throws IOException, IOException, JAXBException, XMLStreamException
	{
		XMLInputFactory xif = XMLInputFactory.newFactory();
		XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(xmlFile));

		JAXBContext jaxbContext = JAXBContext.newInstance(LexicalResource.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		return (LexicalResource) jaxbUnmarshaller.unmarshal(xsr);
	}
}
