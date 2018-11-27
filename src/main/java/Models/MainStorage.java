package Models;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

public class MainStorage {
    
    private static int movieCount = 0;
    private static Set<Movie> movies;
    private static Set<String> categories;

    public static void addMovie(String name, File cover){
        Movie mov = new Movie(name, cover, movieCount);
        movies.add(mov);
        movieCount++;
    }
    
    public static boolean addCategory(String movieName, String categoryName){
        for (Movie mov : movies){
            if (movieName.equals(mov.getTitle())){
                mov.addCategory(categoryName);
                categories.add(categoryName);
                return true;
            }
        }
        return false;
    }
    
    public static Set<Movie> getMovies() {
        return movies;
    }

    public static Set<String> getCategories() {
        return categories;
    }
    
    public static Set<Movie> getMoviesByCategory(String category){
        Set<Movie> moviesCat = new TreeSet<>();
        for (Movie mov : movies){
            moviesCat.add(mov);
        }
        return moviesCat;
    }

    public static void setMovies(Set<Movie> movies) {
        MainStorage.movies = movies;
    }

    public static void setCategories(Set<String> categories) {
        MainStorage.categories = categories;
    }
    
}
