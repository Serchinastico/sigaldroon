package coherence;

import java.util.HashSet;

import mind.Mind;
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
	public void assumeEvents(Events events, Mind mind) {
		HashSet<String> relations = new HashSet<String>();		
		relations.add("Matar");
		Iterable<Relation> deathRelations = mind.getRelations(relations).get("Matar");
		// Chequeamos los objetivos de las relaciones para incluirlos en los muertos
		for (Relation r : deathRelations) 
			if (r.getTarget() != null && !events.isDeath(r.getTarget()))
				events.insertDeath(r.getTarget());
	}

}
