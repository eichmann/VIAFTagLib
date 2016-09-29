package edu.uiowa.slis.VIAFTagLib.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonGivenName extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PersonGivenName currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonGivenName.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PersonGivenNameIterator thePerson = (PersonGivenNameIterator)findAncestorWithClass(this, PersonGivenNameIterator.class);
			pageContext.getOut().print(thePerson.getGivenName());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for givenName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for givenName tag ");
		}
		return SKIP_BODY;
	}
}

