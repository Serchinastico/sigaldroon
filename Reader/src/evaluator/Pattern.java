package evaluator;

import java.util.ArrayList;

import world.Component;

/**
 * Clase que implementa el patrón de las expectativas/preguntas usadas por el evaluador.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Pattern {
	
	/**
	 * Patrón en forma de lista de componentes del mundo (generalizadas).
	 * */
	private ArrayList<Component> exprs;
	
	/**
	 * Valor del patrón. Cuánto más alto sea este valor mejor será la historia
	 * que lo contenga.
	 * */
	private float value;
	
	/**
	 * Constructora mediante un string pensada para crear patrones a 
	 * través de la lectura de un fichero.
	 * La cadena de caracteres ha de tener el siguiente formato (sin corchetes):
	 * [valor] - [exp1]/[exp2]/.../[expN]
	 * 	valor: float.
	 * 	expX: expresión como los componentes del mundo pero sin valor.  
	 * */
	public Pattern(String str) {
		exprs = new ArrayList<Component>();
		
		String[] splittedStr = str.split("-");
		value = Float.parseFloat(splittedStr[0].trim());
		String[] exps = splittedStr[1].trim().split("/");
		for (String strComponent : exps) {
			exprs.add(new Component("0.0," + strComponent));
		}
	}
	
	
	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}


	/**
	 * @return the pattern
	 */
	public ArrayList<Component> getExprs() {
		return exprs;
	}


	/**
	 * @param pattern the pattern to set
	 */
	public void setExprs(ArrayList<Component> exprs) {
		this.exprs = exprs;
	}
}
