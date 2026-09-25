package service;

import model.Produto;

import java.util.ArrayList;

public class EstoqueService {

    private ArrayList<Produto> produtos;

    public EstoqueService() {
        produtos = new ArrayList<>();

        produtos.add(new Produto("Caneta", 10, 2.50));
        produtos.add(new Produto("Caderno", 15, 12.00));
        produtos.add(new Produto("Borracha", 20, 1.50));
        produtos.add(new Produto("Lápis", 25, 1.00));
        produtos.add(new Produto("Apontador", 5, 3.00));
    }

    public Produto buscarProduto(String nome) {

        for (Produto produto : produtos) {

            if (produto.getNome().equalsIgnoreCase(nome)) {
                return produto;
            }
        }

        return null;
    }

    public boolean adicionarEstoque(String nome, int quantidade) {

        Produto produto = buscarProduto(nome);

        if (produto == null) {
            return false;
        }

        produto.adicionarEstoque(quantidade);

        return true;
    }

    public boolean removerEstoque(String nome, int quantidade) {

        Produto produto = buscarProduto(nome);

        if (produto == null) {
            return false;
        }

        produto.removerEstoque(quantidade);

        return true;
    }

    public boolean alterarValor(String nome, double novoValor) {

        Produto produto = buscarProduto(nome);

        if (produto == null) {
            return false;
        }

        produto.alterarValor(novoValor);

        return true;
    }

    public ArrayList<Produto> listarProdutos() {
        return produtos;
    }
}