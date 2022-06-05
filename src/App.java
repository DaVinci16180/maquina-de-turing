import java.util.Scanner;

import Entity.Maquina;
import Util.FileParser;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nDigite o caminho para o arquivo do automato. Caso esteja no mesmo diretorio do arquivo .jar, basta digitar o nome do arquivo.");

        Maquina maquina = null;
        while (maquina == null) {
            try {
                String filePath = scanner.nextLine();
                FileParser parser = new FileParser(filePath);
                maquina = parser.getMaquina();
            } catch (Exception e) {
                System.out.println("Caminho invalido. Tente novamente.");
            }
        }

        System.out.println("\nMaquina de turing criada com sucesso.");

        boolean running = true;
        while (running) {
            System.out.println("\nDigite uma palavra a ser testada pela maquina:");
            String palavra = scanner.nextLine();
            boolean aceita = maquina.assess(palavra);

            if (aceita) {
                System.out.println("\nA palavra e aceita pela maquina :D");
            } else {
                System.out.println("\nA palavra NAO e aceita pela maquina :(");
            }

            System.out.println("\nGostaria de submeter mais palavras? [s/n]");
            String op = scanner.nextLine();

            if (op.equals("n") || op.equals("N")) {
                running = false;
            }
        }

        scanner.close();
    }
}
