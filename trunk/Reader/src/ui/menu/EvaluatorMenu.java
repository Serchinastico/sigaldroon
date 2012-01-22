package ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import evaluator.DynamicEvaluator;
import evaluator.IEvaluator;
import evaluator.SimpleEvaluator;

import ui.StoryJFrame;
import ui.evaluator.EvaluatorJFrame;
import ui.menu.evaluator.CreateEvaluatorDialog;

/**
 * Componente para el men� Evaluador de la barra de men�.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class EvaluatorMenu extends JMenu {
	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ruta a la direcci�n por defecto de los evaluadores din�micos.
	 * */
	private static final String EVALUATOR_CURRENT_DIR = "resources/DynamicEvaluator/";
	
	/**
	 * Frame contenedor de este men�.
	 */
	private StoryJFrame frame;
	
	/**
	 * Opci�n del men� Evaluador para crear un evaluador din�mico.
	 */
	private JMenuItem menuItemCrearEvaluador;
	
	/**
	 * Opci�n del men� Evaluador para guardar el evaluador cargado.
	 */
	private JMenuItem menuItemGuardarEvaluador;
	
	/**
	 * Opci�n del men� Evaluador para cargar un evaluador din�mico.
	 */
	private JMenuItem menuItemCargarEvaluador;
	
	/**
	 * Opci�n del men� Evaluador para cargar el evaluador simple.
	 */
	private JMenuItem menuItemCargarEvaluadorSimple;
	
	/**
	 * Opci�n del men� evaluador para reiniciar los pesos del evaluador din�mico.
	 */
	private JMenuItem menuItemReiniciarEvaluador;
	
	/**
	 * Opci�n del men� Evaluador para ver el evaluador cargado.
	 */
	private JMenuItem menuItemVerEvaluador;
	
	public EvaluatorMenu(StoryJFrame f) {
		super();
		frame = f;
		
		setText("Evaluador");
		
		// Opci�n de crear un nuevo evaluador din�mico
		menuItemCrearEvaluador = new JMenuItem();
		menuItemCrearEvaluador.setText("Crear Evaluador");
		menuItemCrearEvaluador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	CreateEvaluatorDialog ceDialog = new CreateEvaluatorDialog(frame);
            	ceDialog.setVisible(true);
            }
        });
        this.add(menuItemCrearEvaluador);
		
        // Opci�n de guardar el evaluador din�mico usado
        menuItemGuardarEvaluador = new JMenuItem();
        menuItemGuardarEvaluador.setText("Guardar Evaluador");
        menuItemGuardarEvaluador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				IEvaluator evaluator = frame.getObservableReader().getEvaluator();
				if (evaluator instanceof DynamicEvaluator) {
					((DynamicEvaluator) frame.getObservableReader().getEvaluator()).save();
				}
				else {
					JOptionPane.showMessageDialog(null, "El evaluador cargado no es din�mico.", "Error al guardar", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
        this.add(menuItemGuardarEvaluador);
        
		// Opci�n de cargar un nuevo evaluador din�mico
		menuItemCargarEvaluador = new JMenuItem();
		menuItemCargarEvaluador.setText("Cargar Evaluador");
		menuItemCargarEvaluador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	JFileChooser jfc = new JFileChooser(EVALUATOR_CURRENT_DIR);
            	if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            		DynamicEvaluator evaluator = new DynamicEvaluator(jfc.getSelectedFile().getAbsolutePath());
            		evaluator.setFilePath(jfc.getSelectedFile().getAbsolutePath());
					frame.getObservableReader().setEvaluator(evaluator);
				}
            }
        });
        this.add(menuItemCargarEvaluador);
        
		// Opci�n de cargar el evaluador simple
		menuItemCargarEvaluadorSimple = new JMenuItem();
		menuItemCargarEvaluadorSimple.setText("Cargar Evaluador Simple");
		menuItemCargarEvaluadorSimple.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	frame.getObservableReader().setEvaluator(new SimpleEvaluator());
            }
        });
        this.add(menuItemCargarEvaluadorSimple);
        
	    // Opci�n de reiniciar el evaluador din�mico
        menuItemReiniciarEvaluador = new JMenuItem();
        menuItemReiniciarEvaluador.setText("Reiniciar Evaluador");
        menuItemReiniciarEvaluador.addMouseListener(new java.awt.event.MouseAdapter() {
	         public void mousePressed(java.awt.event.MouseEvent evt) {
	        	 IEvaluator evaluator = frame.getObservableReader().getEvaluator();
            	 if (evaluator instanceof DynamicEvaluator) {
            		 ((DynamicEvaluator) frame.getObservableReader().getEvaluator()).randomizeWeights();
            	 }
            	 else {
            		 JOptionPane.showMessageDialog(null, "El evaluador cargado no es din�mico.", "Error al reiniciar el evaluador", JOptionPane.ERROR_MESSAGE);
            	 }
	         }
	     });
	     this.add(menuItemReiniciarEvaluador);
        
        // Opci�n de visualizar el evaluador cargado
        menuItemVerEvaluador = new JMenuItem();
        menuItemVerEvaluador.setText("Ver Evaluador");
        menuItemVerEvaluador.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mousePressed(java.awt.event.MouseEvent evt) {
            	 IEvaluator evaluator = frame.getObservableReader().getEvaluator();
            	 if (evaluator instanceof DynamicEvaluator) {
            		 EvaluatorJFrame evaluatorFrame = new EvaluatorJFrame((DynamicEvaluator) frame.getObservableReader().getEvaluator());
            		 evaluatorFrame.setVisible(true);
            	 }
            	 else {
            		 JOptionPane.showMessageDialog(null, "El evaluador cargado no es din�mico.", "Error del visualizador", JOptionPane.ERROR_MESSAGE);
            	 }
             }
         });
         this.add(menuItemVerEvaluador);
	}
}
