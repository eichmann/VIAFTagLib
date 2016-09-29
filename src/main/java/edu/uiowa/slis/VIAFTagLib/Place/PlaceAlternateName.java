package edu.uiowa.slis.VIAFTagLib.Place;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PlaceAlternateName extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PlaceAlternateName currentInstance = null;
	private static final Log log = LogFactory.getLog(PlaceAlternateName.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PlaceAlternateNameIterator thePlace = (PlaceAlternateNameIterator)findAncestorWithClass(this, PlaceAlternateNameIterator.class);
			pageContext.getOut().print(thePlace.getAlternateName());
		} catch (Exception e) {
			log.error("Can't find enclosing Place for alternateName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Place for alternateName tag ");
		}
		return SKIP_BODY;
	}
}

