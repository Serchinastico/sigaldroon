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
	
	/**
	 * Obtiene el n�mero de iteraciones en la generaci�n del segmento.
	 * @return
	 */
	public int getNumIterations();
	
	/**
	 * Establece el n�mero de iteraciones para la generaci�n de un segmento.
	 * @param numIt El nuevo n�mero de iteraciones.
	 */
	public void setNumIterations(int numIt);
	
}
