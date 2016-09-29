package edu.uiowa.slis.VIAFTagLib.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonCreatorInverse extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PersonCreatorInverse currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonCreatorInverse.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			PersonCreatorInverseIterator thePersonCreatorInverseIterator = (PersonCreatorInverseIterator)findAncestorWithClass(this, PersonCreatorInverseIterator.class);
			pageContext.getOut().print(thePersonCreatorInverseIterator.getCreatorInverse());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for creator tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for creator tag ");
		}
		return SKIP_BODY;
	}
}

