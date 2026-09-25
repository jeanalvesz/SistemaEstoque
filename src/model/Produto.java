package model;

public class Produto {

    private String nome;
    private int estoque;
    private double valor;

    public Produto(String nome, int estoque, double valor) {
        this.nome = nome;
        this.estoque = estoque;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public int getEstoque() {
        return estoque;
    }

    public double getValor() {
        return valor;
    }

    public void adicionarEstoque(int quantidade) {
        estoque += quantidade;
    }

    public void removerEstoque(int quantidade) {
        estoque = Math.max(0, estoque - quantidade);
    }

    public void alterarValor(double novoValor) {
        valor = novoValor;
    }

    @Override
    public String toString() {
        return nome +
                " | Estoque: " + estoque +
                " | Valor: R$" + valor;
    }
}