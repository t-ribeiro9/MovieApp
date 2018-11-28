package Utils;

import Models.MainStorage;
import Models.Movie;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileManager {

    private static final String FOLDER_PATH = "C:\\Program Files\\MovieApp\\";

    private static final String MOVIES_FILE_PATH = FOLDER_PATH + "movies.csv";
    private static final File MOVIES_FILE = new File(MOVIES_FILE_PATH); //name;image download link
    private static final String MOVIES_LINK = "https://drive.google.com/uc?authuser=0&id=1cUt7V8QMVFE99kX-UitrsTpcbGhdzCcj&export=download";
    private static final String IMAGES_FOLDER_PATH = FOLDER_PATH + "images\\";

    private static final String CATEGORIES_FILE_PATH = FOLDER_PATH + "categories.csv";
    private static final File CATEGORIES_FILE = new File(CATEGORIES_FILE_PATH); //category;movie name
    private static final String CATEGORIES_LINK = "https://drive.google.com/uc?authuser=0&id=1DncPyyII2T-xHioxHk470ZpvYimAJWU-&export=download";

    private static final String SEEN_MOVIES_FILE_PATH = FOLDER_PATH + "seenMovies.csv";
    private static final File SEEN_MOVIES_FILE = new File(SEEN_MOVIES_FILE_PATH);

    private static final String DIVIDER = ";";

    public static boolean updateFile() throws IOException {
        if (checkFilesExist()) {
            List<String> movies = getFileLines(MOVIES_FILE_PATH);
            List<String> categories = getFileLines(CATEGORIES_FILE_PATH);
            File moviesFile = downloadFile(MOVIES_LINK, FOLDER_PATH + "tmpMovies.csv");
            File categoriesFile = downloadFile(CATEGORIES_LINK, FOLDER_PATH + "tmpCategories.csv");
            if (!checkFileVersion(movies, getFileLines(moviesFile.getAbsolutePath())) || !checkFileVersion(categories, getFileLines(categoriesFile.getAbsolutePath()))) {
                return updateFile(false, moviesFile, categoriesFile);
            } else {
                addToStorage(true, false);
                return true;
            }
        } else {
            return updateFile(true, null, null);
        }
    }

    private static boolean updateFile(boolean creating, File newMovies, File newCategories) throws IOException {
        try {
            if (!creating) {
                MOVIES_FILE.delete();
                CATEGORIES_FILE.delete();
                newMovies.renameTo(MOVIES_FILE);
                newCategories.renameTo(CATEGORIES_FILE);
                addToStorage(false, true);
            } else {
                File file = downloadFile(MOVIES_LINK, MOVIES_FILE_PATH);
                if (file != null) {
                    file = downloadFile(CATEGORIES_LINK, CATEGORIES_FILE_PATH);
                    if (file != null) {
                        addToStorage(false, true);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static File downloadFile(String url, String fileName) {
        File file = new File(fileName);
        InputStream in;
        try {
            in = new URL(url).openStream();
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
            return file;
        } catch (MalformedURLException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    private static File downloadImage(String url, String movieName) {
        File movieCover = new File(IMAGES_FOLDER_PATH + movieName + ".png");
        if (movieCover.exists()) {
            movieCover.delete();
        }
        InputStream in;
        try {
            in = new URL(url).openStream();
            Files.copy(in, Paths.get(movieCover.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            return movieCover;
        } catch (MalformedURLException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    public static boolean checkFilesExist() {
        return MOVIES_FILE.exists() && CATEGORIES_FILE.exists();
    }

    private static boolean checkFileVersion(List<String> old, List<String> newFile) {
        if (old.size() != newFile.size()) {
            return false;
        }
        for (String str1 : old) {
            boolean exists = false;
            for (String str2 : newFile) {
                if (!exists) {
                    exists = str1.equals(str2);
                }
            }
            if (!exists) {
                return false;
            }
        }
        return true;
    }

    private static List<String> getFileLines(String filePath) throws IOException {
        List<Object> listObject = Files.lines(Paths.get(filePath)).collect(Collectors.toList());
        List<String> listString = new ArrayList<>();
        for (Object obj : listObject) {
            listString.add(obj.toString());
        }
        return listString;
    }

    private static void addToStorage(boolean withSeen, boolean downloadImages) throws IOException {
        List<String> moviesList = getFileLines(MOVIES_FILE_PATH);
        List<String> categoriesList = getFileLines(CATEGORIES_FILE_PATH);
        for (String line : moviesList) {
            String[] tab = line.trim().split(DIVIDER);
            if (downloadImages){
                downloadImage(tab[1], tab[0]);
            } 
            File coverImage = new File(IMAGES_FOLDER_PATH + tab[0] + ".png");
            MainStorage.addMovie(tab[0], coverImage);
        }
        for (String line : categoriesList) {
            String[] tab = line.trim().split(DIVIDER);
            MainStorage.addCategory(tab[1], tab[0]);
        }
        if (withSeen) {
            List<String> seenMoviesList = getFileLines(SEEN_MOVIES_FILE_PATH);
            for (String line : seenMoviesList) {
                MainStorage.addSeen(line);
            }
        } else {
            SEEN_MOVIES_FILE.createNewFile();
        }
    }

    public static void saveSeenMovies() throws FileNotFoundException, IOException {
        Set<Movie> seenMovies = MainStorage.getSeenMovies();
        if (seenMovies.size() > 0) {
            SEEN_MOVIES_FILE.delete();
            SEEN_MOVIES_FILE.createNewFile();
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SEEN_MOVIES_FILE)))) {
                for (Movie mov : seenMovies) {
                    bw.write(mov.getTitle());
                    bw.newLine();
                }
            }
        }
    }

}
