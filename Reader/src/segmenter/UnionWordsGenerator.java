package segmenter;

/**
 * Generador de palabras para unir frases dentro de un mismo p�rrafo.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class UnionWordsGenerator extends WordsGenerator {

	/**
	 * Constructora de palabras uni�n para frases.
	 */
	public UnionWordsGenerator() {
		super();
		wordsStore.add("Tambi�n");
		wordsStore.add("Adem�s");
		wordsStore.add("Mientras");
		wordsStore.add("Durante lo cual");
		wordsStore.add("Mientras tanto");
		wordsStore.add("Y");
	}
}
