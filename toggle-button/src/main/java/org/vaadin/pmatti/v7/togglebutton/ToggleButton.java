package org.vaadin.pmatti.v7.togglebutton;

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

}
