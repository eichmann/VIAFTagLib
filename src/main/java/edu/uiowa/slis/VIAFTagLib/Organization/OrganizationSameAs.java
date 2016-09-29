package edu.uiowa.slis.VIAFTagLib.Organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class OrganizationSameAs extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static OrganizationSameAs currentInstance = null;
	private static final Log log = LogFactory.getLog(OrganizationSameAs.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			OrganizationSameAsIterator theOrganization = (OrganizationSameAsIterator)findAncestorWithClass(this, OrganizationSameAsIterator.class);
			pageContext.getOut().print(theOrganization.getSameAs());
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for sameAs tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for sameAs tag ");
		}
		return SKIP_BODY;
	}
}

