package reader;

import coherence.Events;

import mind.Mind;

/**
 * Un segmento de la historia de un mito griego.
 * Sirve como contenedor de las relaciones existentes en la mente del lector,
 * el texto en lenguaje natural contando lo nuevo que aporta el segmento,
 * los eventos relevantes sucedidos y votos dados por el usuario al segmento.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Segment {

	/**
	 * Tipos de votos posibles para un segmento.
	 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
	 */
	public enum tVote {
		POSITIVE,
		NEUTRAL,
		NEGATIVE
	}
	
	/**
	 * Relaciones de la historia que tiene el lector en su mente durante este segmento.
	 */
	private Mind mind;
	
	/**
	 * Texto en lenguaje natural que cuenta lo novedoso de este segmento.
	 * Lo novedoso respecto a momentos anteriores de la historia.
	 */
	private String textSegment;
	
	/**
	 * Eventos de muertes y matrimonios tenidos en cuenta hasta este segmento de la historia.
	 */
	private Events events;
	
	/**
	 * Votación que el usuario ha dado a este segmento.
	 * Por defecto, voto neutral.
	 * @see tVote para tipos de votos.
	 */
	private tVote vote;
	
	/**
	 * Objeto que lleva información extra sobre el segmento para hacer
	 * una evaluación del sistema posteriormente.
	 */
	private SegmentLog segmentLog;
	
	/**
	 * Constructora por defecto.
	 */
	public Segment() {
		this.mind = null;
		this.textSegment = null;
		this.events = null;
		this.vote = tVote.NEUTRAL;
		this.segmentLog = null;
	}
	
	/**
	 * Crea un segmento a partir de elementos existentes.
	 * El segmento está inicializado con un voto neutral.
	 * @param mind Mente con las relaciones para este segmento.
	 * @param textSegment Cadena con el texto del segmento.
	 * @param events Eventos sucedidos hasta el momento en la historia.
	 */
	public Segment(Mind mind, String textSegment, Events events, SegmentLog segmentLog) {
		this.mind = mind;
		this.textSegment = textSegment;
		this.events = events;
		this.vote = tVote.NEUTRAL;
		this.segmentLog = segmentLog;
	}
	
	/**
	 * Devuelve la mente con las relaciones del segmento.
	 * @return La mente del segmento.
	 */
	public Mind getMind() {
		return mind;
	}
	
	/**
	 * Devuelve el texto en lenguaje natural que cuenta este segmento de la historia.
	 * @return El texto del segmento.
	 */
	public String getTextSegment() {
		return textSegment;
	}
	
	/**
	 * Devuelve los eventos sucedidos hasta este segmento.
	 * @return Los eventos tenidos en cuenta por el segmento.
	 */
	public Events getEvents() {
		return events;
	}
	
	/**
	 * Devuelve el voto establecido en este segmento.
	 * @return El voto.
	 */
	public tVote getVote() {
		return vote;
	}
	
	/**
	 * Vota al segmento con la valoración vote.
	 * @param vote Nueva valoración del segmento.
	 */
	public void setVote(tVote vote) {
		this.vote = vote;
	}

	/**
	 * @return the segmentLog
	 */
	public SegmentLog getSegmentLog() {
		return segmentLog;
	}
	
}
