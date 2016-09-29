package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkExampleOfWorkInverseType extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkExampleOfWorkInverseType currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkExampleOfWorkInverseType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkExampleOfWorkInverseIterator theCreativeWorkExampleOfWorkInverseIterator = (CreativeWorkExampleOfWorkInverseIterator)findAncestorWithClass(this, CreativeWorkExampleOfWorkInverseIterator.class);
			pageContext.getOut().print(theCreativeWorkExampleOfWorkInverseIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for exampleOfWork tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for exampleOfWork tag ");
		}
		return SKIP_BODY;
	}
}

