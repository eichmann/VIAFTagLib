package edu.uiowa.slis.VIAFTagLib.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonDeathDate extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PersonDeathDate currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonDeathDate.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PersonDeathDateIterator thePerson = (PersonDeathDateIterator)findAncestorWithClass(this, PersonDeathDateIterator.class);
			pageContext.getOut().print(thePerson.getDeathDate());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for deathDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for deathDate tag ");
		}
		return SKIP_BODY;
	}
}

