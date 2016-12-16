package edu.uiowa.slis.VIAFTagLib.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonGender extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PersonGender currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonGender.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PersonGenderIterator thePerson = (PersonGenderIterator)findAncestorWithClass(this, PersonGenderIterator.class);
			pageContext.getOut().print(thePerson.getGender());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for gender tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for gender tag ");
		}
		return SKIP_BODY;
	}
}

