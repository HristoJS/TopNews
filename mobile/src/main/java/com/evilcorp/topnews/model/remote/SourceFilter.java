package com.evilcorp.topnews.model.remote;

/**
 * Created by hristo.stoyanov on 18-Apr-17.
 */

public class SourceFilter {

    private String language;
    private String category;
    private String country;

    public SourceFilter(String language, String category, String country) {
        this.category = category;
        this.language = language;
        this.country = country;
    }

    @Override
    public String toString() {
        return "SourceFilter{" +
                "language='" + language + '\'' +
                ", category='" + category + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static final class Categories {
        public static final String BUSINESS = "business";
        public static final String ENTERTAINMENT = "entertainment";
        public static final String GAMING = "gaming";
        public static final String GENERAL = "general";
        public static final String MUSIC = "music";
        public static final String POLITICS = "politics";
        public static final String SCIENCE_AND_NATURE = "science-and-nature";
        public static final String SPORT = "sport";
        public static final String TECHNOLOGY = "technology";

        public static String[] toStringArray() {
            return new String[] { BUSINESS, ENTERTAINMENT, GAMING, GENERAL, MUSIC,
                    POLITICS, SCIENCE_AND_NATURE, SPORT, TECHNOLOGY };
        }
    }

    public static final class Languages {
        public static final String ENGLISH = "en";
        public static final String GERMAN = "de";
        public static final String FRENCH = "fr";

        public static String[] toStringArray() {
            return new String[]{ENGLISH, GERMAN, FRENCH};
        }
    }

    public static final class Countries {
        public static final String USA = "us";
        public static final String GERMANY = "de";
        public static final String FRANCE = "fr";

        public static String[] toStringArray() {
            return new String[]{USA, GERMANY, FRANCE};
        }
    }
}
