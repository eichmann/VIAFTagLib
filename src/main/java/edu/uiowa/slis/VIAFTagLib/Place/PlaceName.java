package edu.uiowa.slis.VIAFTagLib.Place;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PlaceName extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PlaceName currentInstance = null;
	private static final Log log = LogFactory.getLog(PlaceName.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PlaceNameIterator thePlace = (PlaceNameIterator)findAncestorWithClass(this, PlaceNameIterator.class);
			pageContext.getOut().print(thePlace.getName());
		} catch (Exception e) {
			log.error("Can't find enclosing Place for name tag ", e);
			throw new JspTagException("Error: Can't find enclosing Place for name tag ");
		}
		return SKIP_BODY;
	}
}

