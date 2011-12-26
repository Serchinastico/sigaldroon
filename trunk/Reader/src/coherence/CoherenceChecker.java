package coherence;

import java.util.ArrayList;

import coherence.requisites.Death;
import coherence.requisites.Marriage;
import coherence.requisites.Requisite;
import coherence.requisites.VerbForm;

import mind.ChangedMind;
import mind.Mind;
import mind.Relation;

/**
 * Clase que implementa el chequeador de coherencia de historias.
 * Originalmente construido para chequear muertes y matrimonios.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class CoherenceChecker {

	/**
	 * Array con requisitos que deben cumplirse para mantener coherencia.
	 */
	private ArrayList<Requisite> requisites;
	
	/**
	 * Constructora por defecto.
	 */
	public CoherenceChecker() {
		requisites = new ArrayList<Requisite>();
		requisites.add(new VerbForm());
		requisites.add(new Death());
		requisites.add(new Marriage());
	}
	
	/**
	 * Comprueba si las relaciones conceptuales de una mente tienen coherencia
	 * respecto a unos eventos pasados.
	 * @param events Eventos sucedidos anteriormente.
	 * @param mind Mente de un lector con relaciones conceptuales.
	 * @return True si las relaciones mantienen coherencia con los eventos.
	 */
	public boolean checkCoherence(Events events, ChangedMind mind) {
		
		for (Relation relation : mind.getResultingRelations()) {
			for (Requisite requisite : requisites) {
				if (!requisite.keepCoherence(events, relation))
					return false;
			}
		}
		
		return true;
	}

	/**
	 * Crea una estructura de eventos nueva actualizada con las nuevas relaciones
	 * de una mente y eventos anteriores.
	 * @param events Eventos sucedidos anteriormente.
	 * @param mindRelations Nuevas relaciones.
	 * @return Eventos actualizados con las nuevas relaciones.
	 */
	public Events assumeEvents(Events events, Iterable<Relation> mindRelations) {
		Events eventsAssumed;
		
		// Comprueba por si es el segmento inicial y no hay eventos
		if (events == null) {
			eventsAssumed = new Events();
		}
		else
			eventsAssumed = events.copy();
		
		for (Requisite requisite : requisites) 
			requisite.assumeEvents(eventsAssumed, mindRelations);
		
		return eventsAssumed;
	}
	
	public static void main(String[] args) {
		CoherenceChecker cc = new CoherenceChecker();
		Events events = null;
		Mind mind = new Mind();
		Relation r1 = new Relation(1,"Heracles","Matar",null,null,"Hades");
		//Relation r2 = new Relation(1,"Hades","Amar",null,null,"Afrodita");
		Relation r3 = new Relation(1,"Heracles","Amar","Afrodita",null,"Megara");
		
		mind.add(r1);
		events = cc.assumeEvents(events, mind);
		
		ChangedMind cM = new ChangedMind(mind);
		//cM.insertChange(r1, r2, 1);
		cM.insertChange(r1, r3, 0);
		
		if (cc.checkCoherence(events, cM)) {
			System.out.print("Correcto");
		}
		else 
			System.out.print("Incorrecto.");
		
	}

}
