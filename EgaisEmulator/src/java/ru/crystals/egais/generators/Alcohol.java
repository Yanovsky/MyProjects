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
    WINE("Вино", "wine.txt"), 
    VODKA("Водка", "vodka.txt", BigDecimal.valueOf(40)), 
    BRANDY("Коньяк", "brandy.txt", BigDecimal.valueOf(40));

    private String description;
    private List<String> list = new ArrayList<String>();
    private BigDecimal volume;

    private Alcohol(String description, String fileName) {
        this(description, fileName, null);
    }
    
    private Alcohol(String description, String fileName, BigDecimal volume) {
        this.description = description;
        this.volume = volume;
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
        return volume != null ? volume : BigDecimal.valueOf(RandomUtils.nextDouble(10.0, 25.0)).setScale(1, RoundingMode.HALF_UP);
    }

    public BigDecimal getCapacity() {
        return BigDecimal.valueOf(RandomUtils.nextDouble(0.5, 1.0)).setScale(1, RoundingMode.HALF_UP);
    }

 }