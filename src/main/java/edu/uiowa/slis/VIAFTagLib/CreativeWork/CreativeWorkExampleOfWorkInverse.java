package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkExampleOfWorkInverse extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkExampleOfWorkInverse currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkExampleOfWorkInverse.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkExampleOfWorkInverseIterator theCreativeWorkExampleOfWorkInverseIterator = (CreativeWorkExampleOfWorkInverseIterator)findAncestorWithClass(this, CreativeWorkExampleOfWorkInverseIterator.class);
			pageContext.getOut().print(theCreativeWorkExampleOfWorkInverseIterator.getExampleOfWorkInverse());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for exampleOfWork tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for exampleOfWork tag ");
		}
		return SKIP_BODY;
	}
}

