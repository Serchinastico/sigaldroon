package evaluator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import reader.Reader;

/**
 * Implementación estática del evaluador para las historias generadas.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class SimpleEvaluator extends AbstractEvaluator implements Observer {

	/**
	 * Lista de cortes de la historia según el triángulo de Freytag
	 * en valores porcentuales.
	 * */
	private static final float[] STORY_BREAKS = {0.2f, 0.6f, 0.9f};
	
	/**
	 * Estado actual de la historia.
	 * */
	private State state;
	
	public SimpleEvaluator() {
		state = State.INTRODUCTION;
		loadPatterns();
	}
	
	@Override
	protected float getActualWeight(int iPattern) {
		return qPatterns.get(iPattern).getWeight();
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
