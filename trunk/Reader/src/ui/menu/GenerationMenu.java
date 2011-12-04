package ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ui.CommandManager;
import ui.StoryJFrame;

/**
 * Componente para el men� Generaci�n de la barra de men�.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class GenerationMenu extends JMenu {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Frame contenedor de este men�.
	 */
	private StoryJFrame frame;

	/**
     * Opci�n del men� Generaci�n para generar un segmento m�s de la historia.
     */
    private JMenuItem menuItemSiguiente;
    
    /**
     * Opci�n del men� Generaci�n para generar la historia completa.
     */
    private JMenuItem menuItemCompleta;
    
    /**
     * Constructora para el men� Generaci�n.
     * @param f Frame mediator.
     */
    public GenerationMenu(StoryJFrame f) {
    	super();
    	
    	frame = f;
    	setText("Generaci�n");
    	
    	// Opci�n de obtener siguiente segmento
    	menuItemSiguiente = new JMenuItem();
        menuItemSiguiente.setText("Siguiente segmento");
        menuItemSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CommandManager.getInstance().generateNextSegment(frame);
            }
        });
        this.add(menuItemSiguiente);

        // Opci�n de completar historia
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
