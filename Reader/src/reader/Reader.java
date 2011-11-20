package reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import evaluator.IEvaluator;
import evaluator.SimpleEvaluator;

import segmenter.Segmenter;

import world.World;
import world.WorldChanged;

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
	private ArrayList<World> storySoFar;

	/**
	 * Mundo tal y como lo concibe la mente del lector.
	 */
	private World mind;
	
	/**
	 * Número de segmentos que contendrá la historia como máximo.
	 */
	private int maxSegments;
	
	/**
	 * Evaluador de los mundos generados.
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
	 * Inicializa la mente del lector con un mundo.
	 */
	public void createMind() {
		storySoFar = new ArrayList<World>();
		InputStream txt = null;
		try {
			 txt = new FileInputStream("resources/MundoDisco.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mind = new World(txt);
		storySoFar.add(mind);
	}
	
	@Override
	public ArrayList<World> generateStory() {
		
		for (int i = 0; i < maxSegments; i++) {
			
			// Opera con la mente para evolucionarla
			MindEvolver evolver = new MindEvolver(evaluator);
			WorldChanged worldChanged = evolver.evolveMind(mind);
			
			// Extrae un segmento nuevo con la mente cambiada
			Segmenter segmenter = new Segmenter();
			storySoFar.add(segmenter.generateSegment(worldChanged));
			mind = worldChanged.getActualMind();
			
			notifyObservers();
		}
		
		return storySoFar;
	}
	
	public ArrayList<World> generateNextSegment() {
		
		if (storySoFar.size() < maxSegments) {
			// Opera con la mente para evolucionarla
			MindEvolver evolver = new MindEvolver();
			WorldChanged worldChanged = evolver.evolveMind(mind);
			
			// Extrae un segmento nuevo con la mente cambiada
			Segmenter segmenter = new Segmenter();
			storySoFar.add(segmenter.generateSegment(worldChanged));
			mind = worldChanged.getActualMind();
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
	public ArrayList<World> getStorySoFar() {
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
		ArrayList<World> story = r.generateStory();
		for (World w : story) {
			System.out.println(w.toString());
		}
	}
}
