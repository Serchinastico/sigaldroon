package operator;

import java.util.ArrayList;
import java.util.Iterator;

import world.WorldChanged;
import world.ontobridge.OntoBridgeComponent;

/**
 * Operador para especializar elementos bajando un nivel en la ontolog�a.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 */
public class Specialize implements IOperator {

	/**
	 * Aplica el operador a una acci�n.
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i �ndice del elemento en el que reside la acci�n dentro del mundo.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToAction(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getAction();

		// Si ya est� instanciado, no se puede especializar m�s
		if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontolog�a
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

	/**
	 * Aplica el operador a un actor fuente.
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i �ndice del elemento en el que reside la acci�n dentro del mundo.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToSource(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getSource();

		// Si ya est� instanciado, no se puede especializar m�s
		if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontolog�a
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

	/**
	 * Aplica el operador a un actor destino.
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i �ndice del elemento en el que reside la acci�n dentro del mundo.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToTarget(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getTarget();

		// Si ya est� instanciado, no se puede especializar m�s
		if (c.isInstance()) return false;

		// Se crean los hijos mediante las subclases en la ontolog�a
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

	/**
	 * Aplica el operador a un lugar.
	 * @param w Mundo a partir del cual operar.
	 * @param gW Lista de mundos generados.
	 * @param i �ndice del elemento en el que reside la acci�n dentro del mundo.
	 * @return Falso si no se puede especializar.
	 */
	private boolean applyToPlace(WorldChanged w, ArrayList<WorldChanged> gW, int i) {

		OntoBridgeComponent c = w.getActualMind().getComponent(i).getPlace();

		// Si ya est� instanciado o no tiene especificado el lugar, no se puede especializar m�s
		if ((c == null) || (c.isInstance())) return false;

		// Se crean los hijos mediante las subclases en la ontolog�a
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
