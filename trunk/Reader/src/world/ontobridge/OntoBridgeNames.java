package world.ontobridge;

import java.util.ArrayList;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import es.ucm.fdi.gaia.ontobridge.OntologyDocument;


/**
 * Singleton de la ontología para nombres.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 * */
public class OntoBridgeNames {

	/**
	 * Ruta de la ontología de nombres.
	 * */
	private static final String NAMES_ONTOLOGY_PATH = "";
	
	private static OntoBridge instance = null;
	
	private OntoBridgeNames() {
		
	}

	static public OntoBridge getInstance() {
		if (instance == null) {
			instance = new OntoBridge();
			
			instance.initWithOutReasoner();
			OntologyDocument mainOnto = new OntologyDocument("", NAMES_ONTOLOGY_PATH);
			ArrayList<OntologyDocument> subOntologies = new ArrayList<OntologyDocument>();
			instance.loadOntology(mainOnto, subOntologies, false);
		}
		return instance;
	}

}
