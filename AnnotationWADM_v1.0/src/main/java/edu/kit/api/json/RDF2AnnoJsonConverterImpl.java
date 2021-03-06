package edu.kit.api.json;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;

import edu.kit.rdfcore.ChoiceType;
import edu.kit.rdfcore.CreationProvenanceType;
import edu.kit.rdfcore.Creator;
import edu.kit.rdfcore.CreatorType;
import edu.kit.rdfcore.EmbeddedContentType;
import edu.kit.rdfcore.ExternalWebResourceType;
import edu.kit.rdfcore.HasBodyType;
import edu.kit.rdfcore.HasSelector;
import edu.kit.rdfcore.HasTarget;
import edu.kit.rdfcore.ItemsType;
import edu.kit.rdfcore.RDFtype;
import edu.kit.rdfcore.ResourceBodyType;

/**
 * 
 * @author Vaibhav
 * 
 * This class is used to map the RDF/XML to JSON to Convert to Anno Json profile.
 * the Core classes used for mapping is present in edu.kit.rdfcore. Internally it uses JAXB to unmarshal the XML.
 *
 */
public class RDF2AnnoJsonConverterImpl implements RDF2AnnoJsonConverter {
	
	private Model model;
	private String idString;
	private String serviceurl;

	/**
	 * This method unmarshal the RDF/XML to its respectvie object.
	 * This object is further passed to get the Respective Array of Annotation JSON.
	 * 
	 * @param xmlStr RDF/XML format input.
	 * @return String array of Annoatation JSON.
	 * 
	 */
	@Override
	public String parse(String xmlStr) {
		RDFtype rdf = getParser(xmlStr);
		// printFull(rdf);
		return convertAnnoJson(rdf);
	}

	/**
	 * This method is used to convert the RDF/XML to Array of Annotation JSON.
	 * 
	 * @param RDF Object.
	 * @return String Array of JSON
	 */
	@Override
	public String convertAnnoJson(RDFtype rdf) {

		if (null != rdf.getAnnotation()) {
			JSONObject annoJson = new JSONObject();
			annoJson.put("@context", "http://www.w3.org/ns/anno.jsonld");

			annoJson.put("id", rdf.getAnnotation().getAbout());
			annoJson.put("type", "Annotation");
			annoJson.put("creator", getCreator(rdf.getAnnotation().getCreator()));
			annoJson.put("created", rdf.getAnnotation().getCreated());
			annoJson.put("modified", rdf.getAnnotation().getModified());
			annoJson.put("body", getBody(rdf.getAnnotation().getHasBody()));
			annoJson.put("target", getTarget(rdf.getAnnotation().getHasTarget()));

			return annoJson.toString();
		} else
			return null;
	}

	/**
	 * This method is used to map the RDF/XML target information to the respective JSON object.
	 * @param HasTarget from RDF/XML
	 * @return JSONObject having Target in JSON format
	 */
	@Override
	public JSONObject getTarget(HasTarget hasTarget) {
		JSONObject targetJson = new JSONObject();
		targetJson.put("type", "SpecificResource");
		targetJson.put("source", hasTarget.getCreationProvenance().getHasSource().getResource());
		if (null != hasTarget.getCreationProvenance().getHasSelector())
			targetJson.put("selector", getSelector(hasTarget.getCreationProvenance().getHasSelector()));
		return targetJson;
	}

	/**
	 * This Method is used to get the JSONObject of type of selector it has and its informations.
	 * 
	 * @param HasSelector RDF/XML has selector informations
	 * @return JSONObject JSON of selected target
	 */
	@Override
	public JSONObject getSelector(HasSelector hasSelector) {
		JSONObject selectorJson = new JSONObject();
		selectorJson.put("type", "SvgSelector");
		selectorJson.put("value", "<svg><polygon points=\"" + hasSelector.getSelector().getValue() + "\"/></svg>");
		return selectorJson;
	}

