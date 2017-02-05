package ru.crystals.pos;

public class CashException extends Exception {
    private static final long serialVersionUID = 6321114604194637511L;
    private CashErrorType errorType = CashErrorType.NOT_CRITICAL_ERROR;

    public CashException() {
        super();
    }

    public CashException(Throwable rootCause) {
        super(rootCause);
    }

    public CashException(String message, Throwable cause) {
        super(message, cause);
    }

    public CashException(String message) {
        super(message);
        errorType = CashErrorType.NOT_CRITICAL_ERROR;
    }

    public CashException(String message, CashErrorType errorType, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    public CashException(String message, CashErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public CashErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(CashErrorType errorType) {
        this.errorType = errorType;
    }
}
