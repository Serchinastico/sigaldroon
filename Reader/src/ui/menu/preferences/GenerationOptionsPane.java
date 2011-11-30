package ui.menu.preferences;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ui.StoryJFrame;

/**
 * Panel de preferencias para opciones de generación de la historia.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class GenerationOptionsPane extends JPanel {

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
	 * Etiqueta para el número máximo de segmentos.
	 */
	private JLabel maxSegmentLabel;
	
	/**
	 * Casilla para el número máximo de segmentos.
	 */
	private JTextField maxSegmentField;
	
	/**
	 * Etiqueta para el número de iteraciones para generar un segmento.
	 */
	private JLabel numItSegmentLabel;
	
	/**
	 * Casilla para el número de iteraciones para generar un segmento.
	 */
	private JTextField numItSegmentField;
	
	/**
	 * Constructora del panel con las opciones de generación de la historia.
	 * @param f Jframe mediator.
	 */
	public GenerationOptionsPane(StoryJFrame f) {
		super();
		frame = f;
		
		// Objetos para hacer el grid
        this.setLayout(new GridBagLayout());
        GridBagConstraints constrains = new GridBagConstraints();
        
        // Capacidad máxima de segmentos de la historia: etiqueta y casilla
    	maxSegmentLabel = new JLabel("Capacidad máxima de segmentos:");
    	
    	constrains.gridx = 0;
        constrains.gridy = 0;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(maxSegmentLabel,constrains);
        
        maxSegmentField = new JTextField(4);
        if (frame.getObservableReader().isInitialized()) 
        	maxSegmentField.setEditable(false);
        
        constrains.gridx = 1;
        constrains.gridy = 0;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(maxSegmentField,constrains);
        
        // Número de iteraciones para generar un segmento: etiqueta y casilla
    	numItSegmentLabel = new JLabel("Número de iteraciones por segmento:");
    	
    	constrains.gridx = 0;
        constrains.gridy = 1;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
    	
    	this.add(numItSegmentLabel,constrains);
    	
    	numItSegmentField = new JTextField(4);
    	
    	constrains.gridx = 1;
        constrains.gridy = 1;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
    	
    	this.add(numItSegmentField,constrains);
    	
    	// Borde del panel
    	Border  border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    	TitledBorder titleBorder = BorderFactory.createTitledBorder(
    			border, "Generación de la historia");
    	titleBorder.setTitleJustification(TitledBorder.LEFT);
    	this.setBorder(titleBorder);
	}
	
	/**
	 * El número de la casilla del máximo número de segmentos.
	 * @return El número escrito en la casilla.
	 * @throws Exception Si no es un valor numérico de un entero positivo.
	 */
	public int getMaxSegment() throws Exception {
		Integer maxSegments = 0;
		try {
			maxSegments = Integer.parseInt(maxSegmentField.getText());
		}
		catch (Exception e) {
			throw new Exception("El valor introducido para el máximo número de segmentos no es numérico");
		}
		if (maxSegments <= 0) 
			throw new Exception("El valor introducido para el máximo número de segmentos no es positivo.");
		return maxSegments;
	}
	
	/**
	 * Establece el texto de la casilla para el máximo número de segmentos.
	 * @param maxSegment
	 */
	public void setMaxSegment(String maxSegment) {
		maxSegmentField.setText(maxSegment);
	}
	
	/**
	 * El número de la casilla del número de iteraciones por segmento.
	 * @return El número escrito en la casilla.
	 * @throws Exception Si no es un valor numérico de un entero positivo.
	 */
	public int getNumItSegment() throws Exception {
		Integer numItSegment = 0;
		try {
			numItSegment = Integer.parseInt(numItSegmentField.getText());
		}
		catch (Exception e) {
			throw new Exception("El valor introducido para el número de iteraciones por segmento no es numérico");
		}
		if (numItSegment <= 0) 
			throw new Exception("El valor introducido para el número de iteraciones por segmento no es positivo.");
		return numItSegment;
	}
	
	/**
	 * Establece el texto de la casilla para el número de iteraciones por segmento.
	 * @param maxSegment
	 */
	public void setNumItSegment(String numItSegment) {
		numItSegmentField.setText(numItSegment);
	}
}
