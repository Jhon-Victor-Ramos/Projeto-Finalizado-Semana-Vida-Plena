package core.activities;

import core.Participant;
import clinica.entities.Medico;
import java.time.LocalDate;

public class Consulta {
    private final Participant participant;
    private final Medico medico;
    private final LocalDate data;
    private final double preco;

    public Consulta(Participant participant, Medico medico, double preco) {
        this.participant = participant;
        this.medico = medico;
        this.preco = preco;
        this.data = LocalDate.now();
    }

    public Participant getParticipant() { return participant; }
    public Medico getMedico() { return medico; }
    public LocalDate getData() { return data; }
    public double getPreco() { return preco; }
}