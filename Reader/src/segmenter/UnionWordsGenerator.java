package segmenter;

/**
 * Generador de palabras para unir frases dentro de un mismo párrafo.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class UnionWordsGenerator extends WordsGenerator {

	/**
	 * Constructora de palabras unión para frases.
	 */
	public UnionWordsGenerator() {
		super();
		wordsStore.add("También");
		wordsStore.add("Además");
		wordsStore.add("Mientras");
		wordsStore.add("Durante lo cual");
		wordsStore.add("Mientras tanto");
		wordsStore.add("Y");
	}
}
