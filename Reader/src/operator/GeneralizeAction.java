package operator;

import java.util.ArrayList;
import java.util.Iterator;

import world.World;
import world.ontobridge.OntoBridgeComponent;

/**
 * Operador para generalizar acciones subiendo un nivel en la ontolog�a.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 */
public class GeneralizeAction implements IOperator {

	/**
	 * Modifica un elemento generalizando hacia el padre en la ontolog�a.
	 * @param c Acci�n a generalizar.
	 * @return Falso si ya est� en el l�mite de la ontolog�a y no puede generalizar.
	 */
	private boolean apply(OntoBridgeComponent c){
		if (c.getName().equals("Accion")) return false;
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
			if (apply(newWorld.getComponent(i).getAction()))
				generatedWorlds.add(newWorld);
		}
		return generatedWorlds;
	}

}

