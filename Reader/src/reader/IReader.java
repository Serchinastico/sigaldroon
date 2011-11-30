package reader;

import java.util.ArrayList;
import java.util.Observer;

import mind.Mind;

/**
 * Interfaz del lector.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public interface IReader {

	/**
	 * Crea una mente de conceptos que tiene el lector a partir de archivo.
	 */
	public void createMind(String file);
	
	/**
	 * Genera todos los segmentos que quedan por generar de la historia.
	 */
	public void generateStory();
	
	/**
	 * Genera el siguiente segmento de la historia.
	 */
	public void generateNextSegment();
	
	/**
	 * @return the maxSegments
	 */
	public int getMaxSegments();
	
	/**
	 * @param the maxSegments
	 */
	public void setMaxSegments(int maxSegments);
	
	/**
	 * Devuelve los segmentos de la historia.
	 * @return
	 */
	public ArrayList<Mind> getStorySoFar();
	
	/**
	 * Comprueba si la mente ha sido inicializada.
	 * @return True si la mente ha sido inicializada.
	 */
	public boolean isInitialized();
	
	/**
	 * Añade un observador.
	 * @param o
	 */
	public void insertObserver(Observer o);
	
	/**
	 * Elimina un observador.
	 * @param o
	 */
	public void deleteObserver(Observer o);
	
}
