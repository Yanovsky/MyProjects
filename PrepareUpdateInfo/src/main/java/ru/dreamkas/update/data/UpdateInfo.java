package ru.dreamkas.update.data;

import java.util.ArrayList;
import java.util.List;

public class UpdateInfo {
    private final Description description = new Description();
    private final List<Patch> patches = new ArrayList<>();
    private final String product;

    public UpdateInfo(String product) {
        this.product = product;
    }

    public Description getDescription() {
        return description;
    }

    public List<Patch> getPatches() {
        return patches;
    }

    public String getProduct() {
        return product;
    }
}
