package ui.panel.treeViewer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import reader.Segment.tVote;

import ui.StoryJFrame;

/**
 * Clase que sirve para dibujar símbolos junto a los nodos del árbol.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class StoryCellRenderer extends DefaultTreeCellRenderer {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Frame contenedor de este menú.
	 */
	private StoryJFrame frame;

	/**
	 * Icono para segmentos votados negativamente.
	 */
	private Icon redIcon;

	/**
	 * Icono para segmentos votados positivamente.
	 */
	private Icon greenIcon;

	/**
	 * Constructora con el frame mediator y los iconos a usar.
	 * @param f Frame mediator.
	 */
	public StoryCellRenderer(StoryJFrame f) {
		frame = f;
		redIcon = new ImageIcon("resources/icons/negativeVote.png");
		greenIcon = new ImageIcon("resources/icons/positiveVote.png");
	}

	public Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
			int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(
				tree, value, sel,
				expanded, leaf, row,
				hasFocus);
		// Si es un nodo de un segmento valorado, se cambia su icono
		if (value instanceof StoryMutableTreeNode) {
			StoryMutableTreeNode node = (StoryMutableTreeNode) value;
			if (node.isSegment()) {
				tVote segmentVote = frame.getObservableReader().getSegments().get(node.getSegmentPosition()).getVote();
				switch (segmentVote) {
				case POSITIVE:
					setIcon(greenIcon);
					break;
				case NEGATIVE:
					setIcon(redIcon);
					break;
				}
			}
		}
		
		return this;
	}

}
