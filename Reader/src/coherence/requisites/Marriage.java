package coherence.requisites;

import coherence.Events;

import mind.Relation;

/**
 * Requisito para el mantenimiento de coherencia respecto a los matrimonios
 * de elementos de la historia.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Marriage implements Requisite {

	@Override
	public boolean keepCoherence(Events events, Relation relation) {
		// No se puede casar alguien que ya está casado
		// Chequeo del source
		if (events.isMarried(relation.getSource())) return false;
		// Chequeo del target
		if (relation.getTarget() != null && events.isMarried(relation.getTarget())) return false;
		// Chequeo del OB
		if (relation.getDirectObject() != null && events.isMarried(relation.getDirectObject())) return false;
		return true;
	}

	@Override
	public void assumeEvents(Events events, Iterable<Relation> changedRelations) {	
		
		// Chequeamos los objetivos de las relaciones para incluirlos en los casados
		for (Relation r : changedRelations) 
			if (r.getAction().equals("Casar"))
				if (!events.isMarried(r.getSource()) && !events.isMarried(r.getTarget()))
					events.insertMarriage(r.getSource(),r.getTarget());
	}

	
}
