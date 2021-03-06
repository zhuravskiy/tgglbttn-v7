package org.vaadin.pmatti.v7.togglebutton.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.pmatti.v7.togglebutton.ToggleButton;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("ToggleButton Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.pmatti.v7.togglebutton.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

        final ToggleButton button = new ToggleButton(
                "I am toggle button. <i>Click me</i>");
        
        button.setHtmlContentAllowed(true);
        button.setRequired(true);
        layout.addComponent(button);

        button.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                Notification.show("Value changed to " + button.getValue());
            }
        });

        final CheckBox changeState = new CheckBox(
                "Click here to change value on the toggle button");

        changeState.addValueChangeListener(new ValueChangeListener() {
            
            @Override
            public void valueChange(ValueChangeEvent event) {
                button.setValue(changeState.getValue());
            }
        });
        layout.addComponent(changeState);

    }

}
