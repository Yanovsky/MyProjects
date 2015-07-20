package ru.crystals.cards;

public class LuhnAlgorithm {

    public static int getControlDigit(String cardNumber) {
        boolean even = cardNumber.length() % 2 == 0;
        int crc = 0;
        for (int i = 0; i < cardNumber.length() - 1; i++) {
            char c = cardNumber.charAt(i);
            int n = Integer.valueOf(String.valueOf(c));
            if ((i + 1) % 2 == 0) {
                if (even) {
                    crc += n;
                } else {
                    crc += n * 2 > 9 ? n * 2 - 9 : n * 2;
                }
            } else {
                if (even) {
                    crc += n * 2 > 9 ? n * 2 - 9 : n * 2;
                } else {
                    crc += n;
                }
            }
        }
        if (crc % 10 == 0) {
            return 0;
        }
        return (crc + 10) / 10 * 10 - crc;
    }

    public static void checkControlDigit(String cardNumber) throws Exception {
        int expectedCRC = getControlDigit(cardNumber);
        int actualCRC = Integer.valueOf(cardNumber.substring(cardNumber.length() - 1));
        if (actualCRC != expectedCRC) {
            throw new Exception("Wrong control digit. Expected is " + expectedCRC + " but Actual is " + actualCRC);
        }
    }

    public static void main(String[] args) {
        try {
            String cardNumber = "4561261212345467";
            LuhnAlgorithm.checkControlDigit(cardNumber);
            System.out.println("Control digit is OK");
        } catch (Exception e) {
            // TODO: Не забыть прокинуть Exception выше
            e.printStackTrace();
        }
    }
}
