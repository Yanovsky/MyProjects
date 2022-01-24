package ru.alex.atol;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.commons.lang3.SystemUtils;

import ru.atol.drivers10.fptr.Fptr;
import ru.atol.drivers10.fptr.IFptr;

public class AtolPrinter {
    IFptr fptr;

    public AtolPrinter() {
        Path libPath = Paths.get("").resolve("lib").resolve("atol");
        if (SystemUtils.IS_OS_WINDOWS) {
            libPath = libPath
                .resolve("win")
                .resolve(SystemUtils.OS_ARCH.contains("64") ? "x64" : "x86");
        }
        fptr = new Fptr(libPath.toAbsolutePath().toString());
        System.out.println(String.format("Версия драйвера: %s", fptr.version()));
    }

    public void open() {
        fptr.open();
    }

    public void closeShift() {
//        fptr.setParam(1021, "Кассир Иванов И.");
//        fptr.setParam(1203, "123456789047");
//        fptr.operatorLogin();

        fptr.setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_CLOSE_SHIFT);
        int i = fptr.report();
        System.out.println(String.format("Закрытие смены вернуло ошибку: %d", i));

        while (fptr.checkDocumentClosed() < 0) {
            // Не удалось проверить состояние документа. Вывести пользователю текст ошибки, попросить устранить неполадку и повторить запрос
            System.out.println(fptr.errorDescription());
            continue;
        }

        if (!fptr.getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_CLOSED)) {
            // Документ не закрылся. Требуется его отменить (если это чек) и сформировать заново
            fptr.cancelReceipt();
            return;
        }

        if (!fptr.getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_PRINTED)) {
            // Можно сразу вызвать метод допечатывания документа, он завершится с ошибкой, если это невозможно
            while (fptr.continuePrint() < 0) {
                // Если не удалось допечатать документ - показать пользователю ошибку и попробовать еще раз.
                System.out.println(String.format("Не удалось напечатать документ (Ошибка \"%s\"). Устраните неполадку и повторите.", fptr.errorDescription()));
                continue;
            }
        }

    }

    public void openShift() {
        int i = fptr.openShift();
        System.out.println(String.format("Открытие смены вернуло ошибку: %d", i));

        fptr.checkDocumentClosed();
    }

    public void test() {
        fptr.setParam(IFptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_SHIFT_STATE);
        fptr.queryData();

        String state = "xxxxx";
        switch ((int) fptr.getParamInt(IFptr.LIBFPTR_PARAM_SHIFT_STATE)) {
            case IFptr.LIBFPTR_SS_CLOSED:
                state = "Закрыта"; break;
            case IFptr.LIBFPTR_SS_OPENED:
                state = "Открыта"; break;
            case IFptr.LIBFPTR_SS_EXPIRED:
                state = "Истекли 24 часа"; break;
        }
        long number      = fptr.getParamInt(IFptr.LIBFPTR_PARAM_SHIFT_NUMBER);
        Date dateTime    = fptr.getParamDateTime(IFptr.LIBFPTR_PARAM_DATE_TIME);
        System.out.println(String.format("Смена №%s (%s) %s", number, state, dateTime));
    }

    public static void main(String[] args) {
        AtolPrinter atol = new AtolPrinter();
        atol.open();
        atol.test();
        atol.closeShift();
        atol.test();
        atol.openShift();
        atol.test();
    }
}
