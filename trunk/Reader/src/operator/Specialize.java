package operator;

import java.util.ArrayList;
import java.util.Iterator;

import mind.Relation;
import mind.ChangedMind;
import mind.ontobridge.OntoBridgeSingleton;

/**
 * Operador para especializar elementos bajando un nivel en la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class Specialize implements IOperator {

	/**
	 * Peso del operador.
	 */
	private float opWeight = 0.8f;
		
	/**
	 * Genera todos los hijos posibles para una relación intentando especializar
	 * con subclases e instancias cada uno de sus elementos. 
	 * @param m Mente a operar.
	 * @param r Relación a operar.
	 * @param gM Listado actual de mentes generadas.
	 */
	private void apply(ChangedMind m, Relation r, ArrayList<ChangedMind> gM) {

		for (int i = 0; i < OPTarget.NUM_TARGETS; i++) {
			
			Iterator<String> itSubClasses = getSubClasses(r,i);
			
			while (itSubClasses.hasNext()) {
				String subClass = itSubClasses.next();
				ChangedMind newMind = m.copy();
				// Cambio de la relación
				Relation newRelation = r.copy();
				applyChange(newRelation, subClass, i);
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
			
			Iterator<String> itInstances = getDeclaredInstances(r,i);
			
			while (itInstances.hasNext()) {
				String instanceName = itInstances.next();
				ChangedMind newMind = m.copy();
				// Cambio de la relación
				Relation newRelation = r.copy();
				applyChange(newRelation, instanceName, i);
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
	 * Obtiene un iterador con las subclases del objetivo al que se le va a aplicar el operador.
	 * @param r Relación que va a ser operada.
	 * @param opTarget Elemento de la relación que va a sufrir la operación.
	 * @return El iterador de las subclases del objetivo.
	 */
	private Iterator<String> getSubClasses(Relation r, int opTarget) {
		
		switch(opTarget) {
		case OPTarget.ACTION:
			return OntoBridgeSingleton.getInstance().listSubClasses(r.getAction(), true);
		case OPTarget.SOURCE:
			return OntoBridgeSingleton.getInstance().listSubClasses(r.getSource(), true);
		case OPTarget.TARGET:
			return OntoBridgeSingleton.getInstance().listSubClasses(r.getTarget(), true);
		case OPTarget.PLACE:
			return OntoBridgeSingleton.getInstance().listSubClasses(r.getPlace(), true);
		case OPTarget.OD:
			return OntoBridgeSingleton.getInstance().listSubClasses(r.getDirectObject(), true);
		}
		return null;
	}
	
	/**
	 * Obtiene un iterador con las instancias del objetivo al que se le va a aplicar el operador.
	 * @param r Relación que va a ser operada.
	 * @param opTarget Elemento de la relación que va a sufrir la operación.
	 * @return El iterador de las instancias del objetivo.
	 */
	private Iterator<String> getDeclaredInstances(Relation r, int opTarget) {
		
		switch(opTarget) {
		case OPTarget.ACTION:
			return OntoBridgeSingleton.getInstance().listDeclaredInstances(r.getAction());
		case OPTarget.SOURCE:
			return OntoBridgeSingleton.getInstance().listDeclaredInstances(r.getSource());
		case OPTarget.TARGET:
			return OntoBridgeSingleton.getInstance().listDeclaredInstances(r.getTarget());
		case OPTarget.PLACE:
			return OntoBridgeSingleton.getInstance().listDeclaredInstances(r.getPlace());
		case OPTarget.OD:
			return OntoBridgeSingleton.getInstance().listDeclaredInstances(r.getDirectObject());
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
			r.setAction(newName);
			break;
		case OPTarget.SOURCE:
			r.setSource(newName);
			break;
		case OPTarget.TARGET:
			r.setTarget(newName);
			break;
		case OPTarget.PLACE:
			r.setPlace(newName);
			break;
		case OPTarget.OD:
			r.setDirectObject(newName);
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
