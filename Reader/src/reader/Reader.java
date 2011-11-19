package reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import segmenter.Segmenter;

import world.World;
import world.WorldChanged;

/**
 * Clase del lector que implementa el m�todo principal de 
 * la generaci�n de historias.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class Reader implements IReader {
	
	/**
	 * Segmentos en orden que forman la historia hasta el momento.
	 */
	private ArrayList<World> storySoFar;

	/**
	 * Mundo tal y como lo concibe la mente del lector.
	 */
	private World mind;
	
	/**
	 * N�mero de segmentos que contendr� la historia como m�ximo.
	 */
	private int maxSegments;
	
	/**
	 * Constructora por defecto.
	 */
	public Reader() {
		maxSegments = 10;
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
		
		createMind();
		
		for (int i = 0; i < maxSegments; i++) {
			
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
	 * Getter para los segmentos generados de la historia.
	 * @return
	 */
	public ArrayList<World> getStorySoFar() {
		return storySoFar;
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
