package reader;

import java.util.ArrayList;
import java.util.Observer;

import mind.Mind;

/**
 * Interfaz del lector.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public interface IReader {
	
	public enum tVote {
		POSITIVE,
		NEUTRAL,
		NEGATIVE
	}

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
	 * @return the votes
	 * @see tVote para tipos de votos.
	 */
	public ArrayList<tVote> getVotes();
	
	/**
	 * Devuelve los segmentos de la historia.
	 * @return
	 */
	public ArrayList<Mind> getStorySoFar();
	
	/**
	 * Obtiene el evolucionador de la mente.
	 * @return 
	 */
	public IMindEvolver getEvolver();
	
	/**
	 * Comprueba si la mente ha sido inicializada.
	 * @return True si la mente ha sido inicializada.
	 */
	public boolean isInitialized();
	
	/**
	 * A�ade un observador.
	 * @param o Observador.
	 */
	public void insertObserver(Observer o);
	
	/**
	 * Elimina un observador.
	 * @param o Observador.
	 */
	public void removeObserver(Observer o);
	
	/**
	 * Vota al segmento de la historia n�mero i con la valoraci�n vote.
	 * @param i N�mero del segmento a votar en la historia.
	 * @param vote Nueva valoraci�n del segmento.
	 */
	public void voteSegment(int i, tVote vote);
	
}
