package world.ontobridge;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;


/**
 * Aplicaci�n del patr�n Singleton para la clase OntoBridge.
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
