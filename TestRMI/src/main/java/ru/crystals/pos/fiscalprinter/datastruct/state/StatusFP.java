package ru.crystals.pos.fiscalprinter.datastruct.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Cтатус ФР
 */
public class StatusFP {
    public enum Status {
        /**
         * Нормальный
         */
        NORMAL,
        /**
         * Ошибка
         */
        FATAL,
        /**
         * Открыта крышка
         */
        OPEN_COVER,
        /**
         * Закончилась бумага
         */
        END_PAPER,
        /**
         * Смена превысила 24 часа
         */
        SHIFT_IS_OVER
    }

    private final static StatusFP NORMAL_STATUS = new StatusFP();
    /**
     * Статус
     */
    private Status status;
    /**
     * Числовое представление статуса
     */
    private long longStatus;
    /**
     * Набор строк с описанием (ошибками)
     */
    private List<String> descriptions;

    public StatusFP() {
        super();
        descriptions = new ArrayList<String>();
        longStatus = 0;
        status = Status.NORMAL;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getLongStatus() {
        return longStatus;
    }

    public void setLongStatus(long longStatus) {
        this.longStatus = longStatus;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public void addDescription(String description) {
        this.descriptions.add(description);
    }

    public static StatusFP normalStatus() {
        return NORMAL_STATUS;
    }

    @Override
    public String toString() {
        return "StatusFP{" +
            "status=" + status +
            ", longStatus=" + longStatus +
            ", descriptions=" + descriptions +
            '}';
    }
}
