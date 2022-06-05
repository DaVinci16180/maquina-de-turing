package Util;

import Entity.Estado;
import Entity.FuncaoDeTransicao;
import Entity.Maquina;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class FileParser {
    private final BufferedReader br;
    private final Stack<String> stack = new Stack<>();

    public FileParser(String filePath) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(filePath));
        List<String> lines = new ArrayList<>(br.lines().toList());
        Collections.reverse(lines);
        stack.addAll(lines);
    }

    private List<Estado> getEstados() {
        List<Estado> estados = new ArrayList<>();

        if (stack.peek().startsWith("Estados da maquina")) {
            stack.pop();

            String row = stack.pop();
            while (!row.equals("")) {
                String[] inputs = row.split(",");

                String nome = inputs[0];
                boolean inicial = inputs[1].equals("1");
                boolean aceitacao = inputs[2].equals("1");

                Estado estado = new Estado(nome, inicial, aceitacao);
                estados.add(estado);

                row = stack.pop();
            }

            return estados;
        }

        throw new RuntimeException("O arquivo não está no formato adequado.");
    }

    private void linkFuncoesDeTransicaoToEstados(List<Estado> estados) {
        if (stack.peek().startsWith("Funcoes de transicao")) {
            stack.pop();

            String row = stack.pop();
            while (row != null && !row.equals("")) {
                Queue<Character> characters = stringToQueue(row);

                StringBuilder estadoOrigem = new StringBuilder();
                while(characters.peek() != '(') {
                    estadoOrigem.append(characters.poll());
                }
                characters.poll();

                Character condicao = characters.poll();

                if (characters.poll() != ':')
                    throw new RuntimeException("O arquivo não está no formato adequado.");

                Character saida = characters.poll();

                if (characters.poll() != ';')
                    throw new RuntimeException("O arquivo não está no formato adequado.");

                Character movimentoDoCabecote = characters.poll();

                characters.poll();
                characters.poll();
                characters.poll();

                StringBuilder estadoDestino = new StringBuilder();
                while(!characters.isEmpty()) {
                    estadoDestino.append(characters.poll());
                }

                FuncaoDeTransicao funcaoDeTransicao = new FuncaoDeTransicao();

                funcaoDeTransicao.setCondicao(condicao);
                funcaoDeTransicao.setSaida(saida);
                funcaoDeTransicao.setMovimentoDoCabecote(movimentoDoCabecote);

                for (Estado estado : estados) {
                    if (estado.getNome().equals(estadoDestino.toString())) {
                        funcaoDeTransicao.setDestino(estado);
                    }
                    if (estado.getNome().equals(estadoOrigem.toString())) {
                        funcaoDeTransicao.setOrigem(estado);
                        estado.getFuncoesDeTransicao().add(funcaoDeTransicao);
                    }
                }

                if (stack.isEmpty()) {
                    break;
                }

                row = stack.pop();
            }
        } else {
            throw new RuntimeException("O arquivo não está no formato adequado.");
        }
    }

    private Queue<Character> stringToQueue(String string) {
        Queue<Character> queue = new LinkedList<>();

        for (char c : string.toCharArray()) {
            queue.add(c);
        }

        return queue;
    }

    public Maquina getMaquina() throws IOException {
        Maquina maquina = new Maquina();

        List<Estado> estados = getEstados();

        maquina.setEstados(estados);
        linkFuncoesDeTransicaoToEstados(estados);

        br.close();

        return maquina;
    }

}
