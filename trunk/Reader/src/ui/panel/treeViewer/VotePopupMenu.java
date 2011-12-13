package ui.panel.treeViewer;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import reader.Segment.tVote;

import ui.CommandManager;
import ui.StoryJFrame;

/**
 * Menú para votar segmentos que aparece al hacer click derecho sobre uno de ellos.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class VotePopupMenu extends JPopupMenu {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Frame contenedor de este menú.
	 */
	private StoryJFrame frame;
	
	/**
	 * Número del segmento seleccionado.
	 */
	private int segmentPosition;
	
	/**
	 * PAra votar positivamente a un segmento.
	 */
	private JMenuItem positiveVote;
	
	/**
	 * Para votar negativamente a un segmento.
	 */
	private JMenuItem negativeVote;
	
	/**
	 * Para borrar voto a un segmento.
	 */
	private JMenuItem neutralVote;

	/**
	 * Constructora para el popup menu de votaciones.
	 * @param f Frame mediator.
	 */
	public VotePopupMenu(StoryJFrame f, int segmentPos) {
		frame = f;
		segmentPosition = segmentPos;
		
		positiveVote = new JMenuItem("Votar positivo");
		positiveVote.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	CommandManager.getInstance().voteSegment(frame, segmentPosition, tVote.POSITIVE);
            }
        });
		this.add(positiveVote);
		
		neutralVote = new JMenuItem("Votar neutro");
		neutralVote.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	CommandManager.getInstance().voteSegment(frame, segmentPosition, tVote.NEUTRAL);
            }
        });
		this.add(neutralVote);
		
		negativeVote = new JMenuItem("Votar negativo");
		negativeVote.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	CommandManager.getInstance().voteSegment(frame, segmentPosition, tVote.NEGATIVE);
            }
        });
		this.add(negativeVote);
	}
	
}
