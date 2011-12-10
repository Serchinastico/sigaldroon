package coherence;

import java.util.HashSet;

import mind.Mind;
import mind.Relation;

/**
 * Requisito para el mantenimiento de coherencia respecto a los matrimonios
 * de elementos de la historia.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class MarriedChecker implements IRequisite {

	@Override
	public boolean keepCoherence(Events events, Relation relation) {
		// No se puede casar alguien que ya est� casado
		// Chequeo del source
		if (events.isMarried(relation.getSource())) return false;
		// Chequeo del target
		if (relation.getTarget() != null && events.isMarried(relation.getTarget())) return false;
		// Chequeo del OB
		if (relation.getDirectObject() != null && events.isMarried(relation.getDirectObject())) return false;
		return true;
	}

	@Override
	public void assumeEvents(Events events, Mind mind) {
		HashSet<String> relations = new HashSet<String>();		
		relations.add("Casar");
		Iterable<Relation> marriageRelations = mind.getRelations(relations).get("Casar");
		// Chequeamos los objetivos de las relaciones para incluirlos en los casados
		for (Relation r : marriageRelations) 
			if (!events.isMarried(r.getSource()) && !events.isMarried(r.getTarget()))
				events.insertMarriage(r.getSource(),r.getTarget());
	}

	
}