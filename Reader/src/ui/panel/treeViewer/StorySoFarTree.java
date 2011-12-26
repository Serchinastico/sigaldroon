package ui.panel.treeViewer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import reader.Segment;

import ui.StoryJFrame;

import mind.Relation;

/**
 * Componente para representar en jerarquía de árbol una historia.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class StorySoFarTree extends JTree {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Frame contenedor de este menú.
	 */
	private StoryJFrame frame;

	/**
	 * Estructura a cargar en el árbol.
	 */
	private DefaultTreeModel model;

	/**
	 * Título del árbol.
	 */
	private DefaultMutableTreeNode titleTree;

	/**
	 * Constructora con el panel en el que se va a colocar el componente.
	 */
	public StorySoFarTree(StoryJFrame f) {
		super();
		frame = f;
		titleTree = new DefaultMutableTreeNode("Mito");
		model = new DefaultTreeModel(titleTree);
		this.setCellRenderer(new StoryCellRenderer(frame));
		this.setModel(model);
		
		// Listener de selección de nodos
		this.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				selectionPerformed();
			}
		});
		
		// Listener de click derecho del ratón para el popup menu
		MouseAdapter ma = new MouseAdapter() {
			private void myPopupEvent(MouseEvent e) {
				
				// Obtiene la posición del click
				int x = e.getX();
				int y = e.getY();
				JTree tree = (JTree)e.getSource();
				TreePath path = tree.getPathForLocation(x, y);
				if (path == null)
					return; 

				// Se hace seleccionar el nodo en la posición del click
				tree.setSelectionPath(path);
				
				// Se comprueba si el nodo seleccionado es de segmento y se crea el popup menu
				DefaultMutableTreeNode value = (DefaultMutableTreeNode) getLastSelectedPathComponent();
				if (value instanceof StoryMutableTreeNode) {
					StoryMutableTreeNode node = (StoryMutableTreeNode) value;
					if (node.isSegment()) {
						VotePopupMenu popup = new VotePopupMenu(frame, node.getSegmentPosition());
						popup.show(tree, x, y);
					}
				}				
			}
			// Comprueban el click necesario para popup menus (botón derecho)
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) myPopupEvent(e);
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) myPopupEvent(e);
			}
		};
		this.addMouseListener(ma);
		
		frame.getjScrollPaneLeft().setViewportView(this);
	}

	/**
	 * Listener para la selección de elementos del árbol.
	 */
	private void selectionPerformed() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				this.getLastSelectedPathComponent();

		if (node == null) return;

		int depthLevel = node.getLevel();

		switch (depthLevel) {
		case 0:
			frame.getNaturalTextPane().getNaturalTextArea().setText(
					frame.getObservableReader().getTextStory()
			);
			break;
		case 1:
			// Es un segmento
			StoryMutableTreeNode segment = (StoryMutableTreeNode) node;
			frame.getVisualizationPane().getGraphVisualizacion().clearGraph();
			frame.getVisualizationPane().getGraphVisualizacion().printSegment(
					frame.getObservableReader().getSegments().get(
							segment.getSegmentPosition()
					).getMind()
			);
			frame.getNaturalTextPane().getNaturalTextArea().setText(
					frame.getObservableReader().getSegments().get(
							segment.getSegmentPosition()
					).getTextSegment()
			);

			break;
		case 2:
			// Es una relación
			StoryMutableTreeNode relation = (StoryMutableTreeNode) node;
			Iterator<Relation> itRelation = frame.getObservableReader().getSegments().get(relation.getSegmentPosition()).getMind().iterator();
			int j = 0;
			while (j < relation.getRelationPosition()) {
				itRelation.next();
				j++;
			}
			frame.getVisualizationPane().getGraphVisualizacion().clearGraph();
			frame.getVisualizationPane().getGraphVisualizacion().printRelation(itRelation.next());
			frame.getNaturalTextPane().getNaturalTextArea().setText(
					frame.getObservableReader().getSegments().get(
							relation.getSegmentPosition()
					).getTextSegment()
			);
			break;
		}

	}

	/**
	 * Inserta una historia en el árbol y lo actualiza.
	 * @param story Historia a insertar.
	 */
	public void loadStory(ArrayList<Segment> story) {
		for (int i = 0; i < story.size(); i++) {
			StoryMutableTreeNode segment = new StoryMutableTreeNode("Segmento "+i,i);

			model.insertNodeInto(segment, titleTree, i);

			int j = 0;
			Iterator<Relation> itRelation = story.get(i).getMind().iterator();
			while (itRelation.hasNext()) {
				itRelation.next();
				StoryMutableTreeNode relation = new StoryMutableTreeNode("Relación "+j,i,j);
				model.insertNodeInto(relation, segment, j);
				j++;
			}

		}
		model.reload();
	}

}
