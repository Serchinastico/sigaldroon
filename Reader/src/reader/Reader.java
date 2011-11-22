package reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import mind.Mind;
import mind.ChangedMind;

import evaluator.IEvaluator;
import evaluator.SimpleEvaluator;

import segmenter.Segmenter;


/**
 * Clase del lector que implementa el método principal de 
 * la generación de historias.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Reader extends Observable implements IReader{
	
	/**
	 * Segmentos en orden que forman la historia hasta el momento.
	 */
	private ArrayList<Mind> storySoFar;

	/**
	 * Mente del lector.
	 */
	private Mind mind;
	
	/**
	 * Número de segmentos que contendrá la historia como máximo.
	 */
	private int maxSegments;
	
	/**
	 * Evaluador de los mentes generadas.
	 * */
	private IEvaluator evaluator;
	
	/**
	 * Constructora por defecto.
	 */
	public Reader() {
		maxSegments = 10;
		mind = null;
		evaluator = new SimpleEvaluator();
		addObserver((Observer) evaluator);
	}
	
	/**
	 * Inicializa la mente del lector mediante un fichero.
	 */
	public void createMind(String file) {
		storySoFar = new ArrayList<Mind>();
		InputStream txt = null;
		try {
			 txt = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mind = new Mind(txt);
		storySoFar.add(mind);
	}
	
	@Override
	public ArrayList<Mind> generateStory() {
		
		for (int i = 0; i < maxSegments; i++) {
			
			// Opera con la mente para evolucionarla
			MindEvolver evolver = new MindEvolver(evaluator);
			ChangedMind changedMind = evolver.evolveMind(mind);
			
			// Extrae un segmento nuevo con la mente cambiada
			Segmenter segmenter = new Segmenter();
			storySoFar.add(segmenter.generateSegment(changedMind));
			mind = changedMind.getActualMind();
			
			notifyObservers();
		}
		
		return storySoFar;
	}
	
	public ArrayList<Mind> generateNextSegment() {
		
		if (storySoFar.size() < maxSegments) {
			// Opera con la mente para evolucionarla
			MindEvolver evolver = new MindEvolver(evaluator);
			ChangedMind changedMind = evolver.evolveMind(mind);
			
			// Extrae un segmento nuevo con la mente cambiada
			Segmenter segmenter = new Segmenter();
			storySoFar.add(segmenter.generateSegment(changedMind));
			mind = changedMind.getActualMind();
		}
		
		return storySoFar;
	}

	/**
	 * @return the maxSegments
	 */
	public int getMaxSegments() {
		return maxSegments;
	}

	/**
	 * Getter para los segmentos generados de la historia.
	 * @return
	 */
	public ArrayList<Mind> getStorySoFar() {
		return storySoFar;
	}
	
	public boolean isInitialized() {
		return mind != null;
	}
	
	/**
	 * Testing.
	 * @param args nanai
	 */
	public static void main(String[] args) {
		Reader r = new Reader();
		ArrayList<Mind> story = r.generateStory();
		for (Mind w : story) {
			System.out.println(w.toString());
		}
	}
}
