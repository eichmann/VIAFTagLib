package edu.uiowa.slis.VIAFTagLib.Organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

@SuppressWarnings("serial")
public class Organization extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static Organization currentInstance = null;
	private static final Log log = LogFactory.getLog(Organization.class);

	// 'standard' properties

	String subjectURI = null;
	String label = null;
	boolean commitNeeded = false;

	// functional datatype properties, both local and inherited


	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			OrganizationIterator theOrganizationIterator = (OrganizationIterator) findAncestorWithClass(this, OrganizationIterator.class);

			if (theOrganizationIterator != null) {
				subjectURI = theOrganizationIterator.getSubjectURI();
				label = theOrganizationIterator.getLabel();
			}

			if (this.getParent() instanceof edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkAuthorIterator) {
				subjectURI = ((edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkAuthorIterator)this.getParent()).getAuthor();
			}

			if (this.getParent() instanceof edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkCreatorIterator) {
				subjectURI = ((edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkCreatorIterator)this.getParent()).getCreator();
			}

			edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkAuthorIterator theCreativeWorkAuthorIterator = (edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkAuthorIterator) findAncestorWithClass(this, edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkAuthorIterator.class);

			if (subjectURI == null && theCreativeWorkAuthorIterator != null) {
				subjectURI = theCreativeWorkAuthorIterator.getAuthor();
			}

			edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkCreatorIterator theCreativeWorkCreatorIterator = (edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkCreatorIterator) findAncestorWithClass(this, edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkCreatorIterator.class);

			if (subjectURI == null && theCreativeWorkCreatorIterator != null) {
				subjectURI = theCreativeWorkCreatorIterator.getCreator();
			}

			if (theOrganizationIterator == null && subjectURI == null) {
				throw new JspException("subject URI generation currently not supported");
			} else {
				ResultSet rs = getResultSet(prefix
				+ " SELECT ?label ?foafName ?schemaName ?rdfValue  where {"
				+ "  OPTIONAL { <" + subjectURI + "> rdfs:label ?label } "
				+ "  OPTIONAL { <" + subjectURI + "> <http://xmlns.com/foaf/0.1/name> ?foafName } "
				+ "  OPTIONAL { <" + subjectURI + "> <http://schema.org/name> ?schemaName } "
				+ "  OPTIONAL { <" + subjectURI + "> <http://www.w3.org/1999/02/22-rdf-syntax-ns#value> ?rdfValue } "
				+ "}");
				while(rs.hasNext()) {
					QuerySolution sol = rs.nextSolution();
					label = sol.get("?label") == null ? null : sol.get("?label").asLiteral().getString();
					if (label == null)
						label = sol.get("?foafName") == null ? null : sol.get("?foafName").asLiteral().getString();
					if (label == null)
						label = sol.get("?schemaName") == null ? null : sol.get("?schemaName").asLiteral().getString();
					if (label == null)
						label = sol.get("?rdfValue") == null ? null : sol.get("?rdfValue").asLiteral().getString();
				}
			}
		} catch (Exception e) {
			log.error("Exception raised in Organization doStartTag", e);
			throw new JspTagException("Exception raised in Organization doStartTag");
		} finally {
			freeConnection();
		}

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			// do processing
		} catch (Exception e) {
			log.error("Exception raised in Organization doEndTag", e);
			throw new JspTagException("Exception raised in Organization doEndTag");
		} finally {
			clearServiceState();
			freeConnection();
		}

		return super.doEndTag();
	}

	private void clearServiceState() {
		subjectURI = null;
	}

	public void setSubjectURI(String subjectURI) {
		this.subjectURI = subjectURI;
	}

	public String getSubjectURI() {
		return subjectURI;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
