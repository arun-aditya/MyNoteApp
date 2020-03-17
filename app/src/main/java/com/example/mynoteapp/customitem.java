package com.example.mynoteapp;

public class customitem {
    private String spinnerText;
    private int spinnerImage;
    String colorcode;

    public customitem(String spinnerText, int spinnerImage, String colorcode) {
        this.spinnerText = spinnerText;
        this.spinnerImage = spinnerImage;
        this.colorcode=colorcode;
    }

    public String getSpinnerText() {
        return spinnerText;
    }

    public int getSpinnerImage() {
        return spinnerImage;
    }

    public String getColorcode() {
        return colorcode;
    }
}
