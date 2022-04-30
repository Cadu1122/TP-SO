package Business;

public class BracoMecanico {
    private Pacote pacote;

    public void pegar(Pacote pacote) {
        this.pacote = pacote;
    }

    public Pacote entregar() {
        if(pacote != null) {
            Pacote pacote = this.pacote;
            this.pacote = null;
            return pacote;
        } else {
            throw new IndexOutOfBoundsException("Não há pacotes para entregar");
        }
    }
}
