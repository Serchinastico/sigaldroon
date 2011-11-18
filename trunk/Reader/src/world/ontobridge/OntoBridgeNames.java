package world.ontobridge;

import java.util.ArrayList;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import es.ucm.fdi.gaia.ontobridge.OntologyDocument;


/**
 * Singleton de la ontolog�a para nombres.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 * */
public class OntoBridgeNames {
	
	public static void main(String[] args) {
		OntoBridge ob = OntoBridgeNames.getInstance();
	}
	
	/**
	 * Ruta de la ontolog�a de nombres.
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
