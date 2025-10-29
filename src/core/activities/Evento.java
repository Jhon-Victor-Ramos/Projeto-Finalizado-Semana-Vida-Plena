package core.activities;

public class Evento {
    private final String id;
    private final String nome;
    private final String tipo; // "Saúde", "Culinária", "Música"
    private final double preco;

    public Evento(String id, String nome, String tipo, double preco) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
    }

    // Getters para ler as informações
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public double getPreco() { return preco; }
}