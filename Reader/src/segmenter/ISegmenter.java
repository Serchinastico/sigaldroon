package segmenter;

import mind.Mind;
import mind.ChangedMind;

/**
 * Interfaz para un creador de segmentos.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public interface ISegmenter {
	
	/**
	 * Genera una mini-mente con los elementos y relaciones nuevos
	 * que se han generado en la mente m teniendo en cuenta los cambios.
	 * @param m Mente que ha sufrido cambios.
	 * @param c Cambios en la mente m.
	 */
	public Mind generateSegment(ChangedMind m);

}
