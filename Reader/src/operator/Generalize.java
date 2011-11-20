package operator;

import java.util.ArrayList;
import java.util.Iterator;

import world.Component;
import world.WorldChanged;
import world.ontobridge.OntoBridgeComponent;

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
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i Índice del elemento en el que reside la acción dentro del mundo.
	 * @return Falso si no se puede generalizar.
	 */
	private boolean applyToAction(WorldChanged w, ArrayList<WorldChanged> gW, int i) {
		
		OntoBridgeComponent c = w.getActualMind().getComponent(i).getAction();
		
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
				WorldChanged newWorld = w.copy();
				// Cambio del componente
				newWorld.getActualMind().getComponent(i).getAction().setName(parentName);
				// Cambio del peso
				float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
				newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
				// Guardado del cambio
				Component before = w.getActualMind().getComponent(i);
				Component after = newWorld.getActualMind().getComponent(i);
				newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.GENERALIZE));
				gW.add(newWorld);
			}
		}
		
		return true;
	}
	
	/**
	 * Aplica el operador a un actor fuente.
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i Índice del elemento en el que reside la acción dentro del mundo.
	 * @return Falso si no se puede generalizar.
	 */
	private boolean applyToSource(WorldChanged w, ArrayList<WorldChanged> gW, int i) {
		
		OntoBridgeComponent c = w.getActualMind().getComponent(i).getSource();
		
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
				WorldChanged newWorld = w.copy();
				// Cambio del componente
				newWorld.getActualMind().getComponent(i).getSource().setName(parentName);
				// Cambio del peso
				float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
				newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
				// Guardado del cambio
				Component before = w.getActualMind().getComponent(i);
				Component after = newWorld.getActualMind().getComponent(i);
				newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.GENERALIZE));
				gW.add(newWorld);
			}
		}
		
		return true;
	}
	
	/**
	 * Aplica el operador a un actor destino.
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i Índice del elemento en el que reside la acción dentro del mundo.
	 * @return Falso si no se puede generalizar.
	 */
	private boolean applyToTarget(WorldChanged w, ArrayList<WorldChanged> gW, int i) {
		
		OntoBridgeComponent c = w.getActualMind().getComponent(i).getTarget();
		
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
				WorldChanged newWorld = w.copy();
				// Cambio del componente
				newWorld.getActualMind().getComponent(i).getTarget().setName(parentName);
				// Cambio del peso
				float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
				newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
				// Guardado del cambio
				Component before = w.getActualMind().getComponent(i);
				Component after = newWorld.getActualMind().getComponent(i);
				newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.GENERALIZE));
				gW.add(newWorld);
			}
		}
		
		return true;
	}
	
	/**
	 * Aplica el operador a un lugar.
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i Índice del elemento en el que reside la acción dentro del mundo.
	 * @return Falso si no se puede generalizar.
	 */
	private boolean applyToPlace(WorldChanged w, ArrayList<WorldChanged> gW, int i) {
		
		OntoBridgeComponent c = w.getActualMind().getComponent(i).getPlace();
		
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
				WorldChanged newWorld = w.copy();
				// Cambio del componente
				newWorld.getActualMind().getComponent(i).getPlace().setName(parentName);
				// Cambio del peso
				float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
				newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
				// Guardado del cambio
				Component before = w.getActualMind().getComponent(i);
				Component after = newWorld.getActualMind().getComponent(i);
				newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.GENERALIZE));
				gW.add(newWorld);
			}
		}
		
		return true;
	}

	
	@Override
	public void generateWorlds(WorldChanged w, ArrayList<WorldChanged> generatedWorlds) {
		for (int i = 0; i < w.getActualMind().getNumComponents(); i++) {
			applyToAction(w,generatedWorlds,i);
			applyToSource(w,generatedWorlds,i);
			applyToTarget(w,generatedWorlds,i);
			applyToPlace(w,generatedWorlds,i);
		}
	}

}