	/**
	 * This method is used to map the Array of body element of Array of JSON representations.
	 *  
	 * @return	JSONArray can have multiple bodies
	 * @param	List<HasBodyType> has multiple bodies in RDF/XML
	 */
	@Override
	public JSONArray getBody(List<HasBodyType> bodyList) {
		JSONArray bodyArray = new JSONArray();
		JSONArray choiceArray = new JSONArray();
		JSONObject choiceObj = new JSONObject();

		for (HasBodyType hasBody : bodyList) {

			if (null != hasBody.getExternalWebResource()) {
				choiceArray = createExternalWebResource(hasBody.getExternalWebResource());
			} else if (null != hasBody.getCreationProvenance() && null != hasBody.getCreationProvenance().getItems()) {
				choiceArray = createCreationProvenance(hasBody.getCreationProvenance());
			} else if (null != hasBody.getCreationProvenance() && null == hasBody.getCreationProvenance().getItems()) {
				bodyArray.put(creationProvenance(hasBody.getCreationProvenance()));
			} else if (null != hasBody.getChoice()) {
				choiceArray = createChoice(hasBody.getChoice());
			} else if (null != hasBody.getResource()) {
				choiceArray = createResource(hasBody.getResource());
			}

		}
		if (0 != choiceArray.length()) {
			choiceObj.put("type", "Choice");
			choiceObj.put("items", choiceArray);
			bodyArray.put(choiceObj);
		}
		return bodyArray;
	}

	/**
	 * This method is used to create body of resource type
	 * 
	 * @return	JSONArray
	 * @param	ResourceBodyType
	 */
	@Override
	public JSONArray createResource(ResourceBodyType resource) {
		JSONArray jsonArray = new JSONArray();

		if (null != resource.getItems()) {
			for (ItemsType externalItem : resource.getItems()) {
				jsonArray.put(checkItemsType(externalItem));
			}

		}
		return jsonArray;
	}

	/**
	 * This method is used to create the JSONArray of type choice.
	 * Its mapping has been defined from each element of RDF/XML to JSONObject/Array
	 * 
	 * @return	JSONArray
	 * @param	ChoiceType
	 */
	@Override
	public JSONArray createChoice(ChoiceType choice) {
		JSONArray jsonArray = new JSONArray();

		if (null != choice.getItems()) {
			for (ItemsType externalItem : choice.getItems()) {
				jsonArray.put(checkItemsType(externalItem));
			}

		}
		return jsonArray;
	}

	/**
	 * This method is used to create the body of type provenance.
	 * 
	 * @param CreationProvenanceType from RDF/XML
	 * @return JSONArray JSONArrya of body element
	 * 
	 */
	@Override
	public JSONArray createCreationProvenance(CreationProvenanceType creationProvenance) {
		JSONArray jsonArray = new JSONArray();

		if (null != creationProvenance.getItems()) {
			for (ItemsType externalItem : creationProvenance.getItems()) {
				jsonArray.put(checkItemsType(externalItem));
			}

		}
		return jsonArray;
	}

	/**
	 * This method is used to create the body of type ExternalWebResource.
	 * 
	 * @param	ExternalWebResourceType the body element
	 * @return	JSONArray of body element 
	 * 
	 */
	@Override
	public JSONArray createExternalWebResource(ExternalWebResourceType externalWebResource) {

		JSONArray jsonArray = new JSONArray();

		if (null != externalWebResource.getItems()) {
			for (ItemsType externalItem : externalWebResource.getItems()) {
				jsonArray.put(checkItemsType(externalItem));
			}

		}
		return jsonArray;
	}

	/**
	 * This method is used to create the body of type ItemsType.
	 * 
	 * @param	ItemsType
	 * @return	JSONObject
	 */
	@Override
	public JSONObject checkItemsType(ItemsType externalItem) {

		if (null != externalItem.getExternalWebResource()) {
			return externalWebResourceType(externalItem.getExternalWebResource());
		} else if (null != externalItem.getEmbeddedContent()) {
			return embeddedContent(externalItem.getEmbeddedContent());
		} else if (null != externalItem.getCreationProvenance()) {
			return creationProvenance(externalItem.getCreationProvenance());
		} else if (null != externalItem.getResource()) {
			return getResourceType(externalItem.getResource());
		}

		return null;
	}

