package Business;

import java.time.LocalTime;

public class Pedido {
    private String nome;
    private int totalProduto;
    private int qtdProdutos;
    private int prazo;
    private LocalTime horaFinalizacao;

    public Pedido(String nome, int totalProduto, int prazo) {
        this.nome = nome;
        this.totalProduto = totalProduto;
        this.qtdProdutos = totalProduto;
        this.prazo = prazo;
    }

    public void finalizarPedido(LocalTime horario) {
        this.horaFinalizacao = horario;
    }

    public boolean isFinalizado() {
        return this.horaFinalizacao != null;
    }

    public int getQtdProdutos() {
        return qtdProdutos;
    }

    public void entregarProdutos(int qtdProdutos) {
        if(qtdProdutos > 0) {
            if(this.qtdProdutos - qtdProdutos >= 0) {
                this.qtdProdutos -= qtdProdutos;
            } else {
                this.qtdProdutos = 0;
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public int getTotalProduto() {
        return totalProduto;
    }

    public int getPrazo() {
        return prazo;
    }

    @Override
    public String toString() {
        return "nome: " + this.nome + 
        "; total de produtos: " + this.totalProduto +
        "; prazo: " + (this.prazo != 0 ? this.prazo : "sem prazo") + 
        "; " + (isFinalizado() ? "horario de finalização: " + this.horaFinalizacao : "produtos a serem produzidos: " + this.qtdProdutos);
    }
}
