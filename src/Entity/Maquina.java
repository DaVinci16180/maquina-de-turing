package Entity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import Structure.ListaDupla;

public class Maquina {
    private ListaDupla<Character> fita;
    private List<Estado> estados;
    private int cabeçote = 1;
    
    public ListaDupla<Character> getFita() {
        return fita;
    }
    
    public void setFita(ListaDupla<Character> fita) {
        this.fita = fita;
    }
    
    public List<Estado> getEstados() {
        return estados;
    }
    
    public void setEstados(List<Estado> estados) {
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
        Optional<Estado> opt = estados.stream().filter(Estado::isInicial).findFirst();

        if (opt.isEmpty()) {
            throw new RuntimeException("Não existe estado inicial");
        }

        return opt.get();
    }

    public boolean assess(String string) {
        preencherFita(string);

        try {
            printFita();
            return doAssessments(getEstadoInicial());
        } catch (Exception e) {
            return false;
        }
    }

    private void printFita() {
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

        if (fita.size() == cabeçote && !fita.get(cabeçote).equals('B')) {
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

        if (cabeçote > fita.size()) {
            fita.add('B');
        } else if (cabeçote < 0) {
            fita.addAt('B', 0);
            cabeçote = 0;
        }

        return funcao.getDestino();
    }
    
}
