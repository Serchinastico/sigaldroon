package segmenter;

import mind.ChangedMind;
import mind.Mind;

/**
 * Interfaz para un creador de segmentos.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public interface ISegmenter {
	
	/**
	 * Genera el texto del segmento para una mente.
	 * @param m Mente sobre la que realizar la generación.
	 * @return El string contando lo que hay en la mente.
	 */
	public String generateInitialSegment(Mind m);
	
	/**
	 * Genera el texto asociado con los elementos y relaciones nuevos
	 * que se han generado en la mente m teniendo en cuenta los cambios.
	 * @param m Mente que ha sufrido cambios.
	 * return El texto del nuevo segmento.
	 */
	public String generateSegment(ChangedMind m);

}
