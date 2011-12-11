package coherence;

import java.util.ArrayList;

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
public class CoherenceChecker implements ICoherenceChecker {

	/**
	 * Array con requisitos que deben cumplirse para mantener coherencia.
	 */
	private ArrayList<IRequisite> requisites;
	
	/**
	 * Constructora por defecto.
	 */
	public CoherenceChecker() {
		requisites = new ArrayList<IRequisite>();
		requisites.add(new DeathChecker());
		requisites.add(new MarriedChecker());
	}
	
	@Override
	public boolean checkCoherence(Events events, ChangedMind mind) {
		
		for (Relation relation : mind.getResultingRelations()) {
			for (IRequisite requisite : requisites) {
				if (!requisite.keepCoherence(events, relation))
					return false;
			}
		}
		
		return true;
	}

	@Override
	public Events assumeEvents(Events events, ArrayList<Relation> mindRelations) {
		Events eventsAssumed = events.copy();
		for (IRequisite requisite : requisites) 
			requisite.assumeEvents(events, mindRelations);
		return eventsAssumed;
	}
	
	@Override
	public Events assumeInitialEvents(Mind m) {
		ArrayList<Relation> initialRelations = new ArrayList<Relation>();
		for (Relation r : m) {
			initialRelations.add(r);
		}
		return assumeEvents(new Events(), initialRelations);
	}

}
