package ui.menu;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import reader.Reader;

import ui.StoryJFrame;
import ui.treeViewer.StorySoFarTree;

/**
 * Componente para el menú Archivo de la barra de menú.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class FileMenu extends JMenu {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Frame contenedor de este menú.
	 */
	private StoryJFrame frame;
	
	/**
	 * Opción del menú Archivo para abrir una histora desde una archivo.
	 */
	private JMenuItem menuItemArchivo;
	
	/**
	 * Opción del menú Archivo para salir de la aplicación.
	 */
	private JMenuItem menuItemSalir;
	
	/**
	 * Constructora del menú de Archivo.
	 * @param f Frame mediator.
	 */
	public FileMenu(StoryJFrame f) {
		super();
		
		frame = f;
		
		setText("Archivo");
		
		// Opción de comenzar desde archivo
		menuItemArchivo = new JMenuItem();
        menuItemArchivo.setText("Comenzar desde archivo");
        menuItemArchivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuItemArchivoMouseClicked(evt);
            }
        });
        this.add(menuItemArchivo);

        // Opción salir
        menuItemSalir = new JMenuItem();
        menuItemSalir.setText("Salir");
        menuItemSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuItemSalirMouseClicked(evt);
            }
        });
        this.add(menuItemSalir);
	}
	
	/**
	 * Listener para comenzar una historia desde archivo.
	 * @param evt
	 */
	private void menuItemArchivoMouseClicked(java.awt.event.MouseEvent evt) {
    	JFileChooser fc = new JFileChooser("resources");
    	int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            frame.getObservableReader().deleteObserver(frame);
            frame.setObservableReader(new Reader());
            frame.getObservableReader().insertObserver(frame);
            frame.getObservableReader().createMind(file.getPath());
            frame.setStoryJTree(new StorySoFarTree(frame));
            frame.getStoryJTree().loadStory(frame.getObservableReader().getStorySoFar());
        }
    }

	/**
	 * Listener para la opción Salir.
	 * @param evt
	 */
    private void menuItemSalirMouseClicked(java.awt.event.MouseEvent evt) {
    	frame.dispose();
    }
}
