package core;

import core.activities.Consulta;
import core.activities.Evaluation;
import core.activities.Order;
import core.activities.InscricaoEvento;
import core.activities.Voucher;
import java.util.ArrayList;
import java.util.List;

public class Participant {
    private final String id;
    private final String name;
    private Voucher activeVoucher;

    private final List<Consulta> historicoConsultas;
    private final List<InscricaoEvento> historicoEventos;
    private final List<Order> historicoPedidos;
    private final List<Evaluation> historicoAvaliacoes;

    public Participant(String id, String name) {
        this.id = id;
        this.name = name;
        this.activeVoucher = null;
        this.historicoConsultas = new ArrayList<>();
        this.historicoEventos = new ArrayList<>();
        this.historicoPedidos = new ArrayList<>();
        this.historicoAvaliacoes = new ArrayList<>();
    }

    public void setActiveVoucher(Voucher voucher) {
        this.activeVoucher = voucher;
    }

    public Voucher getActiveVoucher() {
        return this.activeVoucher;
    }

    public void clearActiveVoucher() {
        this.activeVoucher = null;
    }

    public void adicionarConsulta(Consulta consulta) {
        this.historicoConsultas.add(consulta);
    }

    public void adicionarInscricao(InscricaoEvento inscricao) {
        this.historicoEventos.add(inscricao);
    }

    public void adicionarPedido(Order pedido) {
        this.historicoPedidos.add(pedido);
    }

    public void adicionarAvaliacao(Evaluation avaliacao) {
        this.historicoAvaliacoes.add(avaliacao);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Consulta> getHistoricoConsultas() { return historicoConsultas; }
    public List<InscricaoEvento> getHistoricoEventos() { return historicoEventos; }
    public List<Order> getHistoricoPedidos() { return historicoPedidos; }
    public List<Evaluation> getHistoricoAvaliacoes() { return historicoAvaliacoes; }
}