package ru.crystals.pos.fiscalprinter.exception;

import ru.crystals.pos.CashErrorType;
import ru.crystals.pos.CashException;
import ru.crystals.pos.fiscalprinter.datastruct.state.StatusFP;

public class FiscalPrinterException extends CashException {
    private static final long serialVersionUID = 2889740958934359590L;
    private StatusFP.Status fiscalPrinterStatus = StatusFP.Status.FATAL;
    private int errorCode;
    private FiscalPrinterExceptionType exceptionType = FiscalPrinterExceptionType.UNKNOWN;

    public FiscalPrinterException() {
        super("", CashErrorType.FISCAL_ERROR);
    }

    public FiscalPrinterException(String message) {
        super(message, CashErrorType.FISCAL_ERROR);
    }

    public FiscalPrinterException(String message, CashErrorType errorType) {
        super(message, errorType);
    }

    public FiscalPrinterException(String message, CashErrorType errorType, FiscalPrinterExceptionType exceptionType) {
        super(message, errorType);
        this.exceptionType = exceptionType;
    }

    public FiscalPrinterException(String message, CashErrorType errorType, Throwable cause) {
        super(message, errorType, cause);
    }

    public FiscalPrinterException(String message, CashErrorType errorType, int errorCode) {
        super(message, errorType);
        this.errorCode = errorCode;
    }

    public FiscalPrinterException(String message, Exception cause) {
        super(message, CashErrorType.FISCAL_ERROR, cause);
    }

    public FiscalPrinterException(String message, CashErrorType errorType, StatusFP.Status fiscalPrinterStatus) {
        this(message, errorType);
        this.fiscalPrinterStatus = fiscalPrinterStatus;
    }

    public FiscalPrinterExceptionType getExceptionType() {
        return exceptionType;
    }

    public StatusFP.Status getFiscalPrinterStatus() {
        return fiscalPrinterStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }
}