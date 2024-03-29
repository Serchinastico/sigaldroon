package evaluator;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Representaci�n de los patrones para las preguntas.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class QuestionPattern {
	
	/**
	 * Valor enumerado que indica en qu� momento de la historia es preferible el patr�n.
	 * Puede tener valor 'P', 'M' o 'F' o una combinaci�n de los mismos.
	 */
	private String moment;
	
	/**
	 * Peso del patr�n.
	 * */
	private float weight;
	
	/**
	 * Lista de patrones de expresiones.
	 * */
	private HashSet<ExpectationPattern> ePatterns;
	
	/**
	 * Lista de patrones de expresiones negadas.
	 * */
	private HashSet<ExpectationPattern> negEPatterns;
	
	//TODO Construir su propia excepci�n
	/**
	 * Construye un patr�n de pregunta mediante un string de la forma:
	 * [peso] - [Moment] - [exp1, exp2, ..., expN] - [nexp1, nexp2, ..., nexpN]
	 * @param str Cadena de caracteres de la cual se lee el patr�n.
	 * @throws Exception 
	 * */
	public QuestionPattern(String str) throws Exception {
		// Patr�n: [float] - [...] - [...] - [...]
		Pattern separatorPattern = Pattern.compile("\\[(\\d(\\.(\\d)+)?)\\]( )*-( )*\\[((.)*)\\]( )*-( )*\\[((.)*)\\]( )*-( )*\\[((.)*)\\]");
		Matcher separatorMatcher = separatorPattern.matcher(str);
		if (!separatorMatcher.find()) {
			throw new Exception("El patr�n de pregunta no tiene el formato adecuado: [peso] - [Moment] - [exp1, ..., expN] - [nexp1, ..., nexpN]");
		}
		weight = Float.parseFloat(separatorMatcher.group(1));
		moment = separatorMatcher.group(6);
		
		// Patr�n: (...){, (...)}*
		// Expectativas
		Pattern expectationsPattern = Pattern.compile("\\([^\\)]*\\)");
		Matcher expectationsMatcher = expectationsPattern.matcher(separatorMatcher.group(10).trim());
		ePatterns = new HashSet<ExpectationPattern>();
		while (expectationsMatcher.find()) {
			ePatterns.add(new ExpectationPattern(expectationsMatcher.group()));
		}
		
		// Expectativas negadas
		Matcher negExpectationsMatcher = expectationsPattern.matcher(separatorMatcher.group(14).trim());
		negEPatterns = new HashSet<ExpectationPattern>();
		while (negExpectationsMatcher.find()) {
			negEPatterns.add(new ExpectationPattern(negExpectationsMatcher.group()));
		}
	}
	
	/**
	 * Devuelve una colecci�n con las acciones usadas en el patr�n.
	 * @return Colecci�n de acciones.
	 * */
	public Collection<String> getActions() {
		HashSet<String> actions = new HashSet<String>();
		
		for (ExpectationPattern expectation : ePatterns) {
			actions.add(expectation.getAction());
		}
		
		return actions;
	}
	
	/**
	 * Devuelve una colecci�n con las acciones usadas en las expectativas negadas.
	 * @return Colecci�n de acciones.
	 * */
	public Collection<String> getNegActions() {
		HashSet<String> actions = new HashSet<String>();
		
		for (ExpectationPattern expectation : negEPatterns) {
			actions.add(expectation.getAction());
		}
		
		return actions;
	}
	
	/**
	 * Devuelve la lista de patrones de expectativas contenidas en el patr�n de pregunta.
	 * @return Lista de patrones de expectativas.
	 * */
	public HashSet<ExpectationPattern> getExpectationPatterns() {
		return ePatterns;
	}
	
	/**
	 * Devuelve la lista de patrones de expectativas negadas contenidas en el patr�n de pregunta.
	 * @return Lista de patrones de expectativas.
	 * */
	public HashSet<ExpectationPattern> getNegExpectationPatterns() {
		return negEPatterns;
	}

	/**
	 * @return the weight
	 */
	public float getWeight() {
		return weight;
	}
	
	public String getMoment() {
		return moment;
	}
	
	public String toString() {
		String str = "";
		
		str += "Expectations: [\n";
		int iExp = 0;
		for (ExpectationPattern ePattern : ePatterns) {
			str += "  " + (iExp++) + "." + ePattern.toString() + "\n";
		}
		str += "]\n";
		iExp = 0;
		str += "Neg. Expectations: [\n";
		for (ExpectationPattern negEPattern : negEPatterns) {
			str += "  " + (iExp++) + "." + negEPattern.toString() + "\n";
		}
		str += "]\n";
		
		return str;
	}

}
