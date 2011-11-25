package operator;

import java.util.ArrayList;
import java.util.Iterator;

import mind.Relation;
import mind.ChangedMind;
import mind.ontobridge.OntoBridgeSingleton;


/**
 * Operador para generalizar acciones subiendo un nivel en la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class Generalize extends OperatorSingle {
	
	/**
	 * Peso del operador.
	 */
	private float opWeight = 0.6f;
	
	/**
	 * Genera todos los hijos posibles para una relación intentando generalizar
	 * con subclases e instancias cada uno de sus elementos. 
	 * @param m Mente a operar.
	 * @param r Relación a operar.
	 * @param gM Listado actual de mentes generadas.
	 */
	protected void apply(ChangedMind m, Relation r, ArrayList<ChangedMind> gM) {
		
		for (int i = 0; i < OPTarget.NUM_TARGETS; i++) {
			
			Iterator<String> itSuperClasses = getSuperClasses(r,i);
			
			while (itSuperClasses.hasNext()) {
				String superClass = itSuperClasses.next();
				ChangedMind newMind = m.copy();
				// Cambio de la relación
				Relation newRelation = r.copy();
				applyChange(newRelation, superClass, i);
				// Cambio del peso
				newRelation.setWeight(r.getWeight() * opWeight);
				// Guardado del cambio
				newMind.getActualMind().remove(r); // Quitamos la antigua relación
				newMind.getActualMind().add(newRelation); // Ponemos la modificada
				Relation before = r.copy();
				Relation after = newRelation.copy();
				newMind.getChanges().add(new Change(before,after,OPList.SPECIALIZE));
				gM.add(newMind);
			}
		}
	}
	
	/**
	 * Obtiene un iterador con las superclases del objetivo al que se le va a aplicar el operador.
	 * @param r Relación que va a ser operada.
	 * @param opTarget Elemento de la relación que va a sufrir la operación.
	 * @return El iterador de las superclases del objetivo.
	 */
	private Iterator<String> getSuperClasses(Relation r, int opTarget) {
		switch(opTarget) {
		case OPTarget.ACTION:
			if (OntoBridgeSingleton.getInstance().existsInstance(r.getAction(), "NamedIndividual")) 
				return OntoBridgeSingleton.getInstance().listDeclaredBelongingClasses(r.getAction());
			else 
				return OntoBridgeSingleton.getInstance().listSuperClasses(r.getAction(), true);
		case OPTarget.SOURCE:
			if (OntoBridgeSingleton.getInstance().existsInstance(r.getSource(), "NamedIndividual")) 
				return OntoBridgeSingleton.getInstance().listDeclaredBelongingClasses(r.getSource());
			else 
				return OntoBridgeSingleton.getInstance().listSuperClasses(r.getSource(), true);
		case OPTarget.TARGET:
			if (OntoBridgeSingleton.getInstance().existsInstance(r.getTarget(), "NamedIndividual")) 
				return OntoBridgeSingleton.getInstance().listDeclaredBelongingClasses(r.getTarget());
			else 
				return OntoBridgeSingleton.getInstance().listSuperClasses(r.getTarget(), true);
		case OPTarget.PLACE:
			if (OntoBridgeSingleton.getInstance().existsInstance(r.getPlace(), "NamedIndividual")) 
				return OntoBridgeSingleton.getInstance().listDeclaredBelongingClasses(r.getPlace());
			else 
				return OntoBridgeSingleton.getInstance().listSuperClasses(r.getPlace(), true);
		case OPTarget.OD:
			if (OntoBridgeSingleton.getInstance().existsInstance(r.getDirectObject(), "NamedIndividual")) 
				return OntoBridgeSingleton.getInstance().listDeclaredBelongingClasses(r.getDirectObject());
			else 
				return OntoBridgeSingleton.getInstance().listSuperClasses(r.getDirectObject(), true);
		}
		return null;
	}

}

