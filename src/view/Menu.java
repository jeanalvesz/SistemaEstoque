package view;

import model.Produto;
import service.EstoqueService;
import util.LogService;

import javax.swing.*;
import java.util.ArrayList;

public class Menu {

    private final EstoqueService estoqueService = new EstoqueService();
    private final LogService logService = new LogService();
    private String usuario;

    public void iniciar() {

        usuario = lerTexto("Digite seu nome:");
        if (usuario == null) return;

        boolean continuar = true;

        while (continuar) {

            String opcao = JOptionPane.showInputDialog(
                    "Olá, " + usuario + "!\n\n" +
                            "1 - Adicionar estoque\n" +
                            "2 - Remover estoque\n" +
                            "3 - Editar valor\n" +
                            "4 - Ver produtos\n" +
                            "5 - Ver log\n" +
                            "6 - Cadastrar produto\n" +
                            "7 - Editar produto\n" +
                            "8 - Excluir produto\n" +
                            "9 - Encerrar"
            );

            if (opcao == null) break;

            switch (opcao) {
                case "1" -> adicionarEstoque();
                case "2" -> removerEstoque();
                case "3" -> alterarValor();
                case "4" -> listarProdutos();
                case "5" -> mostrarLog();
                case "6" -> cadastrarProduto();
                case "7" -> editarProduto();
                case "8" -> excluirProduto();
                case "9" -> continuar = false;
                default -> mensagem("Opção inválida.");
            }
        }

        mensagem("Programa encerrado.\n\nLog final:\n" + logService.obterLog());
    }

    private Produto selecionarProduto() {

        String identificador = lerTexto("Digite o código ou nome do produto:");
        if (identificador == null) return null;

        Produto produto = estoqueService.buscarProduto(identificador);

        if (produto == null)
            mensagem("Produto não encontrado.");

        return produto;
    }

    private void adicionarEstoque() {

        Produto produto = selecionarProduto();
        if (produto == null) return;

        int quantidade = lerInteiro("Quantidade a adicionar:", 1);
        if (quantidade == -1) return;

        int anterior = produto.getEstoque();

        produto.adicionarEstoque(quantidade);

        registrarLog("adicionou " + quantidade + " unidades de " +
                produto.getNome() + ". Estoque: " + anterior +
                " → " + produto.getEstoque());

        mensagem("Estoque atualizado com sucesso!");
    }

    private void removerEstoque() {

        Produto produto = selecionarProduto();
        if (produto == null) return;

        int quantidade = lerInteiro("Quantidade a remover:", 1);
        if (quantidade == -1) return;

        if (quantidade > produto.getEstoque()) {
            mensagem("Estoque insuficiente.\n\nEstoque atual: " +
                    produto.getEstoque() +
                    "\nQuantidade solicitada: " + quantidade);
            return;
        }

        int anterior = produto.getEstoque();

        produto.removerEstoque(quantidade);

        registrarLog("removeu " + quantidade + " unidades de " +
                produto.getNome() + ". Estoque: " + anterior +
                " → " + produto.getEstoque());

        mensagem("Estoque atualizado com sucesso!");
    }

    private void alterarValor() {

        Produto produto = selecionarProduto();
        if (produto == null) return;

        double anterior = produto.getValor();

        double novoValor = lerValor(
                "Valor atual: R$ " + String.format("%.2f", anterior) +
                        "\n\nDigite o novo valor:"
        );

        if (novoValor == -1) return;

        produto.alterarValor(novoValor);

        registrarLog("alterou o valor de " + produto.getNome() +
                " de R$ " + String.format("%.2f", anterior) +
                " para R$ " + String.format("%.2f", novoValor));

        mensagem("Valor alterado com sucesso!");
    }

    private void cadastrarProduto() {

        int codigo = lerInteiro("Digite o código do produto:", 1);
        if (codigo == -1) return;

        if (estoqueService.codigoExiste(codigo)) {
            mensagem("Já existe um produto com esse código.");
            return;
        }

        String nome = lerTexto("Digite o nome do produto:");
        if (nome == null) return;

        int estoque = lerInteiro("Digite o estoque inicial:", 0);
        if (estoque == -1) return;

        double valor = lerValor("Digite o valor do produto:");
        if (valor == -1) return;

        estoqueService.adicionarProduto(
                new Produto(codigo, nome, estoque, valor)
        );

        registrarLog("cadastrou o produto " +
                nome + " (Código: " + codigo + ")");

        mensagem("Produto cadastrado com sucesso!");
    }

