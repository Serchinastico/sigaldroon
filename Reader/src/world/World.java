package world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World implements Iterable<Component> {

	/**
	 * Lista de asertos.
	 * */
	private List<Component> components;
	
	public World() {
		components = new ArrayList<Component>();
	}

	/**
	 * Construye un mundo mediante la copia de otro.
	 * @param wCopy Mundo referencia para hacer la copia.
	 * */
	public World(World wCopy) {
		components = new ArrayList<Component>();
		
		for (Component component : wCopy.components) {
			components.add(component.copy());
		}
	}
	
	@Override
	public Iterator<Component> iterator() {
		return components.iterator();
	}
	
}
