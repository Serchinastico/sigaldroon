package ui.menu.preferences;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ui.StoryJFrame;

/**
 * Ventana para las preferencias.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class SettingsDialog extends JDialog {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Frame contenedor de este men�.
	 */
	private StoryJFrame frame;
	
	/**
	 * Panel con las opciones de generaci�n de la historia.
	 */
	private GenerationOptionsPane generationOptionsPane;
	
	/**
	 * Bot�n para aceptar las opciones escritas.
	 */
	private JButton okButton;
	
	/**
	 * Bot�n para salir sin modificar las opciones.
	 */
	private JButton cancelButton;
	
	/**
     * Constructora por defecto de la ventana de opciones.
     */
    public SettingsDialog(StoryJFrame f) {
    	super(f,true); // Establecido a modal para no permitir tocar otras cosas de la aplicaci�n.
    	frame = f;
    	setConfiguration();
    	initComponents();
    	loadValuesSet();
    }
    
    /**
     * Establece la configuraci�n de la ventana.
     */
    private void setConfiguration() {
    	this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    	this.setTitle("Preferencias");
    	this.setSize(310, 170);
    	this.setResizable(false);
    	this.setLocationRelativeTo(frame);
    }
    
    /**
     * Inicializa los componentes de la ventana.
     */
    private void initComponents() {
    	// Objetos para hacer el grid
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constrains = new GridBagConstraints();
        
        // Panel de opciones para la generaci�n de la historia
    	generationOptionsPane = new GenerationOptionsPane(frame);
    	
    	constrains.gridx = 0;
        constrains.gridy = 0;
        constrains.gridwidth = 2;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.getContentPane().add(generationOptionsPane,constrains);
        
        // Bot�n aceptar
        okButton = new JButton("Aceptar");
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	buttonAceptarMouseClicked(evt);
            }
        });
    	
    	constrains.gridx = 0;
        constrains.gridy = 1;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
    	
    	this.getContentPane().add(okButton,constrains);
    	
    	// Bot�n cancelar
    	cancelButton = new JButton("Cancelar");
    	cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	buttonCancelarMouseClicked(evt);
            }
        });
    	
    	constrains.gridx = 1;
        constrains.gridy = 1;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
    	
    	this.getContentPane().add(cancelButton,constrains);
    }
    
    /**
     * Carga los valores actuales de las opciones de la aplicaci�n en las casillas de las opciones.
     */
    private void loadValuesSet() {
    	
    	String maxSegment = Integer.toString(frame.getObservableReader().getMaxSegments());
    	generationOptionsPane.setMaxSegment(maxSegment);
    	
    	String numItSegment = Integer.toString(frame.getObservableReader().getEvolver().getNumIterations());
    	generationOptionsPane.setNumItSegment(numItSegment);
    	
    }
    
    /**
	 * Listener para aceptar las opciones.
	 * @param evt
	 */
	private void buttonAceptarMouseClicked(java.awt.event.MouseEvent evt) {
    	
		int maxSegment = 0, numItSegment = 0;
		boolean error = false;
		
		// Comprueba los valores escritos en las casillas
		try {
			maxSegment = generationOptionsPane.getMaxSegment();
			numItSegment = generationOptionsPane.getNumItSegment();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this, 
					e.getMessage(), 
					"Valores incorrectos", 
					JOptionPane.ERROR_MESSAGE);
			error = true;
		}
		
		// Si no hay error, se modifican los valores y se cierra la ventana
		if (!error) {
			frame.getObservableReader().setMaxSegments(maxSegment);
			frame.getObservableReader().getEvolver().setNumIterations(numItSegment);
			this.dispose();
		}
		
		
    }
	
	/**
	 * Listener para cancelar las opciones.
	 * @param evt
	 */
	private void buttonCancelarMouseClicked(java.awt.event.MouseEvent evt) {
		this.dispose();
    }
	
}
