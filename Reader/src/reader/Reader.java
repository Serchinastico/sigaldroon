package reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import mind.Mind;
import mind.ChangedMind;
import mind.Relation;

import evaluator.IEvaluator;
import evaluator.SimpleEvaluator;

import segmenter.ISegmenter;
import segmenter.Segmenter;


/**
 * Clase del lector que implementa el método principal de 
 * la generación de historias.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Reader extends Observable implements IReader {
	
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
	 * Índice del segmento actual.
	 * */
	private int actualSegment;
	
	/**
	 * Evaluador de los mentes generadas.
	 * */
	private IEvaluator evaluator;
	
	/**
	 * Evolucionador de la mente del lector.
	 */
	private IMindEvolver evolver;
	
	/**
	 * Creador de los segmentos de la historia.
	 */
	private ISegmenter segmenter;
	
	/**
	 * Constructora por defecto.
	 */
	public Reader() {
		maxSegments = 10;
		actualSegment = 0;
		mind = null;
		evaluator = new SimpleEvaluator();
		evolver = new MindEvolver(evaluator);
		segmenter = new Segmenter();
		addObserver((Observer) evaluator);
	}
	
	@Override
	public void createMind(String file) {
		storySoFar = new ArrayList<Mind>();
		InputStream txt = null;
		try {
			 txt = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mind = new Mind(txt);
		storySoFar.add(mind);
		
		notifyObservers();
	}
	
	@Override
	public void generateStory() {
		for (int i = 0; i < maxSegments; i++)
			generateNextSegment();
	}
	
	@Override
	public void generateNextSegment() {
		
		if (storySoFar.size() < maxSegments) {
			// Opera con la mente para evolucionarla
			ChangedMind changedMind = evolver.evolveMind(mind);
			
			// Extrae un segmento nuevo con la mente cambiada
			storySoFar.add(segmenter.generateSegment(changedMind));
			mind = changedMind.getActualMind();
			
			// Asume que todas las relaciones tienen veracidad o peso 1.0
			assumeConcepts();
			
			actualSegment++;
			
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Asume los conceptos que hay en la mente del lector estableciendo
	 * la veracidad o peso de los mismos a 1.0.
	 */
	private void assumeConcepts() {
		for (Relation relation : mind) {
			relation.setWeight(1.0f); 
		}
	}

	@Override
	public int getMaxSegments() {
		return maxSegments;
	}
	
	@Override
	public void setMaxSegments(int maxSegments) {
		this.maxSegments = maxSegments;
	}

	@Override
	public ArrayList<Mind> getStorySoFar() {
		return storySoFar;
	}
	
	@Override
	public boolean isInitialized() {
		return mind != null;
	}
	
	@Override
	public void insertObserver(Observer o) {
		this.addObserver(o);
	}

	@Override
	public void removeObserver(Observer o) {
		this.deleteObserver(o);
	}

	@Override
	public IMindEvolver getEvolver() {
		return evolver;
	}

	/**
	 * @return the actualSegment
	 */
	public int getActualSegment() {
		return actualSegment;
	}
	
}
