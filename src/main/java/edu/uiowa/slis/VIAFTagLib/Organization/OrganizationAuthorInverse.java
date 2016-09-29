package edu.uiowa.slis.VIAFTagLib.Organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class OrganizationAuthorInverse extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static OrganizationAuthorInverse currentInstance = null;
	private static final Log log = LogFactory.getLog(OrganizationAuthorInverse.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			OrganizationAuthorInverseIterator theOrganizationAuthorInverseIterator = (OrganizationAuthorInverseIterator)findAncestorWithClass(this, OrganizationAuthorInverseIterator.class);
			pageContext.getOut().print(theOrganizationAuthorInverseIterator.getAuthorInverse());
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for author tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for author tag ");
		}
		return SKIP_BODY;
	}
}

