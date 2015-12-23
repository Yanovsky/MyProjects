package ru.crystals.egais.generators;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

public enum Alcohol {
    WINE("Вино", "wine.txt", 12, 20), VODKA("Водка", "vodka.txt", 40, 40), BRANDY("Коньяк", "brandy.txt", 40, 40);

    private String description;
    private List<String> list = new ArrayList<String>();
    private double minVolume;
    private double maxVolume;

    private Alcohol(String description, String fileName, double minVolume, double maxVolume) {
        this.description = description;
        this.minVolume = minVolume;
        this.maxVolume = maxVolume;
        try {
            loadAlco("ru/crystals/egais/" + fileName);
        } catch (IOException e) {
        }
    }

    private void loadAlco(String fileName) throws IOException {
        URL url = ClassLoader.getSystemResource(fileName);
        File file = new File(url.getFile());
        if (file.exists()) {
            List<?> wineNames = FileUtils.readLines(file);
            list = wineNames.stream().map(w -> StringUtils.substringBefore(w.toString(), ".").trim()).distinct().collect(Collectors.toList());
        }
    }

    public String getRandom() {
        return list.get(RandomUtils.nextInt(0, list.size()));
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getVolume() {
        return BigDecimal.valueOf(RandomUtils.nextDouble(minVolume, maxVolume)).setScale(1, RoundingMode.HALF_UP);
    }

    public BigDecimal getCapacity() {
        return BigDecimal.valueOf(RandomUtils.nextDouble(0.5, 1.5)).setScale(1, RoundingMode.HALF_UP);
    }

 }