package operator;

import java.util.ArrayList;
import java.util.Iterator;

import mind.Relation;
import mind.ChangedMind;
import mind.ontobridge.OntoBridgeComponent;

/**
 * Operador para especializar elementos bajando un nivel en la ontolog�a.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 */
public class Specialize implements IOperator {

	/**
	 * Peso del operador.
	 */
	private float opWeight = 0.8f;
	
	/**
	 * Aplica el operador a una acci�n.
	 * @param m Mente a partir de la cual operar.
	 * @param gM Lista de mentes generadas.
	 * @param i �ndice del elemento en el que reside la acci�n dentro de la mente.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToAction(ChangedMind m, ArrayList<ChangedMind> gM, int i) {

		OntoBridgeComponent c = m.getActualMind().getRelation(i).getAction();

		// Si ya est� instanciado, no se puede especializar m�s
		//if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontolog�a
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			ChangedMind newMind = m.copy();
			// Cambio del componente
			newMind.getActualMind().getRelation(i).getAction().setName(subClass);
			// Cambio del peso
			float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
			newMind.getActualMind().getRelation(i).setWeight(componentWeight);
			// Guardado del cambio
			Relation before = m.getActualMind().getRelation(i);
			Relation after = newMind.getActualMind().getRelation(i);
			newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gM.add(newMind);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listDeclaredInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			ChangedMind newMind = m.copy();
			// Cambio del componente
			newMind.getActualMind().getRelation(i).getAction().setName(instanceName);
			// Cambio del peso
			float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
			newMind.getActualMind().getRelation(i).setWeight(componentWeight);
			// Guardado del cambio
			Relation before = m.getActualMind().getRelation(i);
			Relation after = newMind.getActualMind().getRelation(i);
			newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gM.add(newMind);
		}

		return true;
	}

	/**
	 * Aplica el operador a un actor fuente.
	 * @param m Mente a partir de la cual operar.
	 * @param gM Lista de mentes generadas.
	 * @param i �ndice del elemento en el que reside la acci�n dentro de la mente.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToSource(ChangedMind m, ArrayList<ChangedMind> gM, int i) {

		OntoBridgeComponent c = m.getActualMind().getRelation(i).getSource();

		// Si ya est� instanciado, no se puede especializar m�s
		//if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontolog�a
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			ChangedMind newMind = m.copy();
			// Cambio del componente
			newMind.getActualMind().getRelation(i).getSource().setName(subClass);
			// Cambio del peso
			float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
			newMind.getActualMind().getRelation(i).setWeight(componentWeight);
			// Guardado del cambio
			Relation before = m.getActualMind().getRelation(i);
			Relation after = newMind.getActualMind().getRelation(i);
			newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gM.add(newMind);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listDeclaredInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			ChangedMind newMind = m.copy();
			// Cambio del componente
			newMind.getActualMind().getRelation(i).getSource().setName(instanceName);
			// Cambio del peso
			float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
			newMind.getActualMind().getRelation(i).setWeight(componentWeight);
			// Guardado del cambio
			Relation before = m.getActualMind().getRelation(i);
			Relation after = newMind.getActualMind().getRelation(i);
			newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gM.add(newMind);
		}

		return true;
	}

	/**
	 * Aplica el operador a un actor destino.
	 * @param m Mente a partir de la cual operar.
	 * @param gM Lista de mentes generadas.
	 * @param i �ndice del elemento en el que reside la acci�n dentro de la mente.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToTarget(ChangedMind m, ArrayList<ChangedMind> gM, int i) {

		OntoBridgeComponent c = m.getActualMind().getRelation(i).getTarget();

		// Si ya est� instanciado, no se puede especializar m�s
		//if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontolog�a
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			ChangedMind newMind = m.copy();
			// Cambio del componente
			newMind.getActualMind().getRelation(i).getTarget().setName(subClass);
			// Cambio del peso
			float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
			newMind.getActualMind().getRelation(i).setWeight(componentWeight);
			// Guardado del cambio
			Relation before = m.getActualMind().getRelation(i);
			Relation after = newMind.getActualMind().getRelation(i);
			newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gM.add(newMind);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listDeclaredInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			ChangedMind newMind = m.copy();
			// Cambio del componente
			newMind.getActualMind().getRelation(i).getTarget().setName(instanceName);
			// Cambio del peso
			float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
			newMind.getActualMind().getRelation(i).setWeight(componentWeight);
			// Guardado del cambio
			Relation before = m.getActualMind().getRelation(i);
			Relation after = newMind.getActualMind().getRelation(i);
			newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gM.add(newMind);
		}

		return true;
	}

	/**
	 * Aplica el operador a un lugar.
	 * @param m Mente a partir de la cual operar.
	 * @param gM Lista de mentes generadas.
	 * @param i �ndice del elemento en el que reside la acci�n dentro de la mente.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToPlace(ChangedMind m, ArrayList<ChangedMind> gM, int i) {

		OntoBridgeComponent c = m.getActualMind().getRelation(i).getPlace();

		// Si ya est� instanciado o no tiene especificado el lugar, no se puede especializar m�s
		if (c == null) return false;

		// Se crean los hijos mediante las subclases en la ontolog�a
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			ChangedMind newMind = m.copy();
			// Cambio del componente
			newMind.getActualMind().getRelation(i).getPlace().setName(subClass);
			// Cambio del peso
			float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
			newMind.getActualMind().getRelation(i).setWeight(componentWeight);
			// Guardado del cambio
			Relation before = m.getActualMind().getRelation(i);
			Relation after = newMind.getActualMind().getRelation(i);
			newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gM.add(newMind);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listDeclaredInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			ChangedMind newMind = m.copy();
			// Cambio del componente
			newMind.getActualMind().getRelation(i).getPlace().setName(instanceName);
			// Cambio del peso
			float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
			newMind.getActualMind().getRelation(i).setWeight(componentWeight);
			// Guardado del cambio
			Relation before = m.getActualMind().getRelation(i);
			Relation after = newMind.getActualMind().getRelation(i);
			newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gM.add(newMind);
		}

		return true;
	}

	@Override
	public void generateMinds(ChangedMind m, ArrayList<ChangedMind> generatedMinds) {
		for (int i = 0; i < m.getActualMind().getNumRelations(); i++) {
			applyToAction(m,generatedMinds,i);
			applyToSource(m,generatedMinds,i);
			applyToTarget(m,generatedMinds,i);
			applyToPlace(m,generatedMinds,i);
		}
	}

}
