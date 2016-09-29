package edu.uiowa.slis.VIAFTagLib.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonName extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PersonName currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonName.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PersonNameIterator thePerson = (PersonNameIterator)findAncestorWithClass(this, PersonNameIterator.class);
			pageContext.getOut().print(thePerson.getName());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for name tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for name tag ");
		}
		return SKIP_BODY;
	}
}

