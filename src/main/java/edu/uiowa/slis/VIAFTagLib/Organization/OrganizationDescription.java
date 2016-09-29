package edu.uiowa.slis.VIAFTagLib.Organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class OrganizationDescription extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static OrganizationDescription currentInstance = null;
	private static final Log log = LogFactory.getLog(OrganizationDescription.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			OrganizationDescriptionIterator theOrganization = (OrganizationDescriptionIterator)findAncestorWithClass(this, OrganizationDescriptionIterator.class);
			pageContext.getOut().print(theOrganization.getDescription());
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for description tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for description tag ");
		}
		return SKIP_BODY;
	}
}

