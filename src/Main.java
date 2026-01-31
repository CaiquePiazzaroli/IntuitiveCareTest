import java.io.File;

public class Main {
    static void main() {
        try {
            //https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2025/1T2025.zip
            AbsDemonstracoesContabeisScraper abs = new AbsDemonstracoesContabeisScraper();
            abs.donwloadFles();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
