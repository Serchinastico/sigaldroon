package mind;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import mind.ontobridge.OntoBridgeSingleton;

/**
 * Componente fundamental de la mente del lector.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Relation {
	/**
	 * Peso de veracidad
	 * */
	private float weight;
	
	/**
	 * Origen de la acción
	 * */
	private String source;
	
	/**
	 * Acción realizada
	 * */
	private String action;
	
	/**
	 * Objetivo de la acción
	 * */
	private String target;
	
	/**
	 * Lugar donde sucede la acción.
	 * */
	private String place;
	
	/**
	 * Complemento directo.
	 * */
	private String directObject;
	
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
	 * @para str El string del que extraer la información del componente.
	 * */
	public Relation(String str) {
		String[] splittedLine = str.split(",");
		
		weight = Float.parseFloat(splittedLine[0].trim());
		source = splittedLine[1].trim();
		action = splittedLine[2].trim();
		target = (splittedLine[3].trim().equals("")) ?
				null :
				splittedLine[3].trim();
		place = (splittedLine[4].trim().equals("")) ?
				null :
				splittedLine[4].trim();
		directObject = (splittedLine[5].trim().equals("")) ?
				null :
				splittedLine[5].trim();
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
	public Relation(float weight, String source,
			String action, String target,
			String place, String directObject) {
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
	public boolean instanceOf(Relation pattern) {
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		
		boolean targetIsInstanceOf = (pattern.target == null) ||
				(pattern.target != null && ob.isSubClassOf(target, pattern.target));
		boolean placeIsInstanceOf = (pattern.place == null) ||
				(pattern.place != null && ob.isSubClassOf(place, pattern.place));
		boolean directObjectIsInstanceOf = (pattern.directObject == null) ||
				(pattern.directObject != null && ob.isSubClassOf(directObject, pattern.directObject));
		
		return (ob.isSubClassOf(source, pattern.source) && 
				ob.isSubClassOf(action, pattern.action) &&
				targetIsInstanceOf &&
				placeIsInstanceOf &&
				directObjectIsInstanceOf);
	}
	
	/**
	 * Copia el componente.
	 * @return Una copia del componente.
	 * */
	public Relation copy() {
		Relation copy = new Relation(weight, source, action,
				(target == null) ? null : target,
				(place == null) ? null : place,
				(directObject == null) ? null : directObject);
		return copy;
	}
	
	/**
	 * Comprueba si un componente es igual a otro.
	 * @param o Componente con el que comparar.
	 * @return Valor booleano indicando el resultado de la comparación.
	 * */
	public boolean equals(Object o) {
		if (!(o instanceof Relation))
			return false;
		Relation c = (Relation) o;
		
		boolean sourceEq = (source == null && c.source == null) || 
				(source != null && source.equals(c.source));
		boolean actionEq = (action == null && c.action == null) || 
				(action != null && action.equals(c.action));
		boolean targetEq = (target == null && c.target == null) || 
				(target != null && target.equals(c.target));
		boolean placeEq = (place == null && c.place == null) || 
				(place != null && place.equals(c.place));
		boolean directObjectEq = (directObject == null && c.directObject == null) || 
				(directObject != null && directObject.equals(c.directObject));
		
		return (c.weight == weight &&
				sourceEq &&	actionEq &&	targetEq &&
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
				((place == null) ? "" : " at " + place) + "}" + 
				((target == null) ? "" : "-->" + target);
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 * */
	@Override
	public int hashCode() {
		return (weight + source + action + target + place + directObject).hashCode();
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
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	
	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	
	/**
	 * @return the direct object
	 */
	public String getDirectObject() {
		return directObject;
	}

	/**
	 * @param directObject the direct object to set
	 */
	public void setDirectObject(String directObject) {
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
