package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkWorkExample extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkWorkExample currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkWorkExample.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkWorkExampleIterator theCreativeWorkWorkExampleIterator = (CreativeWorkWorkExampleIterator)findAncestorWithClass(this, CreativeWorkWorkExampleIterator.class);
			pageContext.getOut().print(theCreativeWorkWorkExampleIterator.getWorkExample());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for workExample tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for workExample tag ");
		}
		return SKIP_BODY;
	}
}

