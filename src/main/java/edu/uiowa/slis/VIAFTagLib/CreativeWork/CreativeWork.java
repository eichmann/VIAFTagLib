package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

@SuppressWarnings("serial")
public class CreativeWork extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWork currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWork.class);

	// 'standard' properties

	String subjectURI = null;
	String label = null;
	boolean commitNeeded = false;

	// functional datatype properties, both local and inherited


	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			CreativeWorkIterator theCreativeWorkIterator = (CreativeWorkIterator) findAncestorWithClass(this, CreativeWorkIterator.class);

			if (theCreativeWorkIterator != null) {
				subjectURI = theCreativeWorkIterator.getSubjectURI();
				label = theCreativeWorkIterator.getLabel();
			}

			if (this.getParent() instanceof edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkWorkExampleIterator) {
				subjectURI = ((edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkWorkExampleIterator)this.getParent()).getWorkExample();
			}

			if (this.getParent() instanceof edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkExampleOfWorkIterator) {
				subjectURI = ((edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkExampleOfWorkIterator)this.getParent()).getExampleOfWork();
			}

			if (this.getParent() instanceof edu.uiowa.slis.VIAFTagLib.Organization.OrganizationAuthorInverseIterator) {
				subjectURI = ((edu.uiowa.slis.VIAFTagLib.Organization.OrganizationAuthorInverseIterator)this.getParent()).getAuthorInverse();
			}

			if (this.getParent() instanceof edu.uiowa.slis.VIAFTagLib.Person.PersonAuthorInverseIterator) {
				subjectURI = ((edu.uiowa.slis.VIAFTagLib.Person.PersonAuthorInverseIterator)this.getParent()).getAuthorInverse();
			}

			if (this.getParent() instanceof edu.uiowa.slis.VIAFTagLib.Organization.OrganizationCreatorInverseIterator) {
				subjectURI = ((edu.uiowa.slis.VIAFTagLib.Organization.OrganizationCreatorInverseIterator)this.getParent()).getCreatorInverse();
			}

			if (this.getParent() instanceof edu.uiowa.slis.VIAFTagLib.Person.PersonCreatorInverseIterator) {
				subjectURI = ((edu.uiowa.slis.VIAFTagLib.Person.PersonCreatorInverseIterator)this.getParent()).getCreatorInverse();
			}

			edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkWorkExampleIterator theCreativeWorkWorkExampleIterator = (edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkWorkExampleIterator) findAncestorWithClass(this, edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkWorkExampleIterator.class);

			if (subjectURI == null && theCreativeWorkWorkExampleIterator != null) {
				subjectURI = theCreativeWorkWorkExampleIterator.getWorkExample();
			}

			edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkExampleOfWorkIterator theCreativeWorkExampleOfWorkIterator = (edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkExampleOfWorkIterator) findAncestorWithClass(this, edu.uiowa.slis.VIAFTagLib.CreativeWork.CreativeWorkExampleOfWorkIterator.class);

			if (subjectURI == null && theCreativeWorkExampleOfWorkIterator != null) {
				subjectURI = theCreativeWorkExampleOfWorkIterator.getExampleOfWork();
			}

			if (theCreativeWorkIterator == null && subjectURI == null) {
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
			log.error("Exception raised in CreativeWork doStartTag", e);
			throw new JspTagException("Exception raised in CreativeWork doStartTag");
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
			log.error("Exception raised in CreativeWork doEndTag", e);
			throw new JspTagException("Exception raised in CreativeWork doEndTag");
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
