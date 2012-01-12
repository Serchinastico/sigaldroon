package reader;

import java.util.ArrayList;

import mind.Relation;

import evaluator.QuestionPattern;

public class SegmentLog {
	
	private ArrayList<QuestionPattern> patterns;
	
	private ArrayList<Relation> relations;
	
	private ArrayList<String> characters;
	
	private ArrayList<String> scenarios;
	
	private ArrayList<String> props;

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
	public ArrayList<Relation> getRelations() {
		return relations;
	}

	/**
	 * @param relations the relations to set
	 */
	public void setRelations(ArrayList<Relation> relations) {
		this.relations = relations;
	}

	/**
	 * @return the characters
	 */
	public ArrayList<String> getCharacters() {
		return characters;
	}

	/**
	 * @param characters the characters to set
	 */
	public void setCharacters(ArrayList<String> characters) {
		this.characters = characters;
	}

	/**
	 * @return the scenarios
	 */
	public ArrayList<String> getScenarios() {
		return scenarios;
	}

	/**
	 * @param scenarios the scenarios to set
	 */
	public void setScenarios(ArrayList<String> scenarios) {
		this.scenarios = scenarios;
	}

	/**
	 * @return the props
	 */
	public ArrayList<String> getProps() {
		return props;
	}

	/**
	 * @param props the props to set
	 */
	public void setProps(ArrayList<String> props) {
		this.props = props;
	}
	
	
}
