package reader;

import mind.Mind;
import mind.ChangedMind;

/**
 * Interfaz para modificar mentes de lectores.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public interface IMindEvolver {

	/**
	 * Cambia la mente (conceptos que tiene la mente).
	 * @param mind Mente a evolucionar.
	 * @return La mente con los conceptos cambiados tras la evolución.
	 */
	public ChangedMind evolveMind(Mind mind);
	
	/**
	 * Obtiene el número de iteraciones en la generación del segmento.
	 * @return
	 */
	public int getNumIterations();
	
	/**
	 * Establece el número de iteraciones para la generación de un segmento.
	 * @param numIt El nuevo número de iteraciones.
	 */
	public void setNumIterations(int numIt);
	
}
