package operator;

import java.util.ArrayList;
import java.util.Iterator;

import world.WorldChanged;
import world.ontobridge.OntoBridgeComponent;

/**
 * Operador para generalizar acciones subiendo un nivel en la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class Generalize implements IOperator {

	private boolean applyToAction(WorldChanged w, ArrayList<WorldChanged> gW, int i) {
		
		OntoBridgeComponent c = w.getActualMind().getComponent(i).getAction();
		
		// Si no puede subir más en la ontología, salimos
		if (c.getName().equals("Accion")) return false;
		
		Iterator<String> itParents = c.listSuperClasses();
		
		while (itParents.hasNext()) {
			String parentName = itParents.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getAction().setName(parentName);
			gW.add(newWorld);
		}
		
		return true;
	}
	
	private boolean applyToSource(WorldChanged w, ArrayList<WorldChanged> gW, int i) {
		
		OntoBridgeComponent c = w.getActualMind().getComponent(i).getSource();
		
		// Si no puede subir más en la ontología, salimos
		if (c.getName().equals("Actores")) return false;
		
		Iterator<String> itParents = c.listSuperClasses();
		
		while (itParents.hasNext()) {
			String parentName = itParents.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getSource().setName(parentName);
			gW.add(newWorld);
		}
		
		return true;
	}
	
	private boolean applyToTarget(WorldChanged w, ArrayList<WorldChanged> gW, int i) {
		
		OntoBridgeComponent c = w.getActualMind().getComponent(i).getTarget();
		
		// Si no puede subir más en la ontología, salimos
		if (c.getName().equals("Actores")) return false;
		
		Iterator<String> itParents = c.listSuperClasses();
		
		while (itParents.hasNext()) {
			String parentName = itParents.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getTarget().setName(parentName);
			gW.add(newWorld);
		}
		
		return true;
	}
	
	private boolean applyToPlace(WorldChanged w, ArrayList<WorldChanged> gW, int i) {
		
		OntoBridgeComponent c = w.getActualMind().getComponent(i).getPlace();
		
		// Si no puede subir más en la ontología o si el lugar no está definido, salimos
		if ((c == null) || (c.getName().equals("Lugares"))) return false;
		
		Iterator<String> itParents = c.listSuperClasses();
		
		while (itParents.hasNext()) {
			String parentName = itParents.next();
			WorldChanged newWorld = w.copy();
			newWorld.getActualMind().getComponent(i).getPlace().setName(parentName);
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

