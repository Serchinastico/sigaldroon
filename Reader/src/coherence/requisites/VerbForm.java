package coherence.requisites;

import segmenter.syntax.VerbBridge;
import mind.Relation;
import mind.ontobridge.OntoBridgeSingleton;
import coherence.Events;
import es.ucm.fdi.gaia.ontobridge.OntoBridge;

public class VerbForm implements Requisite {

	@Override
	public boolean keepCoherence(Events events, Relation r) {
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		if (ob.existsClass(r.getAction()))
			return true;
		else
			return coherenceDirectObject(r) && coherenceIndirectObject(r);
	}

	@Override
	public void assumeEvents(Events events, Iterable<Relation> changedRelations) {
	}
	
	private boolean coherenceDirectObject(Relation r) {
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		VerbBridge vB = VerbBridge.getInstance();
		String verb = r.getAction();
		String directObject = r.getDirectObject();
		
		// Se toma como coherente si la relación no tiene incluido objeto directo 
		if (directObject == null) return true;
		
		// Desde aquí parte de que la relación TIENE objeto directo
		// por eso si el verbo no puede tenerlo, no es coherente
		if (!vB.canHaveDirectObject(verb)) return false;
		
		// Desde aquí el verbo puede tener OD y la relación tiene OD
		// queda comprobar que es del tipo pedido por el verbo
		
		// Si vale tanto Actor como Objeto es coherente
		String directObjectType = vB.getDirectObjectType(verb);
		if (directObjectType == null) {
			int x = 2;
			int y = x + 2;
		}
		if (directObjectType.equals("Nombre")) return true;
		
		// Si no, comprobamos que el objeto directo de la relación es subclase
		// del tipo pedido por el verbo
		return ob.isInstanceOf(directObject, directObjectType) || directObject.equals(directObjectType);
	}
	
	private boolean coherenceIndirectObject(Relation r) {
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		VerbBridge vB = VerbBridge.getInstance();
		String verb = r.getAction();
		String indirectObject = r.getTarget();
		
		// Se toma como coherente si la relación no tiene incluido objeto indirecto 
		if (indirectObject == null) return true;
		
		// Desde aquí parte de que la relación TIENE objeto indirecto
		// por eso si el verbo no puede tenerlo, no es coherente
		if (!vB.canHaveIndirectObject(verb)) return false;
		
		// Desde aquí el verbo puede tener OI y la relación tiene OI
		// queda comprobar que es del tipo pedido por el verbo
		
		// Si vale tanto Actor como Objeto es coherente
		String indirectObjectType = vB.getIndirectObjectType(verb);
		if (indirectObjectType.equals("Nombre")) return true;
		
		// Si no, comprobamos que el objeto indirecto de la relación es subclase
		// del tipo pedido por el verbo
		return ob.isInstanceOf(indirectObject, indirectObjectType) || indirectObject.equals(indirectObjectType);
	}

}