	/**
	 * This method is used to create the body of type ExternalWebResourceType.
	 * 
	 * @param	ExternalWebResourceType
	 * @return	JSONObject
	 */
	@Override
	public JSONObject externalWebResourceType(ExternalWebResourceType externalWebResource) {
		JSONObject bodyitem = new JSONObject();
		bodyitem.put("type", "TextualBody");
		bodyitem.put("title", externalWebResource.getTitle());
		bodyitem.put("value", externalWebResource.getValue());

		if (null != externalWebResource.getFormat())
			bodyitem.put("format", externalWebResource.getFormat());

		if (null != externalWebResource.getUnit())
			bodyitem.put("unit", externalWebResource.getUnit());

		if (null != externalWebResource.getSubject())
			bodyitem.put("subject", externalWebResource.getSubject());

		if (null != externalWebResource.getIdentifier())
			bodyitem.put("identifier", externalWebResource.getIdentifier());

		if (null != externalWebResource.getContributor())
			bodyitem.put("contributor", externalWebResource.getContributor());

		return bodyitem;
	}

	/**
	 * This method is used to create the body of type ResourceBodyType.
	 * 
	 * @param	ResourceBodyType
	 * @return	JSONObject
	 */
	@Override
	public JSONObject getResourceType(ResourceBodyType resource) {
		JSONObject bodyitem = new JSONObject();
		bodyitem.put("type", "TextualBody");
		bodyitem.put("title", resource.getTitle());
		bodyitem.put("value", resource.getValue());

		if (null != resource.getFormat())
			bodyitem.put("format", resource.getFormat());

		if (null != resource.getUnit())
			bodyitem.put("unit", resource.getUnit());

		if (null != resource.getSubject())
			bodyitem.put("subject", resource.getSubject());

		if (null != resource.getIdentifier())
			bodyitem.put("identifier", resource.getIdentifier());

		if (null != resource.getContributor())
			bodyitem.put("contributor", resource.getContributor());

		return bodyitem;
	}

	/**
	 * This method is used to create the body of type EmbeddedContentType.
	 * 
	 * @param	EmbeddedContentType
	 * @return	JSONObject
	 */
	@Override
	public JSONObject embeddedContent(EmbeddedContentType embeddedContent) {
		JSONObject bodyitem = new JSONObject();
		bodyitem.put("type", "TextualBody");
		bodyitem.put("title", embeddedContent.getTitle());
		bodyitem.put("value", embeddedContent.getValue());

		if (null != embeddedContent.getFormat())
			bodyitem.put("format", embeddedContent.getFormat());

		if (null != embeddedContent.getUnit())
			bodyitem.put("unit", embeddedContent.getUnit());

		if (null != embeddedContent.getSubject())
			bodyitem.put("subject", embeddedContent.getSubject());

		if (null != embeddedContent.getIdentifier())
			bodyitem.put("identifier", embeddedContent.getIdentifier());

		if (null != embeddedContent.getContributor())
			bodyitem.put("contributor", embeddedContent.getContributor());

		return bodyitem;
	}

	/**
	 * This method is used to create the body of type CreationProvenanceType.
	 * 
	 * @param	CreationProvenanceType
	 * @return	JSONObject
	 */
	@Override
	public JSONObject creationProvenance(CreationProvenanceType creationProvenanceType) {
		JSONObject bodyitem = new JSONObject();
		bodyitem.put("type", "TextualBody");

		if (null != creationProvenanceType.getTitle())
			bodyitem.put("title", creationProvenanceType.getTitle());

		if (null != creationProvenanceType.getValue())
			bodyitem.put("value", creationProvenanceType.getValue());

		if (null != creationProvenanceType.getUnit())
			bodyitem.put("unit", creationProvenanceType.getUnit());

		if (null != creationProvenanceType.getSubject())
			bodyitem.put("subject", creationProvenanceType.getSubject());

		if (null != creationProvenanceType.getIdentifier())
			bodyitem.put("identifier", creationProvenanceType.getIdentifier());

		if (null != creationProvenanceType.getFormat())
			bodyitem.put("format", creationProvenanceType.getFormat());

		if (null != creationProvenanceType.getImageHeight())
			bodyitem.put("imageHeight", creationProvenanceType.getImageHeight());

		if (null != creationProvenanceType.getImageWidth())
			bodyitem.put("imageWidth", creationProvenanceType.getImageWidth());

		if (null != creationProvenanceType.getContributor())
			bodyitem.put("contributor", creationProvenanceType.getContributor());

		if (null != creationProvenanceType.getCreator()) 
			bodyitem.put("creator", getCreator(creationProvenanceType.getCreator()));
		
		if(null != creationProvenanceType.getHasPurpose())
			bodyitem.put("purpose", creationProvenanceType.getHasPurpose().getResource().toString().split("#")[1]);

		return bodyitem;
	}

