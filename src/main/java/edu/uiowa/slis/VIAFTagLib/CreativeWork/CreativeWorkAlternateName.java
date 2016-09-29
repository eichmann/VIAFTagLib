package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkAlternateName extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkAlternateName currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkAlternateName.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkAlternateNameIterator theCreativeWork = (CreativeWorkAlternateNameIterator)findAncestorWithClass(this, CreativeWorkAlternateNameIterator.class);
			pageContext.getOut().print(theCreativeWork.getAlternateName());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for alternateName tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for alternateName tag ");
		}
		return SKIP_BODY;
	}
}

