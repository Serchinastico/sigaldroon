package reader;

import world.World;

/**
 * Interfaz del lector.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public interface IReader {

	/**
	 * Genera una historia.
	 * @return Los segmentos generados que contiene la historia.
	 */
	public World[] generateStory();
}
