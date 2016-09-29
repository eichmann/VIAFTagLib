package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkName extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkName currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkName.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkNameIterator theCreativeWork = (CreativeWorkNameIterator)findAncestorWithClass(this, CreativeWorkNameIterator.class);
			pageContext.getOut().print(theCreativeWork.getName());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for name tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for name tag ");
		}
		return SKIP_BODY;
	}
}

