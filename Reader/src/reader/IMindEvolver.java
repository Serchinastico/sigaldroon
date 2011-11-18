package reader;

import world.World;
import world.WorldChanged;

/**
 * Interfaz para modificar mentes de lectores.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public interface IMindEvolver {

	/**
	 * Cambia la mente (conceptos que tiene el mundo).
	 * @param mind Mente a evolucionar.
	 * @return El mundo con los conceptos cambiados tras la evoluci�n.
	 */
	public WorldChanged evolveMind(World mind);
	
}
