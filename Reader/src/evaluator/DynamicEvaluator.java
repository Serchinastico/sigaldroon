package evaluator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mind.Mind;
import mind.Relation;

import operator.OPTarget;
import reader.Reader;

public class DynamicEvaluator extends AbstractEvaluator implements Observer {

	/** Enumerado para diferenciar entre votos positivos y negativos.
	 * */
	private enum VoteType {
		UPVOTE,
		DOWNVOTE
	}
	
	/**
	 * Número de divisiones en la historia.
	 * */
	private int storyBreaks;
	
	/**
	 * Los dos puntos (en función de los storyBreaks) entre los que está el
	 * momento actual de la historia.
	 * */
	private int iWeight1, iWeight2;
	
	/**
	 * Valor comprendido entre 0 y 1 que dice en proporción, a cual de los 2
	 * pesos está más cercano el momento de la historia (para hacer interpolación).
	 * */
	private float prop;
	
	/**
	 * Conjunto de listas de pesos para cada patrón y para cada
	 * momento de la historia.
	 * El primer índice es el mismo que se
	 * usa en el atributo qPatterns y relaciona, de esta forma, cada
	 * patrón de pregunta con sus pesos.
	 * */
	private ArrayList<float[]> weights;
	
	/**
	 * Ruta al fichero que almacena el estado del evaluador.
	 * */
	private String filePath;
	
	/**
	 * Crea un nuevo evaluador dinámico dado el número de divisiones a lo
	 * largo de la historia y la ruta al fichero que tiene la lista de patrones.
	 * @param storyBreaks Número de divisiones de la historia.
	 * @param path Ruta al fichero con la lista de patrones. 
	 * */
	public DynamicEvaluator(int storyBreaks, String path) {
		this.qPatterns = new ArrayList<QuestionPattern>();
		this.weights = new ArrayList<float[]>();
		this.storyBreaks = storyBreaks;
		
		loadPatterns(path);
		for (int iPattern = 0; iPattern < qPatterns.size(); iPattern++) {
			this.weights.add(new float[storyBreaks]);
			for (int iWeight = 0; iWeight < this.weights.get(iPattern).length; iWeight++) {
				this.weights.get(iPattern)[iWeight] = 0.5f;
			}
		}
	}
	
	/**
	 * Carga un evaluador dinámico a partir de un fichero.
	 * @param path Ruta al fichero que contiene el estado del evaluador.
	 * */
	public DynamicEvaluator(String path) {
		this.qPatterns = new ArrayList<QuestionPattern>();
		this.weights = new ArrayList<float[]>();
		this.storyBreaks = -1;
		loadEvaluator(path);
	}
	
	@Override
	protected float getActualWeight(int iPattern) {
		/* Se calcula mediante interpolación lineal:
		 * prop * (w2 - w1) + w1
		 */
		float weight1 = weights.get(iPattern)[iWeight1];
		float weight2 = weights.get(iPattern)[iWeight2];
		
		return prop * (weight2 - weight1) + weight1;
	}
	
	/**
	 * Carga en memoria un evaluador completo con su estado correspondiente.
	 * @param path Ruta del fichero.
	 * */
	private void loadEvaluator(String path) {
		try {
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			
			while ((line = br.readLine()) != null) {
				loadPattern(line);
			}
		} catch (Exception e) {
			System.err.println("Error leyendo los patrones del fichero " + path + ": " + e.getMessage());
		}
	}
	
	/**
	 * Carga un patrón completo del evaluador dinámico. El formato del mismo es:
	 * [qPattern] - [v1, ..., vL]
	 * @param line Linea de texto de donde extraer el patrón.
	 * @throws Exception 
	 * */
	private void loadPattern(String line) throws Exception {
		// Separación completa
		// Patrón: [qPattern] - [weights]
		Pattern separatorPattern = Pattern.compile("\\{((?:[^\\}])*)\\}(?: )*-(?: )*\\[(([^\\]])*)\\]");
		Matcher separatorMatcher = separatorPattern.matcher(line);
		
		if (!separatorMatcher.find()) {
			System.err.println("Error al cargar los patrones del evaluador.");
		}
		
		// Se añade manualmente el peso del patrón por retrocompatibilidad
		qPatterns.add(new QuestionPattern("[1.0] - " + separatorMatcher.group(1)));
		
		String[] splittedWeightStr = separatorMatcher.group(2).split(",");
		
		if (storyBreaks == -1) {
			storyBreaks = splittedWeightStr.length;
		}
		
		weights.add(new float[storyBreaks]);
		for (int iWeight = 0; iWeight < splittedWeightStr.length; iWeight++) {
			String weightStr = splittedWeightStr[iWeight];
			weights.get(weights.size() - 1)[iWeight] = Float.parseFloat(weightStr.trim());
		}
	}

	/**
	 * Carga en memoria los patrones contenidos en un fichero.
	 * @param Ruta del fichero.
	 * */
	private void loadPatterns(String path) {	
		try {
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			
			while ((line = br.readLine()) != null) {
				qPatterns.add(new QuestionPattern(line));
			}
		} catch (Exception e) {
			System.err.println("Error leyendo los patrones del fichero " + path + ": " + e.getMessage());
		}
	}
	
