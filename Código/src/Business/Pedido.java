package Business;

import java.time.LocalTime;

public class Pedido {
    private String nome;
    private int totalProduto;
    private int qtdProdutos;
    private LocalTime prazo;
    private LocalTime horaInicio;
    private LocalTime horaFinalizacao;

    public Pedido(String nome, int totalProduto, int prazo, LocalTime horaInicio) {
        this.nome = nome;
        this.totalProduto = totalProduto;
        this.qtdProdutos = totalProduto;
        this.horaInicio = horaInicio;
        if (prazo != 0) {
            this.prazo = horaInicio.plusMinutes(prazo);
        } else {
            this.prazo = LocalTime.of(8, 0);
        }
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
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

    public synchronized boolean entregarProdutos() {
        if(qtdProdutos == 0) {
            return false;
        }
        if(this.qtdProdutos - Pacote.CAPACIDADE >= 0) {
            this.qtdProdutos -= Pacote.CAPACIDADE;
        } else {
            this.qtdProdutos = 0;
        }
        return true;
    }

    public String getNome() {
        return nome;
    }

    public int getTotalProduto() {
        return totalProduto;
    }

    public LocalTime getPrazo() {
        return prazo;
    }

    @Override
    public String toString() {
        return "nome: " + this.nome + 
        "; total de produtos: " + this.totalProduto +
        "; prazo: " + (!this.prazo.equals(LocalTime.of(8, 0)) ? this.prazo : "sem prazo") + 
        "; horário de chegada: " + this.horaInicio +
        "; " + (isFinalizado() ? "horario de finalização: " + this.horaFinalizacao : "produtos a serem produzidos: " + this.qtdProdutos);
    }
}
