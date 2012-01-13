package evaluator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;

import mind.Mind;
import mind.Relation;
import mind.RelationSet;
import mind.ontobridge.OntoBridgeSingleton;

public abstract class AbstractEvaluator implements IEvaluator {

	/**
	 * Conjunto de patrones de preguntas.
	 * */
	protected ArrayList<QuestionPattern> qPatterns;
	
	//TODO Todas las variables diferentes
	@Override
	public float eval(Mind m, RelationSet rSet) {
		float value = 0.0f;
		
		for (int iPattern = 0; iPattern < qPatterns.size(); iPattern++) {
			QuestionPattern qPattern = qPatterns.get(iPattern);
			
			Collection<String> actions = qPattern.getActions();
			Collection<String> negActions = qPattern.getNegActions();
			HashMap<String, Iterable<Relation>> relations = m.getRelations(actions);
			HashMap<String, Iterable<Relation>> negRelations = m.getRelations(negActions);
			HashMap<String, String> variables = new HashMap<String, String>();
			HashSet<Relation> usedRelations = new HashSet<Relation>();
			
			if (checkQuestionPattern(qPattern.getExpectationPatterns(), qPattern.getNegExpectationPatterns(), relations, negRelations, variables, usedRelations)) {
				// Se saca la media de pesos de las relaciones usadas
				float averageRelationWeight = 0.0f;
				int numRelations = 0;
				for (Relation r : usedRelations) {
					if (r.getWeight() != 1.0) {
						averageRelationWeight += r.getWeight();
						numRelations++;
					}
				}
				averageRelationWeight = (numRelations == 0) ? 0.0f : (averageRelationWeight / numRelations);
				
				float averageRelationPenalty = getAverageRelationPenalty(m, rSet);
				
				value += (numRelations == 0) ? 0.0f : getActualWeight(iPattern) * averageRelationPenalty/* * averageRelationWeight*/;
			}
		}
		
		return value;	
	}
	
	private float getAverageRelationPenalty(Mind m, RelationSet rSet) {
		float average = 0.0f;
		int numRelations = 0;
		
		for (Relation r : m) {
			if (rSet.contains(r)) {
				average += rSet.getWeight(r);
				numRelations++;
			}
		}
		
		return (numRelations == 0) ? 1.0f : (average / (float) numRelations);
	}

	/**
	 * Método que devuelve el peso actual de un patrón pregunta. Está
	 * pensado para ser sobreescrito en las subclases.
	 * @param iPattern Índice del patrón.
	 * @return Peso del patrón.
	 * */
	protected abstract float getActualWeight(int iPattern);
	
	/**
	 * Comprueba que existe una combinación de relaciones y ligaduras de variables
	 * que satisfagan una lista de patrones de expectativas. Al tratarse de una búsqueda
	 * con backtracking el método es recursivo.
	 * @param ePatterns Lista de patrones de expectativas.
	 * @param relations Relaciones del mundo (filtradas por eficiencia).
	 * @param variables Tabla de ligaduras de variables hasta el momento.
	 * @param usedRelations Tabla de relaciones usadas.
	 * @return boolean True si se satisface el patrón.
	 * */
	@SuppressWarnings("unchecked")
	protected boolean checkQuestionPattern(HashSet<ExpectationPattern> ePatterns,
			HashSet<ExpectationPattern> negEPatterns,
			HashMap<String, Iterable<Relation>> relations,
			HashMap<String, Iterable<Relation>> negRelations,
			HashMap<String, String> variables,
			HashSet<Relation> usedRelations) {
		
		// Caso base:
		if (ePatterns.isEmpty())
			return checkNegatedExpectations(negEPatterns, variables, negRelations) && allDifferent(variables);
		
		ExpectationPattern ePattern = ePatterns.iterator().next();
		
		if (relations.get(ePattern.getAction()) == null)
			return false;
	
		// Caso recursivo:
		HashSet<ExpectationPattern> ePatternsCopy = (HashSet<ExpectationPattern>) ePatterns.clone();
		ePatternsCopy.remove(ePattern);

		for (Relation matchedRelation : relations.get(ePattern.getAction())) {
			if (matchedRelation.instanceOf(ePattern)) {
				HashMap<String, String> variablesCopy = (HashMap<String, String>) variables.clone();
				if (linkVariables(variablesCopy, ePattern, matchedRelation)) {
					if (checkQuestionPattern(ePatternsCopy, negEPatterns, relations, negRelations, variablesCopy, usedRelations)) {
						usedRelations.add(matchedRelation);
						return true;
					}
				}
			}
		}
		
		return false;
	}

	private boolean allDifferent(HashMap<String, String> variables) {
		Object[] values = variables.values().toArray();
		for (int i = 0; i < values.length; i++) {
			String s1 = (String) values[i];
			for (int j = i + 1; j < values.length; j++) {
				String s2 = (String) values[j];
				if (s1 != null && s2 != null && s1.equals(s2))
					return false;
			}
		}
		return true;
	}

