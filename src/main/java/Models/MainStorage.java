package Models;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class MainStorage {
    
    private static int movieCount = 0;
    private static Set<Movie> movies = new HashSet<>();
    private static Set<String> categories = new HashSet<>();

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
    
    public static boolean addSeen(String movieName){
        for (Movie mov : movies){
            if (mov.getTitle().equals(movieName)){
                mov.setSeen(true);
                return true;
            }
        }
        return false;
    } 
    
    public static Set<Movie> getSeenMovies(){
        Set<Movie> seenMovies = new HashSet<>();
        for (Movie mov : movies){
            if (mov.getSeen()){
                seenMovies.add(mov);
            }
        }
        return seenMovies;
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
    
    public static Movie getRandomMovie(){
        Random r = new Random();
        int randomMovieInt = r.nextInt(movies.size());
        int i = 0;
        for (Movie mov : movies){
            if (i == randomMovieInt){
                return mov;
            }
            i++;
        }
        return null;
    }

    public static void setMovies(Set<Movie> movies) {
        MainStorage.movies = movies;
    }

    public static void setCategories(Set<String> categories) {
        MainStorage.categories = categories;
    }
    
}
