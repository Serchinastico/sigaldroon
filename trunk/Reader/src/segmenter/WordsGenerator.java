package segmenter;

import java.util.ArrayList;

/**
 * Clase abstracta para hacer un generador de palabras a partir de un
 * almacén de palabras concreto.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public abstract class WordsGenerator {

	/**
	 * Palabras que pueden ser generadas.
	 */
	protected ArrayList<String> wordsStore;
	
	/**
	 * Índice de la última palabra generada en el array.
	 */
	protected int index;
	
	/**
	 * Constructora por defecto.
	 */
	public WordsGenerator() {
		index = 0;
		wordsStore = new ArrayList<String>();
	}
	
	/**
	 * Funcionamiento por defecto para la generación de palabras.
	 * Coger la siguiente en el almacén de palabras.
	 * @return La palabra generada.
	 */
	public String getNextWord() {
		index++;
		if (index >= wordsStore.size())
			index = 0;
		return wordsStore.get(index);
	}
	
}
