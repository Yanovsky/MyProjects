package ru.crystals.egais;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import ru.crystals.egais.cheque.Bottle;
import ru.crystals.egais.cheque.Cheque;
import ru.crystals.egais.cheque.Nopdf;
import ru.crystals.egais.exceptions.EgaisException;

public class Validator {
    private static final Pattern EAN_BARCODE_PATTERN = Pattern.compile("^\\d{8}|\\d{13}|\\d{12}|\\d{14}$");
    private static final Pattern EXCISE_BARCODE_PATTERN = Pattern.compile("^\\d\\d\\w{21}\\d[0-1]\\d[0-3]\\d{10}\\w{31}$");

    public static void validatePurchase(Cheque purchase) throws EgaisException {
        if (Validator.isINNValid(purchase.getInn())) {
            throw new EgaisException("Не заполнен или неверный ИНН");
        }
        if (Validator.isValidKPP(purchase.getKpp())) {
            throw new EgaisException("Не заполнен или неверный КПП");
        }
        if (StringUtils.isBlank(purchase.getKassa())) {
            throw new EgaisException("Не заполнен заводской номер кассы");
        }
        if (StringUtils.isBlank(purchase.getAddress())) {
            throw new EgaisException("Не заполнен адрес");
        }
        if (StringUtils.isBlank(purchase.getName())) {
            throw new EgaisException("Не заполнено наименование организации");
        }
        if (purchase.getBottle().isEmpty() && purchase.getNopdf().isEmpty()) {
            throw new EgaisException("Отсутствуют позиции чека");
        }
        if (!purchase.getBottle().isEmpty() && !purchase.getNopdf().isEmpty()) {
            throw new EgaisException("Недопустимый состав чека. В чеке не могут одновременно находиться и акцизные и неакцизные товары");
        }

        if (purchase.getBottle().size() > 0) {
            validateBottles(purchase.getBottle());
        }
        if (purchase.getNopdf().size() > 0) {
            validateBeer(purchase.getNopdf());
        }
    }

    private static void validateBottles(List<Bottle> bottle) throws EgaisException {
        AtomicReference<EgaisException> ex = new AtomicReference<EgaisException>(null);
        bottle.stream().filter(bootle -> !validateEAN(bootle.getEan())).findAny().ifPresent(b -> ex.set(new EgaisException("Неверный штрих-код")));
        if (ex.get()!= null)
            throw ex.get();

        bottle.stream().filter(bootle -> !validateExcise(bootle.getBarcode())).findAny().ifPresent(b -> ex.set(new EgaisException("Неверная акцизная марка")));
        if (ex.get() != null)
            throw ex.get();

        bottle.stream().filter(bootle -> !NumberUtils.isParsable(bootle.getPrice())).findAny().ifPresent(b -> ex.set(new EgaisException("Неверная цена")));
        if (ex.get() != null)
            throw ex.get();

        bottle.stream().filter(bootle -> !NumberUtils.isParsable(bootle.getVolume())).findAny().ifPresent(b -> ex.set(new EgaisException("Неверное объем")));
        if (ex.get() != null)
            throw ex.get();
    }

    private static boolean validateEAN(String barcode) {
        return EAN_BARCODE_PATTERN.matcher(barcode).matches();
    }

    private static boolean validateExcise(String barcode) {
        return EXCISE_BARCODE_PATTERN.matcher(barcode).matches();
    }

    private static void validateBeer(List<Nopdf> nopdf) throws EgaisException {
        AtomicReference<EgaisException> ex = new AtomicReference<EgaisException>(null);
        nopdf.stream().filter(bootle -> !validateEAN(bootle.getEan())).findAny().ifPresent(b -> ex.set(new EgaisException("Неверный штрих-код")));
        if (ex.get() != null)
            throw ex.get();

        nopdf.stream().filter(bootle -> StringUtils.isBlank(bootle.getCode())).findAny().ifPresent(b -> ex.set(new EgaisException("Неверная код алкогольной продукции")));
        if (ex.get() != null)
            throw ex.get();

        nopdf.stream().filter(bootle -> !NumberUtils.isParsable(bootle.getPrice())).findAny().ifPresent(b -> ex.set(new EgaisException("Неверная цена")));
        if (ex.get() != null)
            throw ex.get();

        nopdf.stream().filter(bootle -> !NumberUtils.isParsable(bootle.getVolume())).findAny().ifPresent(b -> ex.set(new EgaisException("Неверный объем")));
        if (ex.get() != null)
            throw ex.get();

        nopdf.stream().filter(bootle -> !NumberUtils.isParsable(bootle.getAlc())).findAny().ifPresent(b -> ex.set(new EgaisException("Неверное содержание алкоголя")));
        if (ex.get() != null)
            throw ex.get();

        nopdf.stream().filter(bootle -> bootle.getCount() == null).findAny().ifPresent(b -> ex.set(new EgaisException("Не указано количество")));
        if (ex.get() != null)
            throw ex.get();
    }

    public static boolean isINNValid(String inn) {
        int[] multipliers = new int[] { 2, 4, 10, 3, 5, 9, 4, 6, 8 };
        int[] multipliers1 = new int[] { 3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8 };
        int[] multipliers2 = new int[] { 7, 2, 4, 10, 3, 5, 9, 4, 6, 8 };
        int length = inn.length();
        int checksum1;
        if (length == 10) {
            checksum1 = toNumber(inn.charAt(length - 1));
            return checksum1 == calculateSum(inn, multipliers);
        } else {
            checksum1 = toNumber(inn.charAt(length - 1));
            int checksum2 = toNumber(inn.charAt(length - 2));
            return checksum2 == calculateSum(inn, multipliers2) && checksum1 == calculateSum(inn, multipliers1);
        }
    }

    public static boolean isValidKPP(String kpp) {
        return StringUtils.isNotBlank(kpp) && (StringUtils.isNumeric(kpp) && StringUtils.length(kpp) == 9);
    }

    public static int toNumber(char letter) {
        if (!Character.isDigit(letter)) {
            throw new IllegalArgumentException("Ошибка преобразования символа в число");
        } else {
            return Character.digit(letter, 10);
        }
    }

    private static int calculateSum(String inn, int[] multipliers) {
        int sum = 0;

        int result;
        for (result = 0; result < multipliers.length; ++result) {
            sum += toNumber(inn.charAt(result)) * multipliers[result];
        }

        result = sum % 11;
        return result == 10 ? 0 : result;
    }

}
