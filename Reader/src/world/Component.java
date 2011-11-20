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
	
	/**
	 * Lugar donde sucede la acción.
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
	 * Construye un componente del mundo mediante un string con sus atributos
	 * separados por comas.
	 * @para str El string del que extraer la información del componente.
	 * */
	public Component(String str) {
		String[] splittedLine = str.split(",");
		
		weight = Float.parseFloat(splittedLine[0].trim());
		source = new OntoBridgeComponent(splittedLine[1].trim(), OntoBridgeComponent.NAME);
		action = new OntoBridgeComponent(splittedLine[2].trim(), OntoBridgeComponent.ACTION);
		target = new OntoBridgeComponent(splittedLine[3].trim(), OntoBridgeComponent.NAME);
		if (!splittedLine[4].trim().equals(""))
			place = new OntoBridgeComponent(splittedLine[4].trim(), OntoBridgeComponent.NAME);
		if (!splittedLine[5].trim().equals(""))
			directObject = new OntoBridgeComponent(splittedLine[5].trim(), OntoBridgeComponent.NAME);
	}
	
	/**
	 * Construye una instancia dados todos los atributos del componente como parámetros.
	 * @param weight Peso del componente
	 * @param source Fuente
	 * @param action Acción
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
	 * Comprueba si un componente es instancia de otro (patrón).
	 * @param pattern Patrón del componente.
	 * @return Valor booleano indicando si el componente es una instancia del patrón.
	 * */
	public boolean instanceOf(Component pattern) {
		boolean placeIsInstanceOf = (place == null && pattern.place == null) ||
				(place != null && place.equals(pattern.place));
		boolean directObjectIsInstanceOf = (directObject == null && pattern.directObject == null) ||
				(directObject != null && directObject.equals(pattern.directObject));
		
		return (source.isSubClassOf(pattern.source) && 
				action.isSubClassOf(pattern.action) &&
				target.isSubClassOf(pattern.target) &&
				placeIsInstanceOf &&
				directObjectIsInstanceOf);
	}
	
	/**
	 * Copia el componente.
	 * @return Una copia del componente.
	 * */
	public Component copy() {
		Component copy = new Component(weight, source.copy(), action.copy(),
				target.copy(),
				(place == null) ? null : place.copy(),
				(directObject == null) ? null : directObject.copy());
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
		
		boolean placeEq = (place == null && c.place == null) || 
				(place != null && place.equals(c.place));
		boolean directObjectEq = (directObject == null && c.directObject == null) || 
				(directObject != null && directObject.equals(c.directObject));
		
		return (c.weight == weight && c.source.equals(source)
				&& c.action.equals(action) && c.target.equals(target) &&
				placeEq && directObjectEq);
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + weight + "), " + source +
				"--{" + action +
				((directObject == null) ? "" : " [" + directObject + "]") + 
				((place == null) ? "" : " at " + place) + "}-->" + 
				target;
	}
	
	/**
	 * Devuelve un string reducido pensado para usarse en un hasheo posterior.
	 * @return String reducido que identifica la instancia.
	 * */
	public String toShortString() {
		// TODO Tener en cuenta el peso? Comentar con Israel
		return source.getName() + 
				action.getName() + 
				target.getName() + 
				((place == null) ? "" : place.getName()) +
				((directObject == null) ? "" : directObject.getName());
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
	
	public String toStringRelation() {
		String retVal = "Relación\n";
		retVal += toString() + "\n";
		retVal += "Peso: " + this.weight + "\n";
		if (this.source != null) retVal += "Fuente: " + source.toString() + "\n";
		if (this.action != null) retVal += "Acción: " + action.toString() + "\n";
		if (this.target != null) retVal += "Destino: " + target.toString() + "\n";
		if (this.directObject != null) retVal += "Objeto directo: " + directObject.toString() + "\n";
		if (this.place != null) retVal += "Lugar: " + place.toString() + "\n";
		return retVal;
	}
	
}
