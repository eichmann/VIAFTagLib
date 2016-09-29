package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkCreator extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkCreator currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkCreator.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkCreatorIterator theCreativeWorkCreatorIterator = (CreativeWorkCreatorIterator)findAncestorWithClass(this, CreativeWorkCreatorIterator.class);
			pageContext.getOut().print(theCreativeWorkCreatorIterator.getCreator());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for creator tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for creator tag ");
		}
		return SKIP_BODY;
	}
}

