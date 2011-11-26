package evaluator;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Representación de los patrones para las preguntas.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class QuestionPattern {

	/**
	 * Peso del patrón.
	 * */
	private float weight;
	
	/**
	 * Lista de patrones de expresiones.
	 * */
	private HashSet<ExpectationPattern> ePatterns;
	
	//TODO Construir su propia excepción
	/**
	 * Construye un patrón de pregunta mediante un string de la forma:
	 * [peso] - [exp1, exp2, ..., expN]
	 * @param str Cadena de caracteres de la cual se lee el patrón.
	 * @throws Exception 
	 * */
	public QuestionPattern(String str) throws Exception {
		// Patrón: [float] - [...]
		Pattern separatorPattern = Pattern.compile("\\[(\\d(\\.(\\d)+)?)\\]( )*-( )*\\[((.)*)\\]");
		Matcher separatorMatcher = separatorPattern.matcher(str);
		if (!separatorMatcher.find()) {
			throw new Exception("El patrón de pregunta no tiene el formato adecuado: [peso] - [exp1, ..., expN]");
		}
		weight = Float.parseFloat(separatorMatcher.group(1));
		
		// Patrón: {!}?(...){, {!}?(...)}*
		Pattern expectationsPattern = Pattern.compile("(\\!)?\\([^\\)]*\\)");
		Matcher expectationsMatcher = expectationsPattern.matcher(separatorMatcher.group(6).trim());
		ePatterns = new HashSet<ExpectationPattern>();
		while (expectationsMatcher.find()) {
			ePatterns.add(new ExpectationPattern(expectationsMatcher.group()));
		}
	}
	
	/**
	 * Devuelve una colección con las acciones usadas en el patrón.
	 * @return Colección de acciones.
	 * */
	public Collection<String> getActions() {
		HashSet<String> actions = new HashSet<String>();
		
		for (ExpectationPattern expectation : ePatterns) {
			actions.add(expectation.getAction());
		}
		
		return actions;
	}
	
	/**
	 * Devuelve la lista de patrones de expectativas contenidas en el patrón de pregunta.
	 * @return Lista de patrones de expectativas.
	 * */
	public HashSet<ExpectationPattern> getExpectationPatterns() {
		return ePatterns;
	}
	
	public static void main(String[] args) {
		try {
			new QuestionPattern("[0.9]  - [!(hola[Var1], casa[Var2])(adios[Var1], manolo)!(asd, qwe, asd, zxc)]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the weight
	 */
	public float getWeight() {
		return weight;
	}

}
