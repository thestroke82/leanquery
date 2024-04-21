package org.frappa.leanquery.model.mapper;

import org.frappa.leanquery.model.FilmQ;
import org.frappa.leanquery.model.FilmR;
import org.frappa.leanquery.plan.filter.FilmFilterPlan;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static org.frappa.leanquery.jooq.tables.Language.LANGUAGE;
import static org.frappa.leanquery.jooq.tables.Film.FILM;


@Component
public class FilmMapper {
    public FilmR mapFilm(Record input){
        if(input==null){
            return null;
        }
        FilmR output= FilmR.builder()
                .id(input.get(FILM.FILM_ID))
                .title(input.get(FILM.TITLE))
                .description(input.get(FILM.DESCRIPTION))
                .releaseYear(input.get(FILM.RELEASE_YEAR))
                .build();
        return output;
    }

    public FilmR.Language mapLanguage(Record input){
        if(input==null){
            return null;
        }
        FilmR.Language output= FilmR.Language.builder()
                .id(input.get(LANGUAGE.LANGUAGE_ID))
                .name(input.get(LANGUAGE.NAME))
                .build();
        return output;
    }

    public FilmFilterPlan mapFilterPlan(FilmQ input){
        if(input==null){
            return null;
        }
        FilmFilterPlan output= FilmFilterPlan.builder()
                .titleLike(input.getTitle())
                .build();
        return output;
    }
}
