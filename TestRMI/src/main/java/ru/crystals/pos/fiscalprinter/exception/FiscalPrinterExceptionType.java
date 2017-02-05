package ru.crystals.pos.fiscalprinter.exception;

public enum FiscalPrinterExceptionType {
    /**
     * нет ошибки
     */
    NONE,
    /**
     * нетипизированная ошибка в том числе (ошибки фискализации и т.д.)
     */
    UNKNOWN,
    /**
     * перед фискализацией сработало товарное ограничение
     */
    PRODUCT_LIMIT,
    /**
     * не достаточно денег в кассе
     */
    NOT_ENOUGH_MONEY,
    /**
     * ошибка произошла до попытки фискализации, 
     * касса предложить повторить или аннулировать чек
     */
    BEFORE_FISCALIZE
}
