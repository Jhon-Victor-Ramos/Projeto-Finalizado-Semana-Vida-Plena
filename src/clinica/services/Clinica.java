package clinica.services;

import clinica.entities.Medico;
import core.Participant;
import core.activities.Consulta;
import core.activities.Evaluation;
import java.util.ArrayList;
import java.util.List;

public class Clinica {
    private final String name;
    private final List<Medico> medicos;
    private final List<Evaluation> evaluations;

    public Clinica(String name) {
        this.name = name;
        this.evaluations = new ArrayList<>();
        this.medicos = new ArrayList<>();
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

    public void contratarMedico(Medico medico) {
        this.medicos.add(medico);
    }

    public void agendarConsulta(Participant participant, Medico medico, double preco) {
        Consulta novaConsulta = new Consulta(participant, medico, preco);

        participant.adicionarConsulta(novaConsulta);

        System.out.println("Consulta agendada para " + participant.getName() +
                " com Dr(a). " + medico.getNome() +
                " (" + medico.getEspecialidade() + ").");
    }

    public String getName() { return name; }
    public List<Medico> getMedicos() { return medicos; }
}