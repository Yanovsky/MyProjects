package ru.crystals.pos.fiscalprinter.nonfiscalmode.emulator.exceptions;

public enum ExceptionArea {
//@formatter:off
	PRINT_LINE,
	OPEN_CHECK,
	APPEND_POSITION,
	APPEND_PAYMENT,
	APPEND_DISCOUNT,
	CLOSE_CHECK_BEFORE_SAVE,
	CLOSE_CHECK_AFTER_SAVE,
	CLOSE_SHIFT_BEFORE_SAVE,
	CLOSE_SHIFT_AFTER_SAVE,
	CASH_OPERATION_BEFORE_SAVE,
	CASH_OPERATION_AFTER_SAVE,
	OPEN_DRAWER,
	GET_STATUS,
	GET_STATUS_SHIFT_IS_OVER,
//@formatter:on
	;

	public static boolean contains(String exceptionArea) {
		for (ExceptionArea ea: ExceptionArea.values()) {
			if (ea.toString().equals(exceptionArea))
				return true;
		}
		return false;
	}
}
