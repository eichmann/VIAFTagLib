package edu.uiowa.slis.VIAFTagLib.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonSameAs extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PersonSameAs currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonSameAs.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PersonSameAsIterator thePerson = (PersonSameAsIterator)findAncestorWithClass(this, PersonSameAsIterator.class);
			pageContext.getOut().print(thePerson.getSameAs());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for sameAs tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for sameAs tag ");
		}
		return SKIP_BODY;
	}
}

