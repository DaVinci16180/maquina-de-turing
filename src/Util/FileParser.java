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
import java.util.List;
import Structure.Fila;
import Structure.Lista;
import Structure.Pilha;

public class FileParser {
    private final BufferedReader br;
    private final Pilha<String> stack = new Pilha<>();

    public FileParser(String filePath) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(filePath));
        List<String> lines = new ArrayList<>(br.lines().toList());
        Collections.reverse(lines);

        for (String line : lines) {
            stack.push(line);
        }
    }

    private Lista<Estado> getEstados() {
        Lista<Estado> estados = new Lista<>();

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

            estados = Sorting.bubbleSort(estados);

            return estados;
        }

        throw new RuntimeException("O arquivo não está no formato adequado.");
    }

    private void linkFuncoesDeTransicaoToEstados(Lista<Estado> estados) {
        if (stack.peek().startsWith("Funcoes de transicao")) {
            stack.pop();

            String row = stack.pop();
            while (row != null && !row.equals("")) {
                Fila<Character> characters = stringToQueue(row);

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

                for (int i = 0; i < estados.size(); i++) {
                    Estado estado = estados.get(i);

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

    private Fila<Character> stringToQueue(String string) {
        Fila<Character> fila = new Fila<>();

        for (char c : string.toCharArray()) {
            fila.add(c);
        }

        return fila;
    }

    public Maquina getMaquina() throws IOException {
        Maquina maquina = new Maquina();

        Lista<Estado> estados = getEstados();

        maquina.setEstados(estados);
        linkFuncoesDeTransicaoToEstados(estados);

        br.close();

        return maquina;
    }

}
