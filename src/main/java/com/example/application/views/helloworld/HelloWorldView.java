package com.example.application.views.helloworld;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

import com.vaadin.flow.component.textfield.TextArea;

import java.time.LocalDate;
import java.util.Arrays;

import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "hello", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Hello World")
public class HelloWorldView extends VerticalLayout {

    private TextField name;
    private Button sayHello;
    private HorizontalLayout horizontalLayout = new HorizontalLayout();
    public HelloWorldView() {
        addClassName("hello-world-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name,sayHello);
        horizontalLayout.setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        add(horizontalLayout);
        add(new TextField("Text Field"));
        add(new TextArea("TExt Area"));
        
        add(new Checkbox("Check Book",true));
        add(new ComboBox<String>("Combo Box", Arrays.asList("Uno","Dos","Tres","Cuatro","5")));
        add(new DatePicker("DatePicker", LocalDate.now()));
        add(new DateTimePicker("DateTimePicker"));
        add(new EmailField("EmailField"));
        
        ListBox<String> lista = new  ListBox<String>();
        lista.setItems(Arrays.asList("Uno","Dos","Tres","Cuatro","5"));
        add(lista);
        

        MultiSelectListBox<String> lista2 = new  MultiSelectListBox();
        lista2.setItems(Arrays.asList("Uno","Dos","Tres","Cuatro","5"));
        add(lista2);
        
        
        
        
        add(new EmailField("EmailField"));
        
        Select<String> labelSelect = new Select<>();
        labelSelect.setItems("Option one", "Option two");
        labelSelect.setLabel("Label");

        Select<String> placeholderSelect = new Select<>();
        placeholderSelect.setItems("Option one", "Option two");
        placeholderSelect.setPlaceholder("Placeholder");

        Select<String> valueSelect = new Select<>();
        valueSelect.setItems("Value", "Option one", "Option two");
        valueSelect.setValue("Value");

        add(labelSelect, placeholderSelect, valueSelect);
    }

}