    private void editarProduto() {

        Produto produto = selecionarProduto();
        if (produto == null) return;

        String nomeAnterior = produto.getNome();

        String nome = lerTexto(
                "Nome atual: " + produto.getNome() +
                        "\n\nDigite o novo nome:"
        );
        if (nome == null) return;

        double valor = lerValor(
                "Valor atual: R$ " +
                        String.format("%.2f", produto.getValor()) +
                        "\n\nDigite o novo valor:"
        );
        if (valor == -1) return;

        int estoque = lerInteiro(
                "Estoque atual: " + produto.getEstoque() +
                        "\n\nDigite o novo estoque:", 0
        );
        if (estoque == -1) return;

        produto.setNome(nome);
        produto.setEstoque(estoque);
        produto.alterarValor(valor);

        registrarLog("editou o produto " + nomeAnterior +
                " para " + nome +
                " (Código: " + produto.getCodigo() + ")");

        mensagem("Produto alterado com sucesso!");
    }

    private void excluirProduto() {

        Produto produto = selecionarProduto();
        if (produto == null) return;

        int resposta = JOptionPane.showConfirmDialog(
                null,
                "Deseja realmente excluir o produto?\n\n" +
                        "Código: " + produto.getCodigo() +
                        "\nProduto: " + produto.getNome() +
                        "\nEstoque: " + produto.getEstoque() +
                        "\nValor: R$ " + String.format("%.2f", produto.getValor()),
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta != JOptionPane.YES_OPTION) return;

        estoqueService.removerProduto(
                String.valueOf(produto.getCodigo())
        );

        registrarLog("excluiu o produto " +
                produto.getNome() +
                " (Código: " + produto.getCodigo() + ")");

        mensagem("Produto excluído com sucesso!");
    }

    private void listarProdutos() {

        ArrayList<Produto> produtos = estoqueService.listarProdutos();

        if (produtos.isEmpty()) {
            mensagem("Nenhum produto cadastrado.");
            return;
        }

        StringBuilder lista = new StringBuilder("PRODUTOS\n\n");

        for (Produto produto : produtos)
            lista.append(produto).append("\n");

        mensagem(lista.toString());
    }

    private void mostrarLog() {
        mensagem("LOG DE OPERAÇÕES\n\n" + logService.obterLog());
    }

    private int lerInteiro(String mensagem, int minimo) {

        while (true) {

            String entrada = JOptionPane.showInputDialog(mensagem);

            if (entrada == null) return -1;

            try {
                int valor = Integer.parseInt(entrada.trim());

                if (valor < minimo) {
                    mensagem("Digite um valor maior ou igual a " + minimo + ".");
                    continue;
                }

                return valor;

            } catch (NumberFormatException e) {
                mensagem("Digite apenas números inteiros.");
            }
        }
    }

    private double lerValor(String mensagem) {

        while (true) {

            String entrada = JOptionPane.showInputDialog(mensagem);

            if (entrada == null) return -1;

            try {
                double valor = Double.parseDouble(
                        entrada.replace(",", ".").trim()
                );

                if (valor <= 0) {
                    mensagem("O valor deve ser maior que zero.");
                    continue;
                }

                return valor;

            } catch (NumberFormatException e) {
                mensagem("Digite um valor válido.\nExemplo: 10,50");
            }
        }
    }

    private String lerTexto(String mensagem) {

        while (true) {

            String entrada = JOptionPane.showInputDialog(mensagem);

            if (entrada == null) return null;

            entrada = entrada.trim();

            if (!entrada.isEmpty())
                return entrada;

            mensagem("O campo não pode ficar vazio.");
        }
    }

    private void registrarLog(String operacao) {
        logService.registrar(usuario + " " + operacao);
    }

    private void mensagem(String texto) {
        JOptionPane.showMessageDialog(null, texto);
    }
}