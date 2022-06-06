package Entity;

import Structure.Lista;

public class Estado {
    private final String nome;
    private Lista<FuncaoDeTransicao> funcoesDeTransicao = new Lista<>();
    private boolean inicial = false;
    private boolean aceitacao = false;

    public Estado(String nome, boolean inicial, boolean aceitacao) {
        this.nome = nome;
        this.inicial = inicial;
        this.aceitacao = aceitacao;
    }

    public Estado(Estado estado) {
        this.nome = estado.nome;
        this.aceitacao = estado.aceitacao;
        this.inicial = estado.inicial;
        this.funcoesDeTransicao = estado.funcoesDeTransicao;
    }

    public String getNome() {
        return nome;
    }

    public Lista<FuncaoDeTransicao> getFuncoesDeTransicao() {
        return funcoesDeTransicao;
    }

    public boolean isInicial() {
        return inicial;
    }

    public boolean isAceitacao() {
        return aceitacao;
    }

}