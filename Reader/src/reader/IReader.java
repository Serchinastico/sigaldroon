package reader;

import world.World;

/**
 * Interfaz del lector.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public interface IReader {

	/**
	 * Genera una historia.
	 * @return Los segmentos generados que contiene la historia.
	 */
	public World[] generateStory();
}
