package Business;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Esteira extends Thread {
    public static final float TEMPO_PROD_PACOTE = 5;
    public static final float TEMPO_TRANSICAO = 0.5F;

    private List<Pedido> pedidos;
    private BracoMecanico braco;

    public Esteira(BracoMecanico braco) {
        pedidos = new LinkedList<>();
        this.braco = braco;
    }

    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public boolean hasPedido() {
        return !pedidos.isEmpty();
    }

    public Pedido getPedido(int pos) {
        if(pos < 0 && pos >= pedidos.size()) {
            throw new IndexOutOfBoundsException("Não existe pedido na posicao " + pos);
        }
        return pedidos.get(pos);
    }

    public void removePedido(Pedido pedido) {
        pedidos.remove(pedido);
    }

    public Pacote produzirPacote() {
        if(!pedidos.isEmpty()) {
            Pedido pedido = selecionarPedido();
            int qtdProdutos;
            if(pedido.getQtdProdutos() > Pacote.CAPACIDADE) {
                qtdProdutos = Pacote.CAPACIDADE;
            } else {
                qtdProdutos = pedido.getQtdProdutos();
                removePedido(pedido);
            }
            if(pedido.entregarProdutos() == true) {
                Pacote pacote = new Pacote(qtdProdutos, pedido);
                braco.pegar(pacote);
                return pacote;
            } else {
                produzirPacote();
            }
        }
        return null;
    }

    @Override
    public void run() {
        produzirPacote();
    }

    private Pedido selecionarPedido() {
        Pedido escolhido = pedidos.get(0);
        for (Pedido pedido : pedidos) {
            if (pedido.getPrazo() != escolhido.getPrazo()) {
                if(escolhido.getPrazo().equals(LocalTime.of(8, 0)) || (pedido.getPrazo().isBefore(escolhido.getPrazo()) && !pedido.getPrazo().equals(LocalTime.of(8, 0)))) {
                    escolhido = pedido;
                }
            } else {
                if(pedido.getQtdProdutos() < escolhido.getQtdProdutos()) {
                    escolhido = pedido;
                }
            }
        }
        return escolhido;
    }
}