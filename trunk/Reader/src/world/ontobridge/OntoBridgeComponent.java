package world.ontobridge;

import java.util.Iterator;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;

/**
 * Componente de OntoBridge con métodos para tratar con la ontología fácilmente.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class OntoBridgeComponent {

	public static final int ACTION = 1;
	public static final int NAME = 2;
	
	/**
	 * Origen de la acción
	 * */
	private String name;
	
	/**
	 * Tipo del componente, acción o nombre.
	 * */
	private int type;
	
	/**
	 * Instancia de la ontología que usa el componente
	 * */
	private OntoBridge ontobridge;
	
	/**
	 * Construye un componente mediante su nombre y su tipo.
	 * */
	public OntoBridgeComponent(String name, int type) {
		this.name = name;
		this.type = type;
		switch(type) {
		case ACTION: 
			ontobridge = OntoBridgeActions.getInstance();
			break;
		case NAME:
			ontobridge = OntoBridgeNames.getInstance();
			break;
		}
	}
	
	/**
	 * Comprueba si el componente es superclase de otro.
	 * @param c Componente subclase.
	 * @return true si el componente es superclase.
	 * */
	public boolean isSuperClassOf(OntoBridgeComponent c) {
		return ontobridge.isSubClassOf(c.name, name);
	}
	
	/**
	 * Comprueba si el componente es subclase de otro.
	 * @param c Componente superclase.
	 * @return true si el componente es subclase.
	 * */
	public boolean isSubClassOf(OntoBridgeComponent c) {
		return ontobridge.isSubClassOf(name, c.name);
	}
	
	/**
	 * Devuelve todas las superclases de la clase dada.
	 * @return Iterador de todas las superclases.
	 * */
	public Iterator<String> listSuperClasses() {
		return ontobridge.listSuperClasses(name, false);
	}
	
	/**
	 * Devuelve todas las subclases de la clase dada.
	 * @return Iterador de todas las subclases.
	 * */
	public Iterator<String> listSubClasses() {
		return ontobridge.listSubClasses(name, false);
	}

	/**
	 * Copia el componente.
	 * @return Una copia del componente.
	 * */
	public OntoBridgeComponent copy() {
		return new OntoBridgeComponent(name, type);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof OntoBridgeComponent)) {
			return false;
		}
		
		OntoBridgeComponent c = (OntoBridgeComponent) o;
		return (c.name.equals(name));
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
}
