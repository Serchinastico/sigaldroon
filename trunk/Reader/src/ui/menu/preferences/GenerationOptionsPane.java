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
 * Panel de preferencias para opciones de generaci�n de la historia.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class GenerationOptionsPane extends JPanel {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Frame contenedor de este men�.
	 */
	@SuppressWarnings("unused")
	private StoryJFrame frame;
	
	/**
	 * Etiqueta para el n�mero m�ximo de segmentos.
	 */
	private JLabel maxSegmentLabel;
	
	/**
	 * Casilla para el n�mero m�ximo de segmentos.
	 */
	private JTextField maxSegmentField;
	
	/**
	 * Etiqueta para el n�mero de iteraciones para generar un segmento.
	 */
	private JLabel numItSegmentLabel;
	
	/**
	 * Casilla para el n�mero de iteraciones para generar un segmento.
	 */
	private JTextField numItSegmentField;
	
	/**
	 * Constructora del panel con las opciones de generaci�n de la historia.
	 * @param f Jframe mediator.
	 */
	public GenerationOptionsPane(StoryJFrame f) {
		super();
		frame = f;
		
		// Objetos para hacer el grid
        this.setLayout(new GridBagLayout());
        GridBagConstraints constrains = new GridBagConstraints();
        
        // Capacidad m�xima de segmentos de la historia: etiqueta y casilla
    	maxSegmentLabel = new JLabel("Capacidad m�xima de segmentos:");
    	
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
        
        // N�mero de iteraciones para generar un segmento: etiqueta y casilla
    	numItSegmentLabel = new JLabel("N�mero de iteraciones por segmento:");
    	
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
    			border, "Generaci�n de la historia");
    	titleBorder.setTitleJustification(TitledBorder.LEFT);
    	this.setBorder(titleBorder);
	}
	
	/**
	 * El n�mero de la casilla del m�ximo n�mero de segmentos.
	 * @return El n�mero escrito en la casilla.
	 * @throws Exception Si no es un valor num�rico de un entero positivo.
	 */
	public int getMaxSegment() throws Exception {
		Integer maxSegments = 0;
		try {
			maxSegments = Integer.parseInt(maxSegmentField.getText());
		}
		catch (Exception e) {
			throw new Exception("El valor introducido para el m�ximo n�mero de segmentos no es num�rico");
		}
		if (maxSegments <= 0) 
			throw new Exception("El valor introducido para el m�ximo n�mero de segmentos no es positivo.");
		return maxSegments;
	}
	
	/**
	 * Establece el texto de la casilla para el m�ximo n�mero de segmentos.
	 * @param maxSegment
	 */
	public void setMaxSegment(String maxSegment) {
		maxSegmentField.setText(maxSegment);
	}
	
	/**
	 * El n�mero de la casilla del n�mero de iteraciones por segmento.
	 * @return El n�mero escrito en la casilla.
	 * @throws Exception Si no es un valor num�rico de un entero positivo.
	 */
	public int getNumItSegment() throws Exception {
		Integer numItSegment = 0;
		try {
			numItSegment = Integer.parseInt(numItSegmentField.getText());
		}
		catch (Exception e) {
			throw new Exception("El valor introducido para el n�mero de iteraciones por segmento no es num�rico");
		}
		if (numItSegment <= 0) 
			throw new Exception("El valor introducido para el n�mero de iteraciones por segmento no es positivo.");
		return numItSegment;
	}
	
	/**
	 * Establece el texto de la casilla para el n�mero de iteraciones por segmento.
	 * @param maxSegment
	 */
	public void setNumItSegment(String numItSegment) {
		numItSegmentField.setText(numItSegment);
	}
}