	/** Trata de ligar las variables libres y comprueba que las ligadas cumplan
	 * con la relación de instancia/subclase/superclase actualizando la tabla convenientemente
	 * @param variables Tabla de variables ligadas.
	 * @param ePattern Patrón de expectativas.
	 * @param relation Relación con la que se realiza el matching. 
	 * @return boolean True si no hay incompatibilidades con las variables.
	 * */
	protected boolean linkVariables(HashMap<String, String> variables,
			ExpectationPattern ePattern, Relation relation) {
		boolean success = true;
		
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		
		for (int iAtt = 0; success && iAtt < Relation.NUM_ELEMENTS; iAtt++) {
			String rElement = relation.getElement(iAtt);
			String eVariable = ePattern.getVariable(iAtt);
			
			if (eVariable == null) continue;
			if (rElement == null) continue;
			
			if (!variables.containsKey(eVariable)) {
				variables.put(eVariable, rElement);
			}
			
			if (!ob.existsClass(variables.get(eVariable))) {
				// Var: instancia - Rel: instancia
				if (!ob.existsClass(rElement)) {
					success = rElement.equals(variables.get(eVariable));
				}
				// Var: instancia - Rel: clase
				else {
					success = ob.isInstanceOf(variables.get(eVariable), rElement);
				}
			}
			else {
				// Var: clase - Rel: instancia
				if (!ob.existsClass(rElement)) {
					if (ob.isInstanceOf(rElement, variables.get(eVariable))) {
						variables.put(eVariable, rElement);
					}
					else { 
						success = false;
					}
				}
				// Var: clase - Rel: clase
				else {
					if (ob.isSubClassOf(rElement, variables.get(eVariable))) {
						variables.put(eVariable, rElement);
					}
					else {
						success = ob.isSubClassOf(variables.get(eVariable), rElement);
					}
				}
			}
			
			/*// Si el nuevo elemento es subclase, se especializa la ligadura
			else if (ob.isSubClassOf(rElement, variables.get(eVariable))) {
				variables.put(eVariable, rElement);
			}
			// Si el nuevo elemento es superclase se continúa sin hacer cambios
			else if (ob.isSubClassOf(variables.get(eVariable), rElement)) {
				continue;
			}
			// Si no mantienen relación es que la relación no encaja con la ligadura de variables actual
			else {
				success = false;
			}*/
		}
		
		return success;
	}
	
	/**
	 * Comprueba que las expetativas negadas son satisfactibles dado un conjunto
	 * de ligaduras de variables y un conjunto de relaciones de la mente.
	 * @param negEPatterns Conjunto de expectativas negadas
	 * @param variables Conjunto de ligaduras de variables.
	 * @param relations Relaciones de la mente.
	 * @return True si se satisface el conjunto de expectativas negadas.
	 * */
	protected boolean checkNegatedExpectations(HashSet<ExpectationPattern> negEPatterns,
			HashMap<String, String> variables,
			HashMap<String, Iterable<Relation>> relations) {
		boolean satisfiable = true;
		
		for (Iterator<ExpectationPattern> itNegExp = negEPatterns.iterator(); itNegExp.hasNext() && satisfiable;) {
			ExpectationPattern negExp = itNegExp.next();
			if (relations.get(negExp.getAction()) == null)
				continue;
			for (Iterator<Relation> itRel = relations.get(negExp.getAction()).iterator(); itRel.hasNext() && satisfiable;) {
				Relation relation = itRel.next();
				if (relation.instanceOf(negExp)) {
					if (!checkNegVariables(negExp, relation, variables)) {
						satisfiable = false;
					}
				}
			}
		}
		
		return satisfiable;
	}

	/**
	 * Comprueba que, dada una expresión (negada), una relación y un conjunto de ligaduras 
	 * de variables, no se satisfaga la expresión.
	 * @param negExp Patrón de expresión negada.
	 * @param relation Relación de la mente.
	 * @param variables Conjunto de ligaduras de variables.
	 * @return True si no se satisface la expresión.
	 * */
	protected boolean checkNegVariables(ExpectationPattern negExp,
			Relation relation, HashMap<String, String> variables) {
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		boolean satisfiable = true;
		
		for (int iAtt = 0; satisfiable && iAtt < Relation.NUM_ELEMENTS; iAtt++) {
			String rElement = relation.getElement(iAtt);
			String eVariable = negExp.getVariable(iAtt);
			
			if (!variables.containsKey(eVariable)) {
				System.err.println("Variable desconocida en el patrón negado de expectativa: " + eVariable);
			}
			else if (ob.isSubClassOf(rElement, variables.get(eVariable)) || 
					ob.isSubClassOf(variables.get(eVariable), rElement)) {
				satisfiable = false;
			}
		}
		
		return satisfiable;
	}

	public ArrayList<QuestionPattern> getUsedPatterns(Mind m, int maxSegments, int mindSegment) {
		ArrayList<QuestionPattern> usedPatterns = new ArrayList<QuestionPattern>();
		for (int iPattern = 0; iPattern < qPatterns.size(); iPattern++) {
			QuestionPattern qPattern = qPatterns.get(iPattern);
			
			Collection<String> actions = qPattern.getActions();
			Collection<String> negActions = qPattern.getNegActions();
			HashMap<String, Iterable<Relation>> relations = m.getRelations(actions);
			HashMap<String, Iterable<Relation>> negRelations = m.getRelations(negActions);
			HashMap<String, String> variables = new HashMap<String, String>();
			HashSet<Relation> usedRelations = new HashSet<Relation>();
			
			if (checkQuestionPattern(qPattern.getExpectationPatterns(), qPattern.getNegExpectationPatterns(), relations, negRelations, variables, usedRelations)) {
				usedPatterns.add(qPattern);
			}
		}
		
		return usedPatterns;
	}
	
	/**
	 * @return the qPatterns
	 */
	public ArrayList<QuestionPattern> getQPatterns() {
		return qPatterns;
	}

}
