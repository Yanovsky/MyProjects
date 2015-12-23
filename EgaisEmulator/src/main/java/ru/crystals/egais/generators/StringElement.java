package ru.crystals.egais.generators;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class StringElement extends JAXBElement<String> {
    private static final long serialVersionUID = 1L;

    public StringElement(String value) {
        super(new QName("http://www.example.org/schema", "foo"), String.class, value);
    }

}
