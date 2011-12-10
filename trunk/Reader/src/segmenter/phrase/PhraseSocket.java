package segmenter.phrase;

import java.util.ArrayList;

import mind.Relation;

/**
 * Clase abstracta para generar frases a partir de relaciones.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public abstract class PhraseSocket {

	/**
	 * Genera el texto de una frase a partir de un listado de relaciones.
	 * @param phraseRelations Relaciones sobre las que crear la frase.
	 * @return El string con el texto de la frase.
	 */
	public abstract String generatePhrase(ArrayList<Relation> phraseRelations);
}
