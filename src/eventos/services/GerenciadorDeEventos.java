package eventos.services;

import core.Participant;
import core.activities.Evento;
import core.activities.Evaluation;
import core.activities.InscricaoEvento;
import core.activities.Voucher;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeEventos {
    private final String nome;
    private final List<Evento> catalogoEventos;
    private final List<Voucher> issuedVouchers = new ArrayList<>();
    private final List<Evaluation> evaluations;

    public GerenciadorDeEventos(String nome) {
        this.nome = nome;
        this.catalogoEventos = new ArrayList<>();
        this.evaluations = new ArrayList<>();
    }

    public void addEvaluation(Evaluation eval) {
        this.evaluations.add(eval);
    }

    public double getAverageRating() {
        if (evaluations.isEmpty()) {
            return 0.0;
        }
        double totalRating = 0;
        for (Evaluation eval : evaluations) {
            totalRating += eval.getRating();
        }
        return totalRating / evaluations.size();
    }

    public void adicionarEventoAoCatalogo(Evento evento) {
        this.catalogoEventos.add(evento);
    }

    public void inscreverParticipante(Participant participant, Evento evento) {
        InscricaoEvento novaInscricao = new InscricaoEvento(participant, evento);
        participant.adicionarInscricao(novaInscricao);

        String voucherId = "V-" + evento.getId() + "-" + participant.getId();
        Voucher newVoucher = new Voucher(voucherId, participant, evento);
        issuedVouchers.add(newVoucher);
        System.out.println(participant.getName() + " foi inscrito(a) com sucesso no evento: " + evento.getNome());
        System.out.println("└→ " + participant.getName() + " inscrito(a) e recebeu o voucher \u001B[0;1m" + newVoucher.getId() + "\u001B[0m");
    }

    public String getNome() { return nome; }
    public List<Evento> getCatalogoEventos() { return catalogoEventos; }
    public List<Voucher> getIssuedVouchers() { return issuedVouchers; }
}