package Business;

public class Pacote {
    public static final int VOLUME_MAX = 5000;
    public static final int CAPACIDADE = VOLUME_MAX / Produto.VOLUME;

    private int qtdProdutos;
    private Pedido pedido;

    public Pacote(int qtdProdutos, Pedido pedido) {
        this.qtdProdutos = qtdProdutos;
        this.pedido = pedido;
    }

    public int getQtdProdutos() {
        return qtdProdutos;
    }

    public Pedido getPedido() {
        return pedido;
    }
}
