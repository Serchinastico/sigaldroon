package reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import coherence.CoherenceChecker;
import coherence.Events;
import coherence.ICoherenceChecker;

import mind.Mind;
import mind.ChangedMind;
import mind.Relation;

import evaluator.IEvaluator;
import evaluator.SimpleEvaluator;

import segmenter.ISegmenter;
import segmenter.NaturalSegmenter;


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
	 * Votaciones de los segmentos en orden que forman la historia hasta el momento.
	 * @see tVote para tipos de votos.
	 */
	private ArrayList<tVote> votes;
	
	/**
	 * Almacena el texto asociado a cada segmento.
	 */
	private ArrayList<String> textSegment;
	
	/**
	 * Eventos de muertes y matrimonios tenidos en cuenta en cada segmento de la historia.
	 * El componente i refiere al segmento i.
	 */
	private ArrayList<Events> events;
	
	/**
	 * Número de segmentos que contendrá la historia como máximo.
	 */
	private int maxSegments;
	
	/**
	 * Comprobador de la coherencia de la historia.
	 */
	private ICoherenceChecker coherenceChecker;
	
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
		mind = null;
		events = new ArrayList<Events>();
		coherenceChecker = new CoherenceChecker();
		evaluator = new SimpleEvaluator();
		evolver = new MindEvolver(evaluator,coherenceChecker);
		segmenter = new NaturalSegmenter();
		votes = new ArrayList<tVote>();
		textSegment = new ArrayList<String>();
		addObserver((Observer) evaluator);
	}
	
	@Override
	public void createMind(String file) {
		storySoFar = new ArrayList<Mind>();
		events = new ArrayList<Events>();
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
		events.add(coherenceChecker.assumeInitialEvents(mind));
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
			ChangedMind changedMind = evolver.evolveMind(mind, events.get(events.size() - 1));
			
			// Extrae un segmento nuevo con la mente cambiada
			storySoFar.add(changedMind.getActualMind());
			textSegment.add(segmenter.generateSegment(changedMind));
			votes.add(tVote.NEUTRAL);
			mind = changedMind.getActualMind();
			
			// Asume que todas las relaciones tienen veracidad o peso 1.0
			assumeConcepts();
			
			// Asume los eventos para el coherenciador
			events.add(coherenceChecker.assumeEvents(events.get(events.size() - 1), changedMind.getResultingRelations()));
			
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
