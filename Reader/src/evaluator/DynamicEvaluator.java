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
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mind.Mind;
import mind.Relation;

import reader.Reader;

public class DynamicEvaluator extends AbstractEvaluator implements Observer {

	/** Enumerado para diferenciar entre votos positivos y negativos.
	 * */
	private enum VoteType {
		UPVOTE,
		DOWNVOTE
	}
	
	/**
	 * N�mero de divisiones en la historia.
	 * */
	private int storyBreaks;
	
	/**
	 * Los dos puntos (en funci�n de los storyBreaks) entre los que est� el
	 * momento actual de la historia.
	 * */
	private int iWeight1, iWeight2;
	
	/**
	 * Valor comprendido entre 0 y 1 que dice en proporci�n, a cual de los 2
	 * pesos est� m�s cercano el momento de la historia (para hacer interpolaci�n).
	 * */
	private float prop;
	
	/**
	 * Conjunto de listas de pesos para cada patr�n y para cada
	 * momento de la historia.
	 * El primer �ndice es el mismo que se
	 * usa en el atributo qPatterns y relaciona, de esta forma, cada
	 * patr�n de pregunta con sus pesos.
	 * */
	private ArrayList<float[]> weights;
	
	/**
	 * Ruta al fichero que almacena el estado del evaluador.
	 * */
	private String filePath;
	
	/**
	 * Tipo de evaluador.
	 */
	private Type type;
	
	/**
	 * Tipos de evaluador din�mico aleatorio o din�mico guiado.
	 */
	public enum Type {
		RANDOM,
		GUIDED
	}
	
	/**
	 * Crea un nuevo evaluador din�mico dado el n�mero de divisiones a lo
	 * largo de la historia y la ruta al fichero que tiene la lista de patrones.
	 * @param storyBreaks N�mero de divisiones de la historia.
	 * @param path Ruta al fichero con la lista de patrones. 
	 * */
	public DynamicEvaluator(int storyBreaks, String path, Type type) {
		this.qPatterns = new ArrayList<QuestionPattern>();
		this.weights = new ArrayList<float[]>();
		this.storyBreaks = storyBreaks;
		this.type = type;
		
		loadPatterns(path);
		randomizeWeights();
	}
	
