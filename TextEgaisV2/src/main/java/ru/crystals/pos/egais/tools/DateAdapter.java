package ru.crystals.pos.egais.tools;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateAdapter extends XmlAdapter<String, XMLGregorianCalendar> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public DateAdapter() {

	}

	@Override
	public String marshal(XMLGregorianCalendar date) throws Exception {
		return dateFormat.format(date.toGregorianCalendar().getTime());
	}

	@Override
	public XMLGregorianCalendar unmarshal(String date) throws Exception {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(dateFormat.parse(date));
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	}

}
