package segmenter.paragrah;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import mind.Relation;

import segmenter.UnionWordsGenerator;
import segmenter.WordsGenerator;
import segmenter.phrase.BasicPhrase;
import segmenter.phrase.PhraseSocket;

public class BasicParagraph extends ParagraphSocket {

	/**
	 * Generador de palabras para unir frases entre p�rrafos.
	 */
	private WordsGenerator unionWords;
	
	/**
	 * Generador de frases.
	 */
	private PhraseSocket phraseGen;

	/**
	 * Constructora por defecto.
	 */
	public BasicParagraph() {
		unionWords = new UnionWordsGenerator();
		phraseGen = new BasicPhrase();
	}

	/**
	 * Genera un p�rrafo que especifica primero el lugar si lo hay,
	 * y luego cuenta las frases anexionando acciones realizadas por el mismo
	 * personaje y unidas por palabras de uni�n entre frases.
	 * @param paragraphRelations Relaciones a contar.
	 * @return El texto con el p�rrafo.
	 */
	public String generateParagraph(ArrayList<Relation> paragraphRelations) {
		String paragraph = "";

		// Establece si hay un lugar para todas las relaciones o no est� especificado
		if (paragraphRelations.get(0).getPlace() != null) {
			paragraph += "En " + paragraphRelations.get(0).getPlace() +", ";
		}

		// Categorizamos las relaciones por Source
		HashMap<String, ArrayList<Relation>> relationsBySource = new HashMap<String, ArrayList<Relation>>();
		for (Relation relation : paragraphRelations) {
			if (relationsBySource.containsKey(relation.getSource())) {
				relationsBySource.get(relation.getSource()).add(relation);
			}
			else {
				ArrayList<Relation> newSourceRelation = new ArrayList<Relation>();
				newSourceRelation.add(relation);
				relationsBySource.put(relation.getSource(), newSourceRelation);
			}
		}

		boolean start = true;
		// Para cada categor�a de Source, se crea una frase
		for (Entry<String, ArrayList<Relation>> entry : relationsBySource.entrySet()) {
			if (start) 
				start = false;
			else 
				paragraph += unionWords.getNextWord() + ", ";
			paragraph += phraseGen.generatePhrase(entry.getValue()) + " ";
		}

		paragraph += "\n";
		return paragraph;
	}

}
