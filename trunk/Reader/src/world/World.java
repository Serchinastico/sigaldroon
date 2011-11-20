package world;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación del mundo desde el punto de vista del lector.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
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
	 * Construye un mundo mediante un flujo de entrada (fichero, entrada estándar, ...).
	 * @param in Flujo de entrada.
	 * */
	public World(InputStream in) {
		components = new ArrayList<Component>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while ((line = br.readLine()) != null) {
				// Se pueden insertar comentarios iniciando la linea con "//"
				if (line.startsWith("//"))
					continue;
				Component c = new Component(line);
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
	
	/**
	 * @return El número de componentes que hay en el mundo.
	 */
	public int getNumComponents() {
		return components.size();
	}
	
	/**
	 * Obtiene la i-ésima relación del listado de componentes.
	 * @param i La i-ésima relación dentro del listado de componentes.
	 * @return La relación número i.
	 */
	public Component getComponent(int i) {
		return components.get(i);
	}
	
	@Override
	public String toString() {
		return "World [components=" + components.toString() + "]";
	}
	
	public String toShortString() {
		String shortStr = "";
		for (Component c : components) {
			shortStr += c.toShortString();
		}
		return shortStr;
	}
	
	public String toStringSegment() {
		String retVal = "Segmento\n";
		for (int i = 0; i < components.size(); i++) {
			retVal += components.get(i).toStringRelation() + "\n";
		}
		return retVal;
	}
}
