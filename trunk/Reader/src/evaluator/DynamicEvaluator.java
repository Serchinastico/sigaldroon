package evaluator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import operator.OPTarget;

public class DynamicEvaluator extends AbstractEvaluator {

	/**
	 * Número de divisiones en la historia.
	 * */
	private int storyBreaks;
	
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
	
}
