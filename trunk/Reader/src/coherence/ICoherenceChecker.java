package coherence;

import mind.Mind;

/**
 * Interfaz del comprobador de coherencia de historias.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public interface ICoherenceChecker {

	/**
	 * Comprueba si las relaciones conceptuales de una mente tienen coherencia
	 * respecto a unos eventos pasados.
	 * @param events Eventos sucedidos anteriormente.
	 * @param mind Mente de un lector con relaciones conceptuales.
	 * @return True si las relaciones mantienen coherencia con los eventos.
	 */
	public boolean checkCoherence(Events events, Mind mind);
	
	/**
	 * Crea una estructura de eventos nueva actualizada con las nuevas relaciones
	 * de una mente y eventos anteriores.
	 * @param events Eventos sucedidos anteriormente.
	 * @param mind Mente con nuevas relaciones.
	 * @return Eventos actualizados con las nuevas relaciones.
	 */
	public Events assumeEvents(Events events, Mind mind);
}
