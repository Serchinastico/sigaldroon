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
	
	public static void main(String[] args) {
		OntoBridge ob = OntoBridgeNames.getInstance();
	}
	
	/**
	 * Ruta de la ontología de nombres.
	 * */
	private static final String NAMES_ONTOLOGY_PATH = "file:resources/ontologies/Nombres.owl";
	
	private static OntoBridge instance = null;
	
	private OntoBridgeNames() {
		
	}

	static public OntoBridge getInstance() {
		if (instance == null) {
			instance = new OntoBridge();
			instance.initWithPelletReasoner();
			OntologyDocument mainOnto = new OntologyDocument("", NAMES_ONTOLOGY_PATH);
			ArrayList<OntologyDocument> subOntologies = new ArrayList<OntologyDocument>();
			instance.loadOntology(mainOnto, subOntologies, false);
		}
		return instance;
	}

}
