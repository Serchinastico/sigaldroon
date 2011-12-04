package coherence;

import mind.Mind;
import mind.Relation;

/**
 * Interfaz para un requisito en el mantenimiento de la coherencia de una historia.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public interface IRequisite {

	/**
	 * Comprueba que una relaci�n es coherente respecto a unos eventos dados.
	 * @param events Eventos a tener en cuenta.
	 * @param r Relaci�n a comprobar si mantiene coherencia.
	 * @return True si se mantiene coherencia contango con la relaci�n
	 */
	public boolean keepCoherence(Events events, Relation relation);
	
	/**
	 * Incluye los eventos relacionados con el requisito que se encuentren en la mente.
	 * @param events Estructura a la que a�adir los nuevos eventos.
	 * @param mind Mente de la que extraer nuevos eventos.
	 */
	public void assumeEvents(Events events, Mind mind);
	
}
