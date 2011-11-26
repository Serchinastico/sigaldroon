package evaluator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;

import operator.OPTarget;

import reader.Reader;

import mind.Mind;
import mind.Relation;
import mind.ontobridge.OntoBridgeSingleton;

/**
 * Implementación estática del evaluador para las historias generadas.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class SimpleEvaluator implements IEvaluator, Observer {

	/**
	 * Lista de cortes de la historia según el triángulo de Freytag
	 * en valores porcentuales.
	 * */
	private static final float[] STORY_BREAKS = {0.2f, 0.6f, 0.9f};
	
	/**
	 * Estado actual de la historia.
	 * */
	private State state;
	
	/**
	 * Lista de patrones de preguntas para el estado actual.
	 * */
	private ArrayList<QuestionPattern> qPatterns;
	
	public SimpleEvaluator() {
		state = State.INTRODUCTION;
		loadPatterns();
	}
	
	//TODO Todas las variables diferentes
	@Override
	public float eval(Mind m) {
		float value = 0.0f;
		
		for (QuestionPattern qPattern : qPatterns) {
			Collection<String> actions = qPattern.getActions();
			HashMap<String, Iterable<Relation>> relations = m.getRelations(actions);
			HashMap<String, String> variables = new HashMap<String, String>();
			
			if (checkQuestionPattern(qPattern.getExpectationPatterns(), relations, variables)) {
				value += qPattern.getWeight();
			}
		}
		
		return value;		
	}
	
	/**
	 * Comprueba que existe una combinación de relaciones y ligaduras de variables
	 * que satisfagan una lista de patrones de expectativas. Al tratarse de una búsqueda
	 * con backtracking el método es recursivo.
	 * @param ePatterns Lista de patrones de expectativas.
	 * @param relations Relaciones del mundo (filtradas por eficiencia).
	 * @param variables Tabla de ligaduras de variables hasta el momento.
	 * @return boolean True si se satisface el patrón.
	 * */
	@SuppressWarnings("unchecked")
	private boolean checkQuestionPattern(HashSet<ExpectationPattern> ePatterns,
			HashMap<String, Iterable<Relation>> relations,
			HashMap<String, String> variables) {
		
		// Caso base:
		if (ePatterns.isEmpty())
			return true;
		
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
					if (checkQuestionPattern(ePatternsCopy, relations, variablesCopy)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	/** Trata de ligar las variables libres y comprueba que las ligadas cumplan
	 * con la relación de instancia/subclase/superclase actualizando la tabla convenientemente
	 * @param variables Tabla de variables ligadas.
	 * @param ePattern Patrón de expectativas.
	 * @param relation Relación con la que se realiza el matching. 
	 * @return boolean True si no hay incompatibilidades con las variables.
	 * */
	private boolean linkVariables(HashMap<String, String> variables,
			ExpectationPattern ePattern, Relation relation) {
		boolean success = true;
		
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		
		for (int iAtt = 0; success && iAtt < OPTarget.NUM_TARGETS; iAtt++) {
			String rElement = relation.getElement(iAtt);
			String eVariable = ePattern.getVariable(iAtt);
			
			if (eVariable == null) continue;
			
			if (!variables.containsKey(eVariable)) {
				variables.put(eVariable, rElement);
			}
			// Si el nuevo elemento es subclase, se especializa la ligadura
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
				break;
			}
		}
		
		return success;
	}

	/**
	 * Carga en memoria los patrones contenidos en un fichero.
	 * */
	private void loadPatterns() {	
		qPatterns = new ArrayList<QuestionPattern>();
		
		try {
			FileInputStream fstream = new FileInputStream(state.getPath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			
			while ((line = br.readLine()) != null) {
				qPatterns.add(new QuestionPattern(line));
			}
		} catch (Exception e) {
			System.err.println("Error leyendo los patrones del fichero " + state.getPath() + ": " + e.getMessage());
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Reader))
			return;
		
		Reader r = (Reader) o;
		int maxSegments = r.getMaxSegments();
		
		State prevState = state;
		// Actualiza el estado de la historia por el tanto por ciento que se haya contado
		if (maxSegments >= 0 && maxSegments < maxSegments * STORY_BREAKS[0])
			state = State.INTRODUCTION;
		else if (maxSegments >= maxSegments * 0.2 && maxSegments < maxSegments * STORY_BREAKS[1])
			state = State.RISE;
		else if (maxSegments >= maxSegments * 0.6 && maxSegments < maxSegments * STORY_BREAKS[2])
			state = State.CLIMAX;
		else if (maxSegments >= maxSegments * 0.9 && maxSegments < maxSegments * 1.0)
			state = State.CATASTROPHE;
		
		// Se actualiza la lista de patrones si hemos cambiado de estado
		if (prevState != state) {
			loadPatterns();
		}
	}

	
	/**
	 * Enum con los posibles estados del evaluador que corresponden con los puntos
	 * del triángulo de Freytag.
	 * */	
	private enum State {
		INTRODUCTION ("resources/SimpleEvaluator/introduction_patterns.txt"), 
		RISE ("resources/SimpleEvaluator/rise_patterns.txt"),
		CLIMAX ("resources/SimpleEvaluator/climax_patterns.txt"),
		CATASTROPHE ("resources/SimpleEvaluator/catastrophe_patterns.txt");
		
		private String path;
		
		State(String path) {
			this.path = path;
		}
		
		public String getPath() {
			return path;
		}
	}
}
