package edu.uiowa.slis.VIAFTagLib.Organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class OrganizationAlternateName extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static OrganizationAlternateName currentInstance = null;
	private static final Log log = LogFactory.getLog(OrganizationAlternateName.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			OrganizationAlternateNameIterator theOrganization = (OrganizationAlternateNameIterator)findAncestorWithClass(this, OrganizationAlternateNameIterator.class);
			pageContext.getOut().print(theOrganization.getAlternateName());
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for alternateName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for alternateName tag ");
		}
		return SKIP_BODY;
	}
}

