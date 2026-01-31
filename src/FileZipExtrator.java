import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileZipExtrator {

    public static void zipExtractor(String directory, String zipFileName) throws IOException {
        String absoluteDirectory = createDirectory(directory);

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            String filePath = absoluteDirectory + File.separator + zipEntry.getName();
            if(!zipEntry.isDirectory()) {
                FileOutputStream fos = new FileOutputStream(filePath);
                int len;
                while ((len = zis.read(buffer)) > 0)  {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            } else {
                File dir = new File(filePath);
                dir.mkdir();
            }
            zis.closeEntry();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        System.out.println("Unzipping complete");
    }

    static public String createDirectory(String directory) {
        File file = new File(directory);
        if(!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
