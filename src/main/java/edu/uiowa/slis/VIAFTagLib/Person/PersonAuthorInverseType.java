package edu.uiowa.slis.VIAFTagLib.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonAuthorInverseType extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PersonAuthorInverseType currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonAuthorInverseType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			PersonAuthorInverseIterator thePersonAuthorInverseIterator = (PersonAuthorInverseIterator)findAncestorWithClass(this, PersonAuthorInverseIterator.class);
			pageContext.getOut().print(thePersonAuthorInverseIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for author tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for author tag ");
		}
		return SKIP_BODY;
	}
}

