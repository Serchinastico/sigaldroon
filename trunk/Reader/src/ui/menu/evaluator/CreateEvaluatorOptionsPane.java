package ui.menu.evaluator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Panel de preferencias para opciones de creación del evaluador.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class CreateEvaluatorOptionsPane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Campo de texto con la ruta al fichero de patrones elegido.
	 */
	private JTextField patternsPathTextField;
	
	/**
	 * Campo de texto con la ruta al fichero donde se quiere guardar
	 * el evaluador.
	 */
	private JTextField evaluatorPathTextField;

	/**
	 * Número de cortes de la historia en el evaluador.
	 */
	private JTextField numStoryBreaksTextField;
	
	/**
	 * Tipo del evaluador.
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox tipoEvaluadorComboBox; 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CreateEvaluatorOptionsPane() {
		super();
		
		// Objetos para hacer el grid
        this.setLayout(new GridBagLayout());
        GridBagConstraints constrains = new GridBagConstraints();
		
		JLabel patternsPathLabel = new JLabel("Fichero de patrones:");
		
		constrains.gridx = 0;
        constrains.gridy = 0;
        constrains.gridwidth = 2;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(patternsPathLabel, constrains);
		
		patternsPathTextField = new JTextField();
		patternsPathTextField.setEditable(false);
		
		constrains.gridx = 0;
        constrains.gridy = 1;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.95;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(patternsPathTextField, constrains);
		
		JButton patternsPathButton = new JButton("...");
		// Acción para elegir un fichero de patrones existente
		patternsPathButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser jfc = new JFileChooser("resources/DynamicEvaluator");
				if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					patternsPathTextField.setText(jfc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		constrains.gridx = 1;
        constrains.gridy = 1;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.05;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(patternsPathButton, constrains);
		
		JLabel evaluatorPathLabel = new JLabel("Fichero del evaluador:");
		
		constrains.gridx = 0;
        constrains.gridy = 2;
        constrains.gridwidth = 2;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(evaluatorPathLabel, constrains);
		
		evaluatorPathTextField = new JTextField();
		evaluatorPathTextField.setEditable(false);
		
		constrains.gridx = 0;
        constrains.gridy = 3;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.95;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(evaluatorPathTextField, constrains);
		
		JButton evaluatorPathButton = new JButton("...");
		// Acción para elegir el fichero donde se guardará el evaluador
		evaluatorPathButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser jfc = new JFileChooser("resources/DynamicEvaluator");
				if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					evaluatorPathTextField.setText(jfc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		constrains.gridx = 1;
        constrains.gridy = 3;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.05;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(evaluatorPathButton, constrains);
		
		JLabel numStoryBreaksLabel = new JLabel("Número de divisiones en la historia:");
		
		constrains.gridx = 0;
        constrains.gridy = 4;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.90;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(numStoryBreaksLabel, constrains);
		
		numStoryBreaksTextField = new JTextField("10");
		
		constrains.gridx = 1;
        constrains.gridy = 4;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.10;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(numStoryBreaksTextField, constrains);
        
        JLabel tipoEvaluadorLabel = new JLabel("Tipo de evaluador:");
		
		constrains.gridx = 0;
        constrains.gridy = 5;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.90;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(tipoEvaluadorLabel, constrains);
        
        String[] tipos = {"Dinámico aleatorio", "Dinámico guiado"};
        tipoEvaluadorComboBox = new JComboBox(tipos);
        
        constrains.gridx = 1;
        constrains.gridy = 5;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.10;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(tipoEvaluadorComboBox, constrains);
        
        // Borde del panel
    	Border  border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    	TitledBorder titleBorder = BorderFactory.createTitledBorder(
    			border, "Opciones del evaluador");
    	titleBorder.setTitleJustification(TitledBorder.LEFT);
    	this.setBorder(titleBorder);
	}

	public String getPatternsPath() {
		return patternsPathTextField.getText();
	}
	
	public String getEvaluatorPath() {
		return evaluatorPathTextField.getText();
	}
	
	public int getNumStoryBreaks() throws NumberFormatException {
		return Integer.parseInt(numStoryBreaksTextField.getText());
	}

	public evaluator.DynamicEvaluator.Type getEvaluatorType() {
		switch (tipoEvaluadorComboBox.getSelectedIndex()) {
		case 0: return evaluator.DynamicEvaluator.Type.RANDOM;
		case 1: return evaluator.DynamicEvaluator.Type.GUIDED;
		default: return null;
		}
	}
	
}
