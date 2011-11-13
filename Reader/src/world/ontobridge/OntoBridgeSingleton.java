package world.ontobridge;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;


/**
 * Aplicación del patrón Singleton para la clase OntoBridge.
 * */
public class OntoBridgeSingleton {
	
	private static OntoBridge instance = null;
	
	private OntoBridgeSingleton() {
		
	}
	
	static public OntoBridge getSingleton() {
		if (instance == null) {
			instance = new OntoBridge();
		}
		return instance;
	}
}
