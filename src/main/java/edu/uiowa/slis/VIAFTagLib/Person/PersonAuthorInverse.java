package edu.uiowa.slis.VIAFTagLib.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonAuthorInverse extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PersonAuthorInverse currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonAuthorInverse.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			PersonAuthorInverseIterator thePersonAuthorInverseIterator = (PersonAuthorInverseIterator)findAncestorWithClass(this, PersonAuthorInverseIterator.class);
			pageContext.getOut().print(thePersonAuthorInverseIterator.getAuthorInverse());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for author tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for author tag ");
		}
		return SKIP_BODY;
	}
}

