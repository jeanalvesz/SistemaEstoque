package model;

public class Produto {

    private int codigo;
    private String nome;
    private int estoque;
    private double valor;

    public Produto(int codigo, String nome, int estoque, double valor) {
        this.codigo = codigo;
        this.nome = nome;
        this.estoque = estoque;
        this.valor = valor;
    }

    public int getCodigo() {
        return codigo;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public void adicionarEstoque(int quantidade) {
        estoque += quantidade;
    }

    public void removerEstoque(int quantidade) {
        estoque -= quantidade;
    }

    public void alterarValor(double novoValor) {
        valor = novoValor;
    }

    @Override
    public String toString() {
        return "Código: " + codigo +
                " | Produto: " + nome +
                " | Estoque: " + estoque +
                " | Valor: R$ " + String.format("%.2f", valor);
    }
}