package operator;

import java.util.ArrayList;
import java.util.Iterator;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;

import mind.Relation;
import mind.ChangedMind;
import mind.ontobridge.OntoBridgeSingleton;

/**
 * Operador para especializar elementos bajando un nivel en la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class Specialize extends OperatorSingle {

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
	protected void apply(ChangedMind m, Relation r, ArrayList<ChangedMind> gM) {

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
		OntoBridge onto = OntoBridgeSingleton.getInstance();
		switch(opTarget) {
		case OPTarget.ACTION:
			return onto.listSubClasses(r.getAction(), true);
		case OPTarget.SOURCE:
			return onto.listSubClasses(r.getSource(), true);
		case OPTarget.TARGET:
			return onto.listSubClasses(r.getTarget(), true);
		case OPTarget.PLACE:
			return onto.listSubClasses(r.getPlace(), true);
		case OPTarget.OD:
			return onto.listSubClasses(r.getDirectObject(), true);
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
		OntoBridge onto = OntoBridgeSingleton.getInstance();
		switch(opTarget) {
		case OPTarget.ACTION:
			return onto.listDeclaredInstances(r.getAction());
		case OPTarget.SOURCE:
			return onto.listDeclaredInstances(r.getSource());
		case OPTarget.TARGET:
			return onto.listDeclaredInstances(r.getTarget());
		case OPTarget.PLACE:
			return onto.listDeclaredInstances(r.getPlace());
		case OPTarget.OD:
			return onto.listDeclaredInstances(r.getDirectObject());
		}
		return null;
	}

}
