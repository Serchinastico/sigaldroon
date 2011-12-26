package ui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import ui.StoryJFrame;



/**
 * Panel para la visualización de texto natural de partes seleccionadas en el árbol.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class NaturalTextArea extends JPanel {

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
	private JLabel labelNaturalText;
	
	/**
	 * Área de texto para mostrar elementos.
	 */
    private JTextArea naturalTextArea;
	
	/**
	 * Constructora del área de texto natural.
	 * @param f Frame mediator.
	 */
	public NaturalTextArea(StoryJFrame f) {
		super();
		frame = f;
		
		jScrollPaneRight = new JScrollPane();
		naturalTextArea = new javax.swing.JTextArea();
        labelNaturalText = new javax.swing.JLabel();
        
        naturalTextArea.setColumns(10);
        naturalTextArea.setRows(5);
        naturalTextArea.setFont(new java.awt.Font("Garamond", 0, 18));
        jScrollPaneRight.setViewportView(naturalTextArea);
        jScrollPaneRight.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        GridBagConstraints constrains = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        
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
        
        naturalTextArea.setLineWrap(true);
        naturalTextArea.setWrapStyleWord(true);
        
        
        
        labelNaturalText.setFont(new java.awt.Font("Garamond", 0, 18));
        labelNaturalText.setText("Texto en lenguaje natural:");
        labelNaturalText.setBorder(BorderFactory.createEtchedBorder());
        
        
       
        constrains.gridx = 0;
        constrains.gridy = 0;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.0;
        constrains.weighty = 0.1;
        constrains.fill = GridBagConstraints.NONE;
        constrains.anchor = GridBagConstraints.LINE_START;
        
        this.add(labelNaturalText,constrains);
        
        
	}

	/**
	 * Accesora para el área de texto en lenguaje natural.
	 * @return El área de texto en lenguaje natural.
	 */
	public JTextArea getNaturalTextArea() {
		return naturalTextArea;
	}
}
