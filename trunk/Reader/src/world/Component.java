package world;

import world.ontobridge.OntoBridgeComponent;

/**
 * Componente fundamental del mundo del lector.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Component {
	/**
	 * Peso de veracidad
	 * */
	private float weight;
	
	/**
	 * Origen de la acción
	 * */
	private OntoBridgeComponent source;
	
	/**
	 * Acción realizada
	 * */
	private OntoBridgeComponent action;
	
	/**
	 * Objetivo de la acción
	 * */
	private OntoBridgeComponent target;
	
	public Component() {
		this.weight = -1.0f;
		this.source = null;
		this.action = null;
		this.target = null;
	}
	
	/**
	 * Construye una instancia dados todos los atributos del componente como parámetros
	 * @param weight Peso del componente
	 * @param source Fuente
	 * @param action Acción
	 * @param target Objetivo
	 * */
	public Component(float weight, OntoBridgeComponent source,
			OntoBridgeComponent action, OntoBridgeComponent target) {
		this.weight = weight;
		this.source = source;
		this.action = action;
		this.target = target;
	}
	
	/**
	 * Comprueba si un componente es instancia de otro (patrón).
	 * @param pattern Patrón del componente.
	 * @return Valor booleano indicando si el componente es una instancia del patrón.
	 * */
	public boolean instanceOf(Component pattern) {
		return (source.isSubClassOf(pattern.source) && 
				action.isSubClassOf(pattern.action) &&
				target.isSubClassOf(pattern.target));
	}
	
	/**
	 * Copia el componente.
	 * @return Una copia del componente.
	 * */
	public Component copy() {
		Component copy = new Component(weight, source.copy(), action.copy(), target.copy());
		return copy;
	}
	
	/**
	 * Comprueba si un componente es igual a otro.
	 * @param o Componente con el que comparar.
	 * @return Valor booleano indicando el resultado de la comparación.
	 * */
	public boolean equals(Object o) {
		if (!(o instanceof Component))
			return false;
		
		Component c = (Component) o;		
		return (c.weight == weight && c.source.equals(source) &&
				c.action.equals(action) && c.target.equals(target));
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + weight + "), " + source +
				"--{" + action + "}-->" + target;
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
	
}
