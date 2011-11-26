package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel para la visualizaci�n de selecciones y contenido de la historia.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class Visualization extends JPanel {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Frame contenedor de este men�.
	 */
	private StoryJFrame frame;
	
    /**
     * Etiqueta para el t�tulo del panel.
     */
	private JLabel labelVisualizacion;
	
	/**
	 * �rea de texto para mostrar elementos.
	 */
    private GraphPane graphVisualizacion;
	
	/**
	 * Constructora del �rea de visualizaci�n.
	 * @param f Frame mediator.
	 */
	public Visualization(StoryJFrame f) {
		super();
		frame = f;
		
		graphVisualizacion = new GraphPane(frame);
        labelVisualizacion = new javax.swing.JLabel();

        labelVisualizacion.setFont(new java.awt.Font("Garamond", 0, 18));
        labelVisualizacion.setText("Visualizaci�n de selecci�n:");
        labelVisualizacion.setBorder(BorderFactory.createEtchedBorder());
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints constrains = new GridBagConstraints();
        constrains.gridx = 0;
        constrains.gridy = 0;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.0;
        constrains.weighty = 0.1;
        constrains.fill = GridBagConstraints.NONE;
        constrains.anchor = GridBagConstraints.LINE_START;
        
        this.add(labelVisualizacion,constrains);
        
        constrains.gridx = 0;
        constrains.gridy = 1;
        constrains.gridwidth = 1;
        constrains.gridheight = 2;
        constrains.weightx = 1.0;
        constrains.weighty = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        constrains.anchor = GridBagConstraints.CENTER;
        this.add(graphVisualizacion,constrains);
	}
	
	/**
	 * Accesora para el �rea del grafo de visualizaci�n.
	 * @return El �rea del grafo de visualizaci�n.
	 */
	public GraphPane getGraphVisualizacion() {
		return graphVisualizacion;
	}
	
}
