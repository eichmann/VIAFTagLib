package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkSameAs extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkSameAs currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkSameAs.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkSameAsIterator theCreativeWork = (CreativeWorkSameAsIterator)findAncestorWithClass(this, CreativeWorkSameAsIterator.class);
			pageContext.getOut().print(theCreativeWork.getSameAs());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for sameAs tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for sameAs tag ");
		}
		return SKIP_BODY;
	}
}

