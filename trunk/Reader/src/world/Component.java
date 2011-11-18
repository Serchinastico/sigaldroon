package world;

import world.ontobridge.OntoBridgeComponent;

/**
 * Componente fundamental del mundo del lector.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class Component {
	/**
	 * Peso de veracidad
	 * */
	private float weight;
	
	/**
	 * Origen de la acci�n
	 * */
	private OntoBridgeComponent source;
	
	/**
	 * Acci�n realizada
	 * */
	private OntoBridgeComponent action;
	
	/**
	 * Objetivo de la acci�n
	 * */
	private OntoBridgeComponent target;
	
	/**
	 * Lugar donde sucede la acci�n.
	 * */
	private OntoBridgeComponent place;
	
	/**
	 * Complemento directo.
	 * */
	private OntoBridgeComponent directObject;
	
	public Component() {
		this.weight = -1.0f;
		this.source = null;
		this.action = null;
		this.target = null;
		this.place = null;
		this.directObject = null;
	}
	
	/**
	 * Construye una instancia dados todos los atributos del componente como par�metros.
	 * @param weight Peso del componente
	 * @param source Fuente
	 * @param action Acci�n
	 * @param target Objetivo
	 * @param place Lugar
	 * @param directObject Complemento directo
	 * */
	public Component(float weight, OntoBridgeComponent source,
			OntoBridgeComponent action, OntoBridgeComponent target,
			OntoBridgeComponent place, OntoBridgeComponent directObject) {
		this.weight = weight;
		this.source = source;
		this.action = action;
		this.target = target;
		this.place = place;
		this.directObject = directObject;
	}
	
	/**
	 * Comprueba si un componente es instancia de otro (patr�n).
	 * @param pattern Patr�n del componente.
	 * @return Valor booleano indicando si el componente es una instancia del patr�n.
	 * */
	public boolean instanceOf(Component pattern) {
		return (source.isSubClassOf(pattern.source) && 
				action.isSubClassOf(pattern.action) &&
				target.isSubClassOf(pattern.target) &&
				place.isSubClassOf(pattern.place) &&
				directObject.isSubClassOf(pattern.directObject));
	}
	
	/**
	 * Copia el componente.
	 * @return Una copia del componente.
	 * */
	public Component copy() {
		Component copy = new Component(weight, source.copy(), action.copy(),
				target.copy(), place.copy(), directObject.copy());
		return copy;
	}
	
	/**
	 * Comprueba si un componente es igual a otro.
	 * @param o Componente con el que comparar.
	 * @return Valor booleano indicando el resultado de la comparaci�n.
	 * */
	public boolean equals(Object o) {
		if (!(o instanceof Component))
			return false;
		
		Component c = (Component) o;		
		return (c.weight == weight && c.source.equals(source)
				&& c.action.equals(action) && c.target.equals(target) &&
				c.place.equals(place) && c.directObject.equals(directObject));
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + weight + "), " + source +
				"--{" + action + " [" + directObject + "] at " + place + "}-->" + 
				target;
	}
	
	/**
	 * @return the weight
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	/**
	 * @return the source
	 */
	public OntoBridgeComponent getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(OntoBridgeComponent source) {
		this.source = source;
	}

	/**
	 * @return the action
	 */
	public OntoBridgeComponent getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(OntoBridgeComponent action) {
		this.action = action;
	}

	/**
	 * @return the target
	 */
	public OntoBridgeComponent getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(OntoBridgeComponent target) {
		this.target = target;
	}
	
	/**
	 * @return the place
	 */
	public OntoBridgeComponent getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(OntoBridgeComponent place) {
		this.place = place;
	}
	
	/**
	 * @return the direct object
	 */
	public OntoBridgeComponent getDirectObject() {
		return directObject;
	}

	/**
	 * @param directObject the direct object to set
	 */
	public void setDirectObject(OntoBridgeComponent directObject) {
		this.directObject = directObject;
	}
	
}
