package ui;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import mind.Mind;
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
		this.setModel(model);
		this.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				selectionPerformed();
			}
		});
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
		case 1:
			// Es un segmento
			StoryMutableTreeNode segment = (StoryMutableTreeNode) node;
			String segmentContent = frame.getObservableReader().getStorySoFar().get(segment.getSegmentPosition()).toStringSegment();
			frame.getTextAreaVisualizacion().setText(segmentContent);
			break;
		case 2:
			// Es una relación
			StoryMutableTreeNode relation = (StoryMutableTreeNode) node;
			Iterator<Relation> itRelation = frame.getObservableReader().getStorySoFar().get(relation.getSegmentPosition()).iterator();
			int j = 0;
			while (j < relation.getRelationPosition()) {
				itRelation.next();
				j++;
			}
			String relationContent = itRelation.next().toStringRelation();
			frame.getTextAreaVisualizacion().setText(relationContent);
			break;
		}

	}

	/**
	 * Inserta una historia en el árbol y lo actualiza.
	 * @param story Historia a insertar.
	 */
	public void loadStory(ArrayList<Mind> story) {
		for (int i = 0; i < story.size(); i++) {
			StoryMutableTreeNode segment = new StoryMutableTreeNode("Segmento "+i,i);
			model.insertNodeInto(segment, titleTree, i);

			int j = 0;
			Iterator<Relation> itRelation = story.get(i).iterator();
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
