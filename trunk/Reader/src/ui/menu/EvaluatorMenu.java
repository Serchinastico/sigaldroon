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
 * Componente para el menú Evaluador de la barra de menú.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class EvaluatorMenu extends JMenu {
	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ruta a la dirección por defecto de los evaluadores dinámicos.
	 * */
	private static final String EVALUATOR_CURRENT_DIR = "resources/DynamicEvaluator/";
	
	/**
	 * Frame contenedor de este menú.
	 */
	private StoryJFrame frame;
	
	/**
	 * Opción del menú Evaluador para crear un evaluador dinámico.
	 */
	private JMenuItem menuItemCrearEvaluador;
	
	/**
	 * Opción del menú Evaluador para guardar el evaluador cargado.
	 */
	private JMenuItem menuItemGuardarEvaluador;
	
	/**
	 * Opción del menú Evaluador para cargar un evaluador dinámico.
	 */
	private JMenuItem menuItemCargarEvaluador;
	
	/**
	 * Opción del menú Evaluador para cargar el evaluador simple.
	 */
	private JMenuItem menuItemCargarEvaluadorSimple;
	
	/**
	 * Opción del menú evaluador para reiniciar los pesos del evaluador dinámico.
	 */
	private JMenuItem menuItemReiniciarEvaluador;
	
	/**
	 * Opción del menú Evaluador para ver el evaluador cargado.
	 */
	private JMenuItem menuItemVerEvaluador;
	
	public EvaluatorMenu(StoryJFrame f) {
		super();
		frame = f;
		
		setText("Evaluador");
		
		// Opción de crear un nuevo evaluador dinámico
		menuItemCrearEvaluador = new JMenuItem();
		menuItemCrearEvaluador.setText("Crear Evaluador");
		menuItemCrearEvaluador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	CreateEvaluatorDialog ceDialog = new CreateEvaluatorDialog(frame);
            	ceDialog.setVisible(true);
            }
        });
        this.add(menuItemCrearEvaluador);
		
        // Opción de guardar el evaluador dinámico usado
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
					JOptionPane.showMessageDialog(null, "El evaluador cargado no es dinámico.", "Error al guardar", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
        this.add(menuItemGuardarEvaluador);
        
		// Opción de cargar un nuevo evaluador dinámico
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
        
		// Opción de cargar el evaluador simple
		menuItemCargarEvaluadorSimple = new JMenuItem();
		menuItemCargarEvaluadorSimple.setText("Cargar Evaluador Simple");
		menuItemCargarEvaluadorSimple.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	frame.getObservableReader().setEvaluator(new SimpleEvaluator());
            }
        });
        this.add(menuItemCargarEvaluadorSimple);
        
	    // Opción de reiniciar el evaluador dinámico
        menuItemReiniciarEvaluador = new JMenuItem();
        menuItemReiniciarEvaluador.setText("Reiniciar Evaluador");
        menuItemReiniciarEvaluador.addMouseListener(new java.awt.event.MouseAdapter() {
	         public void mousePressed(java.awt.event.MouseEvent evt) {
	        	 IEvaluator evaluator = frame.getObservableReader().getEvaluator();
            	 if (evaluator instanceof DynamicEvaluator) {
            		 ((DynamicEvaluator) frame.getObservableReader().getEvaluator()).randomizeWeights();
            	 }
            	 else {
            		 JOptionPane.showMessageDialog(null, "El evaluador cargado no es dinámico.", "Error al reiniciar el evaluador", JOptionPane.ERROR_MESSAGE);
            	 }
	         }
	     });
	     this.add(menuItemReiniciarEvaluador);
        
        // Opción de visualizar el evaluador cargado
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
            		 JOptionPane.showMessageDialog(null, "El evaluador cargado no es dinámico.", "Error del visualizador", JOptionPane.ERROR_MESSAGE);
            	 }
             }
         });
         this.add(menuItemVerEvaluador);
	}
}
