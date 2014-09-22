package org.vaadin.pmatti.v7.togglebutton;

import org.vaadin.pmatti.v7.togglebutton.client.ToggleButtonState;

import com.vaadin.data.Property;
import com.vaadin.ui.CheckBox;

// This is the server-side UI component that provides public API 
// for ToggleButton
public class ToggleButton extends CheckBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8050666306123685852L;

	public ToggleButton() {
		super();
	}

	public ToggleButton(String caption, boolean initialState) {
		super(caption, initialState);
	}

	public ToggleButton(String caption, Property<?> dataSource) {
		super(caption, dataSource);
	}

	public ToggleButton(String caption) {
		super(caption);
	}

	public void setHtmlContentAllowed(boolean htmlAllowed) {
    	getState().htmlContentAllowed = htmlAllowed;
    }
	
	public boolean isHtmlContentAllowed() {
		return getState(false).htmlContentAllowed;
	}

    public void setToggleDisabled(boolean disabled) {
        getState().toggleDisable = disabled;
    }

    public boolean isToggleDisabled() {
        return getState().toggleDisable;
    }

	@Override
	protected ToggleButtonState getState() {
		return (ToggleButtonState) super.getState();
	}
	
	@Override
	protected ToggleButtonState getState(boolean markAsDirty) {
		return (ToggleButtonState) super.getState(markAsDirty);
	}

}
