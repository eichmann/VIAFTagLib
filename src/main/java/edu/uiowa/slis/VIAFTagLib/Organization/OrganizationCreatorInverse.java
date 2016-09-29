package edu.uiowa.slis.VIAFTagLib.Organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class OrganizationCreatorInverse extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static OrganizationCreatorInverse currentInstance = null;
	private static final Log log = LogFactory.getLog(OrganizationCreatorInverse.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			OrganizationCreatorInverseIterator theOrganizationCreatorInverseIterator = (OrganizationCreatorInverseIterator)findAncestorWithClass(this, OrganizationCreatorInverseIterator.class);
			pageContext.getOut().print(theOrganizationCreatorInverseIterator.getCreatorInverse());
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for creator tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for creator tag ");
		}
		return SKIP_BODY;
	}
}

