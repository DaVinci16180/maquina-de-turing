package Entity;

import Structure.Lista;
import Structure.ListaDupla;

public class Maquina {
    private ListaDupla<Character> fita;
    private Lista<Estado> estados;
    private int cabeçote = 1;
    
    public ListaDupla<Character> getFita() {
        return fita;
    }
    
    public void setFita(ListaDupla<Character> fita) {
        this.fita = fita;
    }
    
    public Lista<Estado> getEstados() {
        return estados;
    }
    
    public void setEstados(Lista<Estado> estados) {
        this.estados = estados;
    }
    
    public int getCabeçote() {
        return cabeçote;
    }
    
    public void setCabeçote(int cabeçote) {
        this.cabeçote = cabeçote;
    }

    private void preencherFita(String string) {
        fita = new ListaDupla<>();
        cabeçote = 1;

        fita.add('B');

        for (char c : string.toCharArray()) {
            fita.add(c);
        }

        fita.add('B');
    }

    private Estado getEstadoInicial() {
        for (int i = 0; i < estados.size(); i++) {
            Estado estado = estados.get(i);

            if (estado.isInicial()) {
                return estado;
            }
        }

        throw new RuntimeException("Não existe estado inicial");
    }

    public void assess(String string) {
        preencherFita(string);

        try {
            System.out.println("\n==========");
            System.out.println("  Passos");
            System.out.println("==========\n");

            printFita();
            boolean aceita = doAssessments(getEstadoInicial());

            System.out.println("\n================");
            System.out.println("   Relatorio");
            System.out.println("================\n");

            System.out.println("Estado final da fita:");
            printFita();

            System.out.println("A computação " + (aceita ? "finalizou" : "não finalizou") + " em um estado de aceitação.");
        } catch (Exception e) {
        }
    }

    public void printFita() {
        for (int i = 0; i < fita.size() + 2; i++) {
            System.out.print('-');
        }

        System.out.print("\n|");
        for (int i = 0; i < fita.size(); i++) {
            System.out.print(fita.get(i));
        }
        System.out.print("|\n");

        System.out.print('-');
        for (int i = 0; i < fita.size(); i++) {
            if (i == cabeçote) {
                System.out.print('^');    
            } else {
                System.out.print('-');
            }
        }
        System.out.print("-\n");
    }

    private boolean doAssessments(Estado estadoAtual) {
        for (int i = 0; i < estadoAtual.getFuncoesDeTransicao().size(); i++) {
            FuncaoDeTransicao funcao = estadoAtual.getFuncoesDeTransicao().get(i);
            Character condicao = funcao.getCondicao();
            Character naFita = fita.get(cabeçote);
            if (condicao.equals(naFita)) {
                estadoAtual = transicao(funcao);
                printFita();

                if (estadoAtual.isAceitacao()) {
                    return true;
                }

                return doAssessments(estadoAtual);
            }
        }
        
        return false;
    }
    
    private Estado transicao(FuncaoDeTransicao funcao) {
        fita.replace(cabeçote, funcao.getSaida());

        if (fita.size() - 1 == cabeçote && !fita.get(cabeçote).equals('B')) {
            fita.add('B');
        } else if (cabeçote == 0 && !fita.get(cabeçote).equals('B')) {
            fita.addAt('B', 0);
        }
        
        switch (funcao.getMovimentoDoCabecote()) {
            case 'D':
            case 'd':
            case 'R':
            case 'r':
                cabeçote++;
                break;
            case 'E':
            case 'e':
            case 'L':
            case 'l':
                cabeçote--;
                break;
            default:
                throw new RuntimeException("Movimento do cabeçote não reconhecido.");
        }

        if (cabeçote > fita.size() - 1) {
            fita.add('B');
        } else if (cabeçote < 0) {
            fita.addAt('B', 0);
            cabeçote = 0;
        }

        return funcao.getDestino();
    }
    
}
