package Business;

import java.util.LinkedList;
import java.util.List;

public class Esteira {
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
            pedido.entregarProdutos(qtdProdutos);
            Pacote pacote = new Pacote(qtdProdutos, pedido);
            braco.pegar(pacote);
            return pacote;
        }
        return null;
    }

    private Pedido selecionarPedido() {
        Pedido escolhido = pedidos.get(0);
        for (Pedido pedido : pedidos) {
            if (pedido.getPrazo() != escolhido.getPrazo()) {
                if((escolhido.getPrazo() == 0) || (pedido.getPrazo() < escolhido.getPrazo() && pedido.getPrazo() != 0)) {
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