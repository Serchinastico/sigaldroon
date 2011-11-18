package operator;

import java.util.ArrayList;
import java.util.Iterator;

import world.WorldChanged;
import world.ontobridge.OntoBridgeComponent;

/**
 * Operador para generalizar acciones subiendo un nivel en la ontolog�a.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 */
public class Generalize implements IOperator {

	/**
	 * Modifica un elemento generalizando hacia el padre en la ontolog�a.
	 * @param c Acci�n a generalizar.
	 * @param topClass Nombre de la superclase sobre la cual no se puede generalizar m�s.
	 * @return Falso si ya est� en el l�mite de la ontolog�a y no puede generalizar.
	 */
	private boolean apply(OntoBridgeComponent c, String topClass){
		if (c.getName().equals(topClass)) return false;
		Iterator<String> it = c.listSuperClasses();
		String parentName = it.next();
		c.setName(parentName);
		return true;
	}
	
	@Override
	public void generateWorlds(WorldChanged w, ArrayList<WorldChanged> generatedWorlds) {
		for (int i = 0; i < w.getActualMind().getNumComponents(); i++) {
			WorldChanged newWorld = w.copy();
			if (apply(newWorld.getActualMind().getComponent(i).getAction(),"Accion"))
				generatedWorlds.add(newWorld);
			WorldChanged newWorld2 = w.copy();
			if (apply(newWorld2.getActualMind().getComponent(i).getSource(),"Actores"))
				generatedWorlds.add(newWorld2);
			WorldChanged newWorld3 = w.copy();
			if (apply(newWorld3.getActualMind().getComponent(i).getTarget(),"Actores"))
				generatedWorlds.add(newWorld3);
			WorldChanged newWorld4 = w.copy();
			if (apply(newWorld4.getActualMind().getComponent(i).getPlace(),"Lugares"))
				generatedWorlds.add(newWorld4);
		}
	}

}

