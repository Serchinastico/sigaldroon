package coherence;

import mind.Mind;
import mind.Relation;

/**
 * Interfaz para un requisito en el mantenimiento de la coherencia de una historia.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public interface IRequisite {

	/**
	 * Comprueba que una relación es coherente respecto a unos eventos dados.
	 * @param events Eventos a tener en cuenta.
	 * @param r Relación a comprobar si mantiene coherencia.
	 * @return True si se mantiene coherencia contango con la relación
	 */
	public boolean keepCoherence(Events events, Relation relation);
	
	/**
	 * Incluye los eventos relacionados con el requisito que se encuentren en la mente.
	 * @param events Estructura a la que añadir los nuevos eventos.
	 * @param mind Mente de la que extraer nuevos eventos.
	 */
	public void assumeEvents(Events events, Mind mind);
	
}
