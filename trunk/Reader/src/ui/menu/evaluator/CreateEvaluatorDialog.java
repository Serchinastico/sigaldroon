package ui.menu.evaluator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import evaluator.DynamicEvaluator;

import ui.StoryJFrame;

/**
 * Ventana para la creación de un evaluador.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class CreateEvaluatorDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private CreateEvaluatorOptionsPane optionsPane;
	
	/**
	 * Frame contenedor de este menú.
	 */
	private StoryJFrame frame;
	
	/**
     * Constructora por defecto de la ventana de creación del evaluador.
     */
    public CreateEvaluatorDialog(StoryJFrame owner) {
    	super(owner, true); // Establecido a modal para no permitir tocar otras cosas de la aplicación.
    	frame = owner;
    	setConfiguration(owner);
    	initComponents();
    }
    
    /**
     * Establece la configuración de la ventana.
     */
    private void setConfiguration(JFrame owner) {
    	this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    	this.setTitle("Preferencias");
    	this.setSize(310, 250);
    	this.setResizable(false);
    	this.setLocationRelativeTo(owner);
    }
    
    /**
     * Inicializa los componentes de la ventana.
     */
    private void initComponents() {
    	// Objetos para hacer el grid
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constrains = new GridBagConstraints();
        
        // Panel de opciones para la generación de la historia
    	optionsPane = new CreateEvaluatorOptionsPane();
    	
    	constrains.gridx = 0;
        constrains.gridy = 0;
        constrains.gridwidth = 2;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.getContentPane().add(optionsPane, constrains);
        
        // Botón aceptar
        JButton okButton = new JButton("Aceptar");
        okButton.addActionListener(new OkButtonActionListener());
    	
    	constrains.gridx = 0;
        constrains.gridy = 2;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
    	
    	this.getContentPane().add(okButton, constrains);
    	
    	// Botón cancelar
    	JButton cancelButton = new JButton("Cancelar");
    	cancelButton.addActionListener(new CancelButtonActionListener());
    	
    	constrains.gridx = 1;
        constrains.gridy = 2;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
    	
    	this.getContentPane().add(cancelButton, constrains);
    }
    
    /**
     * Listener para la acción de pulsar sobre el botón Ok
     * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
     *
     */
    private class OkButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String patternsPath = null;
			String evaluatorPath = null;
			int numStoryBreaks = 10;
			boolean error = false;
			
			try {
				patternsPath = optionsPane.getPatternsPath();
				evaluatorPath = optionsPane.getEvaluatorPath();
				numStoryBreaks = optionsPane.getNumStoryBreaks();
			} catch(Exception exc) {
				JOptionPane.showMessageDialog(null, 
						exc.getMessage(), 
						"Valores incorrectos", 
						JOptionPane.ERROR_MESSAGE);
				error = true;
			}
			
			if (!error) {
				DynamicEvaluator evaluator = new DynamicEvaluator(numStoryBreaks, patternsPath);
				frame.getObservableReader().setEvaluator(evaluator);
				evaluator.setFilePath(evaluatorPath);
				evaluator.save();
				dispose();
			}
		}
    }
    
    /**
     * Listener para la acción de pulsar sobre el botón Cancelar
     * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
     *
     */
    private class CancelButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
    }
}
