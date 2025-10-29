package clinica.entities;

public class Medico {
    private final String id;
    private final String nome;
    private final String especialidade;

    public Medico(String id, String nome, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEspecialidade() { return especialidade; }
}