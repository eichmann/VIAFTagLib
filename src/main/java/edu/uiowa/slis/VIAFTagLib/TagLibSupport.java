package edu.uiowa.slis.VIAFTagLib;

import java.util.Hashtable;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.tdb.TDBFactory;

@SuppressWarnings("serial")
public class TagLibSupport extends javax.servlet.jsp.tagext.TagSupport {
	static final boolean useSPARQL = false;
	static String endpoint = "http://marengo.info-science.uiowa.edu:2021/sparql";
	static protected String prefix = 
		"PREFIX ld4l: <http://bib.ld4l.org/ontology/>"
			+ "PREFIX ld4lcornell: <http://draft.ld4l.org/cornell/>"
			+ "PREFIX madsrdf: <http://www.loc.gov/mads/rdf/v1#>"
			+ "PREFIX oa: <http://www.w3.org/ns/oa#>"
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "PREFIX void: <http://rdfs.org/ns/void#>"
			+ "PREFIX worldcat: <http://www.worldcat.org/oclc/>"
			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
	static Hashtable<String, String> classNameHash = new Hashtable<String, String>();
	static Hashtable<String, String> classLocalNameHash = new Hashtable<String, String>();

	static {
		classNameHash.put("http://schema.org/Organization", "Organization");
		classNameHash.put("http://schema.org/Place", "Place");
		classNameHash.put("http://schema.org/Person", "Person");
		classNameHash.put("http://schema.org/CreativeWork", "CreativeWork");

		classLocalNameHash.put("Organization", "http://schema.org/Organization");
		classLocalNameHash.put("Place", "http://schema.org/Place");
		classLocalNameHash.put("Person", "http://schema.org/Person");
		classLocalNameHash.put("CreativeWork", "http://schema.org/CreativeWork");
	}

	Dataset dataset = null;

	public ResultSet getResultSet(String queryString) {
		if (useSPARQL) {
			Query theClassQuery = QueryFactory.create(queryString, Syntax.syntaxARQ);
			QueryExecution theClassExecution = QueryExecutionFactory.sparqlService(endpoint, theClassQuery);
			return theClassExecution.execSelect();
		} else {
			dataset = TDBFactory.createDataset("/Volumes/LD4L/viaf");
			Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
			QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
			return qexec.execSelect();
		}
	}

	public void freeConnection() {
		if (!useSPARQL) {
			//dataset.close();
		}
	}

	public String getLocalName(String classString) {
		if (classString == null) {
			return null;
		}
		return classNameHash.get(classString);
	}

}
