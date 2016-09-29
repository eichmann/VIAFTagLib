package edu.uiowa.slis.VIAFTagLib.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonBirthDate extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PersonBirthDate currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonBirthDate.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PersonBirthDateIterator thePerson = (PersonBirthDateIterator)findAncestorWithClass(this, PersonBirthDateIterator.class);
			pageContext.getOut().print(thePerson.getBirthDate());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for birthDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for birthDate tag ");
		}
		return SKIP_BODY;
	}
}

