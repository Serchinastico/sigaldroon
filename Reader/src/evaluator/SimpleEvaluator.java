package evaluator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import reader.Reader;

import world.Component;
import world.World;

/**
 * Implementación estática del evaluador para las historias generadas.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class SimpleEvaluator implements IEvaluator, Observer {

	private static final int INTRODUCTION = 0;
	private static final int RISE = 1;
	private static final int CLIMAX = 2;
	private static final int CATASTROPHE = 3;
	private static final String[] PATTERNS_PATH = {
		"resources/SimpleEvaluator/introduction_patterns.txt",
		"resources/SimpleEvaluator/rise_patterns.txt",
		"resources/SimpleEvaluator/climax_patterns.txt",
		"resources/SimpleEvaluator/catastrophe_patterns.txt"};
	
	/**
	 * Estado actual de la historia.
	 * */
	private int state;
	
	/**
	 * Patrones de expectativas y preguntas usadas en el estado
	 * actual de la historia.
	 * */
	private ArrayList<Pattern> patterns;
	
	/**
	 * Constructora por defecto.
	 * */
	public SimpleEvaluator() {
		state = INTRODUCTION;
		loadPatterns(PATTERNS_PATH[state]);
	}
	
	/**
	 * Carga en memoria los patrones contenidos en un fichero.
	 * @param path Ruta al fichero que contiene los patrones.
	 * */
	private void loadPatterns(String path) {
		patterns = new ArrayList<Pattern>();
		
		try {
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			
			while ((line = br.readLine()) != null) {
				patterns.add(new Pattern(line));
			}
		} catch (Exception e) {
			System.err.println("Error leyendo los patrones del fichero " + path + ": " + e.getMessage());
		}
	}
	
	@Override
	public float eval(World w) {
		float value = 0.0f;
		
		/* TODO: El encaje de patrones está un poco 'tricky' y es ineficiente.
		 * > Organizar las relaciones del mundo de otra forma para que al buscar exp
		 * 	sea más eficiente que un recorrido completo.
		 * > Usar bucles clásicos para clarificar (?).
		 */
		for (Pattern p : patterns) {
			boolean fitPattern = true;
			
			// Se comprueba si encajan todas las 'expectativas'/'expresiones' del patrón
			for (int iExp = 0; iExp < p.getExprs().size(); iExp++) {
				Component exp = p.getExprs().get(iExp);
				
				boolean fitExp = false;
				
				for (Component worldComp : w) {
					if (worldComp.instanceOf(exp)) {
						// Si se ha encontrado y no se busca la expresión negada se sale con éxito
						if (!p.getNegExprs().get(iExp)) {
							fitExp = true;
						}
						break;
					}
				}
				
				if (!fitExp) {
					fitPattern = false;
					break;
				}
			}
			
			// Si encaja el patrón en el mundo se suma su valor
			if (fitPattern) {
				value += p.getValue();
			}
		}
		
		return value;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Reader))
			return;
		
		Reader r = (Reader) o;
		int maxSegments = r.getMaxSegments();
		
		int prevState = state;
		// Actualiza el estado de la historia por el tanto por ciento que se haya contado
		if (maxSegments >= 0 && maxSegments < maxSegments * 0.2)
			state = INTRODUCTION;
		else if (maxSegments >= maxSegments * 0.2 && maxSegments < maxSegments * 0.6)
			state = RISE;
		else if (maxSegments >= maxSegments * 0.6 && maxSegments < maxSegments * 0.9)
			state = CLIMAX;
		else if (maxSegments >= maxSegments * 0.9 && maxSegments < maxSegments * 1.0)
			state = CATASTROPHE;
		
		// Se actualiza la lista de patrones si hemos cambiado de estado
		if (prevState != state) {
			loadPatterns(PATTERNS_PATH[state]);
		}
	}

}
