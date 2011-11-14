package segmenter;

import world.World;

/**
 * Interfaz para un creador de segmentos.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public interface ISegmenter {
	
	/**
	 * Genera un mini-mundo con los elementos y relaciones nuevos
	 * que se han generado en el mundo w teniendo encuenta los cambios.
	 * @param w Mundo que ha sufrido cambios.
	 * @param c Cambios en el mundo w.
	 */
	public World generateSegment(World w, Changes c);

}
