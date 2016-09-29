package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkAuthorType extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkAuthorType currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkAuthorType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkAuthorIterator theCreativeWorkAuthorIterator = (CreativeWorkAuthorIterator)findAncestorWithClass(this, CreativeWorkAuthorIterator.class);
			pageContext.getOut().print(theCreativeWorkAuthorIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for author tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for author tag ");
		}
		return SKIP_BODY;
	}
}

