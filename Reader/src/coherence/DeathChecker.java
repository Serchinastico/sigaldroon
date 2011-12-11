package coherence;

import java.util.ArrayList;

import mind.Relation;

/**
 * Requisito para el mantenimiento de coherencia respecto a las muertes
 * de elementos de la historia.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class DeathChecker implements IRequisite {

	@Override
	public boolean keepCoherence(Events events, Relation relation) {
		// Ningún elemento que interviene en la relación puede estar muerto
		// Chequeo del source
		if (events.isDeath(relation.getSource())) return false;
		// Chequeo del target
		if (relation.getTarget() != null && events.isDeath(relation.getTarget())) return false;
		return true;
	}

	@Override
	public void assumeEvents(Events events, ArrayList<Relation> changedRelations) {
		
		// Chequeamos los objetivos de las relaciones para incluirlos en los muertos
		for (Relation r : changedRelations) 
			if (r.getAction().equals("Matar"))
				if (r.getTarget() != null && !events.isDeath(r.getTarget()))
					events.insertDeath(r.getTarget());
	}

}
