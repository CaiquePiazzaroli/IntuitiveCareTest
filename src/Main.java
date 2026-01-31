import java.util.ArrayList;
import java.util.List;

public class Main {

    static void main() {
        String destinationFolder = "C:\\Users\\Caique\\Downloads";
        String baseUrl = "https://dadosabertos.ans.gov.br/FTP/PDA/";

        try {
            AnsAPIScraper abs = new AnsAPIScraper(baseUrl);
            abs.downloadFiles(destinationFolder);
            abs.unzipFiles(destinationFolder, "file1");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
