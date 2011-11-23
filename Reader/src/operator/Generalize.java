package operator;

import java.util.ArrayList;
import java.util.Iterator;

import mind.Relation;
import mind.ChangedMind;
import mind.ontobridge.OntoBridgeActions;
import mind.ontobridge.OntoBridgeComponent;
import mind.ontobridge.OntoBridgeNames;


/**
 * Operador para generalizar acciones subiendo un nivel en la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class Generalize implements IOperator {
	
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
	private void apply(ChangedMind m, Relation r, ArrayList<ChangedMind> gM) {
		
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
			if (OntoBridgeActions.getInstance().existsInstance(r.getAction().getName(), "NamedIndividual")) 
				return OntoBridgeActions.getInstance().listDeclaredBelongingClasses(r.getAction().getName());
			else 
				return OntoBridgeActions.getInstance().listSuperClasses(r.getAction().getName(), true);
		case OPTarget.SOURCE:
			if (OntoBridgeNames.getInstance().existsInstance(r.getSource().getName(), "NamedIndividual")) 
				return OntoBridgeNames.getInstance().listDeclaredBelongingClasses(r.getSource().getName());
			else 
				return OntoBridgeNames.getInstance().listSuperClasses(r.getSource().getName(), true);
		case OPTarget.TARGET:
			if (OntoBridgeNames.getInstance().existsInstance(r.getTarget().getName(), "NamedIndividual")) 
				return OntoBridgeNames.getInstance().listDeclaredBelongingClasses(r.getTarget().getName());
			else 
				return OntoBridgeNames.getInstance().listSuperClasses(r.getTarget().getName(), true);
		case OPTarget.PLACE:
			if (OntoBridgeNames.getInstance().existsInstance(r.getPlace().getName(), "NamedIndividual")) 
				return OntoBridgeNames.getInstance().listDeclaredBelongingClasses(r.getPlace().getName());
			else 
				return OntoBridgeNames.getInstance().listSuperClasses(r.getPlace().getName(), true);
		case OPTarget.OD:
			if (OntoBridgeNames.getInstance().existsInstance(r.getDirectObject().getName(), "NamedIndividual")) 
				return OntoBridgeNames.getInstance().listDeclaredBelongingClasses(r.getDirectObject().getName());
			else 
				return OntoBridgeNames.getInstance().listSuperClasses(r.getDirectObject().getName(), true);
		}
		return null;
	}
	
	/**
	 * Aplica el cambio, poniendo el nuevo nombre al objetivo.
	 * @param r Relación a cambiar.
	 * @param newName Nuevo nombre para el elemento a cambiar.
	 * @param opTarget El elemento a cambiar en la relación.
	 */
	private void applyChange(Relation r, String newName, int opTarget) {
		
		switch(opTarget) {
		case OPTarget.ACTION:
			r.getAction().setName(newName);
			break;
		case OPTarget.SOURCE:
			r.getSource().setName(newName);
			break;
		case OPTarget.TARGET:
			r.getTarget().setName(newName);
			break;
		case OPTarget.PLACE:
			r.getPlace().setName(newName);
			break;
		case OPTarget.OD:
			r.getDirectObject().setName(newName);
			break;
		}
		
	}


	
	@Override
	public void generateMinds(ChangedMind m, ArrayList<ChangedMind> generatedMinds) {
		
		Iterator<Relation> itRel = m.getActualMind().iterator();
		
		while (itRel.hasNext())
			apply(m, itRel.next(), generatedMinds);
	}

}

