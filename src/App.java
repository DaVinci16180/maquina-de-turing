import java.util.Scanner;

import Entity.Maquina;
import Util.FileParser;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nDigite o caminho para o arquivo da maquina de turing.");

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
            System.out.println("\nInforme a entrada da maquina:");
            String palavra = scanner.nextLine();
            maquina.assess(palavra);

            System.out.println("\nSubmeter nova entrada? [s/n]");
            String op = scanner.nextLine();

            if (op.equals("n") || op.equals("N")) {
                running = false;
            }
        }

        scanner.close();
    }
}
