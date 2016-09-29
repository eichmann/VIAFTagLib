package edu.uiowa.slis.VIAFTagLib.Place;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PlaceDescription extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PlaceDescription currentInstance = null;
	private static final Log log = LogFactory.getLog(PlaceDescription.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PlaceDescriptionIterator thePlace = (PlaceDescriptionIterator)findAncestorWithClass(this, PlaceDescriptionIterator.class);
			pageContext.getOut().print(thePlace.getDescription());
		} catch (Exception e) {
			log.error("Can't find enclosing Place for description tag ", e);
			throw new JspTagException("Error: Can't find enclosing Place for description tag ");
		}
		return SKIP_BODY;
	}
}

