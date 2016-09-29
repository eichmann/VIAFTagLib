package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkWorkExampleInverseType extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkWorkExampleInverseType currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkWorkExampleInverseType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkWorkExampleInverseIterator theCreativeWorkWorkExampleInverseIterator = (CreativeWorkWorkExampleInverseIterator)findAncestorWithClass(this, CreativeWorkWorkExampleInverseIterator.class);
			pageContext.getOut().print(theCreativeWorkWorkExampleInverseIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for workExample tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for workExample tag ");
		}
		return SKIP_BODY;
	}
}

