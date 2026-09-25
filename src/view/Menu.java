package view;

import model.Produto;
import service.EstoqueService;
import util.LogService;

import javax.swing.*;
import java.util.ArrayList;

public class Menu {

    private EstoqueService estoqueService;
    private LogService logService;
    private String usuario;

    public Menu() {
        estoqueService = new EstoqueService();
        logService = new LogService();
    }

    public void iniciar() {

        usuario = JOptionPane.showInputDialog(
                "Digite seu nome:"
        );

        boolean continuar = true;

        while (continuar) {

            String opcao = JOptionPane.showInputDialog(
                    "Olá, " + usuario + "!\n\n" +
                            "1 - Adicionar estoque\n" +
                            "2 - Remover estoque\n" +
                            "3 - Editar valor\n" +
                            "4 - Ver produtos\n" +
                            "5 - Ver log\n" +
                            "6 - Encerrar"
            );

            if (opcao == null) {
                break;
            }

            switch (opcao) {

                case "1":
                    adicionarEstoque();
                    break;

                case "2":
                    removerEstoque();
                    break;

                case "3":
                    alterarValor();
                    break;

                case "4":
                    listarProdutos();
                    break;

                case "5":
                    mostrarLog();
                    break;

                case "6":
                    continuar = false;
                    break;

                default:
                    JOptionPane.showMessageDialog(
                            null,
                            "Opção inválida."
                    );
            }
        }

        JOptionPane.showMessageDialog(
                null,
                "Programa encerrado.\n\n" +
                        "Log final:\n" +
                        logService.obterLog()
        );
    }

    private void adicionarEstoque() {

        String nome = JOptionPane.showInputDialog(
                "Nome do produto:"
        );

        int quantidade = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Quantidade a adicionar:"
                )
        );

        Produto produto = estoqueService.buscarProduto(nome);

        if (produto == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Produto não encontrado."
            );

            return;
        }

        estoqueService.adicionarEstoque(nome, quantidade);

        logService.registrar(
                usuario +
                        " adicionou " +
                        quantidade +
                        " unidades de " +
                        produto.getNome() +
                        ". Estoque atual: " +
                        produto.getEstoque()
        );

        JOptionPane.showMessageDialog(
                null,
                "Estoque atualizado com sucesso!"
        );
    }

    private void removerEstoque() {

        String nome = JOptionPane.showInputDialog(
                "Nome do produto:"
        );

        int quantidade = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Quantidade a remover:"
                )
        );

        Produto produto = estoqueService.buscarProduto(nome);

        if (produto == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Produto não encontrado."
            );

            return;
        }

        estoqueService.removerEstoque(nome, quantidade);

        logService.registrar(
                usuario +
                        " removeu " +
                        quantidade +
                        " unidades de " +
                        produto.getNome() +
                        ". Estoque atual: " +
                        produto.getEstoque()
        );

        JOptionPane.showMessageDialog(
                null,
                "Estoque atualizado com sucesso!"
        );
    }

    private void alterarValor() {

        String nome = JOptionPane.showInputDialog(
                "Nome do produto:"
        );

        double novoValor = Double.parseDouble(
                JOptionPane.showInputDialog(
                        "Novo valor:"
                )
        );

        Produto produto = estoqueService.buscarProduto(nome);

        if (produto == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Produto não encontrado."
            );

            return;
        }

        double valorAnterior = produto.getValor();

        estoqueService.alterarValor(nome, novoValor);

        logService.registrar(
                usuario +
                        " alterou o valor de " +
                        produto.getNome() +
                        " de R$" +
                        valorAnterior +
                        " para R$" +
                        novoValor
        );

        JOptionPane.showMessageDialog(
                null,
                "Valor alterado com sucesso!"
        );
    }

    private void listarProdutos() {

        StringBuilder lista = new StringBuilder();

        lista.append("PRODUTOS\n\n");

        ArrayList<Produto> produtos =
                estoqueService.listarProdutos();

        for (Produto produto : produtos) {

            lista.append(produto)
                    .append("\n");
        }

        JOptionPane.showMessageDialog(
                null,
                lista.toString()
        );
    }

    private void mostrarLog() {

        JOptionPane.showMessageDialog(
                null,
                "LOG DE OPERAÇÕES\n\n" +
                        logService.obterLog()
        );
    }
}