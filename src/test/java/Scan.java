import org.xewn.jaxb.*;

import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class Scan
{
	private final boolean verbose;

	public Scan(final boolean verbose)
	{
		this.verbose = verbose;
	}

	public void scanSenses(final LexicalResource lexicalResource)
	{
		assertNotNull(lexicalResource);
		final Lexicon lexicon = lexicalResource.getLexicon().get(0);
		assertNotNull(lexicon);

		for (LexicalEntry lexEntry : lexicon.getLexicalEntry())
		{
			Lemma lemma = lexEntry.getLemma();
			PartOfSpeechType pos = lemma.getPartOfSpeech();
			String writtenForm = lemma.getWrittenForm();
			if (this.verbose)
				System.out.printf("lemma %s %s%n", writtenForm, pos);

			for (Sense sense : lexEntry.getSense())
			{
				walkSense(sense);
				Synset synset = (Synset) sense.getSynset();
				walkSynset(synset);
				System.out.println();
			}
		}
	}

	public void scanSynsets(final LexicalResource lexicalResource)
	{
		assertNotNull(lexicalResource);
		final Lexicon lexicon = lexicalResource.getLexicon().get(0);
		assertNotNull(lexicon);
		for (Synset synset : lexicon.getSynset())
		{
			walkSynset(synset);
			System.out.println();
		}
	}

	public void walkSense(Sense sense)
	{
		String senseId = sense.getId();

		BigInteger n = sense.getN();
		BigInteger idx = sense.getSenseidx();
		BigInteger order = sense.getOrder();
		LexFileType lexFile = sense.getLexfile();
		String lexId = sense.getLexid();
		String identifier = sense.getIdentifier();
		String sensekey = sense.getSensekey();
		String adjHead = sense.getAdjHead();
		boolean isAdjHead = sense.isAdjIsHead();
		AdjPositionType adjposition = sense.getAdjposition();
		AdjPositionType adjPosition = sense.getAdjPosition();
		if (verbose)
			System.out.printf("senseid:'%s' n:%d idx:%d order:%d lexid=%s lexfile=%sidentifier:%s sensekey:%s adjpos:%s adjhead:%s %b%n", //
					senseId, n, idx, order, lexId, lexFile, sensekey, identifier, adjposition, adjHead, isAdjHead);

		// verb frames and templates
		List<Object> verbFrames = sense.getVerbFrames();
		if (verbFrames != null)
			for (Object verbFrame : verbFrames)
			{
				SyntacticBehaviour sb = (SyntacticBehaviour) verbFrame;
				String frame = sb.getSubcategorizationFrame();
				if (verbose)
					System.out.printf("verbframe: %s%n", frame);
			}
		List<Object> verbTemplates = sense.getVerbTemplates();
		if (verbTemplates != null)
			for (Object verbTemplate : verbTemplates)
			{
				SyntacticBehaviour sb = ((SyntacticBehaviour) verbTemplate);
				String template = sb.getSentenceTemplate();
				if (verbose)
					System.out.printf("verbtemplate: %s%n", template);
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
				if (verbose)
					System.out.printf("relation: %s to target lemma '%s' synset '%s'%n", type, targetLemma, targetSynset.getDefinition().get(0).getContent());
			}
		}
	}

	public void walkSynset(Synset synset)
	{
		String synsetid = synset.getId();
		PartOfSpeechType pos = synset.getPartOfSpeech();
		if (verbose)
			System.out.printf("synsetid: %s pos: '%s'%n", synsetid, pos);
		Definition definition = synset.getDefinition().get(0);
		if (verbose)
			System.out.printf("definition: '%s'%n", definition.getContent());
		for (Example example : synset.getExample())
		{
			if (this.verbose)
				System.out.printf("example: %s%n", definition.getContent());
		}
		for (SynsetRelation synsetRelation : synset.getSynsetRelation())
		{
			Synset target = (Synset) synsetRelation.getTarget();
			if (target != null) // local ref within this file
			{
				SynsetRelationType type = synsetRelation.getRelType();
				if (this.verbose)
					System.out.printf("relation: %s to target synset '%s'%n", type, target.getDefinition().get(0).getContent());
			}
		}
	}
}