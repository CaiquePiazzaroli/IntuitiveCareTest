import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class AbsDemonstracoesContabeisScraper {
    public void donwloadFles() {
        List<String> urlFiles = scrapeZipUrls();
        int index = urlFiles.size() - 1;
        int count = 0;
        while (count < 3) {
            String link = urlFiles.get(index);
            String fileType = "zip";

            if(link.endsWith(".csv")) {
                fileType = "csv";
            };

            String downloadPath = FileExtractor.createDownloadsFolder();
            String fileDestination = String.format("%s\\file%d.%s", downloadPath, count, fileType);
            File out = new File(fileDestination);
            new Thread(new UrlFileDownloader(link, out)).start();
            count++;
            index--;
        }
    }



    public List<String> scrapeZipUrls() {
        try {
            Document htmlPage = Jsoup.connect(resolveLatestYearUrl()).get();
            return htmlPage.select("a[href]").stream()
                    .map(element -> element.attr("abs:href"))
                    .filter(url -> url.toLowerCase().endsWith(".zip"))
                    .toList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return List.of();
    }

    public String resolveLatestYearUrl() throws Exception {
        String demonstracoesContabeisUrl = resolveDemonstracoesPageUrl();
        Document doc = Jsoup.connect(demonstracoesContabeisUrl).get();
        Elements aTagElements = doc.select("a");
        String lastYearAvailable = findMostRecentYear(aTagElements);
        return demonstracoesContabeisUrl + lastYearAvailable;
    }

    public String resolveDemonstracoesPageUrl() throws Exception {
        String BASE_URL = "https://dadosabertos.ans.gov.br/FTP/PDA/";
        Document doc = Jsoup.connect(BASE_URL).get();
        Elements aTagElements = doc.select("a");
        String demonstracoesContabeisHrefValue = extractDemonstracoesHref(aTagElements);
        if(demonstracoesContabeisHrefValue == null) {
            throw new Exception("Termo nao encontrado");
        }
        return BASE_URL + demonstracoesContabeisHrefValue;
    }

    public String findMostRecentYear(Elements elements) {
        int currentYear = LocalDate.now().getYear();
        int limitYear = currentYear - 10;
        for (int year = currentYear; year >= limitYear; year--) {
            String yearString = String.valueOf(year);
            boolean found = elements.stream().anyMatch(el -> el.attr("href").contains(yearString));
            if (found) {
                return yearString;
            }
        }
        throw new RuntimeException("Nenhum ano recente encontrado nos links.");
    }

    public String extractDemonstracoesHref(Elements elements) throws Exception {
        for (Element aTag : elements) {
            String href = aTag.attr("href").toLowerCase();
            if (href.contains("demonstracoes") || href.contains("contabeis")) {
                return aTag.attr("href");
            }
        }
        return null;
    }
}