package ru.crystals.pos.egais.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DecimalAdapter extends XmlAdapter<String, BigDecimal> {
    private final DecimalFormat format;

    public DecimalAdapter() {
        format = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(' ');
        format.setDecimalFormatSymbols(symbols);
        format.setGroupingUsed(false);
        format.setParseBigDecimal(true);

    }

    @Override
    public String marshal(BigDecimal value) throws Exception {
        return value.compareTo(BigDecimal.ZERO) == 0 ? value.setScale(1).toString() : value.setScale(3).toString();
    }

    @Override
    public BigDecimal unmarshal(String value) throws Exception {
        return (BigDecimal) format.parse(value, new ParsePosition(0));
    }

    public static void main(String[] args) throws Exception {
        DecimalAdapter a = new DecimalAdapter();
        BigDecimal d = a.unmarshal("4");
        System.out.println(d.toString());
        System.out.println(a.marshal(d));
    }


}
