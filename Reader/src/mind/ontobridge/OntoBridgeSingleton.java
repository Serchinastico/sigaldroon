package mind.ontobridge;

import java.util.ArrayList;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import es.ucm.fdi.gaia.ontobridge.OntologyDocument;


/**
 * Singleton de la ontolog�a.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 * */
public class OntoBridgeSingleton{

	/**
	 * Ruta de la ontolog�a de nombres.
	 * */
	private static final String ONTOLOGY_PATH = "file:resources/ontologies/Ontology.owl";
	
	private static OntoBridge instance = null;
	
	private OntoBridgeSingleton() {
		
	}

	static public OntoBridge getInstance() {
		if (instance == null) {
			instance = new OntoBridge();
			instance.initWithPelletReasoner();
			OntologyDocument mainOnto = new OntologyDocument("", ONTOLOGY_PATH);
			ArrayList<OntologyDocument> subOntologies = new ArrayList<OntologyDocument>();
			instance.loadOntology(mainOnto, subOntologies, false);
		}
		return instance;
	}

}