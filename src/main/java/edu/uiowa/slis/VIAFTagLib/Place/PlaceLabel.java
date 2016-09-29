package edu.uiowa.slis.VIAFTagLib.Place;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PlaceLabel extends edu.uiowa.slis.VIAFTagLib.TagLibSupport {
	static PlaceLabel currentInstance = null;
	private static final Log log = LogFactory.getLog(PlaceLabel.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			Place thePlace = (Place)findAncestorWithClass(this, Place.class);
			if (!thePlace.commitNeeded) {
				pageContext.getOut().print(thePlace.getLabel());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Place for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Place for label tag ");
		}
		return SKIP_BODY;
	}

	public String getLabel() throws JspTagException {
		try {
			Place thePlace = (Place)findAncestorWithClass(this, Place.class);
			return thePlace.getLabel();
		} catch (Exception e) {
			log.error(" Can't find enclosing Place for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Place for label tag ");
		}
	}

	public void setLabel(String label) throws JspTagException {
		try {
			Place thePlace = (Place)findAncestorWithClass(this, Place.class);
			thePlace.setLabel(label);
		} catch (Exception e) {
			log.error("Can't find enclosing Place for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Place for label tag ");
		}
	}
}

