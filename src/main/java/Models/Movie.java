package Models;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Movie {
    
    private int id;
    private String title;
    private File cover;
    private Set<String> categories = new HashSet<>();
    private boolean seen = false;

    public Movie(String title, File cover, int id) {
        this.title = title;
        this.cover = cover;
        this.id = id;
        
    }

    public int getId(){
        return id;
    }
    
    public String getTitle() {
        return title;
    }

    public File getCover() {
        return cover;
    }

    public Set<String> getCategories() {
        return categories;
    }
    
    public String getAllCategories(){
        String categoriesString = "";
        for (String cat : categories){
            categoriesString += cat + ", ";
        }
        return categoriesString.substring(0, categoriesString.length() - 2);
    }
    
    public boolean getSeen(){
        return seen;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCover(File cover) {
        this.cover = cover;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }
    
    public void setSeen(boolean seen){
        this.seen = seen;
    }
    
    public void addCategory(String category){
        this.categories.add(category);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String seenMovie = "";
        if (seen){
            seenMovie = "Seen.";
        } else {
            seenMovie = "Not yet seen.";
        }
        return title + " - " + seenMovie;
    }
    
}
