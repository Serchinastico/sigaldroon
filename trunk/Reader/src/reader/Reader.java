package reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import coherence.CoherenceChecker;

import mind.Mind;
import mind.ChangedMind;
import mind.Relation;
import mind.ontobridge.OntoBridgeSingleton;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
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
public class Reader extends Observable {

	/**
	 * Última mente del lector generada.
	 */
	private Mind mind;

	/**
	 * Almacén de segmentos de la historia producidos hasta el momento.
	 * @see Segment para ver el contenido de los segmentos.
	 */
	private ArrayList<Segment> segments;

	/**
	 * Número de segmentos que contendrá la historia como máximo.
	 */
	private int maxSegments;

	/**
	 * Evolucionador de la mente del lector.
	 */
	private MindEvolver evolver;

	/**
	 * Evaluador de los mentes generadas.
	 * */
	private IEvaluator evaluator;

	/**
	 * Comprobador de coherencia de la historia.
	 */
	private CoherenceChecker coherenceChecker;

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
		coherenceChecker = new CoherenceChecker();
		evaluator = new SimpleEvaluator();
		evolver = new MindEvolver(evaluator,coherenceChecker);
		segmenter = new NaturalSegmenter();
		segments = new ArrayList<Segment>();
		addObserver((Observer) evaluator);
	}

	/**
	 * @return the maxSegments
	 */
	public int getMaxSegments() {
		return maxSegments;
	}

	/**
	 * Devuelve los elementos que forman la historia.
	 * Los generados hasta el momento.
	 * @return Los segmentos.
	 * @see Segment para ver el contenido de los mismos.
	 */
	public ArrayList<Segment> getSegments() {
		return segments;
	}

	/**
	 * Obtiene el evolucionador de la mente.
	 * @return 
	 */
	public MindEvolver getEvolver() {
		return evolver;
	}

	/**
	 * @return the evaluator 
	 */
	public IEvaluator getEvaluator() {
		return this.evaluator;
	}

	/**
	 * Devuelve el texto en lenguaje natural de todo lo que se lleve de historia.
	 * @return El texto de la historia.
	 */
	public String getTextStory() {
		String textStory = "";
		for (Segment segment : segments) {
			textStory += segment.getTextSegment();
		}
		return textStory;
	}
	
	/**
	 * Devuelve información en texto de todo lo que se lleve de historia.
	 * @return Información de la historia.
	 */
	public String getDataStory() {
		String dataStory = "------- Log de la historia -------\n\r";
		for (int i = 0; i < segments.size(); i++) {
			dataStory += "<*> Segmento " + i + " <*> ------------------------------------------------ <*>\n\r" 
					+ segments.get(i).getSegmentLog().toString();
		}
		return dataStory;
	}

	/**
	 * Comprueba si la mente ha sido inicializada.
	 * @return True si ha sido inicializada.
	 */
	public boolean isInitialized() {
		return mind != null;
	}

	/**
	 * @param the maxSegments
	 */
	public void setMaxSegments(int maxSegments) {
		this.maxSegments = maxSegments;
	}

	/**
	 * @param the evaluator
	 */
	public void setEvaluator(IEvaluator evaluator) {
		this.evaluator = evaluator;
		this.evolver.setEvaluator(evaluator);
	}

	/**
	 * Crea una mente de conceptos que tiene el lector a partir de archivo.
	 * Inicializa el segmento inicial de la mente que ha cargado desde
	 * el archivo.
	 * @param file
	 */
	public void createMind(String file) {

		// Obtiene la mente del lector de archivo
		InputStream txt = null;
		try {
			txt = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mind = new Mind(txt);

		// Crear y completar el segment log
		SegmentLog segmentLog = new SegmentLog();
		createLogInfo(segmentLog, mind);
		segmentLog.setPatterns(evaluator.getUsedPatterns(mind, maxSegments, 0));

		// Crea del segmento inicial a partir de la mente extraída
		Segment segment = new Segment(mind, 
				segmenter.generateInitialSegment(mind),
				coherenceChecker.assumeEvents(null,mind),
				segmentLog
				);
		segments.add(segment);

		notifyObservers();
	}

	public void reset() {
		segments = new ArrayList<Segment>();
		mind = null;
		evolver.resetStoryMinds();
	}

	/**
	 * Genera todos los segmentos que quedan por generar de la historia.
	 * Si se ha alcanzado el límite de segmentos máximos, no se hace nada.
	 */
	public void generateStory() {
		for (int i = segments.size(); i < maxSegments; i++)
			generateNextSegment();
	}

	/**
	 * Genera el siguiente segmento de la historia.
	 * Si se ha alcanzado el límite de segmentos máximos, no se hace nada.
	 */
	public void generateNextSegment() {

		if (segments.size() < maxSegments) {
			// Opera con la mente para evolucionarla
			ChangedMind changedMind = evolver.evolveMind(mind, segments.get(segments.size() - 1).getEvents());

			// Crear y completar el segment log
			SegmentLog segmentLog = new SegmentLog();
			createLogInfo(segmentLog, changedMind.getResultingRelations());
			segmentLog.setPatterns(evaluator.getUsedPatterns(mind, maxSegments, segments.size()));

			// Extrae un segmento nuevo con la mente cambiada
			Segment segment = new Segment(
					changedMind.getActualMind(),
					segmenter.generateSegment(changedMind),
					coherenceChecker.assumeEvents(segments.get(segments.size() - 1).getEvents(), changedMind.getResultingRelations()),
					segmentLog
					);
			segments.add(segment);
			mind = changedMind.getActualMind();

			// Asume que todas las relaciones tienen veracidad o peso 1.0
			assumeConcepts();

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

	private void createLogInfo(SegmentLog segmentLog, Iterable<Relation> mind) {
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		HashSet<Relation> relations = new HashSet<Relation>();		
		HashSet<String> chars = new HashSet<String>();		
		HashSet<String> scenarios = new HashSet<String>();
		HashSet<String> props = new HashSet<String>();
		for (Relation r : mind) {
			// Sujeto
			chars.add(r.getSource());
			// Objeto directo
			if (r.getDirectObject() != null) {
				if (ob.existsClass(r.getDirectObject())) {
					if (ob.isSubClassOf(r.getDirectObject(), "Actor"))
						chars.add(r.getDirectObject());
					else if (ob.isSubClassOf(r.getDirectObject(), "Objeto"))
						props.add(r.getDirectObject());
				}
				else 
					if (ob.isInstanceOf(r.getDirectObject(), "Actor"))
						chars.add(r.getDirectObject());
					else if (ob.isInstanceOf(r.getDirectObject(), "Objeto"))
						props.add(r.getDirectObject());
			}
			// Objeto indirecto
			if (r.getTarget() != null) {
				if (ob.existsClass(r.getTarget())) {
					if (ob.isSubClassOf(r.getTarget(), "Actor"))
						chars.add(r.getTarget());
					else if (ob.isSubClassOf(r.getTarget(), "Objeto"))
						props.add(r.getTarget());
				}
				else 
					if (ob.isInstanceOf(r.getTarget(), "Actor"))
						chars.add(r.getTarget());
					else if (ob.isInstanceOf(r.getTarget(), "Objeto"))
						props.add(r.getTarget());
			}
			// Lugar
			if (r.getPlace() != null) 
				scenarios.add(r.getPlace());

			relations.add(r.clone());
		}
		
		segmentLog.setRelations(relations);
		segmentLog.setCharacters(chars);
		segmentLog.setProps(props);
		segmentLog.setScenarios(scenarios);
	}

}
