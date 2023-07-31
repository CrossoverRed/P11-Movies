package sg.edu.rp.c326.id22015010.p11_movies;

public class movie {
    private int id;
    private String title;
    private String genres;
    private int year;
    private String ratings;

    public movie(int id, String title, String genres, int year, String ratings) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.year = year;
        this.ratings = ratings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }
}
