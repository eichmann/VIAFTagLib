package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkInLanguage extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkInLanguage currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkInLanguage.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkInLanguageIterator theCreativeWork = (CreativeWorkInLanguageIterator)findAncestorWithClass(this, CreativeWorkInLanguageIterator.class);
			pageContext.getOut().print(theCreativeWork.getInLanguage());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for inLanguage tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for inLanguage tag ");
		}
		return SKIP_BODY;
	}
}

