package operator;

import java.util.ArrayList;
import java.util.Iterator;

import world.World;
import world.ontobridge.OntoBridgeComponent;

/**
 * Operador para generalizar nombres subiendo un nivel en la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class GeneralizeActor implements IOperator {

	/**
	 * Modifica un elemento generalizando hacia el padre en la ontología.
	 * @param c Nombre a generalizar.
	 * @return Falso si ya está en el límite de la ontología y no puede generalizar.
	 */
	private boolean apply(OntoBridgeComponent c){
		if (c.getName().equals("Actores")) return false;
		Iterator<String> it = c.listSuperClasses();
		String parentName = it.next();
		c.setName(parentName);
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
