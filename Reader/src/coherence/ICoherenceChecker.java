package coherence;

import java.util.ArrayList;

import mind.ChangedMind;
import mind.Mind;
import mind.Relation;

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
	public boolean checkCoherence(Events events, ChangedMind mind);
	
	/**
	 * Crea una estructura de eventos nueva actualizada con las nuevas relaciones
	 * de una mente y eventos anteriores.
	 * @param events Eventos sucedidos anteriormente.
	 * @param mindRelations Nuevas relaciones.
	 * @return Eventos actualizados con las nuevas relaciones.
	 */
	public Events assumeEvents(Events events, ArrayList<Relation> mindRelations);
	
	/**
	 * Crea una estructura de eventos con una mente.
	 * @param m Mente sobre la cual crear la estructura de eventos.
	 * @return Events extraídos de la mente.
	 */
	public Events assumeInitialEvents(Mind m);
	
}
