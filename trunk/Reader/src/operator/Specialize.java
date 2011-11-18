package operator;

import java.util.ArrayList;
import java.util.Iterator;

import utils.RandomSeed;
import world.WorldChanged;
import world.ontobridge.OntoBridgeComponent;

/**
 * Operador para especializar elementos bajando un nivel en la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class Specialize implements IOperator {

	/**
	 * Modifica un elemento especializando hacia uno de los hijos al azar en la ontología.
	 * @param c Elemento a especializar.
	 * @return Falso si ya está instanciado y no se puede especializar.
	 */
	private boolean apply(OntoBridgeComponent c){
		if (c.isInstance()) return false;
		Iterator<String> itSubClasses = c.listSubClasses();
		if (itSubClasses.hasNext()) {
			ArrayList<String> subClassNames = new ArrayList<String>();
			while (itSubClasses.hasNext()) {
				subClassNames.add(itSubClasses.next());
			}
			int subClassChoice = RandomSeed.generateRandom(subClassNames.size());
			String subClass = subClassNames.get(subClassChoice);
			c.setName(subClass);
			return true;
		}
		else {
			Iterator<String> itInstances = c.listInstances();
			if (!itInstances.hasNext()) return false;
			ArrayList<String> instances = new ArrayList<String>();
			while (itInstances.hasNext()) {
				instances.add(itInstances.next());
			}
			int instanceChoice = RandomSeed.generateRandom(instances.size());
			String instance = instances.get(instanceChoice);
			c.setName(instance);
			return true;
		}
	}
	
	@Override
	public void generateWorlds(WorldChanged w, ArrayList<WorldChanged> generatedWorlds) {
		for (int i = 0; i < w.getActualMind().getNumComponents(); i++) {
			WorldChanged newWorld = w.copy();
			if (apply(newWorld.getActualMind().getComponent(i).getAction()))
				generatedWorlds.add(newWorld);
			WorldChanged newWorld2 = w.copy();
			if (apply(newWorld2.getActualMind().getComponent(i).getSource()))
				generatedWorlds.add(newWorld2);
			WorldChanged newWorld3 = w.copy();
			if (apply(newWorld3.getActualMind().getComponent(i).getTarget()))
				generatedWorlds.add(newWorld3);
			WorldChanged newWorld4 = w.copy();
			if (apply(newWorld4.getActualMind().getComponent(i).getPlace()))
				generatedWorlds.add(newWorld4);
		}
	}

}
