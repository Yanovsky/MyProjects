package ru.alex.phonebook.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ezvcard.parameter.TelephoneType;
import ezvcard.property.Telephone;

public class VCardUtils {
    public interface IPredicate<T> {
        boolean apply(T type);
    }

    private static Comparator<? super Telephone> numberSorter = new Comparator<Telephone>() {
        @Override
        public int compare(Telephone telephone1, Telephone telephone2) {
            return telephone1.getTypes().contains(TelephoneType.PREF) ? -1 : 1;
        }
    };

    public static List<Telephone> sortTelephones(List<Telephone> numbers) {
        List<Telephone> result = new ArrayList<Telephone>(numbers);
        Collections.sort(result, numberSorter);
        return result;
    }

    public static <T> Collection<T> filter(Collection<T> target, IPredicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element : target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

}
