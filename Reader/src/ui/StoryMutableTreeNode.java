package ui;

import javax.swing.tree.DefaultMutableTreeNode;

public class StoryMutableTreeNode extends DefaultMutableTreeNode{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Posici�n del segmento en la historia..
	 */
	private int segmentPosition;
	
	/**
	 * Posici�n de la relaci�n en el segmento.
	 */
	private int relationPosition;
	
	/**
	 * Crea un nodo para segmentos.
	 * @param nodeName Nombre a mostrar del nodo.
	 * @param posS Posici�n en el array de segmentos.
	 */
	public StoryMutableTreeNode(String nodeName, int posS) {
		super(nodeName);
		setSegmentPosition(posS);
	}
	
	/**
	 * Crea un nodo para relaciones.
	 * @param nodeName Nombre a mostrar del nodo.
	 * @param posS Posici�n en el array de segmentos.
	 * @param posR Posici�n en el array de relaciones.
	 */
	public StoryMutableTreeNode(String nodeName, int posS, int posR) {
		super(nodeName);
		setSegmentPosition(posS);
		setRelationPosition(posR);
	}

	/**
	 * Setter en el array de posiciones.
	 * @param segmentPosition
	 */
	public void setSegmentPosition(int segmentPosition) {
		this.segmentPosition = segmentPosition;
	}

	/**
	 * Getter de la posici�n en el array de segmentos.
	 * @return
	 */
	public int getSegmentPosition() {
		return segmentPosition;
	}

	/**
	 * Setter para la posici�n de la relaci�n en el array de relaciones.
	 * @param relationPosition
	 */
	public void setRelationPosition(int relationPosition) {
		this.relationPosition = relationPosition;
	}

	/**
	 * Getter para la posici�n de la relaci�n en el array de relaciones.
	 * @return
	 */
	public int getRelationPosition() {
		return relationPosition;
	}
	
	
}