	/**
	 * Guarda el estado del evaluador dinámico en disco.
	 * @return True si no ha habido ningún problema en el proceso de guardado.
	 * */
	public boolean save() {
		boolean success = true;
		
		FileWriter file = null;
		PrintWriter pw = null;
		try {
			file = new FileWriter(filePath);
			pw = new PrintWriter(file);
			
			for (int iPattern = 0; iPattern < qPatterns.size(); iPattern++) {
				pw.print("{[");
				for (ExpectationPattern exp : qPatterns.get(iPattern).getExpectationPatterns()) {
					String strExp = "(";
					for (int iAtt = 0; iAtt < OPTarget.NUM_TARGETS; iAtt++) {
						String att = exp.getElement(iAtt);
						if (att != null) {
							strExp += exp.getElement(iAtt) + ", ";
						}
					}
					strExp = strExp.substring(0, strExp.length() - 2);
					pw.print(strExp + ")");
				}
				pw.print("] - [");
				for (ExpectationPattern exp : qPatterns.get(iPattern).getNegExpectationPatterns()) {
					String strExp = "(";
					for (int iAtt = 0; iAtt < OPTarget.NUM_TARGETS; iAtt++) {
						String att = exp.getElement(iAtt);
						if (att != null) {
							strExp += exp.getElement(iAtt) + ", ";
						}
					}
					strExp = strExp.substring(0, strExp.length() - 2);
					pw.print(strExp + ")");
				}
				pw.print("]} - [");
				String strWeights = "";
				for (float weight : weights.get(iPattern)) {
					strWeights += weight + ", ";
				}
				strWeights = strWeights.substring(0, strWeights.length() - 2);
				pw.println(strWeights + "]");
			}
		} catch (IOException e) {
			success = false;
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				success = false;
			}
		}
		
		return success;
	}
	
	/**
	 * Vota positivamente un segmento generado, provocando que los patrones activados en esa mente
	 * salgan beneficiados y sean más importantes en siguientes generaciones.
	 * @param m Mente que se vota positivamente.
	 * @param maxSegments Número máximo de segmentos del lector.
	 * @param mindSegment Índice del segmento al que pertenece la mente.
	 * */
	public void upvote(Mind m, int maxSegments, int mindSegment) {
		vote(m, maxSegments, mindSegment, VoteType.UPVOTE);
	}
	
	/**
	 * Vota negativamente un segmento generado, provocando que los patrones activados en esa mente
	 * salgan perjudicados y sean menos importantes en siguientes generaciones.
	 * @param m Mente que se vota negativamente.
	 * @param maxSegments Número máximo de segmentos del lector.
	 * @param mindSegment Índice del segmento al que pertenece la mente.
	 * */
	public void downvote(Mind m, int maxSegments, int mindSegment) {
		vote(m, maxSegments, mindSegment, VoteType.DOWNVOTE);
	}
	
	/**
	 * Vota una mente para que los patrones activados se actualicen convenientemente.
	 * @param m Mente que se vota.
	 * @param maxSegments Número máximo de segmentos del lector.
	 * @param mindSegment Índice del segmento al que pertenece la mente.
	 * @param vote Tipo de voto.
	 * */
	private void vote(Mind m, int maxSegments, int mindSegment, VoteType vote) {
		// Se calcula en qué storyBreak debemos hacer el voto.
		int iStoryBreak = (int) Math.ceil(storyBreaks * (mindSegment / maxSegments));
		
		// TODO Refactorizar con el método eval()
		HashSet<Integer> usedPatterns = new HashSet<Integer>();
		for (int iPattern = 0; iPattern < qPatterns.size(); iPattern++) {
			QuestionPattern qPattern = qPatterns.get(iPattern);
			
			Collection<String> actions = qPattern.getActions();
			Collection<String> negActions = qPattern.getNegActions();
			HashMap<String, Iterable<Relation>> relations = m.getRelations(actions);
			HashMap<String, Iterable<Relation>> negRelations = m.getRelations(negActions);
			HashMap<String, String> variables = new HashMap<String, String>();
			HashSet<Relation> usedRelations = new HashSet<Relation>();
			
			if (checkQuestionPattern(qPattern.getExpectationPatterns(), qPattern.getNegExpectationPatterns(), relations, negRelations, variables, usedRelations)) {
				usedPatterns.add(iPattern);
			}
		}
		
		for (int iPattern : usedPatterns) {
			switch(vote) {
			case UPVOTE:
				weights.get(iPattern)[iStoryBreak] = (float) Math.min(1.0f, weights.get(iPattern)[iStoryBreak] * 1.25f);
				break;
			case DOWNVOTE:
				weights.get(iPattern)[iStoryBreak] = (float) Math.max(0.0f, weights.get(iPattern)[iStoryBreak] * 0.75f);
				break;	
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Reader))
			return;
		
		Reader r = (Reader) o;
		int maxSegments = r.getMaxSegments();
		int actualSegment = r.getSegments().size();
		
		/* Se calcula el punto exacto (puede caer entre dos valores) de la historia
		 * en el que estamos según el número de cortes que tiene el evaluador.
		 */		
		float f = storyBreaks * (actualSegment / maxSegments);
		
		// Se calcula entre qué 2 pesos está el punto
		if (f == storyBreaks) {
			iWeight1 = storyBreaks - 2;
			iWeight2 = storyBreaks - 1;
		}
		else {
			iWeight1 = (int) Math.ceil(f);
			iWeight2 = ((int) Math.ceil(f)) + 1;
		}
		prop = f - iWeight1;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * @return the weights
	 */
	public ArrayList<float[]> getWeights() {
		return weights;
	}

	/**
	 * @return the storyBreaks
	 */
	public int getStoryBreaks() {
		return storyBreaks;
	};
	
}
