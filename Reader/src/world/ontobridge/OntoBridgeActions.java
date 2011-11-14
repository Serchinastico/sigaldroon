package world.ontobridge;

import java.util.ArrayList;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import es.ucm.fdi.gaia.ontobridge.OntologyDocument;


/**
 * Singleton de la ontología para acciones.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 * */
public class OntoBridgeActions{

	/**
	 * Ruta de la ontología de nombres.
	 * */
	private static final String ACTIONS_ONTOLOGY_PATH = "";
	
	private static OntoBridge instance = null;
	
	private OntoBridgeActions() {
		
	}

	static public OntoBridge getInstance() {
		if (instance == null) {
			instance = new OntoBridge();
			
			instance.initWithOutReasoner();
			OntologyDocument mainOnto = new OntologyDocument("", ACTIONS_ONTOLOGY_PATH);
			ArrayList<OntologyDocument> subOntologies = new ArrayList<OntologyDocument>();
			instance.loadOntology(mainOnto, subOntologies, false);
		}
		return instance;
	}

}