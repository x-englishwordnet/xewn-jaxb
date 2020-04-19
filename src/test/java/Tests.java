import org.junit.Test;
import org.xewn.jaxb.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class Tests
{
	@Test public void mainTestXX() throws IOException, IOException, JAXBException
	{
		String xewnHome = System.getenv("XEWNHOME") + File.separator + "BUILD" + File.separator + "xsrc";
		final File xmlFile = new File(xewnHome, "wn-verb.body.xml");

		JAXBContext jaxbContext = JAXBContext.newInstance(LexicalResource.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		// jaxbUnmarshaller.setListener(new Unmarshaller.Listener()
		// {
		//		@Override public void afterUnmarshal(Object target, Object parent)
		//		{
		//		}
		// });
		LexicalResource lexicalResource = (LexicalResource) jaxbUnmarshaller.unmarshal(xmlFile);

		assertNotNull(lexicalResource);
		final Lexicon lexicon = lexicalResource.getLexicon().get(0);
		assertNotNull(lexicon);

		for (LexicalEntry entry : lexicon.getLexicalEntry())
		{
			Lemma lemma = entry.getLemma();
			PartOfSpeechType pos = lemma.getPartOfSpeech();
			String writtenForm = lemma.getWrittenForm();
			for (Sense sense : entry.getSense())
			{
				String id = sense.getId();
				Synset synset = (Synset) sense.getSynset();
				//System.out.printf("Synset %s%n", object.toString());

				BigInteger n = sense.getN();
				BigInteger idx = sense.getSenseidx();
				LexFileType lexFile = sense.getLexfile();
				String lexId = sense.getLexid();
				BigInteger order = sense.getOrder();
				String identifier = sense.getIdentifier();
				String sensekey = sense.getSensekey();
				AdjPositionType adjPosition = sense.getAdjPosition();
				String adjHead = sense.getAdjHead();
				boolean isAdjHead = sense.isAdjIsHead();

				// verb frames and templates
				List<Object> verbFrames = sense.getVerbFrames();
				if (verbFrames != null)
					for (Object object : verbFrames)
					{
						SyntacticBehaviour syntacticBehaviour = (SyntacticBehaviour) object;
						String verbFrame = syntacticBehaviour.getSubcategorizationFrame();
						// System.out.printf("verb frame %s%n", verbFrame);
					}
				List<Object> verbTemplates = sense.getVerbTemplates();
				if (verbTemplates != null)
					for (Object object : verbTemplates)
					{
						SyntacticBehaviour syntacticBehaviour = (SyntacticBehaviour) object;
						String verbTemplate = syntacticBehaviour.getSentenceTemplate();
						// System.out.printf("verb template %s%n", verbTemplate);
					}
				// lex relations
				for (SenseRelation senseRelation : sense.getSenseRelation())
				{
					Sense target = (Sense) senseRelation.getTarget();
					SenseRelationType type = senseRelation.getRelType();
					if (target != null) // local ref within this file
					{
						LexicalEntry targetLexEntry = (LexicalEntry) target.getParent();
						String targetLemma = targetLexEntry.getLemma().getWrittenForm();
						Synset targetSynset = (Synset) target.getSynset();
						System.out.printf("%s relation to target lemma '%s' synset '%s'%n", type, targetLemma, targetSynset.getDefinition().get(0).getContent());
					}
				}
			}
		}
		for (Synset synset : lexicon.getSynset())
		{
			PartOfSpeechType pos = synset.getPartOfSpeech();
			Definition definition = synset.getDefinition().get(0);
			for (Example example : synset.getExample())
			{
				example.getContent();
			}
			for (SynsetRelation synsetRelation : synset.getSynsetRelation())
			{
				Synset target = (Synset) synsetRelation.getTarget();
				SynsetRelationType type = synsetRelation.getRelType();
				Synset synset2 = (Synset) synsetRelation.getTarget();
			}
		}
	}
}