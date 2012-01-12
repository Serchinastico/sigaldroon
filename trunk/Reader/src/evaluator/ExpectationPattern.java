package evaluator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mind.Relation;

/**
 * Representaci�n de los patrones de expectativas.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class ExpectationPattern extends Relation {
	
	/**
	 * Variables de la relaci�n: source, action, target, place, directObject.
	 */
	private String[] variables;

	// TODO Crear la excepci�n propia
	/**
	 * Construye un patr�n de expresi�n mediante un string de la forma:
	 * (source{:Var}?, action{:Var}?, {target{:Var}?}?, {place{:Var}?}?, {directObject{:Var}?}?, {atribute{:Var}?}?)
	 * @param str Cadena de caracteres de la cual se extrae el patr�n.
	 * @throws Exception
	 * */
	public ExpectationPattern(String str) throws Exception {
		// Patr�n: (source, action, {target}?, {place}?, {directObject}?)
		Pattern separatorPattern = Pattern.compile("\\((([^\\)])*)\\)");
		Matcher separatorMatcher = separatorPattern.matcher(str);
		if (!separatorMatcher.find()) {
			throw new Exception("El patr�n de expectativa no tiene el formato adecuado: (source{:Var}?, action{:Var}?, {target{:Var}?}?, {place{:Var}?}?, {directObject{:Var}?}?, {atribute{:Var}?}?)");
		}
		
		variables = new String[NUM_ELEMENTS];
		
		String[] splittedRelation = separatorMatcher.group(1).split(",");
		switch(splittedRelation.length) {
		case 6:
			readElement(splittedRelation[5].trim(), ATTRIBUTE);
		case 5:
			readElement(splittedRelation[4].trim(), DIRECT_OBJECT);
		case 4:
			readElement(splittedRelation[3].trim(), PLACE);
		case 3:
			readElement(splittedRelation[2].trim(), TARGET);
		case 2:
			readElement(splittedRelation[1].trim(), ACTION);
			readElement(splittedRelation[0].trim(), SOURCE);
			break;
		default:
			throw new Exception("El patr�n de expectativa no tiene el formato adecuado: (source{:Var}?, action{:Var}?, {target{:Var}?}?, {place{:Var}?}?, {directObject{:Var}?}?)");	
		}
	}
	
	/**
	 * Lee un elemento con el siguiente formato:
	 * elemento{:variable}?
	 * @param strElement String de donde se lee el elemento.
	 * @param iElement �ndice del elemento que se est� leyendo.
	 * @throws Exception
	 * */
	private void readElement(String strElement, int iElement) throws Exception {
		Pattern varPattern = Pattern.compile("((\\w)*)(:((\\w*)))?");
		Matcher varMatcher = varPattern.matcher(strElement);
		if (!varMatcher.find())
			throw new Exception("El patr�n de expectativa no tiene el formato adecuado: (source{:Var}?, action{:Var}?, {target{:Var}?}?, {place{:Var}?}?, {directObject{:Var}?}?)");
		
		elements[iElement] = varMatcher.group(1);
		variables[iElement] = varMatcher.group(4);
	}
	
	/**
	 * Devuelve la variable de la relaci�n pedido por argumento seg�n el �ndice,
	 * ver constantes de la clase Relation.
	 * @param numElem �ndice de la variable de la relaci�n.
	 * @return La variable de la relaci�n pedida: source, acci�n, target, place, direct object.
	 */
	public String getVariable(int numVar) {
		return this.variables[numVar];
	}
}
