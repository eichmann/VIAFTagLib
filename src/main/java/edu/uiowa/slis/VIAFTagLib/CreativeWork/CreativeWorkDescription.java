package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkDescription extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkDescription currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkDescription.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkDescriptionIterator theCreativeWork = (CreativeWorkDescriptionIterator)findAncestorWithClass(this, CreativeWorkDescriptionIterator.class);
			pageContext.getOut().print(theCreativeWork.getDescription());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for description tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for description tag ");
		}
		return SKIP_BODY;
	}
}

