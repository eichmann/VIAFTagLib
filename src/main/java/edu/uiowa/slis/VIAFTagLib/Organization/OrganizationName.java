package edu.uiowa.slis.VIAFTagLib.Organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class OrganizationName extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static OrganizationName currentInstance = null;
	private static final Log log = LogFactory.getLog(OrganizationName.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			OrganizationNameIterator theOrganization = (OrganizationNameIterator)findAncestorWithClass(this, OrganizationNameIterator.class);
			pageContext.getOut().print(theOrganization.getName());
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for name tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for name tag ");
		}
		return SKIP_BODY;
	}
}

