package ru.crystals.pos;

/**
 * Тип ошибки (сценарии обработки)
 */
public enum CashErrorType {
    /**
     * Ситуации, выход из которых не требует перегрузки кассового компьютера
     */
    NOT_CRITICAL_ERROR,

    /**
     * Ситуации, выход из которых не требует перегрузки кассового компьютера, но повторять бесполезно
     */
    NOT_CRITICAL_ERROR_WITHOUT_REPEAT,
    
    /**
     * Ситуации, выход из которых не требует перегрузки кассового компьютера, но повторять бесполезно
     */
    NOT_CRITICAL_WARN_WITHOUT_REPEAT,

    /**
     * Ситуации, выход из которых требует перегрузки кассового компьютера
     */
    NEED_RESTART,

    /**
     * Ситуации, когда КП самостоятельно устраняет замечания в работе
     */
    AUTO_FIXE,

    /**
     * Ситуации, выход из которых требует обязательного вызова ЦТО
     */
    FATAL_ERROR,

    /**
     * Ошибки валидации смены
     */
    SHIFT_VALIDATION_ERROR,

    /**
     * Необходимо выполнить операции со сменой
     */
    SHIFT_OPERATION_NEED,

    /**
     * Ошибки фискальника (одинаковая обработка всех ошибок)
     */
    FISCAL_ERROR,
    /**
     * Ошибка при печати фискального отчета
     */
    FISCAL_REPORT_ERROR,

    /**
     * Ошибка с контрольной лентой
     */
    CONTROL_TAPE_ERROR

}