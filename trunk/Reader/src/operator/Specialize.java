package operator;

import java.util.ArrayList;
import java.util.Iterator;

import world.Component;
import world.WorldChanged;
import world.ontobridge.OntoBridgeComponent;

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
	 * Aplica el operador a una acción.
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i Índice del elemento en el que reside la acción dentro del mundo.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToAction(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getAction();

		// Si ya está instanciado, no se puede especializar más
		//if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontología
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			WorldChanged newWorld = w.copy();
			// Cambio del componente
			newWorld.getActualMind().getComponent(i).getAction().setName(subClass);
			// Cambio del peso
			float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
			newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
			// Guardado del cambio
			Component before = w.getActualMind().getComponent(i);
			Component after = newWorld.getActualMind().getComponent(i);
			newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gW.add(newWorld);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listDeclaredInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			WorldChanged newWorld = w.copy();
			// Cambio del componente
			newWorld.getActualMind().getComponent(i).getAction().setName(instanceName);
			// Cambio del peso
			float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
			newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
			// Guardado del cambio
			Component before = w.getActualMind().getComponent(i);
			Component after = newWorld.getActualMind().getComponent(i);
			newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gW.add(newWorld);
		}

		return true;
	}

	/**
	 * Aplica el operador a un actor fuente.
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i Índice del elemento en el que reside la acción dentro del mundo.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToSource(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getSource();

		// Si ya está instanciado, no se puede especializar más
		//if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontología
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			WorldChanged newWorld = w.copy();
			// Cambio del componente
			newWorld.getActualMind().getComponent(i).getSource().setName(subClass);
			// Cambio del peso
			float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
			newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
			// Guardado del cambio
			Component before = w.getActualMind().getComponent(i);
			Component after = newWorld.getActualMind().getComponent(i);
			newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gW.add(newWorld);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listDeclaredInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			WorldChanged newWorld = w.copy();
			// Cambio del componente
			newWorld.getActualMind().getComponent(i).getSource().setName(instanceName);
			// Cambio del peso
			float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
			newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
			// Guardado del cambio
			Component before = w.getActualMind().getComponent(i);
			Component after = newWorld.getActualMind().getComponent(i);
			newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gW.add(newWorld);
		}

		return true;
	}

	/**
	 * Aplica el operador a un actor destino.
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i Índice del elemento en el que reside la acción dentro del mundo.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToTarget(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getTarget();

		// Si ya está instanciado, no se puede especializar más
		//if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontología
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			WorldChanged newWorld = w.copy();
			// Cambio del componente
			newWorld.getActualMind().getComponent(i).getTarget().setName(subClass);
			// Cambio del peso
			float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
			newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
			// Guardado del cambio
			Component before = w.getActualMind().getComponent(i);
			Component after = newWorld.getActualMind().getComponent(i);
			newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gW.add(newWorld);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listDeclaredInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			WorldChanged newWorld = w.copy();
			// Cambio del componente
			newWorld.getActualMind().getComponent(i).getTarget().setName(instanceName);
			// Cambio del peso
			float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
			newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
			// Guardado del cambio
			Component before = w.getActualMind().getComponent(i);
			Component after = newWorld.getActualMind().getComponent(i);
			newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gW.add(newWorld);
		}

		return true;
	}

	/**
	 * Aplica el operador a un lugar.
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i Índice del elemento en el que reside la acción dentro del mundo.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToPlace(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getPlace();

		// Si ya está instanciado o no tiene especificado el lugar, no se puede especializar más
		if (c == null) return false;

		// Se crean los hijos mediante las subclases en la ontología
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			WorldChanged newWorld = w.copy();
			// Cambio del componente
			newWorld.getActualMind().getComponent(i).getPlace().setName(subClass);
			// Cambio del peso
			float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
			newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
			// Guardado del cambio
			Component before = w.getActualMind().getComponent(i);
			Component after = newWorld.getActualMind().getComponent(i);
			newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gW.add(newWorld);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listDeclaredInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			WorldChanged newWorld = w.copy();
			// Cambio del componente
			newWorld.getActualMind().getComponent(i).getPlace().setName(instanceName);
			// Cambio del peso
			float componentWeight = newWorld.getActualMind().getComponent(i).getWeight() * opWeight;
			newWorld.getActualMind().getComponent(i).setWeight(componentWeight);
			// Guardado del cambio
			Component before = w.getActualMind().getComponent(i);
			Component after = newWorld.getActualMind().getComponent(i);
			newWorld.getChanges().add(new Change(before.copy(),after.copy(),OPList.SPECIALIZE));
			gW.add(newWorld);
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
