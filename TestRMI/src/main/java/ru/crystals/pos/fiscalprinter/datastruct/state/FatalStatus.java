package ru.crystals.pos.fiscalprinter.datastruct.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Фатальный статус ФР
 */
public class FatalStatus {
	
	public enum Status {
        /**
         * Состояние, может быть автоматически исправлено пользователем\системой
         */
		NOT_FATAL,
        /**
         * Требуется вызов инженера
         */
		FATAL
	}
	
	/**
	 * Статус
	 */
	private Status fatalStatus;
	
	/**
	 * Числовое представление статуса
	 */
	private long status;
	
	/**
	 * Набор строк с описанием (ошибками)
	 */
	private List<String> descriptions;

	public FatalStatus() {
		super();
		descriptions = new ArrayList<String>();
		status = 0;
		fatalStatus = Status.NOT_FATAL;
	}

    /**
     * Получить статус ФР
     */
	public Status getFatalStatus() {
		return fatalStatus;
	}

    /**
     * Установить статус ФР
     */
	public void setFatalStatus(Status fatalStatus) {
		this.fatalStatus = fatalStatus;
	}

    /**
     * Получить числовое представление статуса ФР
     */
	public long getStatus() {
		return status;
	}

    /**
     * Установить числовое представление статуса ФР
     */
	public void setStatus(long status) {
		this.status = status;
	}

    /**
     * Получить набор строк с описанием ошибок
     */
	public List<String> getDescriptions() {
		return descriptions;
	}

    /**
     * Установить набор строк с описанием ошибок
     */
	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

    /**
     * Добавить описание ошибки
     */
	public void addDescription(String description) {
		this.descriptions.add(description);
	}

}
