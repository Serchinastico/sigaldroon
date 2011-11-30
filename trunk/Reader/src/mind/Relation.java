package mind;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import mind.ontobridge.OntoBridgeSingleton;

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
	 * Elementos de la relaci�n: source, action, target, place, directObject.
	 */
	protected String[] elements;
	
	/** 
	 * �ndice para el origen de la acci�n.
	 */
	public static final int SOURCE = 0;
	
	/**
	 * �ndice para la acci�n realizada.
	 */
	public static final int ACTION = 1;
	
	/**
	 * �ndice para el objetivo de la acci�n.
	 */
	public static final int TARGET = 2;
	
	/**
	 * �ndice para el lugar donde sucede la acci�n.
	 */
	public static final int PLACE = 3;
	
	/**
	 * �ndice para el complemento directo.
	 */
	public static final int DIRECT_OBJECT = 4;
	
	/**
	 * N�mero de elementos de la relaci�n.
	 */
	protected static int NUM_ELEMENTS = 5;
	
	/**
	 * Constructora por defecto.
	 */
	public Relation() {
		this.weight = -1.0f;
		this.elements = new String[NUM_ELEMENTS];
		this.elements[SOURCE] = null;
		this.elements[ACTION] = null;
		this.elements[TARGET] = null;
		this.elements[PLACE] = null;
		this.elements[DIRECT_OBJECT] = null;
	}
	
	/**
	 * Construye un componente de la mente mediante un string con sus atributos
	 * separados por comas.
	 * @throws Exception 
	 * @para str El string del que extraer la informaci�n del componente.
	 * */
	public Relation(String str) throws Exception {
		// Se separa el peso del resto de la relaci�n: {[peso] - }?[atributos]
		Pattern separatorPattern = Pattern.compile("(\\[(\\d(\\.(\\d)+)?)\\]( )*-( )*)?\\[((.)*)\\]");
		Matcher separatorMatcher = separatorPattern.matcher(str);
		if (!separatorMatcher.find()) {
			throw new Exception("La relaci�n no tiene la estructura adecuada: {[peso] - }?[atributos]");
		}

		// Si se omite el peso se asume un valor de 1.0
		this.weight = (separatorMatcher.group(2) == null) ?
				1.0f :
				Float.parseFloat(separatorMatcher.group(2)); 

		this.elements = new String[NUM_ELEMENTS];
		String[] splittedAtts = separatorMatcher.group(7).split(",");
		for (int iStr = 0, iAtt = 0; iStr < splittedAtts.length; iStr++, iAtt++) {
			// Cada atributo es de la forma: {n:}?concepto
			Pattern attPattern = Pattern.compile("(?:(\\d+)(?: )*\\:(?: )*)?((\\w*))");
			Matcher attMatcher = attPattern.matcher(splittedAtts[iStr].trim());
			if (!attMatcher.find()) {
				throw new Exception("La relaci�n no tiene la estructura adecuada: [{{n:}?concepto}*]");
			}
			
			// Si el �ndice n existe se pone el contador a dicho valor
			if (attMatcher.group(1) != null) {
				iAtt = Integer.parseInt(attMatcher.group(1));
			}
			
			if (iAtt >= NUM_ELEMENTS) {
				throw new Exception("Se ha intentado introducir un atributo con �ndice mayor del permitido en la creaci�n de la Relaci�n.");
			}
			
			this.elements[iAtt] = (attMatcher.group(2).trim().equals("")) ?
					null :
					attMatcher.group(2).trim();
		}
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
	public Relation(float weight, String source,
			String action, String target,
			String place, String directObject) {
		this.weight = weight;
		this.elements = new String[NUM_ELEMENTS];
		this.elements[SOURCE] = source;
		this.elements[ACTION] = action;
		this.elements[TARGET] = target;
		this.elements[PLACE] = place;
		this.elements[DIRECT_OBJECT] = directObject;
	}
	
	/**
	 * Comprueba si un componente es instancia de otro (patr�n).
	 * @param pattern Patr�n del componente.
	 * @return Valor booleano indicando si el componente es una instancia del patr�n.
	 * */
	public boolean instanceOf(Relation pattern) {
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		
		boolean targetIsInstanceOf = (pattern.getTarget() == null) ||
				(pattern.getTarget() != null && ob.isSubClassOf(getTarget(), pattern.getTarget()));
		boolean placeIsInstanceOf = (pattern.getPlace() == null) ||
				(pattern.getPlace() != null && ob.isSubClassOf(getPlace(), pattern.getPlace()));
		boolean directObjectIsInstanceOf = (pattern.getDirectObject() == null) ||
				(pattern.getDirectObject() != null && ob.isSubClassOf(getDirectObject(), pattern.getDirectObject()));
		
		return (ob.isSubClassOf(getSource(), pattern.getSource()) && 
				ob.isSubClassOf(getAction(), pattern.getAction()) &&
				targetIsInstanceOf &&
				placeIsInstanceOf &&
				directObjectIsInstanceOf);
	}
	
	/**
	 * Copia el componente.
	 * @return Una copia del componente.
	 * */
	public Relation copy() {
		Relation copy = new Relation(weight, getSource(), getAction(),
				(getTarget() == null) ? null : getTarget(),
				(getPlace() == null) ? null : getPlace(),
				(getDirectObject() == null) ? null : getDirectObject());
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
		
		boolean sourceEq = (getSource() == null && c.getSource() == null) || 
				(getSource() != null && getSource().equals(c.getSource()));
		boolean actionEq = (getAction() == null && c.getAction() == null) || 
				(getAction() != null && getAction().equals(c.getAction()));
		boolean targetEq = (getTarget() == null && c.getTarget() == null) || 
				(getTarget() != null && getTarget().equals(c.getTarget()));
		boolean placeEq = (getPlace() == null && c.getPlace() == null) || 
				(getPlace() != null && getPlace().equals(c.getPlace()));
		boolean directObjectEq = (getDirectObject() == null && c.getDirectObject() == null) || 
				(getDirectObject() != null && getDirectObject().equals(c.getDirectObject()));
		
		return (c.weight == weight &&
				sourceEq &&	actionEq &&	targetEq &&
				placeEq && directObjectEq);
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + weight + "), " + getSource() +
				"--{" + getAction() +
				((getDirectObject() == null) ? "" : " [" + getDirectObject() + "]") + 
				((getPlace() == null) ? "" : " at " + getPlace()) + "}" + 
				((getTarget() == null) ? "" : "-->" + getTarget());
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 * */
	@Override
	public int hashCode() {
		return (weight + getSource() + getAction() + getTarget() + getPlace() + getDirectObject()).hashCode();
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
		return elements[SOURCE];
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.elements[SOURCE] = source;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return elements[ACTION];
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.elements[ACTION] = action;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return elements[TARGET];
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.elements[TARGET] = target;
	}
	
	/**
	 * @return the place
	 */
	public String getPlace() {
		return elements[PLACE];
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.elements[PLACE] = place;
	}
	
	/**
	 * @return the direct object
	 */
	public String getDirectObject() {
		return elements[DIRECT_OBJECT];
	}

	/**
	 * @param directObject the direct object to set
	 */
	public void setDirectObject(String directObject) {
		this.elements[DIRECT_OBJECT] = directObject;
	}
	
	/**
	 * Devuelve el elemento de la relaci�n pedido por argumento seg�n el �ndice,
	 * ver constantes de la clase Relation.
	 * @param numElem �ndice del elemento de la relaci�n.
	 * @return El elemento de la relaci�n pedido: source, acci�n, target, place, direct object.
	 */
	public String getElement(int numElem) {
		return this.elements[numElem];
	}
	
	/**
	 * Modifica uno de los elementos de la relaci�n.
	 * @param numElem El �ndice dado por constantes de la clase Relation.
	 * @param elem El nuevo elemento.
	 */
	public void setElement(int numElem, String elem) {
		this.elements[numElem] = elem;
	}
	
	/**
	 * Representaci�n en String temporal para la interfaz.
	 * @return El string para la interfaz que muestra esta relaci�n.
	 */
	public String toStringRelation() {
		String retVal = "Relaci�n\n";
		retVal += toString() + "\n";
		retVal += "Peso: " + this.weight + "\n";
		if (getSource() != null) retVal += "Fuente: " + getSource() + "\n";
		if (getAction() != null) retVal += "Acci�n: " + getAction() + "\n";
		if (getTarget() != null) retVal += "Destino: " + getTarget() + "\n";
		if (getDirectObject() != null) retVal += "Objeto directo: " + getDirectObject() + "\n";
		if (getPlace() != null) retVal += "Lugar: " + getPlace() + "\n";
		return retVal;
	}
	
}
