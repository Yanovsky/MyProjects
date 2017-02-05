package ru.crystals.pos.fiscalprinter.datastruct.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Статус принтера
 */
public class PrinterState {
	
	public enum State {
        /**
         * Нормальный
         */
		NORMAL,
        /**
         * Открыта крышка
         */
		OPEN_COVER,
        /**
         * Закончилась бумага
         */
		END_PAPER				
	}
	
	private State state;
	
	/**
	 * Статус
	 */
	private long longState;
	/**
	 * Набор строк с описанием (ошибками)
	 */
	private List<String> descriptions;
	
	public PrinterState() {
		super();
		descriptions = new ArrayList<String>();
		longState = 0;
		state = State.NORMAL;
	}

    /**
     * Получить статус принтера
     */
	public State getState() {
		return state;
	}

    /**
     * Установить статус принтера
     */
	public void setState(State state) {
		this.state = state;
	}

    /**
     * Получить числовое представление статуса принтера
     */
	public long getLongState() {
		return longState;
	}

    /**
     * Установить числовое представление статуса принтера
     */
	public void setLongState(long state) {
		this.longState = state;
	}

    /**
     * Получить список описания ошибок
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
