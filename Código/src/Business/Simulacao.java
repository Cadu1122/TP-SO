package Business;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

public class Simulacao {
    private LocalTime horario;
    private int qtdPedidosPerdidos;
    private BracoMecanico[] braco;
    private Esteira[] esteira;
    private List<Pedido> pedidos;
    private List<Pedido> pedidosIncluir;
    private int qtdPedidosAntes12;
    private LocalTime mediaTempoEntrega;
    private long media;

    public Simulacao() {
        horario = LocalTime.of(8, 0);
        pedidos = new LinkedList<>();
        braco = new BracoMecanico[2];
        braco[0] = new BracoMecanico();
        braco[1] = new BracoMecanico();
        pedidosIncluir = new LinkedList<>();
        esteira = new Esteira[2];
        esteira[0] = new Esteira(braco[0]);
        esteira[1] = new Esteira(braco[1]);
        this.qtdPedidosPerdidos = 0;
        this.qtdPedidosAntes12 = 0;
    }

    public LocalTime getMediaTempoEntrega() {
        return mediaTempoEntrega;
    }

    public int getQtdPedidosAntes12() {
        return qtdPedidosAntes12;
    }

    public int getQtdPedidosPerdidos() {
        return qtdPedidosPerdidos;
    }

    public int sizePedidos() {
        return pedidos.size();
    }

    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public Pedido getPedido(int index) {
        return pedidos.get(index);
    }

    public LocalTime getTempo() {
        return horario;
    }

    private void init () {
        for (Pedido pedido : pedidos) {
            if(pedido.getHoraInicio().equals(LocalTime.of(8, 0))) {
                for (Esteira esteira : esteira) {
                    esteira.addPedido(pedido);
                }
            } else {
                pedidosIncluir.add(pedido);
            }
        }
    }

    public void executar() {
        init();
        while(esteira[0].hasPedido() && esteira[1].hasPedido()) {
            moverEsteira();
            for (Esteira esteira : esteira) {
                try {
                    esteira.join();
                } catch (InterruptedException e) {
                    System.err.println("Thread interrompida");
                }
            }
            for (BracoMecanico bracoMecanico : braco) {
                Pacote pacote = bracoMecanico.entregar();
                if(pacote != null) {
                    analizarDados(pacote.getPedido());
                }
            }
            incluirPedidos();
        }
        mediaTempoEntrega = LocalTime.ofSecondOfDay(media / pedidos.size());
    }

    private void analizarDados(Pedido pedido) {
        if (pedido.getQtdProdutos() <= 0 && !pedido.isFinalizado()) {
            pedido.finalizarPedido(horario);
            if (pedido.getPrazo().isBefore(horario) && !pedido.getPrazo().equals(LocalTime.of(8, 0))) {
                this.qtdPedidosPerdidos++;
            }
            if(LocalTime.of(12, 0).isAfter(horario)) {
                this.qtdPedidosAntes12++;
            }
            media += horario.getLong(ChronoField.SECOND_OF_DAY);
        }
    }

    private void incluirPedidos() {
        List<Pedido> pedidosRemover = new LinkedList<>();
        for (Pedido pedido : pedidosIncluir) {
            if(pedido.getHoraInicio().isBefore(horario)) {
                for (Esteira esteira : esteira) {
                    esteira.addPedido(pedido);
                }
                pedidosRemover.add(pedido);
            }
        }
        for (Pedido pedido : pedidosRemover) {
            pedidosIncluir.remove(pedido);
        }
    }

    private void moverEsteira() {
        horario = horario.plus((int) (1000 * Esteira.TEMPO_TRANSICAO), ChronoUnit.MILLIS);
        for (Esteira esteira : esteira) {
            esteira.run();
        }
        horario = horario.plusSeconds(5);
    }
}
