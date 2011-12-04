package coherence;

import java.util.ArrayList;

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
	
	@Override
	public boolean checkCoherence(Events events, Mind mind) {
		
		for (Relation relation : mind) {
			for (IRequisite requisite : requisites) {
				if (!requisite.keepCoherence(events, relation))
					return false;
			}
		}
		
		return true;
	}

	@Override
	public Events assumeEvents(Events events, Mind mind) {
		Events eventsAssumed = events.copy();
		for (IRequisite requisite : requisites) 
			requisite.assumeEvents(events, mind);
		return eventsAssumed;
	}

}
