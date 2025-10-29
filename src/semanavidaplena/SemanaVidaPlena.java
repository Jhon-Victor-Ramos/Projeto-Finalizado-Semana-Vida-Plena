package semanavidaplena;

import clinica.services.Clinica;
import core.Participant;
import core.activities.*;
import restaurant.services.Restaurant;
import eventos.services.GerenciadorDeEventos;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SemanaVidaPlena {
    private final Restaurant restaurante;
    private final Clinica clinica;
    private final GerenciadorDeEventos gerenciadorDeEventos;
    private final List<Participant> participants;

    // O gerente é "contratado" e já conhece os locais de trabalho.
    public SemanaVidaPlena(Restaurant restaurante, Clinica clinica, GerenciadorDeEventos gerenciadorDeEventos) {
        this.restaurante = restaurante;
        this.clinica = clinica;
        this.gerenciadorDeEventos = gerenciadorDeEventos;
        this.participants = new ArrayList<>();
    }

    // O gerente sabe como encontrar ou cadastrar um participante.
    public Participant findOrCreateParticipant(String name) {
        for (Participant p : participants) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        String newId = "P" + (participants.size() + 1);
        Participant newParticipant = new Participant(newId, name);
        participants.add(newParticipant);
        return newParticipant;
    }

    // O gerente dá acesso aos locais para o garçom, se necessário.
    public Restaurant getRestaurante() { return restaurante; }
    public Clinica getClinica() { return clinica; }
    public GerenciadorDeEventos getGerenciadorDeEventos() { return gerenciadorDeEventos; }

    private double calcularGastoTotalEmConsultas(Participant p) {
        double total = 0;
        // Para cada consulta no histórico do participante...
        for (Consulta consulta : p.getHistoricoConsultas()) {
            total += consulta.getPreco();
        }
        return total;
    }

    private double calcularGastoTotalEmEventos(Participant p) {
        double total = 0;
        for (InscricaoEvento inscricao : p.getHistoricoEventos()) {
            total += inscricao.getEvento().getPreco();
        }
        return total;
    }

    private double calcularGastoTotalEmPedidos(Participant p) {
        double total = 0;
        for (Order pedido : p.getHistoricoPedidos()) {
            total += pedido.calculateTotal();
        }
        return total;
    }

    public void printCrossSystemReport() {
        System.out.println("\n--- RELATÓRIO INTEGRADO DE ATIVIDADES E GASTOS ---");

        if (participants.isEmpty()) {
            System.out.println("Nenhum participante cadastrado no sistema ainda.");
            return;
        }

        for (Participant p : participants) {
            double gastoConsultas = calcularGastoTotalEmConsultas(p);
            double gastoPedidos = calcularGastoTotalEmPedidos(p);
            double gastoEventos = calcularGastoTotalEmEventos(p);
            double gastoTotal = gastoConsultas + gastoPedidos + gastoEventos;

            int numConsultas = p.getHistoricoConsultas().size();
            int numPedidos = p.getHistoricoPedidos().size();
            int numEventos = p.getHistoricoEventos().size();

            System.out.printf("—→ Participante: %s\n", p.getName());
            System.out.printf("                   └→ Consultas: %d (R$%.2f)\n", numConsultas, gastoConsultas);
            System.out.printf("                   └→ Pedidos: %d (R$%.2f)\n", numPedidos, gastoPedidos);
            System.out.printf("                   └→ Eventos: %d (R$%.2f)\n", numEventos, gastoEventos);
            System.out.printf("                   └→ GASTO TOTAL: R$%.2f\n", gastoTotal);
            System.out.println();
        }
        System.out.println("-------------------------------------------------");
    }

    // Métodos para responder as perguntas
    // 1. Os clientes costumam frequentar mais de um estabelecimento?
    public void analisarFrequenciaMultipla() {
        System.out.println("\n--- ANÁLISE: Frequência em Múltiplos Estabelecimentos ---");
        int contagemUmLocal = 0;
        int contagemDoisLocais = 0;
        int contagemTresLocais = 0;

        for (Participant p : participants) {
            int locaisVisitados = 0;
            if (!p.getHistoricoConsultas().isEmpty()) locaisVisitados++;
            if (!p.getHistoricoPedidos().isEmpty()) locaisVisitados++;
            if (!p.getHistoricoEventos().isEmpty()) locaisVisitados++;

            if (locaisVisitados == 1) contagemUmLocal++;
            else if (locaisVisitados == 2) contagemDoisLocais++;
            else if (locaisVisitados == 3) contagemTresLocais++;
        }

        System.out.printf("Participantes que frequentaram apenas 1 local: %d\n", contagemUmLocal);
        System.out.printf("Participantes que frequentaram 2 locais: %d\n", contagemDoisLocais);
        System.out.printf("Participantes que frequentaram todos os 3 locais (Jornada Plena): %d\n", contagemTresLocais);
        System.out.println("---------------------------------------------------------");
    }

    // 2. Gasto médio realizado em cada um dos locais
    public void calcularGastoMedioPorLocal() {
        System.out.println("\n--- ANÁLISE: Gasto Médio por Local ---");
        double totalGastoClinica = 0;
        double totalGastoRestaurante = 0;
        double totalGastoEventos = 0;
        int participantesComGastoClinica = 0;
        int participantesComGastoRestaurante = 0;
        int participantesComGastoEventos = 0;

        for (Participant p : participants) {
            double gastoClinica = calcularGastoTotalEmConsultas(p);
            if (gastoClinica > 0) {
                totalGastoClinica += gastoClinica;
                participantesComGastoClinica++;
            }
            double gastoRestaurante = calcularGastoTotalEmPedidos(p);
            if (gastoRestaurante > 0) {
                totalGastoRestaurante += gastoRestaurante;
                participantesComGastoRestaurante++;
            }
            double gastoEventos = calcularGastoTotalEmEventos(p);
            if (gastoEventos > 0) {
                totalGastoEventos += gastoEventos;
                participantesComGastoEventos++;
            }
        }

        double mediaClinica = (participantesComGastoClinica == 0) ? 0 : totalGastoClinica / participantesComGastoClinica;
        double mediaRestaurante = (participantesComGastoRestaurante == 0) ? 0 : totalGastoRestaurante / participantesComGastoRestaurante;
        double mediaEventos = (participantesComGastoEventos == 0) ? 0 : totalGastoEventos / participantesComGastoEventos;

        System.out.printf("Gasto médio por participante na Clínica: R$%.2f\n", mediaClinica);
        System.out.printf("Gasto médio por participante no Restaurante: R$%.2f\n", mediaRestaurante);
        System.out.printf("Gasto médio por participante em Eventos: R$%.2f\n", mediaEventos);
        System.out.println("-----------------------------------------");
    }

    // 3. Quantos clientes, após participar de uma palestra de saúde, realizam uma consulta na clínica?
    public void analisarConversaoPalestraSaude() {
        System.out.println("\n--- ANÁLISE: Conversão Palestra de Saúde -> Consulta ---");
        int participantesEmPalestraSaude = 0;
        int converteramParaConsulta = 0;

        for (Participant p : participants) {
            boolean participouPalestraSaude = false;
            // Verifica se o participante foi a uma palestra de saúde
            for (InscricaoEvento inscricao : p.getHistoricoEventos()) {
                if (inscricao.getEvento().getTipo().equalsIgnoreCase("Saúde")) {
                    participouPalestraSaude = true;
                    break;
                }
            }

            if (participouPalestraSaude) {
                participantesEmPalestraSaude++;
                // Se participou, verifica se tem alguma consulta no histórico
                if (!p.getHistoricoConsultas().isEmpty()) {
                    converteramParaConsulta++;
                }
            }
        }

        System.out.printf("Total de participantes em palestras de saúde: %d\n", participantesEmPalestraSaude);
        System.out.printf("Desses, %d também realizaram consultas na clínica.\n", converteramParaConsulta);
        System.out.println("---------------------------------------------------------");
    }

    // 4. Qual evento gera os clientes que mais gastam no restaurante?
    public void analisarGastoPorEventoDeOrigem() {
        System.out.println("\n--- ANÁLISE: Gasto no Restaurante por Evento de Origem do Voucher ---");
        Map<Evento, List<Double>> gastosPorEvento = new HashMap<>();

        for (Participant p : participants) {
            for (Order pedido : p.getHistoricoPedidos()) {
                if (pedido.getAppliedVoucher() != null) {
                    Evento eventoOrigem = pedido.getAppliedVoucher().getOriginEvent();
                    gastosPorEvento.computeIfAbsent(eventoOrigem, k -> new ArrayList<>()).add(pedido.calculateTotal());
                }
            }
        }

        gastosPorEvento.forEach((evento, gastos) -> {
            double total = gastos.stream().mapToDouble(Double::doubleValue).sum();
            double media = total / gastos.size();
            System.out.printf("Evento '\u001B[0;1m%s\u001B[0m': Média de gasto de R$%.2f por pedido com voucher.\n", evento.getNome(), media);
        });
        System.out.println("------------------------------------------------------------------");
    }

    // 5. Vouchers são usados com mais frequência logo após o evento?
    public void analisarTempoUsoVoucher() {
        System.out.println("\n--- ANÁLISE: Tempo Médio de Uso do Voucher Pós-Evento ---");
        List<Long> diasParaUso = new ArrayList<>();

        // Precisamos pegar a lista de todos os vouchers do GerenciadorDeEventos
        List<Voucher> todosOsVouchers = gerenciadorDeEventos.getIssuedVouchers();

        for (Voucher v : todosOsVouchers) {
            if (v.isUsed()) {
                long dias = java.time.temporal.ChronoUnit.DAYS.between(v.getIssueDate(), v.getUsageDate());
                diasParaUso.add(dias);
            }
        }

        if (diasParaUso.isEmpty()) {
            System.out.println("Nenhum voucher foi utilizado ainda.");
        } else {
            double mediaDias = diasParaUso.stream().mapToLong(Long::longValue).average().orElse(0.0);
            System.out.printf("O tempo médio para um voucher ser utilizado após o evento é de %.1f dias.\n", mediaDias);
        }
        System.out.println("---------------------------------------------------------");
    }

    // 6. O ticket médio de um participante que fez consulta com Nutricionista é maior no restaurante?
    public void compararTicketMedioNutricionista() {
        System.out.println("\n--- ANÁLISE: Impacto da Consulta com Nutricionista no Ticket Médio ---");
        double totalGastoComNutricionista = 0;
        int contagemPedidosComNutricionista = 0;
        double totalGastoSemNutricionista = 0;
        int contagemPedidosSemNutricionista = 0;

        for (Participant p : participants) {
            boolean foiEmNutricionista = false;
            for (Consulta c : p.getHistoricoConsultas()) {
                if (c.getMedico().getEspecialidade().equalsIgnoreCase("Nutricionista")) {
                    foiEmNutricionista = true;
                    break;
                }
            }

            if (!p.getHistoricoPedidos().isEmpty()) {
                if (foiEmNutricionista) {
                    totalGastoComNutricionista += calcularGastoTotalEmPedidos(p);
                    contagemPedidosComNutricionista += p.getHistoricoPedidos().size();
                } else {
                    totalGastoSemNutricionista += calcularGastoTotalEmPedidos(p);
                    contagemPedidosSemNutricionista += p.getHistoricoPedidos().size();
                }
            }
        }

        double mediaComNutri = (contagemPedidosComNutricionista == 0) ? 0 : totalGastoComNutricionista / contagemPedidosComNutricionista;
        double mediaSemNutri = (contagemPedidosSemNutricionista == 0) ? 0 : totalGastoSemNutricionista / contagemPedidosSemNutricionista;

        System.out.printf("Ticket médio no restaurante (após consulta com Nutricionista): R$%.2f\n", mediaComNutri);
        System.out.printf("Ticket médio no restaurante (sem consulta com Nutricionista): R$%.2f\n", mediaSemNutri);
        System.out.println("--------------------------------------------------------------------");
    }

    // 7. Faturamento total médio de um participante da "jornada plena"
    public void analisarFaturamentoJornadaPlena() {
        System.out.println("\n--- ANÁLISE: Faturamento Médio da Jornada Plena ---");
        double faturamentoTotalJornadaPlena = 0;
        int participantesJornadaPlena = 0;

        for (Participant p : participants) {
            boolean fezConsulta = !p.getHistoricoConsultas().isEmpty();
            boolean fezPedido = !p.getHistoricoPedidos().isEmpty();
            boolean foiAEvento = !p.getHistoricoEventos().isEmpty();

            if (fezConsulta && fezPedido && foiAEvento) {
                participantesJornadaPlena++;
                faturamentoTotalJornadaPlena += calcularGastoTotalEmConsultas(p);
                faturamentoTotalJornadaPlena += calcularGastoTotalEmPedidos(p);
                faturamentoTotalJornadaPlena += calcularGastoTotalEmEventos(p);
            }
        }

        double mediaFaturamento = (participantesJornadaPlena == 0) ? 0 : faturamentoTotalJornadaPlena / participantesJornadaPlena;

        System.out.printf("Total de participantes que completaram a Jornada Plena: %d\n", participantesJornadaPlena);
        System.out.printf("Faturamento médio gerado por esses participantes: R$%.2f\n", mediaFaturamento);
        System.out.println("-----------------------------------------------------");
    }

    // 8. Qual é a atividade com a maior avaliação média geral?
    public void analisarAvaliacaoMediaPorAtividade() {
        System.out.println("\n--- ANÁLISE: Avaliação Média por Atividade ---");
        double mediaRestaurante = restaurante.getAverageRating();
        // double mediaClinica = clinica.getAverageRating(); // Você precisará criar este método em Clinica.java
        // double mediaEventos = gerenciadorDeEventos.getAverageRating(); // E este em GerenciadorDeEventos.java

        System.out.printf("Avaliação média do Restaurante: %.2f estrelas.\n", mediaRestaurante);
        // System.out.printf("Avaliação média da Clínica: %.2f estrelas.\n", mediaClinica);
        // System.out.printf("Avaliação média dos Eventos: %.2f estrelas.\n", mediaEventos);
        System.out.println("------------------------------------------------");
    }

    // 9. O perfil que completa a "jornada plena" tende a participar mais de palestras de 'Saúde' ou de 'Culinária'?
    public void analisarPerfilJornadaPlena() {
        System.out.println("\n--- ANÁLISE: Perfil de Interesses da Jornada Plena ---");
        int contagemSaude = 0;
        int contagemCulinaria = 0;
        int contagemMusica = 0;

        for (Participant p : participants) {
            boolean jornadaPlena = !p.getHistoricoConsultas().isEmpty() &&
                    !p.getHistoricoPedidos().isEmpty() &&
                    !p.getHistoricoEventos().isEmpty();

            if (jornadaPlena) {
                for (InscricaoEvento inscricao : p.getHistoricoEventos()) {
                    String tipo = inscricao.getEvento().getTipo();
                    if (tipo.equalsIgnoreCase("Saúde")) contagemSaude++;
                    else if (tipo.equalsIgnoreCase("Culinária")) contagemCulinaria++;
                    else if (tipo.equalsIgnoreCase("Música")) contagemMusica++;
                }
            }
        }

        System.out.println("Dentro do grupo que completou a Jornada Plena:");
        System.out.printf("- Inscrições em eventos de Saúde: %d\n", contagemSaude);
        System.out.printf("- Inscrições em eventos de Culinária: %d\n", contagemCulinaria);
        System.out.printf("- Inscrições em eventos de Música: %d\n", contagemMusica);
        System.out.println("-----------------------------------------------------");
    }
}