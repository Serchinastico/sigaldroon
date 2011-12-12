package ui.menu;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import evaluator.DynamicEvaluator;
import evaluator.IEvaluator;

import ui.StoryJFrame;
import ui.evaluator.EvaluatorJFrame;

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
	 * Opción del menú Evaluador para cargar un evaluador dinámico.
	 */
	private JMenuItem menuItemCargarEvaluador;
	
	/**
	 * Opción del menú Evaluador para ver el evaluador cargado.
	 */
	private JMenuItem menuItemVerEvaluador;
	
	public EvaluatorMenu(StoryJFrame f) {
		super();
		frame = f;
		
		setText("Evaluador");
		
		// Opción de cargar un nuevo evaluador dinámico
		menuItemCargarEvaluador = new JMenuItem();
		menuItemCargarEvaluador.setText("Cargar Evaluador");
		menuItemCargarEvaluador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	JFileChooser jfc = new JFileChooser(EVALUATOR_CURRENT_DIR);
            	if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					frame.getObservableReader().setEvaluator(new DynamicEvaluator(jfc.getSelectedFile().getAbsolutePath()));
				}
            }
        });
        this.add(menuItemCargarEvaluador);
        
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
            		 //TODO: Mostrar un cartelito advirtiendo del problema
            	 }
             }
         });
         this.add(menuItemVerEvaluador);
	}
}
