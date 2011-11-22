package operator;

import java.util.ArrayList;
import java.util.Iterator;

import mind.Relation;
import mind.ChangedMind;
import mind.ontobridge.OntoBridgeComponent;


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
	 * Aplica el operador a una acción.
	 * @param m Mente a partir de la cual operar.
	 * @param gM Lista de mentes generadas.
	 * @param i Índice del elemento en el que reside la acción dentro de la mente.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToAction(ChangedMind m, ArrayList<ChangedMind> gM, int i) {
		
		OntoBridgeComponent c = m.getActualMind().getRelation(i).getAction();
		
		// Si no puede subir más en la ontología, salimos
		if (c.getName().equals("Accion")) return false;
		
		Iterator<String> itParents;
		
		if (c.isInstance()) 
			itParents = c.listDeclaredBelongingSuperClasses();
		else 
			itParents = c.listSuperClasses();
		
		while (itParents.hasNext()) {
			String parentName = itParents.next();
			if (!parentName.contains("NamedIndividual") && (!parentName.contains("Class") && (!parentName.contains("Nothing")))) {
				ChangedMind newMind = m.copy();
				// Cambio del componente
				newMind.getActualMind().getRelation(i).getAction().setName(parentName);
				// Cambio del peso
				float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
				newMind.getActualMind().getRelation(i).setWeight(componentWeight);
				// Guardado del cambio
				Relation before = m.getActualMind().getRelation(i);
				Relation after = newMind.getActualMind().getRelation(i);
				newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.GENERALIZE));
				gM.add(newMind);
			}
		}
		
		return true;
	}
	
	/**
	 * Aplica el operador a un actor fuente.
	 * @param m Mente a partir de la cual operar.
	 * @param gM Lista de mentes generadas.
	 * @param i Índice del elemento en el que reside la acción dentro de la mente.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToSource(ChangedMind m, ArrayList<ChangedMind> gM, int i) {
		
		OntoBridgeComponent c = m.getActualMind().getRelation(i).getSource();
		
		// Si no puede subir más en la ontología, salimos
		if (c.getName().equals("Actores")) return false;
		
		Iterator<String> itParents;
		
		if (c.isInstance()) 
			itParents = c.listDeclaredBelongingSuperClasses();
		else 
			itParents = c.listSuperClasses();
		
		while (itParents.hasNext()) {
			String parentName = itParents.next();
			if (!parentName.contains("NamedIndividual") && (!parentName.contains("Class") && (!parentName.contains("Nothing")))) {
				ChangedMind newMind = m.copy();
				// Cambio del componente
				newMind.getActualMind().getRelation(i).getSource().setName(parentName);
				// Cambio del peso
				float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
				newMind.getActualMind().getRelation(i).setWeight(componentWeight);
				// Guardado del cambio
				Relation before = m.getActualMind().getRelation(i);
				Relation after = newMind.getActualMind().getRelation(i);
				newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.GENERALIZE));
				gM.add(newMind);
			}
		}
		
		return true;
	}
	
	/**
	 * Aplica el operador a un actor destino.
	 * @param m Mente a partir de la cual operar.
	 * @param gM Lista de mentes generadas.
	 * @param i Índice del elemento en el que reside la acción dentro de la mente.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToTarget(ChangedMind m, ArrayList<ChangedMind> gM, int i) {
		
		OntoBridgeComponent c = m.getActualMind().getRelation(i).getTarget();
		
		// Si no puede subir más en la ontología, salimos
		if (c.getName().equals("Actores")) return false;
		
		Iterator<String> itParents;
		
		if (c.isInstance()) 
			itParents = c.listDeclaredBelongingSuperClasses();
		else 
			itParents = c.listSuperClasses();
		
		while (itParents.hasNext()) {
			String parentName = itParents.next();
			if (!parentName.contains("NamedIndividual") && (!parentName.contains("Class") && (!parentName.contains("Nothing")))) {
				ChangedMind newMind = m.copy();
				// Cambio del componente
				newMind.getActualMind().getRelation(i).getTarget().setName(parentName);
				// Cambio del peso
				float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
				newMind.getActualMind().getRelation(i).setWeight(componentWeight);
				// Guardado del cambio
				Relation before = m.getActualMind().getRelation(i);
				Relation after = newMind.getActualMind().getRelation(i);
				newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.GENERALIZE));
				gM.add(newMind);
			}
		}
		
		return true;
	}
	
	/**
	 * Aplica el operador a un lugar.
	 * @param m Mente a partir de la cual operar.
	 * @param gM Lista de mentes generadas.
	 * @param i Índice del elemento en el que reside la acción dentro de la mente.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToPlace(ChangedMind m, ArrayList<ChangedMind> gM, int i) {
		
		OntoBridgeComponent c = m.getActualMind().getRelation(i).getPlace();
		
		// Si no puede subir más en la ontología o si el lugar no está definido, salimos
		if ((c == null) || (c.getName().equals("Lugares"))) return false;
		
		Iterator<String> itParents;
		
		if (c.isInstance()) 
			itParents = c.listDeclaredBelongingSuperClasses();
		else 
			itParents = c.listSuperClasses();
		
		while (itParents.hasNext()) {
			String parentName = itParents.next();
			if (!parentName.contains("NamedIndividual") && (!parentName.contains("Class") && (!parentName.contains("Nothing")))) {
				ChangedMind newMind = m.copy();
				// Cambio del componente
				newMind.getActualMind().getRelation(i).getPlace().setName(parentName);
				// Cambio del peso
				float componentWeight = newMind.getActualMind().getRelation(i).getWeight() * opWeight;
				newMind.getActualMind().getRelation(i).setWeight(componentWeight);
				// Guardado del cambio
				Relation before = m.getActualMind().getRelation(i);
				Relation after = newMind.getActualMind().getRelation(i);
				newMind.getChanges().add(new Change(before.copy(),after.copy(),OPList.GENERALIZE));
				gM.add(newMind);
			}
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

