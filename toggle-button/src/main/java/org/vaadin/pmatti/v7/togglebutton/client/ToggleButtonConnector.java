package org.vaadin.pmatti.v7.togglebutton.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.EventHelper;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.communication.StateChangeEvent.StateChangeHandler;
import com.vaadin.client.ui.AbstractFieldConnector;
import com.vaadin.client.ui.Icon;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.FieldRpc.FocusAndBlurServerRpc;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.Connect.LoadStyle;
import com.vaadin.shared.ui.checkbox.CheckBoxServerRpc;
import org.vaadin.pmatti.v7.togglebutton.ToggleButton;

@Connect(value = ToggleButton.class, loadStyle = LoadStyle.EAGER)
public class ToggleButtonConnector extends AbstractFieldConnector implements
        BlurHandler, FocusHandler, ClickHandler {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2776511668017939086L;
    private HandlerRegistration focusHandlerRegistration;
    private HandlerRegistration blurHandlerRegistration;

    @Override
    public void init() {
        super.init();
        // Code copied&pasted from ButtonConnector
        final VToggleButton widget = getWidget();
        widget.addClickHandler(this);
        widget.client = getConnection();

        widget.client = getConnection();
        widget.id = getConnectorId();
        widget.toggleDisabled = getState().toggleDisable;
        addStateChangeHandler("errorMessage", new StateChangeHandler() {
            /**
			 * 
			 */
            private static final long serialVersionUID = 4478916014015243319L;

            @Override
            public void onStateChanged(StateChangeEvent stateChangeEvent) {
                if (null != getState().errorMessage) {
                    widget.setAriaInvalid(true);
                    if (widget.errorIndicatorElement == null) {
                        widget.errorIndicatorElement = DOM.createSpan();
                        widget.errorIndicatorElement.setInnerHTML("&nbsp;");
                        widget.errorIndicatorElement
                                .setClassName("v-errorindicator");
                    }
                    widget.wrapper.insertBefore(widget.errorIndicatorElement,
                            widget.captionElement);

                } else if (widget.errorIndicatorElement != null) {
                    widget.wrapper.removeChild(widget.errorIndicatorElement);
                    widget.errorIndicatorElement = null;
                }
            }
        });

        addStateChangeHandler("resources", new StateChangeHandler() {
            /**
			 * 
			 */
            private static final long serialVersionUID = 1802325065662265596L;

            @Override
            public void onStateChanged(StateChangeEvent stateChangeEvent) {
                if (getWidget().icon != null) {
                    getWidget().wrapper.removeChild(getWidget().icon.getElement());
                    getWidget().icon = null;
                }
                Icon icon = getIcon();
                if (icon != null) {
                    getWidget().icon = icon;
                    icon.setAlternateText(getState().iconAltText);
                    getWidget().wrapper.insertBefore(icon.getElement(),
                            getWidget().captionElement);
                }
            }
        });
    }

    @Override
    public VToggleButton getWidget() {
        return (VToggleButton) super.getWidget();
    }

    /*
     * @Override public ToggleButtonWidget createWidget() { // return
     * GWT.create(ToggleButtonWidget.class); return (ToggleButtonWidget)
     * super.createWidget(); }
     */

    @Override
    public void onStateChanged(StateChangeEvent e) {
        super.onStateChanged(e);
        final VToggleButton widget = getWidget();

        // update handler registrations
        focusHandlerRegistration = EventHelper.updateFocusHandler(this,
                focusHandlerRegistration);
        blurHandlerRegistration = EventHelper.updateBlurHandler(this,
                blurHandlerRegistration);

        // update the caption
        if (e.hasPropertyChanged("caption")
                || e.hasPropertyChanged("htmlContentAllowed")) {
            // Set text
            if (getState().htmlContentAllowed) {
                widget.setHtml(getState().caption);
            } else {
                widget.setText(getState().caption);
            }
        }

        // update the icon alternative text
        if (widget.icon != null && e.hasPropertyChanged("iconAltText")) {
            widget.icon.setAlternateText(getState().iconAltText);
        }

        // update the click shortcut
        widget.clickShortcut = getState().clickShortcutKeyCode;
        widget.toggleDisabled = getState().toggleDisable;
        //update value from the state
        widget.setValue(getState().checked, false);
        
        
    }

    /**
     * This method is overriden to avoid displaying caption over the button
     * 
     * <p>
     * Contrary to the vaadin documentation
     * {@link ComponentConnector#delegateCaptionHandling()}, this will return
     * false. This is done because Vaadin's button uses caption as the text for
     * it's face
     * 
     * @return always false, so caption is displayed only on button's face
     */
    @Override
    public boolean delegateCaptionHandling() {
        return false;
    }

    @Override
    public ToggleButtonState getState() {
        return (ToggleButtonState) super.getState();
    }

    @Override
    public void onClick(ClickEvent event) {
        if (!isEnabled()) {
            return;
        }
        getWidget().setValue(!getWidget().getValue()); // toggle if enabled
        getState().checked = getWidget().getValue();

        MouseEventDetails details = MouseEventDetailsBuilder
                .buildMouseEventDetails(event.getNativeEvent(), getWidget()
                        .getElement());
        getRpcProxy(CheckBoxServerRpc.class).setChecked(getState().checked,
                details);

    }

    @Override
    public void onFocus(FocusEvent event) {
        getRpcProxy(FocusAndBlurServerRpc.class).focus();
    }

    @Override
    public void onBlur(BlurEvent event) {
        getRpcProxy(FocusAndBlurServerRpc.class).blur();
    }

}
