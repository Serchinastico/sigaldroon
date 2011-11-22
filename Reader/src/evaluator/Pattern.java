package evaluator;

import java.util.ArrayList;

import mind.Relation;


/**
 * Clase que implementa el patrón de las expectativas/preguntas usadas por el evaluador.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Pattern {
	
	/**
	 * Patrón en forma de lista de componentes de la mente (generalizadas).
	 * */
	private ArrayList<Relation> exprs;
	
	/**
	 * Lista de booleanos que indica si el patrón i-ésimo es negado o no.
	 * */
	private ArrayList<Boolean> negExprs;
	
	
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
	 * 	expX: expresión como los componentes de la mente pero sin valor. El símbolo '!'
	 * 			al principio indica que la expresión está negada.
	 * */
	public Pattern(String str) {
		exprs = new ArrayList<Relation>();
		negExprs = new ArrayList<Boolean>();
		
		String[] splittedStr = str.split("-");
		value = Float.parseFloat(splittedStr[0].trim());
		String[] exps = splittedStr[1].split("/");
		for (String strComponent : exps) {
			String strExp = strComponent;
			if (strExp.startsWith("!")) {
				strExp = strExp.substring(1);
				negExprs.add(true);
			}
			else {
				negExprs.add(false);
			}
			exprs.add(new Relation("0.0, " + strExp));
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
	public ArrayList<Relation> getExprs() {
		return exprs;
	}


	/**
	 * @param pattern the pattern to set
	 */
	public void setExprs(ArrayList<Relation> exprs) {
		this.exprs = exprs;
	}

	/**
	 * @return the negExprs
	 */
	public ArrayList<Boolean> getNegExprs() {
		return negExprs;
	}

	/**
	 * @param negExprs the negExprs to set
	 */
	public void setNegExprs(ArrayList<Boolean> negExprs) {
		this.negExprs = negExprs;
	}
}
