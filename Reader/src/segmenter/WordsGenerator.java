package segmenter;

import java.util.ArrayList;

/**
 * Clase abstracta para hacer un generador de palabras a partir de un
 * almac�n de palabras concreto.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public abstract class WordsGenerator {

	/**
	 * Palabras que pueden ser generadas.
	 */
	protected ArrayList<String> wordsStore;
	
	/**
	 * �ndice de la �ltima palabra generada en el array.
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
	 * Funcionamiento por defecto para la generaci�n de palabras.
	 * Coger la siguiente en el almac�n de palabras.
	 * @return La palabra generada.
	 */
	public String getNextWord() {
		index++;
		if (index >= wordsStore.size())
			index = 0;
		return wordsStore.get(index);
	}
	
}
