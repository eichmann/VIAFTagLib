package edu.uiowa.slis.VIAFTagLib.Place;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PlaceSameAs extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PlaceSameAs currentInstance = null;
	private static final Log log = LogFactory.getLog(PlaceSameAs.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PlaceSameAsIterator thePlace = (PlaceSameAsIterator)findAncestorWithClass(this, PlaceSameAsIterator.class);
			pageContext.getOut().print(thePlace.getSameAs());
		} catch (Exception e) {
			log.error("Can't find enclosing Place for sameAs tag ", e);
			throw new JspTagException("Error: Can't find enclosing Place for sameAs tag ");
		}
		return SKIP_BODY;
	}
}

