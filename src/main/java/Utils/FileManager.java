package Utils;

import Models.MainStorage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {
    
    private static final String FOLDER_PATH = "";
    
    private static final String MOVIES_FILE_PATH = FOLDER_PATH + "/movies.csv"; 
    private static final File MOVIES_FILE = new File(MOVIES_FILE_PATH); //name;image download link
    private static final String MOVIES_LINK = "";
    private static final String IMAGES_FOLDER_PATH = FOLDER_PATH + "/images";
    
    private static final String CATEGORIES_FILE_PATH = FOLDER_PATH + "/categories.csv"; 
    private static final File CATEGORIES_FILE = new File(CATEGORIES_FILE_PATH); //category;movie name
    private static final String CATEGORIES_LINK = "";
    
    private static final String DIVIDER = ";";
    
    public static boolean updateFile() throws IOException{
        if (checkFilesExist()){
            List<String> movies = getFileLines(MOVIES_FILE_PATH);
            List<String> categories = getFileLines(CATEGORIES_FILE_PATH);
            File moviesFile = downloadFile(MOVIES_LINK, FOLDER_PATH + "tmpMovies.csv");
            File categoriesFile = downloadFile(CATEGORIES_LINK, FOLDER_PATH + "tmpCategories.csv");
            if (!checkFileVersion(movies, getFileLines(moviesFile.getAbsolutePath())) && !checkFileVersion(categories, getFileLines(categoriesFile.getAbsolutePath()))){
                return updateFile(false, moviesFile, categoriesFile);
            } else {
                return false;
            }
        } else {
            return updateFile(true, null, null);
        }
    }
    
    private static boolean updateFile(boolean creating, File newMovies, File newCategories) throws IOException{
        try {
            if (!creating){
            MOVIES_FILE.delete();
            CATEGORIES_FILE.delete();
            newMovies.renameTo(MOVIES_FILE);
            newCategories.renameTo(CATEGORIES_FILE);
            addToStorage();
            } else {
            downloadFile(MOVIES_LINK, MOVIES_FILE_PATH);
            downloadFile(CATEGORIES_LINK, CATEGORIES_FILE_PATH);
            addToStorage();
            }
        } catch (IOException e){
            return false;
        }
        return true;
    }
    
    private static File downloadFile(String url, String fileName){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private static File downloadImage(String url, String movieName){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private static boolean checkFilesExist(){
        return MOVIES_FILE.exists() && CATEGORIES_FILE.exists();
    }
    
    private static boolean checkFileVersion(List<String> old, List<String> newFile){
        if (old.size() != newFile.size()){
            return false;
        }
        for (String str1 : old){
            boolean exists = false;
            for (String str2 : newFile){
                if (!exists){
                    exists = str1.equals(str2);
                }
            }
            if (!exists){
                return false;
            }
        }
        return true;
    }
    
    private static List<String> getFileLines(String filePath) throws IOException{
        List<Object> listObject = Files.lines(Paths.get(filePath)).collect(Collectors.toList());
        List<String> listString = new ArrayList<>();
        for (Object obj : listObject){
            listString.add(obj.toString());
        }
        return listString;
    }
    
    private static void addToStorage() throws IOException{
        List<String> moviesList = getFileLines(MOVIES_FILE_PATH);
        List<String> categoriesList = getFileLines(CATEGORIES_FILE_PATH);
        for (String line : moviesList){
            String[] tab = line.trim().split(DIVIDER);
            File coverImage = downloadImage(tab[1], tab[0]);
            MainStorage.addMovie(tab[0], coverImage);
        }
        for (String line : categoriesList){
            String[] tab = line.trim().split(DIVIDER);
            MainStorage.addCategory(tab[1], tab[0]);
        }
    }
    
}