	/**
	 * This method is used to build the creator which can be either Software/Person.
	 * 
	 * @param creator
	 * @return JSONObject
	 */
	private JSONObject getCreator(Creator creator) {
		JSONObject creatorJson = new JSONObject();
		if (null != creator.getSoftwareAgent()) {
			if (null != creator.getSoftwareAgent().getAbout())
				creatorJson.put("id", creator.getSoftwareAgent().getAbout());

			creatorJson.put("type", "Software");

			if (null != creator.getSoftwareAgent().getName())
				creatorJson.put("name", creator.getSoftwareAgent().getName());

		} else {
			String queryString = " SELECT ?s ?p ?o " + "WHERE { GRAPH " + idString + " " + "{ ?s ?p ?o. "
					+ " FILTER (?s = <" + creator.getResource() + ">) } } ";

			QueryExecution eachGraphQuery = QueryExecutionFactory.sparqlService(serviceurl, queryString);
			ResultSet results = eachGraphQuery.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				if (soln.get("p").toString().equals("http://xmlns.com/foaf/0.1/name"))
					creatorJson.put("name", soln.get("o"));
				if (soln.get("o").toString().equals("http://www.w3.org/ns/prov/SoftwareAgent"))
					creatorJson.put("type", "Software");
				if (soln.get("o").toString().equals("http://www.w3.org/ns/prov/PersonAgent"))
					creatorJson.put("type", "Person");
			}
		}
		return creatorJson;
	}

	/**
	 * This Method is used to get the creator.
	 * The model is used to retreive the exsiting creator if any else new creator is created.
	 * @param	CreatorType
	 * @return	JSONObject
	 */
	@Override
	public JSONObject getCreator(CreatorType creator) {

		JSONObject creatorJson = new JSONObject();

		if (null != creator.getResource()) {
			String queryString = " SELECT ?s ?p ?o " + "WHERE { GRAPH " + idString + " " + "{ ?s ?p ?o. "
					+ " FILTER (?s = <" + creator.getResource() + ">) } } ";

			QueryExecution eachGraphQuery = QueryExecutionFactory.sparqlService(serviceurl, queryString);
			ResultSet results = eachGraphQuery.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				if (soln.get("p").toString().equals("http://xmlns.com/foaf/0.1/name"))
					creatorJson.put("name", soln.get("o"));
				if (soln.get("o").toString().equals("http://www.w3.org/ns/prov/SoftwareAgent"))
					creatorJson.put("type", "Software");
				if (soln.get("o").toString().equals("http://www.w3.org/ns/prov/PersonAgent"))
					creatorJson.put("type", "Person");
			}

		} else {
			creatorJson.put("id", creator.getSoftwareAgent().getAbout());
			creatorJson.put("type", "Software");
			creatorJson.put("name", creator.getSoftwareAgent().getName());
		}
		return creatorJson;
	}

	/**
	 * This Method is used to unmarshal the RDF/XML using the JAXB and mapped to the pojo classes.
	 * 
	 * @param	String	RDF/XML
	 * @return	RDFtype JAXBObject
	 */
	@Override
	public RDFtype getParser(String xmlStr) {
		Source source = new StreamSource(new StringReader(xmlStr));
		RDFtype rDFtype = null;
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(RDFtype.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<RDFtype> root = jaxbUnmarshaller.unmarshal(source, RDFtype.class);
			rDFtype = root.getValue();
			return rDFtype;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setModel(Model model, String idString, String serviceurl) {
		this.model = model;
		this.idString = idString;
		this.serviceurl = serviceurl;
	}

	public void setModel(Model model, String serviceurl) {
		this.model = model;
		this.serviceurl = serviceurl;
	}
}
