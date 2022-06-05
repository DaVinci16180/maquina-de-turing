package Entity;
public class FuncaoDeTransicao {
    private Character condicao;
    private Character saida;
    private Estado origem;
    private Estado destino;
    private Character movimentoDoCabecote;

    public Character getCondicao() {
        return condicao;
    }

    public void setCondicao(Character condicao) {
        this.condicao = condicao;
    }

    public Character getSaida() {
        return saida;
    }

    public void setSaida(Character saida) {
        this.saida = saida;
    }

    public Estado getOrigem() {
        return origem;
    }

    public void setOrigem(Estado origem) {
        this.origem = origem;
    }

    public Estado getDestino() {
        return destino;
    }

    public void setDestino(Estado destino) {
        this.destino = destino;
    }

    public Character getMovimentoDoCabecote() {
        return movimentoDoCabecote;
    }

    public void setMovimentoDoCabecote(Character movimentoDoCabecote) {
        this.movimentoDoCabecote = movimentoDoCabecote;
    }

}
