package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Panel para la visualización de selecciones y contenido de la historia.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Visualization extends JPanel {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Frame contenedor de este menú.
	 */
	@SuppressWarnings("unused")
	private StoryJFrame frame;
	
	/**
     * Panel derecho para contener el resto de componentes de la interfaz.
     */
    private JScrollPane jScrollPaneRight;
	
    /**
     * Etiqueta para el título del panel.
     */
	private JLabel labelVisualizacion;
	
	/**
	 * Área de texto para mostrar elementos.
	 */
    private JTextArea textAreaVisualizacion;
	
	/**
	 * Constructora del área de visualización.
	 * @param f Frame mediator.
	 */
	public Visualization(StoryJFrame f) {
		super();
		frame = f;
		
		jScrollPaneRight = new JScrollPane();
        textAreaVisualizacion = new javax.swing.JTextArea();
        labelVisualizacion = new javax.swing.JLabel();
        
        textAreaVisualizacion.setColumns(20);
        textAreaVisualizacion.setRows(5);
        textAreaVisualizacion.setFont(new java.awt.Font("Garamond", 0, 18));
        jScrollPaneRight.setViewportView(textAreaVisualizacion);
        
        labelVisualizacion.setFont(new java.awt.Font("Garamond", 0, 18));
        labelVisualizacion.setText("Visualización de selección:");
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
        this.add(jScrollPaneRight,constrains);
	}

	/**
	 * Accesora para el área de texto de visualización.
	 * @return El área de texto de visualización.
	 */
	public JTextArea getTextAreaVisualizacion() {
		return textAreaVisualizacion;
	}
	
}
