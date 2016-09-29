package edu.uiowa.slis.VIAFTagLib.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonAlternateName extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PersonAlternateName currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonAlternateName.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PersonAlternateNameIterator thePerson = (PersonAlternateNameIterator)findAncestorWithClass(this, PersonAlternateNameIterator.class);
			pageContext.getOut().print(thePerson.getAlternateName());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for alternateName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for alternateName tag ");
		}
		return SKIP_BODY;
	}
}

