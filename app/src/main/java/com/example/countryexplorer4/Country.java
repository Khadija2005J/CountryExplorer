package com.example.countryexplorer4;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Country implements Serializable {
    @SerializedName("name")
    private Name name;
    @SerializedName("flags")
    private Flags flags;
    @SerializedName("capital")
    private List<String> capital;
    @SerializedName("region")
    private String region;
    @SerializedName("population")
    private long population;
    @SerializedName("cca3")
    private String cca3;
    @SerializedName("languages")
    private Map<String, String> languages;
    @SerializedName("currencies")
    private Map<String, Currency> currencies;
    public String getCommonName() {
        return name != null ? name.common : "";
    }
    public String getFlagUrl() {
        return flags != null ? flags.png : "";
    }
    public String getCapital() {
        return (capital != null && !capital.isEmpty()) ? capital.get(0) : "N/A";
    }

    public String getRegion() {
        return region;
    }

    public long getPopulation() {
        return population;
    }

    public String getCca3() {
        return cca3;
    }

    public String getLanguages() {
        if (languages == null || languages.isEmpty()) return "N/A";
        StringBuilder sb = new StringBuilder();
        for (String lang : languages.values()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(lang);
        }
        return sb.toString();
    }

    public String getCurrencies() {
        if (currencies == null || currencies.isEmpty()) return "N/A";
        StringBuilder sb = new StringBuilder();
        for (Currency c : currencies.values()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(c.name).append(" (").append(c.symbol).append(")");
        }
        return sb.toString();
    }

    public static class Name implements Serializable {
        @SerializedName("common")
        public String common;
    }

    public static class Flags implements Serializable {
        @SerializedName("png")
        public String png;
    }

    public static class Currency implements Serializable {
        @SerializedName("name")
        public String name;
        @SerializedName("symbol")
        public String symbol;
    }
}