	/**
	 * Carga un evaluador din�mico a partir de un fichero.
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
		/* Se calcula mediante interpolaci�n lineal:
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
			
			if ((line = br.readLine()) != null) {
				if (line.trim().equals("RANDOM")) {
					type = Type.RANDOM;
				}
				else if (line.trim().equals("GUIDED")) {
					type = Type.GUIDED;
				}
			}
			while ((line = br.readLine()) != null) {
				loadPattern(line);
			}
		} catch (Exception e) {
			System.err.println("Error leyendo los patrones del fichero " + path + ": " + e.getMessage());
		}
	}
	
	/**
	 * Carga un patr�n completo del evaluador din�mico. El formato del mismo es:
	 * [Moment] - [qPattern] - [v1, ..., vL]
	 * @param line Linea de texto de donde extraer el patr�n.
	 * @throws Exception 
	 * */
	private void loadPattern(String line) throws Exception {
		// Separaci�n completa
		// Patr�n: [Moment] - [qPattern] - [weights]
		Pattern separatorPattern = Pattern.compile("( )*\\[((.)*)\\]( )*-( )*\\{((?:[^\\}])*)\\}(?: )*-(?: )*\\[(([^\\]])*)\\]");
		Matcher separatorMatcher = separatorPattern.matcher(line);
		
		if (!separatorMatcher.find()) {
			System.err.println("Error al cargar los patrones del evaluador.");
		}
		
		// Se a�ade manualmente el peso del patr�n por retrocompatibilidad
		qPatterns.add(new QuestionPattern("[1.0] - [" + separatorMatcher.group(2)  + "] - " + separatorMatcher.group(6)));
		
		String[] splittedWeightStr = separatorMatcher.group(7).split(",");
		
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
	 * Guarda el estado del evaluador din�mico en disco.
	 * @return True si no ha habido ning�n problema en el proceso de guardado.
	 * */
	public boolean save() {
		boolean success = true;
		
		FileWriter file = null;
		PrintWriter pw = null;
		try {
			file = new FileWriter(filePath);
			pw = new PrintWriter(file);
			
			pw.println(type);
			for (int iPattern = 0; iPattern < qPatterns.size(); iPattern++) {
				pw.print("[");
				pw.print(qPatterns.get(iPattern).getMoment());
				pw.print("] - ");
				pw.print("{[");
				for (ExpectationPattern exp : qPatterns.get(iPattern).getExpectationPatterns()) {
					String strExp = "(";
					for (int iAtt = 0; iAtt < Relation.NUM_ELEMENTS; iAtt++) {
						String att = exp.getElement(iAtt);
						if (att != null) {
							strExp += exp.getElement(iAtt);
							String var = exp.getVariable(iAtt);
							if (var != null) {
								strExp += ":" + var;
							}
							strExp += ", ";
						}
					}
					strExp = strExp.substring(0, strExp.length() - 2);
					pw.print(strExp + ")");
				}
				pw.print("] - [");
				for (ExpectationPattern exp : qPatterns.get(iPattern).getNegExpectationPatterns()) {
					String strExp = "(";
					for (int iAtt = 0; iAtt < Relation.NUM_ELEMENTS; iAtt++) {
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
	 * salgan beneficiados y sean m�s importantes en siguientes generaciones.
	 * @param m Mente que se vota positivamente.
	 * @param maxSegments N�mero m�ximo de segmentos del lector.
	 * @param mindSegment �ndice del segmento al que pertenece la mente.
	 * */
	public void upvote(Mind m, int maxSegments, int mindSegment) {
		vote(m, maxSegments, mindSegment, VoteType.UPVOTE);
	}
	
	/**
	 * Vota negativamente un segmento generado, provocando que los patrones activados en esa mente
	 * salgan perjudicados y sean menos importantes en siguientes generaciones.
	 * @param m Mente que se vota negativamente.
	 * @param maxSegments N�mero m�ximo de segmentos del lector.
	 * @param mindSegment �ndice del segmento al que pertenece la mente.
	 * */
	public void downvote(Mind m, int maxSegments, int mindSegment) {
		vote(m, maxSegments, mindSegment, VoteType.DOWNVOTE);
	}
	
	/**
	 * Vota una mente para que los patrones activados se actualicen convenientemente.
	 * @param m Mente que se vota.
	 * @param maxSegments N�mero m�ximo de segmentos del lector.
	 * @param mindSegment �ndice del segmento al que pertenece la mente.
	 * @param vote Tipo de voto.
	 * */
	private void vote(Mind m, int maxSegments, int mindSegment, VoteType vote) {
		// Se calcula en qu� storyBreak debemos hacer el voto.
		int iStoryBreak = (int) Math.ceil(storyBreaks * (mindSegment / maxSegments));

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
	
	/**
	 * Sustituye la matriz de pesos del evaluador por valores aleatorios.
	 */
	public void randomizeWeights() {
		Random random = new Random(System.currentTimeMillis());
		
		switch (type) {
		case RANDOM:
			for (int iPattern = 0; iPattern < qPatterns.size(); iPattern++) {
				this.weights.add(new float[storyBreaks]);
				for (int iWeight = 0; iWeight < this.weights.get(iPattern).length; iWeight++) {
					this.weights.get(iPattern)[iWeight] = random.nextFloat();
				}
			}
			break;
		case GUIDED:
			for (int iPattern = 0; iPattern < qPatterns.size(); iPattern++) {
				this.weights.add(new float[storyBreaks]);
				
				//float center = random.nextFloat() * 9;
				float center = nextNormalFloat(random, qPatterns.get(iPattern).getMoment());
				float aperture = (random.nextFloat() * 9) + 1;
				
				for (int iWeight = 0; iWeight < this.weights.get(iPattern).length; iWeight++) {
					float fy = (-1.0f / aperture) * ((iWeight - center) * (iWeight - center)) + 1;
					this.weights.get(iPattern)[iWeight] = (fy < 0) ? 0.0f : fy;
				}
			}
			break;
		}
	}
	
	/**
	 * Devuelve un valor aleatorio distribuido seg�n una funci�n normal seg�n el momento de la historia.
	 * Se usa el m�todo de Box-Muller: http://es.wikipedia.org/wiki/M%C3%A9todo_de_Box-Muller
	 * @param random Objeto para crear n�meros aleatorios
	 * @param m Momento de la historia
	 */
	public float nextNormalFloat(Random random, String m) {
		ArrayList<Float> normalValues = new ArrayList<Float>();
		
		if (m.contains("P") || m.trim().equals("")) {
			normalValues.add(nextNormalFloat(random, 0.0f, 1.0f));
		}
		if (m.contains("M") || m.trim().equals("")) {
			normalValues.add(nextNormalFloat(random, storyBreaks / 2.0f, 1.0f));
		}
		if (m.contains("F") || m.trim().equals("")) {
			normalValues.add(nextNormalFloat(random, storyBreaks, 1.0f));
		}
		
		return normalValues.get(random.nextInt(normalValues.size()));
	}
	
	/**
	 * Devuelve un valor aleatorio distribuido seg�n la funci�n normal de media y varianza dadas.
	 * Se usa el m�todo de Box-Muller: http://es.wikipedia.org/wiki/M%C3%A9todo_de_Box-Muller
	 * @param random Objeto para crear los n�meros aleatorios con distribuci�n uniforme
	 * @param mean Media de la distribuci�n
	 * @param variance Varianza de la distribuci�n
	 */
	private float nextNormalFloat(Random random, float mean, float variance) {
		float u1 = random.nextFloat();
		float u2 = random.nextFloat();
		
		float z = mean + (float) (Math.sqrt(variance) * Math.sqrt(-2 * Math.log(u1)) * Math.cos(2 * Math.PI * u2));
		
		// Reajuste de la variable por si se sale de rango
		if (z < 0.0f) {
			z = -z;
		}
		while (z > storyBreaks) {
			z = 2.0f * storyBreaks - z;
		}
		
		return z;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Reader))
			return;
		
		Reader r = (Reader) o;
		int maxSegments = r.getMaxSegments();
		int actualSegment = r.getSegments().size();
		
		/* Se calcula el punto exacto (puede caer entre dos valores) de la historia
		 * en el que estamos seg�n el n�mero de cortes que tiene el evaluador.
		 */		
		float f = storyBreaks * (actualSegment / maxSegments);
		
		// Se calcula entre qu� 2 pesos est� el punto
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
