package service;

import model.Produto;

import java.util.ArrayList;

public class EstoqueService {

    private ArrayList<Produto> produtos;

    public EstoqueService() {

        produtos = new ArrayList<>();

        produtos.add(new Produto(1, "Caneta", 10, 2.50));
        produtos.add(new Produto(2, "Caderno", 15, 12.00));
        produtos.add(new Produto(3, "Borracha", 20, 1.50));
        produtos.add(new Produto(4, "Lápis", 25, 1.00));
        produtos.add(new Produto(5, "Apontador", 5, 3.00));
    }

    public ArrayList<Produto> listarProdutos() {
        return produtos;
    }

    // Buscar produto pelo código ou pelo nome
    public Produto buscarProduto(String identificador) {

        // Busca pelo nome
        for (Produto produto : produtos) {

            if (produto.getNome().equalsIgnoreCase(identificador)) {
                return produto;
            }
        }

        // Busca pelo código
        try {

            int codigo = Integer.parseInt(identificador);

            return buscarProdutoPorCodigo(codigo);

        } catch (NumberFormatException e) {

            return null;
        }
    }

    // Buscar produto pelo código
    public Produto buscarProdutoPorCodigo(int codigo) {

        for (Produto produto : produtos) {

            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }

        return null;
    }

    // Verificar se o código já existe
    public boolean codigoExiste(int codigo) {

        return buscarProdutoPorCodigo(codigo) != null;
    }

    // Cadastrar produto
    public void adicionarProduto(Produto produto) {

        produtos.add(produto);
    }

    // Excluir produto
    public boolean removerProduto(String identificador) {

        Produto produto = buscarProduto(identificador);

        if (produto != null) {

            produtos.remove(produto);

            return true;
        }

        return false;
    }

    // Adicionar estoque
    public void adicionarEstoque(String identificador, int quantidade) {

        Produto produto = buscarProduto(identificador);

        if (produto != null) {

            produto.adicionarEstoque(quantidade);
        }
    }

    // Remover estoque
    public void removerEstoque(String identificador, int quantidade) {

        Produto produto = buscarProduto(identificador);

        if (produto != null) {

            produto.removerEstoque(quantidade);
        }
    }

    // Alterar valor
    public void alterarValor(String identificador, double novoValor) {

        Produto produto = buscarProduto(identificador);

        if (produto != null) {

            produto.alterarValor(novoValor);
        }
    }
}