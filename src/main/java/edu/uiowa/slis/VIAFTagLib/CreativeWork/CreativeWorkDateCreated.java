package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkDateCreated extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkDateCreated currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkDateCreated.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkDateCreatedIterator theCreativeWork = (CreativeWorkDateCreatedIterator)findAncestorWithClass(this, CreativeWorkDateCreatedIterator.class);
			pageContext.getOut().print(theCreativeWork.getDateCreated());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for dateCreated tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for dateCreated tag ");
		}
		return SKIP_BODY;
	}
}

