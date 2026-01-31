import java.io.File;

public class FileExtractor {

    static public String createDownloadsFolder() {
        String directory = "downloads";
        File file = new File(directory);
        if(!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

}
