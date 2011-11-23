package mind;

import mind.ontobridge.OntoBridgeComponent;

/**
 * Componente fundamental de la mente del lector.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class Relation {
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
	
	public Relation() {
		this.weight = -1.0f;
		this.source = null;
		this.action = null;
		this.target = null;
		this.place = null;
		this.directObject = null;
	}
	
	/**
	 * Construye un componente de la mente mediante un string con sus atributos
	 * separados por comas.
	 * @para str El string del que extraer la informaci�n del componente.
	 * */
	public Relation(String str) {
		String[] splittedLine = str.split(",");
		
		weight = Float.parseFloat(splittedLine[0].trim());
		source = new OntoBridgeComponent(splittedLine[1].trim(), OntoBridgeComponent.NAME);
		action = new OntoBridgeComponent(splittedLine[2].trim(), OntoBridgeComponent.ACTION);
		if (!splittedLine[3].trim().equals(""))
			target = new OntoBridgeComponent(splittedLine[3].trim(), OntoBridgeComponent.NAME);
		if (!splittedLine[4].trim().equals(""))
			place = new OntoBridgeComponent(splittedLine[4].trim(), OntoBridgeComponent.NAME);
		if (!splittedLine[5].trim().equals(""))
			directObject = new OntoBridgeComponent(splittedLine[5].trim(), OntoBridgeComponent.NAME);
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
	public Relation(float weight, OntoBridgeComponent source,
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
	public boolean instanceOf(Relation pattern) {
		boolean targetIsInstanceOf = (pattern.target == null) ||
				(pattern.target != null && pattern.target.isSuperClassOf(target));
		boolean placeIsInstanceOf = (pattern.place == null) ||
				(pattern.place != null && pattern.place.isSuperClassOf(place));
		boolean directObjectIsInstanceOf = (pattern.directObject == null) ||
				(pattern.directObject != null && pattern.directObject.isSuperClassOf(directObject));
		
		return (source.isSubClassOf(pattern.source) && 
				action.isSubClassOf(pattern.action) &&
				targetIsInstanceOf &&
				placeIsInstanceOf &&
				directObjectIsInstanceOf);
	}
	
	/**
	 * Copia el componente.
	 * @return Una copia del componente.
	 * */
	public Relation copy() {
		Relation copy = new Relation(weight, source.copy(), action.copy(),
				(target == null) ? null : target.copy(),
				(place == null) ? null : place.copy(),
				(directObject == null) ? null : directObject.copy());
		return copy;
	}
	
	/**
	 * Comprueba si un componente es igual a otro.
	 * @param o Componente con el que comparar.
	 * @return Valor booleano indicando el resultado de la comparaci�n.
	 * */
	public boolean equals(Object o) {
		if (!(o instanceof Relation))
			return false;
		Relation c = (Relation) o;
		
		boolean targetEq = (target == null && c.target == null) || 
				(target != null && target.equals(c.target));
		boolean placeEq = (place == null && c.place == null) || 
				(place != null && place.equals(c.place));
		boolean directObjectEq = (directObject == null && c.directObject == null) || 
				(directObject != null && directObject.equals(c.directObject));
		
		return (c.weight == weight && c.source.equals(source)
				&& c.action.equals(action) &&
				targetEq &&	placeEq && directObjectEq);
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + weight + "), " + source +
				"--{" + action +
				((directObject == null) ? "" : " [" + directObject + "]") + 
				((place == null) ? "" : " at " + place) + "}" + 
				((target == null) ? "" : "-->" + target);
	}
	
	/**
	 * Devuelve un string reducido pensado para usarse en un hasheo posterior.
	 * @return String reducido que identifica la instancia.
	 * */
	public String toShortString() {
		// TODO Tener en cuenta el peso? Comentar con Israel
		return source.getName() + 
				action.getName() + 
				((target == null) ? "" : target.getName()) + 
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
		String retVal = "Relaci�n\n";
		retVal += toString() + "\n";
		retVal += "Peso: " + this.weight + "\n";
		if (this.source != null) retVal += "Fuente: " + source.toString() + "\n";
		if (this.action != null) retVal += "Acci�n: " + action.toString() + "\n";
		if (this.target != null) retVal += "Destino: " + target.toString() + "\n";
		if (this.directObject != null) retVal += "Objeto directo: " + directObject.toString() + "\n";
		if (this.place != null) retVal += "Lugar: " + place.toString() + "\n";
		return retVal;
	}
	
}