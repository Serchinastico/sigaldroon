package reader;

import mind.Mind;
import mind.ChangedMind;

/**
 * Interfaz para modificar mentes de lectores.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public interface IMindEvolver {

	/**
	 * Cambia la mente (conceptos que tiene la mente).
	 * @param mind Mente a evolucionar.
	 * @return La mente con los conceptos cambiados tras la evoluci�n.
	 */
	public ChangedMind evolveMind(Mind mind);
	
}
