package ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ui.CommandManager;
import ui.StoryJFrame;

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
	 * Opción del menú Archivo para guardar la historia en un archivo de texto.
	 */
	private JMenuItem menuItemExportarTextoHistoria;

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
				CommandManager.getInstance().initStoryFromFile(frame);
			}
		});
		this.add(menuItemArchivo);

		// Opción de comenzar desde archivo
		menuItemExportarTextoHistoria = new JMenuItem();
		menuItemExportarTextoHistoria.setText("Exportar texto de la historia");
		menuItemExportarTextoHistoria.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				CommandManager.getInstance().exportStoryText(frame);
			}
		});
		this.add(menuItemExportarTextoHistoria);

		// Opción salir
		menuItemSalir = new JMenuItem();
		menuItemSalir.setText("Salir");
		menuItemSalir.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				CommandManager.getInstance().closeApplication(frame);
			}
		});
		this.add(menuItemSalir);
	}

}
