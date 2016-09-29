package edu.uiowa.slis.VIAFTagLib.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonDescription extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PersonDescription currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonDescription.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PersonDescriptionIterator thePerson = (PersonDescriptionIterator)findAncestorWithClass(this, PersonDescriptionIterator.class);
			pageContext.getOut().print(thePerson.getDescription());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for description tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for description tag ");
		}
		return SKIP_BODY;
	}
}

