package segmenter.paragrah;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import mind.Relation;

import segmenter.UnionWordsGenerator;
import segmenter.WordsGenerator;
import segmenter.phrase.BasicPhrase;
import segmenter.phrase.PhraseSocket;
import segmenter.syntax.NameBridge;

public class BasicParagraph extends ParagraphSocket {

	/**
	 * Generador de palabras para unir frases entre párrafos.
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
	 * Genera un párrafo que especifica primero el lugar si lo hay,
	 * y luego cuenta las frases anexionando acciones realizadas por el mismo
	 * personaje y unidas por palabras de unión entre frases.
	 * @param paragraphRelations Relaciones a contar.
	 * @return El texto con el párrafo.
	 */
	public String generateParagraph(ArrayList<Relation> paragraphRelations) {
		String paragraph = "";
		boolean capitalize;

		// Establece si hay un lugar para todas las relaciones o no está especificado
		if (paragraphRelations.get(0).getPlace() != null) {
			paragraph += "En " + NameBridge.getInstance().getName(paragraphRelations.get(0).getPlace()) +", ";
			capitalize = false;
		}
		else
			capitalize = true;

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
		// Para cada categoría de Source, se crea una frase
		for (Entry<String, ArrayList<Relation>> entry : relationsBySource.entrySet()) {
			String phrase = "";
			if (start) 
				start = false;
			else 
				paragraph += unionWords.getNextWord() + ", ";
			phrase = phraseGen.generatePhrase(entry.getValue()) + " ";
			if (capitalize) {
				phrase = firstLetterToUppercase(phrase);
				capitalize = false;
			}
			
			paragraph += phrase;
		}

		paragraph += "\n";
		return paragraph;
	}
	
	/**
	 * Convierte la primera letra de la palabra a mayúsculas.
	 * @param word Palabra cuya primera letra convertir a mayúsculas.
	 * @return La palabra con la primera letra en mayúsculas.
	 */
	private String firstLetterToUppercase(String word) {
		return word.substring(0,1).toUpperCase() + word.substring(1);
	}

}
