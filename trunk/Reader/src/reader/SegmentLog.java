package reader;

import java.util.ArrayList;
import java.util.HashSet;

import mind.Relation;

import evaluator.QuestionPattern;

public class SegmentLog {

	private ArrayList<QuestionPattern> patterns;

	private HashSet<Relation> relations;

	private HashSet<String> characters;

	private HashSet<String> scenarios;

	private HashSet<String> props;

	/**
	 * @return the patterns
	 */
	public ArrayList<QuestionPattern> getPatterns() {
		return patterns;
	}

	/**
	 * @param patterns the patterns to set
	 */
	public void setPatterns(ArrayList<QuestionPattern> patterns) {
		this.patterns = patterns;
	}

	/**
	 * @return the relations
	 */
	public HashSet<Relation> getRelations() {
		return relations;
	}

	/**
	 * @param relations the relations to set
	 */
	public void setRelations(HashSet<Relation> relations) {
		this.relations = relations;
	}

	/**
	 * @return the characters
	 */
	public HashSet<String> getCharacters() {
		return characters;
	}

	/**
	 * @param characters the characters to set
	 */
	public void setCharacters(HashSet<String> characters) {
		this.characters = characters;
	}

	/**
	 * @return the scenarios
	 */
	public HashSet<String> getScenarios() {
		return scenarios;
	}

	/**
	 * @param scenarios the scenarios to set
	 */
	public void setScenarios(HashSet<String> scenarios) {
		this.scenarios = scenarios;
	}

	/**
	 * @return the props
	 */
	public HashSet<String> getProps() {
		return props;
	}

	/**
	 * @param props the props to set
	 */
	public void setProps(HashSet<String> props) {
		this.props = props;
	}

	@Override
	public String toString() {
		String dataLog = "";

		// Relaciones
		dataLog += "- Relaciones:\n\r";
		for (Relation r : relations) {
			dataLog += r.toStringRelation() + "\n\r";
		}

		// Personajes
		dataLog += "- Personajes:\n\r";
		for (String chars : characters) {
			dataLog += "    " + chars + "\n\r";
		}

		// Objetos
		dataLog += "- Objetos:\n\r";
		for (String prop : props) {
			dataLog += "    " + prop + "\n\r";
		}

		// Escenarios
		dataLog += "- Escenarios:\n\r";
		for (String scenario : scenarios) {
			dataLog += "    " + scenario + "\n\r";
		}
		
		// Patrones
		dataLog += "- Patrones:\n\r";
		for (QuestionPattern qP : patterns) {
			dataLog += "    " + qP.toString() + "\n\r";
		}

		return dataLog;
	}

}
