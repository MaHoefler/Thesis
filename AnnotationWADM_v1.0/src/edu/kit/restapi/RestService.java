package edu.kit.restapi;

import edu.kit.api.PageXMLProcessing;
import edu.kit.api.QueryByTarget;

public class RestService {

	// storing string can be anything JSON-LD/XML
	public void storeAnnotation(String digitalObjId, String xmlString) {
		
		PageXMLProcessing xmlProcessing = new PageXMLProcessing(digitalObjId,xmlString);
		xmlProcessing.parseAndStoreXML();
		
	}
	/**
	 * The following RDF formats are supported by Jena.
     * Jena reader	RIOT Lang
     * 
	 * 	  "TURTLE"	TURTLE
	 * 	     "TTL"	TURTLE
	 * 	  "Turtle"	TURTLE
	 * "N-TRIPLES"	NTRIPLES
	 *  "N-TRIPLE"	NTRIPLES
	 * 	      "NT"	NTRIPLES
	 *   "RDF/XML"	RDFXML
	 * 	      "N3"	N3
	 *   "JSON-LD"	JSONLD
	 *  "RDF/JSON"	RDFJSON
	 *  "RDF/JSON"	RDFJSON
	 *  
	 * @param targetString
	 * @param format
	 */
	public void getAnnotationByTarget(String targetString,String format){
		QueryByTarget queryByTarget = new QueryByTarget();
		String queryOutPut = queryByTarget.getByTarget(targetString,format);
		System.out.println("Query Output\n---------------\n"+queryOutPut);
		
	}
	
	

}