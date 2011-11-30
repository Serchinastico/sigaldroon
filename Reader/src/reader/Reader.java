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
			MindEvolver evolver = new MindEvolver(evaluator);
			ChangedMind changedMind = evolver.evolveMind(mind);
			
			// Extrae un segmento nuevo con la mente cambiada
			Segmenter segmenter = new Segmenter();
			storySoFar.add(segmenter.generateSegment(changedMind));
			mind = changedMind.getActualMind();
			
			// Asume que todas las relaciones tienen veracidad o peso 1.0
			assumeConcepts();
			
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Asume los conceptos que hay en la mente del lector subiendo
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
	public void deleteObserver(Observer o) {
		this.deleteObserver(o);
	}
	
}
