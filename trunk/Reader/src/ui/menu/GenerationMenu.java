package ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ui.CommandManager;
import ui.StoryJFrame;

/**
 * Componente para el menú Generación de la barra de menú.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class GenerationMenu extends JMenu {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Frame contenedor de este menú.
	 */
	private StoryJFrame frame;

	/**
     * Opción del menú Generación para generar un segmento más de la historia.
     */
    private JMenuItem menuItemSiguiente;
    
    /**
     * Opción del menú Generación para generar la historia completa.
     */
    private JMenuItem menuItemCompleta;
    
    /**
     * Constructora para el menú Generación.
     * @param f Frame mediator.
     */
    public GenerationMenu(StoryJFrame f) {
    	super();
    	
    	frame = f;
    	setText("Generación");
    	
    	// Opción de obtener siguiente segmento
    	menuItemSiguiente = new JMenuItem();
        menuItemSiguiente.setText("Siguiente segmento");
        menuItemSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CommandManager.getInstance().generateNextSegment(frame);
            }
        });
        this.add(menuItemSiguiente);

        // Opción de completar historia
        menuItemCompleta = new JMenuItem();
        menuItemCompleta.setText("Completa");
        menuItemCompleta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CommandManager.getInstance().generateCompleteStory(frame);
            }
        });
        this.add(menuItemCompleta);
    }
}
