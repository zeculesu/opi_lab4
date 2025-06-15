package org.example.models;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named("checkboxesStorage")
@ApplicationScoped
public class CheckboxesStorage implements Serializable {
    private boolean cb1;
    private boolean cb2;
    private boolean cb3;
    private boolean cb4;
    private boolean cb5;
    private Map<String, Boolean> selectedRValues = new HashMap<>();

    public void initCheckboxMap() {
        System.out.println("initCheckboxMap");
        selectedRValues.put("1", false);
        selectedRValues.put("1.5", false);
        selectedRValues.put("2", false);
        selectedRValues.put("2.5", false);
        selectedRValues.put("3", false);
        updateAllCbs();
        System.err.println("selectedRValues: " + selectedRValues.toString());
        System.err.println("cbs: " + cb1 + cb2 + cb3 + cb4 + cb5);
    }

    public void updateAllCbs() {
        setCb1(selectedRValues.get("1"));
        setCb2(selectedRValues.get("1.5"));
        setCb3(selectedRValues.get("2"));
        setCb4(selectedRValues.get("2.5"));
        setCb5(selectedRValues.get("3"));
    }

    public void updateCb(String value) {
        boolean lastFlag = selectedRValues.get(value);
        selectedRValues.remove(value);
        selectedRValues.put(value, !lastFlag);
        updateAllCbs();
    }

    // Метод для получения максимального выбранного значения R
    public double calculateMaxSelectedR() {
        double maxR = 0;
        if (selectedRValues.get("1") != null && selectedRValues.get("1")) maxR = 1;
        if (selectedRValues.get("1.5") != null && selectedRValues.get("1.5")) maxR = 1.5;
        if (selectedRValues.get("2") != null && selectedRValues.get("2")) maxR = 2;
        if (selectedRValues.get("2.5") != null && selectedRValues.get("2.5")) maxR = 2.5;
        if (selectedRValues.get("3") != null && selectedRValues.get("3")) maxR = 3;
        System.err.println(maxR);
        return maxR;
    }

    public Map<String, Boolean> getSelectedRValues() {
        return selectedRValues;
    }

    public void setSelectedRValues(Map<String, Boolean> selectedRValues) {
        this.selectedRValues = selectedRValues;
    }

    public boolean isCb1() {
        return cb1;
    }

    public void setCb1(boolean cb1) {
        this.cb1 = cb1;
    }

    public boolean isCb2() {
        return cb2;
    }

    public void setCb2(boolean cb2) {
        this.cb2 = cb2;
    }

    public boolean isCb3() {
        return cb3;
    }

    public void setCb3(boolean cb3) {
        this.cb3 = cb3;
    }

    public boolean isCb4() {
        return cb4;
    }

    public void setCb4(boolean cb4) {
        this.cb4 = cb4;
    }

    public boolean isCb5() {
        return cb5;
    }

    public void setCb5(boolean cb5) {
        this.cb5 = cb5;
    }
}
