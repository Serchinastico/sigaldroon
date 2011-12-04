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
import segmenter.NaturalSegmenter;


/**
 * Clase del lector que implementa el m�todo principal de 
 * la generaci�n de historias.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
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
	 * Votaciones de los segmentos en orden que forman la historia hasta el momento.
	 * @see tVote para tipos de votos.
	 */
	private ArrayList<tVote> votes;
	
	/**
	 * Almacena el texto asociado a cada segmento.
	 */
	private ArrayList<String> textSegment;
	
	/**
	 * N�mero de segmentos que contendr� la historia como m�ximo.
	 */
	private int maxSegments;
	
	/**
	 * �ndice del segmento actual.
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
		segmenter = new NaturalSegmenter();
		votes = new ArrayList<tVote>();
		textSegment = new ArrayList<String>();
		addObserver((Observer) evaluator);
	}
	
	@Override
	public void createMind(String file) {
		storySoFar = new ArrayList<Mind>();
		votes = new ArrayList<tVote>();
		textSegment = new ArrayList<String>();
		InputStream txt = null;
		try {
			 txt = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mind = new Mind(txt);
		storySoFar.add(mind);
		votes.add(tVote.NEUTRAL);
		textSegment.add(segmenter.generateInitialSegment(mind));
		
		notifyObservers();
	}
	
	@Override
	public void generateStory() {
		for (int i = storySoFar.size(); i < maxSegments; i++)
			generateNextSegment();
	}
	
	@Override
	public void generateNextSegment() {
		
		if (storySoFar.size() < maxSegments) {
			// Opera con la mente para evolucionarla
			ChangedMind changedMind = evolver.evolveMind(mind);
			
			// Extrae un segmento nuevo con la mente cambiada
			storySoFar.add(changedMind.getActualMind());
			textSegment.add(segmenter.generateSegment(changedMind));
			votes.add(tVote.NEUTRAL);
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

	@Override
	public ArrayList<tVote> getVotes() {
		return votes;
	}

	@Override
	public void voteSegment(int i, tVote vote) {
		votes.set(i, vote);
	}

	@Override
	public ArrayList<String> getTextSegments() {
		return textSegment;
	}
	
}
