package nju.cgm.sparkgraphxtest.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/20 19:15
 * @description: FilmInfo
 */
public class FilmInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String filmName;

    private List<String> filmActors;

    private String filmYear;

    public FilmInfo() {
    }

    public FilmInfo(String filmName, List<String> filmActors, String filmYear) {
        this.filmName = filmName;
        this.filmActors = filmActors;
        this.filmYear = filmYear;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public List<String> getFilmActors() {
        return filmActors;
    }

    public void setFilmActors(List<String> filmActors) {
        this.filmActors = filmActors;
    }

    public String getFilmYear() {
        return filmYear;
    }

    public void setFilmYear(String filmYear) {
        this.filmYear = filmYear;
    }
}
