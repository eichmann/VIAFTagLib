package edu.uiowa.slis.VIAFTagLib.utility;

import java.io.File;
import java.io.IOException;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.tdb.TDBFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

public class Indexer implements Runnable {
    protected static Logger logger = Logger.getLogger(Indexer.class);
    
    static enum modes {WORK, PERSON, ORGANIZATION, PLACE };
    static modes mode = null;
    
    static boolean useSPARQL = false;
    static Dataset mainDataset = null;
    static String tripleStore = null;
    static String endpoint = null;

    static String dataPath = "/usr/local/RAID/";
    static String lucenePath = "/usr/local/RAID/LD4L/lucene/viaf/person";
    static IndexWriter theWriter = null;
    static String prefix = 
	    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
	    + " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
	    + " PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
	    + " PREFIX mads: <http://www.loc.gov/mads/rdf/v1#> "
	    + " PREFIX skos: <http://www.w3.org/2004/02/skos/core#> "
	    + " PREFIX bib: <http://bib.ld4l.org/ontology/> ";
    static ResultSet mainRS = null;
    
    Dataset dataset = null;
    int threadID = 0;
    static int count = 0;
    
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws CorruptIndexException, LockObtainFailedException, IOException, InterruptedException {
	PropertyConfigurator.configure("log4j.info");

	tripleStore = dataPath + args[0];
	endpoint = "http://services.ld4l.org/fuseki/" + args[0] + "/sparql";

	if (args.length > 0 && args[1].equals("work")) {
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "viaf" + "/" + args[1];
	    mode = modes.WORK;
	}
	if (args.length > 0 && args[1].equals("person")) {
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "viaf" + "/" + args[1];
	    mode = modes.PERSON;
	}
	if (args.length > 0 && args[1].equals("organization")) {
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "viaf" + "/" + args[1];
	    mode = modes.ORGANIZATION;
	}
	if (args.length > 0 && args[1].equals("place")) {
	    lucenePath = "/usr/local/RAID/LD4L/lucene/" + "viaf" + "/" + args[1];
	    mode = modes.PLACE;
	}

	theWriter = new IndexWriter(FSDirectory.open(new File(lucenePath)), new StandardAnalyzer(org.apache.lucene.util.Version.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED);

	String queryString = null;
	switch (mode) {
	case WORK:
	    queryString =
		"SELECT ?uri WHERE { "
		+ "?uri rdf:type <http://schema.org/CreativeWork> . "
    		+ "} ";
	    break;
	case PERSON:
	    queryString =
		"SELECT ?uri WHERE { "
		+ "?uri rdf:type <http://schema.org/Person> . "
    		+ "} ";
	    break;
	case ORGANIZATION:
	    queryString =
		"SELECT ?uri WHERE { "
		+ "?uri rdf:type <http://schema.org/Organization> . "
    		+ "} ";
	    break;
	case PLACE:
	    queryString =
		"SELECT ?uri WHERE { "
		+ "?uri rdf:type <http://schema.org/Place> . "
    		+ "} ";
	    break;
	}
	mainDataset = TDBFactory.createDataset(tripleStore);
	mainDataset.begin(ReadWrite.READ);
	Query query = QueryFactory.create(prefix + queryString, Syntax.syntaxARQ);
	QueryExecution qexec = QueryExecutionFactory.create(query, mainDataset);
	mainRS = qexec.execSelect();

	int maxCrawlerThreads = Runtime.getRuntime().availableProcessors();
	Thread[] scannerThreads = new Thread[maxCrawlerThreads];
	for (int i = 0; i < maxCrawlerThreads; i++) {
	    logger.info("starting thread " + i);
	    Thread theThread = new Thread(new Indexer(i));
	    theThread.setPriority(Math.max(theThread.getPriority() - 2, Thread.MIN_PRIORITY));
	    theThread.start();
	    scannerThreads[i] = theThread;
	}
	for (int i = 0; i < maxCrawlerThreads; i++) {
	    scannerThreads[i].join();
	}

	mainDataset.end();
	logger.info("total added: " + count);
	logger.info("optimizing index...");
	theWriter.optimize();
	theWriter.close();
    }
    
    static synchronized String getURI() {
	if (mainRS.hasNext()) {
	    QuerySolution sol = mainRS.nextSolution();
	    return sol.get("?uri").toString();
	} else
	    return null;
    }
    
    public Indexer(int threadID) {
	this.threadID = threadID;
	dataset = TDBFactory.createDataset(tripleStore);
    }
    
    public void run() {
	String URI = null;
	while ((URI = getURI()) != null) {
	    try {
		switch (mode) {
		case WORK:
		indexWorkTitles(URI);
		break;
		case PERSON:
		indexPersons(URI);
		break;
		case ORGANIZATION:
		indexOrganizations(URI);
		break;
		case PLACE:
		indexPlaces(URI);
		break;
		}
	    } catch (CorruptIndexException e) {
		logger.error("error raised: ", e);
	    } catch (IOException e) {
		logger.error("error raised: ", e);
	    }
	}
    }

    void indexWorkTitles(String workURI) throws CorruptIndexException, IOException {
	String titleQuery =
    		"SELECT ?lab WHERE { "
    		+ "OPTIONAL { SELECT ?labelUS  WHERE { <" + workURI + "> <http://schema.org/name> ?labelUS  FILTER (lang(?labelUS) = \"en-US\")}    LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?labelENG WHERE { <" + workURI + "> <http://schema.org/name> ?labelENG FILTER (langMatches(?labelENG,\"en\"))} LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?label    WHERE { <" + workURI + "> <http://schema.org/name> ?label    FILTER (lang(?label) = \"\")}           LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?labelANY WHERE { <" + workURI + "> <http://schema.org/name> ?labelANY FILTER (lang(?labelANY) != \"\")}       LIMIT 1 } "
    		+ "BIND(COALESCE(?labelUS, ?labelENG, ?label, ?labelANY ) as ?lab) "
    		+ "} ";
	dataset.begin(ReadWrite.READ);
	Query query = QueryFactory.create(prefix + titleQuery, Syntax.syntaxARQ);
	QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
	ResultSet rs = qexec.execSelect();
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    if (sol.get("?lab") == null)
		continue;
	    String title = sol.get("?lab").toString();
	    logger.debug("[" + threadID + "] " + "work: " + workURI + "\ttitle: " + title);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", workURI, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", title, Field.Store.NO, Field.Index.ANALYZED));
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("[" + threadID + "] " + "count: " + count);
	}
    	dataset.end();
    }
    
    void indexOrganizations(String orgURI) throws CorruptIndexException, IOException {
	String orgQuery =
    		"SELECT ?lab WHERE { "
    		+ "OPTIONAL { SELECT ?labelUS  WHERE { <" + orgURI + "> skos:prefLabel ?labelUS  FILTER (lang(?labelUS) = \"en-US\")}       LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?labelENG WHERE { <" + orgURI + "> skos:prefLabel ?labelENG FILTER (langMatches(?labelENG,\"en\"))} LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?label    WHERE { <" + orgURI + "> skos:prefLabel ?label    FILTER (lang(?label) = \"\")}           LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?labelANY WHERE { <" + orgURI + "> skos:prefLabel ?labelANY FILTER (lang(?labelANY) != \"\")}       LIMIT 1 } "
    		+ "BIND(COALESCE(?labelUS, ?labelENG, ?label, ?labelANY ) as ?lab) "
    		+ "}";
	dataset.begin(ReadWrite.READ);
	Query query = QueryFactory.create(prefix + orgQuery, Syntax.syntaxARQ);
	QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
	ResultSet rs = qexec.execSelect();
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    if (sol.get("?lab") == null)
		continue;
	    String title = sol.get("?lab").toString();
	    logger.debug("[" + threadID + "] " + "organization: " + orgURI + "\ttitle: " + title);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", orgURI, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", title, Field.Store.NO, Field.Index.ANALYZED));
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("[" + threadID + "] " + "count: " + count);
	}
    	dataset.end();
    }
    
    void indexPlaces(String placeURI) throws CorruptIndexException, IOException {
	String placeQuery =
    		"SELECT ?lab WHERE { "
    		+ "OPTIONAL { SELECT ?labelUS  WHERE { <" + placeURI + "> skos:prefLabel ?labelUS  FILTER (lang(?labelUS) = \"en-US\")}       LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?labelENG WHERE { <" + placeURI + "> skos:prefLabel ?labelENG FILTER (langMatches(?labelENG,\"en\"))} LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?label    WHERE { <" + placeURI + "> skos:prefLabel ?label    FILTER (lang(?label) = \"\")}           LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?labelANY WHERE { <" + placeURI + "> skos:prefLabel ?labelANY FILTER (lang(?labelANY) != \"\")}       LIMIT 1 } "
    		+ "BIND(COALESCE(?labelUS, ?labelENG, ?label, ?labelANY ) as ?lab) "
    		+ "}";
	dataset.begin(ReadWrite.READ);
	Query query = QueryFactory.create(prefix + placeQuery, Syntax.syntaxARQ);
	QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
	ResultSet rs = qexec.execSelect();
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    if (sol.get("?lab") == null)
		continue;
	    String title = sol.get("?lab").toString();
	    logger.debug("[" + threadID + "] " + "place: " + placeURI + "\ttitle: " + title);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", placeURI, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", title, Field.Store.NO, Field.Index.ANALYZED));
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("[" + threadID + "] " + "count: " + count);
	}
    	dataset.end();
    }
    
    void indexPersons(String personURI) throws CorruptIndexException, IOException {
   	String nameQuery =
    		"SELECT ?lab WHERE { "
    		+ "OPTIONAL { SELECT ?labelUS  WHERE { <" + personURI + "> skos:prefLabel ?labelUS  FILTER (lang(?labelUS) = \"en-US\")}       LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?labelENG WHERE { <" + personURI + "> skos:prefLabel ?labelENG FILTER (langMatches(?labelENG,\"en\"))} LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?label    WHERE { <" + personURI + "> skos:prefLabel ?label    FILTER (lang(?label) = \"\")}           LIMIT 1 } "
    		+ "OPTIONAL { SELECT ?labelANY WHERE { <" + personURI + "> skos:prefLabel ?labelANY FILTER (lang(?labelANY) != \"\")}       LIMIT 1 } "
    		+ "BIND(COALESCE(?labelUS, ?labelENG, ?label, ?labelANY ) as ?lab) "
        		+ "} ";
	dataset.begin(ReadWrite.READ);
	Query query = QueryFactory.create(prefix + nameQuery, Syntax.syntaxARQ);
	QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
	ResultSet rs = qexec.execSelect();
    	while (rs.hasNext()) {
    	    QuerySolution sol = rs.nextSolution();
    	    String name = sol.get("?lab") == null ? null : sol.get("?lab").asLiteral().getString();
    	    logger.debug("[" + threadID + "] " + "uri: " + personURI+ "\tlab: " + sol.get("?lab"));
    	    
    	    if (name == null)
    		continue;
    
    	    Document theDocument = new Document();
    	    theDocument.add(new Field("uri", personURI, Field.Store.YES, Field.Index.NOT_ANALYZED));
    	    theDocument.add(new Field("name", name, Field.Store.YES, Field.Index.NOT_ANALYZED));
    	    theDocument.add(new Field("content", name, Field.Store.NO, Field.Index.ANALYZED));
    	    theWriter.addDocument(theDocument);
    	    count++;
    	    if (count % 10000 == 0)
    		logger.info("[" + threadID + "] " + "count: " + count);
    	}
    	dataset.end();
    }

    static public ResultSet getResultSet(String queryString) {
	if (useSPARQL) {
	    Query theClassQuery = QueryFactory.create(queryString, Syntax.syntaxARQ);
	    QueryExecution theClassExecution = QueryExecutionFactory.sparqlService(endpoint, theClassQuery);
	    return theClassExecution.execSelect();
	} else {
	    mainDataset = TDBFactory.createDataset(tripleStore);
	    Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
	    QueryExecution qexec = QueryExecutionFactory.create(query, mainDataset);
	    return qexec.execSelect();
	}
    }
}
