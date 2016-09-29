package edu.uiowa.slis.VIAFTagLib.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkExampleOfWork extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static CreativeWorkExampleOfWork currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkExampleOfWork.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			CreativeWorkExampleOfWorkIterator theCreativeWorkExampleOfWorkIterator = (CreativeWorkExampleOfWorkIterator)findAncestorWithClass(this, CreativeWorkExampleOfWorkIterator.class);
			pageContext.getOut().print(theCreativeWorkExampleOfWorkIterator.getExampleOfWork());
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for exampleOfWork tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for exampleOfWork tag ");
		}
		return SKIP_BODY;
	}
}

