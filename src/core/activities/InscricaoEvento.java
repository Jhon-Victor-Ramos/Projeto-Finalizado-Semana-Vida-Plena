package core.activities;

import core.Participant;
import java.time.LocalDate;

public class InscricaoEvento {
    private final Participant participant;
    private final Evento evento;
    private final LocalDate dataInscricao;

    public InscricaoEvento(Participant participant, Evento evento) {
        this.participant = participant;
        this.evento = evento;
        this.dataInscricao = LocalDate.now();
    }

    // Getters
    public Participant getParticipant() { return participant; }
    public Evento getEvento() { return evento; }
    public LocalDate getDataInscricao() { return dataInscricao; }
}