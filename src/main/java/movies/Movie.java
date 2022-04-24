package movies;

import java.time.LocalDate;

public class Movie {

    private Long id;
    private String title;
    private LocalDate releaseDate;
    private int length;

    public Movie(String title, LocalDate releaseDate, int length) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.length = length;
    }

    public Movie(Long id, String title, LocalDate releaseDate, int length) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }


    public int getLength() {
        return length;
    }

    public Long getId() {
        return id;
    }
}
