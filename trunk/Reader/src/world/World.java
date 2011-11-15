package world;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import world.ontobridge.OntoBridgeComponent;

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
	
	/**
	 * Construye un mundo mediante un flujo de entrada (fichero, entrada est�ndar, ...).
	 * @param in Flujo de entrada.
	 * */
	public World(InputStream in) {
		components = new ArrayList<Component>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("//"))
					continue;
				
				String[] splittedLine = line.split(",");
				Component c = new Component();
				c.setWeight(Float.parseFloat(splittedLine[0].trim()));
				c.setSource(new OntoBridgeComponent(splittedLine[1].trim(), OntoBridgeComponent.NAME));
				c.setAction(new OntoBridgeComponent(splittedLine[2].trim(), OntoBridgeComponent.ACTION));
				c.setTarget(new OntoBridgeComponent(splittedLine[3].trim(), OntoBridgeComponent.NAME));
				
				components.add(c);
			}
		} catch (Exception e) {
			System.err.println("Error leyendo el estado del mundo: " + e.getMessage());
		}
	}
	
	@Override
	public Iterator<Component> iterator() {
		return components.iterator();
	}
	
}