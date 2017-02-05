package ru.crystals.pos.fiscalprinter.nonfiscalmode.emulator.exceptions;

import ru.crystals.pos.fiscalprinter.exception.FiscalPrinterException;

public class ManualFiscalPrinterException extends FiscalPrinterException {
	private static final long serialVersionUID = 2163652689591502135L;
	private ExceptionArea exceptionArea;
	private boolean fatal;

	public ManualFiscalPrinterException(String message, ExceptionArea area) {
		this(message, area, false);
	}

	public ManualFiscalPrinterException(String message, ExceptionArea area, boolean fatal) {
		super(message);
		setFatal(fatal);
		setExceptionArea(area);
	}

	public ExceptionArea getExceptionArea() {
		return exceptionArea;
	}
	public void setExceptionArea(ExceptionArea exceptionArea) {
		this.exceptionArea = exceptionArea;
	}

	public boolean isFatal() {
		return fatal;
	}
	public void setFatal(boolean fatal) {
		this.fatal = fatal;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ManualFiscalPrinterException [");
		if (exceptionArea != null) {
			builder.append("exceptionArea: ");
			builder.append(exceptionArea);
			builder.append(", ");
		}
		builder.append("fatal is ");
		builder.append(fatal);
		builder.append(", ");
		if (getMessage() != null) {
			builder.append("Message: \"");
			builder.append(getMessage());
			builder.append("\"");
		}
		builder.append("]");
		return builder.toString();
	}

}
