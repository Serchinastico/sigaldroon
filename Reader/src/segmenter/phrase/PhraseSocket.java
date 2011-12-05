package segmenter.phrase;

import java.util.ArrayList;

import mind.Relation;

public abstract class PhraseSocket {

	public abstract String generatePhrase(ArrayList<Relation> phraseRelations);
}
