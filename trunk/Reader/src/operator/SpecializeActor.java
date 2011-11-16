package operator;

import java.util.ArrayList;
import java.util.Iterator;

import utils.RandomSeed;
import world.World;
import world.ontobridge.OntoBridgeComponent;

/**
 * Operador para especializar nombres bajando un nivel en la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class SpecializeActor implements IOperator {

	/**
	 * Modifica un elemento especializando hacia uno de los hijos al azar en la ontología.
	 * @param c Nombre a especializar.
	 * @return Falso si ya está instanciado y no puede especializar.
	 */
	private boolean apply(OntoBridgeComponent c){
		Iterator<String> it = c.listSubClasses();
		if (!it.hasNext()) return false;
		ArrayList<String> subClassNames = new ArrayList<String>();
		while (it.hasNext()) {
			subClassNames.add(it.next());
		}
		int subClassChoice = RandomSeed.generateRandom(subClassNames.size());
		String subClass = subClassNames.get(subClassChoice);
		c.setName(subClass);
		return true;
	}
	
	@Override
	public ArrayList<World> generateWorlds(World w) {
		ArrayList<World> generatedWorlds = new ArrayList<World>();
		for (int i = 0; i < w.getNumComponents(); i++) {
			World newWorld = new World(w);
			if (apply(newWorld.getComponent(i).getSource()))
				generatedWorlds.add(newWorld);
			World newWorld2 = new World(w);
			if (apply(newWorld2.getComponent(i).getTarget()))
				generatedWorlds.add(newWorld2);
		}
		return generatedWorlds;
	}

}
