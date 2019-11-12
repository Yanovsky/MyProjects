package ru.jane;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Cypher {
    private static final int OFFSET = 5;
    private final List<String> rus1 = new ArrayList<>();
    private final List<List<String>> rus2 = new ArrayList<>();

    Cypher() {
        initRus();
    }

    private void initRus() {
        IntStream.range(0x410, 0x430).filter(i -> i != 0x42A && i != 0x42C).mapToObj(i -> (char) i).forEach(c -> {
            int i;
            switch (c) {
                case 'Э':
                    i = rus1.indexOf("Е");
                    rus1.add(i, rus1.remove(i) + "Э");
                    break;
                case 'Й':
                    i = rus1.indexOf("И");
                    rus1.add(i, rus1.remove(i) + "Й");
                    break;
                case 'С':
                    i = rus1.indexOf("Р");
                    rus1.add(i, rus1.remove(i) + "С");
                    break;
                case 'Х':
                    i = rus1.indexOf("Ф");
                    rus1.add(i, rus1.remove(i) + "Х");
                    break;
                case 'Щ':
                    i = rus1.indexOf("Ш");
                    rus1.add(i, rus1.remove(i) + "Щ");
                    break;
                default:
                    rus1.add(String.valueOf(c));
                    break;
            }
        });

        List<String> item = new ArrayList<>();
        int i = 1;
        for (String s : rus1) {
            item.add(s);
            if (i % 5.0 == 0) {
                rus2.add(item);
                item = new ArrayList<>();
            }
            i++;
        }
    }

    String encrypt1(String value) {
        return Stream.of(value.split(" ")).map(this::encryptWord1).collect(Collectors.joining(" "));
    }

    String encrypt2(String value) {
        return Stream.of(value.split(" ")).map(this::encryptWord2).collect(Collectors.joining(" "));
    }

    private String encryptWord1(String value) {
        StringBuilder result = new StringBuilder();
        for (char c : value.toUpperCase().toCharArray()) {
            int newIndex;
            String item = rus1.stream().filter(s -> s.indexOf(c) > -1).findFirst().orElse(null);
            int index = rus1.indexOf(item);
            if (index < 0) {
                throw new IllegalArgumentException("Строка содержит неподдерживаемый символ");
            }
            if (index < rus1.size() - OFFSET) {
                newIndex = index + OFFSET;
            } else {
                newIndex = OFFSET - (rus1.size() - index);
            }
            result.append(rus1.get(newIndex).charAt(0));
        }
        return result.toString();
    }

    private String encryptWord2(String value) {
        List<Point> coordinates = new ArrayList<>();
        for (char c : value.toUpperCase().toCharArray()) {
            List<String> vItem = rus2.stream().filter(s -> s.stream().anyMatch(x -> x.indexOf(c) > -1)).findFirst().orElse(null);
            if (vItem == null) {
                throw new IllegalArgumentException("Строка содержит неподдерживаемый символ");
            }
            int vertical = rus2.indexOf(vItem);
            if (vertical < 0) {
                throw new IllegalArgumentException("Строка содержит неподдерживаемый символ");
            }

            String hItem = vItem.stream().filter(s -> s.indexOf(c) > -1).findFirst().orElse(null);
            int horizontal = vItem.indexOf(hItem);
            coordinates.add(new Point(vertical + 1, horizontal + 1));
        }
        List<Point> newCoordinates = new ArrayList<>();
        Integer z = null;
        for (Point point : coordinates) {
            if (z == null) {
                z = point.y;
            } else {
                newCoordinates.add(new Point(z, point.y));
                z = null;
            }
        }
        for (Point point : coordinates) {
            if (z == null) {
                z = point.x;
            } else {
                //noinspection SuspiciousNameCombination
                newCoordinates.add(new Point(z, point.x));
                z = null;
            }
        }

        return newCoordinates.stream().map(p -> rus2.get(p.y - 1).get(p.x-1).substring(0, 1)).collect(Collectors.joining());
    }
}
