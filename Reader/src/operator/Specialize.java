package operator;

import java.util.ArrayList;
import java.util.Iterator;

import world.WorldChanged;
import world.ontobridge.OntoBridgeComponent;

/**
 * Operador para especializar elementos bajando un nivel en la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class Specialize implements IOperator {

	private boolean applyToAction(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getAction();

		// Si ya está instanciado, no se puede especializar más
		if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontología
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getAction().setName(subClass);
			gW.add(newWorld);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getAction().setName(instanceName);
			gW.add(newWorld);
		}

		return true;
	}

	private boolean applyToSource(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getSource();

		// Si ya está instanciado, no se puede especializar más
		if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontología
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getSource().setName(subClass);
			gW.add(newWorld);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getSource().setName(instanceName);
			gW.add(newWorld);
		}

		return true;
	}

	private boolean applyToTarget(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getTarget();

		// Si ya está instanciado, no se puede especializar más
		if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontología
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getTarget().setName(subClass);
			gW.add(newWorld);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getTarget().setName(instanceName);
			gW.add(newWorld);
		}

		return true;
	}

	private boolean applyToPlace(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getPlace();

		// Si ya está instanciado o no tiene especificado el lugar, no se puede especializar más
		if ((c == null) || (c.isInstance())) return false;

		// Se crean los hijos mediante las subclases en la ontología
		Iterator<String> itSubClasses = c.listSubClasses();

		while (itSubClasses.hasNext()) {
			String subClass = itSubClasses.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getPlace().setName(subClass);
			gW.add(newWorld);
		}

		// Se crean los hijos mediante las instancias de la clase
		Iterator<String> itInstances = c.listInstances();

		while (itInstances.hasNext()) {
			String instanceName = itInstances.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getPlace().setName(instanceName);
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
