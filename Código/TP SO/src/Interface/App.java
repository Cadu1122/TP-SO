package Interface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import Business.Pedido;
import Business.Simulacao;

public class App {
    public static void main(String[] args) {
        Simulacao simulacao = new Simulacao();
        File file = new File("dados_tp01.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            String[] pedidos;
            int qtdPedidos = Integer.parseInt(scanner.nextLine());
            for(int i = 1; i <= qtdPedidos; i++) {
                pedidos = scanner.nextLine().split(";");
                Pedido pedido = new Pedido(pedidos[0], Integer.parseInt(pedidos[1]), Integer.parseInt(pedidos[2]));
                simulacao.addPedido(pedido);
            }
            simulacao.executar();
            for(int i = 0; i < simulacao.sizePedidos(); i++) {
                System.out.println(simulacao.getPedido(i));
            }
            System.out.println("Tempo de término: " + simulacao.getTempo());
            System.out.println("Quantidade de pedidos entregues após o prazo: " + simulacao.getQtdPedidosPerdidos());
            System.out.println("Quantidade de pedidos entregues antes de meio dia: " + simulacao.getQtdPedidosAntes12());
            System.out.println("Média do horário de pedidos entregues: " + simulacao.getMediaTempoEntrega());
        } catch (FileNotFoundException e) {
            System.err.println("O arquivo " + file.getPath() + " não foi encontrado.");
        } catch (PatternSyntaxException e) {
            System.err.println("O arquivo " + file.getPath() + " não está no formato correto.");
        } catch (NoSuchElementException e) {
            System.err.println("O arquivo " + file.getPath() + " não possui a quantidade de elementos que deveria");
        } finally {
            scanner.close();
        }
    }
}
