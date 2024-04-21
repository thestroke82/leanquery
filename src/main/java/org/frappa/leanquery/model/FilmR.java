package org.frappa.leanquery.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public  class FilmR {
    private Integer id;
    private String title;
    private String description;
    private Integer releaseYear;
    private Language language;

    @Builder
    @Getter
    @Setter
    public static class Language{
        private Integer id;
        private String name;
    }
}