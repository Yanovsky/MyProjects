package ru.crystals.egais;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Marchallers {

    private static final Map<Class<?>, Unmarshaller> unmarshallers = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Marshaller> marshallers = new ConcurrentHashMap<>();

    public static Unmarshaller getUnmarshaller(Class<?> clazz) throws JAXBException {
        if (!unmarshallers.containsKey(clazz)) {
            JAXBContext context = JAXBContext.newInstance(clazz.getPackage().getName());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshallers.put(clazz, unmarshaller);
        }
        return unmarshallers.get(clazz);
    }

    public static Marshaller getMarshaller(Class<?> clazz) throws JAXBException {
        if (!marshallers.containsKey(clazz)) {
            JAXBContext context = JAXBContext.newInstance(clazz.getPackage().getName());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshallers.put(clazz, marshaller);
        }
        return marshallers.get(clazz);
    }

}
