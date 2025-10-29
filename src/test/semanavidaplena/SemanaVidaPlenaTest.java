package test.semanavidaplena;

import clinica.entities.Medico;
import clinica.services.Clinica;
import core.Participant;
import core.activities.Consulta;
import core.activities.Evento;
import core.activities.Order;
import eventos.services.GerenciadorDeEventos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test; // O import mais importante!
import restaurant.entities.Dish;
import restaurant.entities.Ingredient;
import restaurant.entities.Stock;
import restaurant.services.Restaurant;
import semanavidaplena.SemanaVidaPlena;

// O nome da classe de teste geralmente termina com "Test"
class SemanaVidaPlenaTest {

    // A anotação @Test diz ao JUnit: "Este metodo é um teste automatizado. Rode ele!"
    @Test
    void testeCompletoJornadaPlena_DeveCalcularGastosCorretamente() {
        // =================================================================
        // 1. ARRANGE (Organizar) - Preparamos to.do o cenario aqui.
        // Isso substitui toda a digitação que você fazia no Main.
        // =================================================================

        // Setup do Restaurante
        Stock stock = new Stock();
        Ingredient tomate = new Ingredient("I01", "Tomate", "unidades");
        stock.add(tomate, 50.0);
        Restaurant restaurante = new Restaurant("Teste Restaurante", stock);
        Dish pizza = new Dish("D01", "Pizza", 50.0);
        pizza.addIngredientToRecipe(tomate, 2.0);
        restaurante.addDishToMenu(pizza);

        // Setup da Clínica
        Clinica clinica = new Clinica("Teste Clínica");
        Medico medico = new Medico("M01", "Dr. Teste", "Tests");
        clinica.contratarMedico(medico);

        // Setup dos Eventos
        GerenciadorDeEventos ge = new GerenciadorDeEventos("Teste Eventos");
        Evento palestra = new Evento("E01", "Palestra de Testes", "Tech", 100.0);
        ge.adicionarEventoAoCatalogo(palestra);

        // Setup do Cérebro Central e do Participante
        SemanaVidaPlena svp = new SemanaVidaPlena(restaurante, clinica, ge);
        Participant p = svp.findOrCreateParticipant("Tester");

        // =================================================================
        // 2. ACT (Agir) - Executamos as ações que queremos testar.
        // =================================================================

        // Ação 1: Ir à clínica
        clinica.agendarConsulta(p, medico, 75.0);

        // Ação 2: Fazer um pedido no restaurante
        Order order = new Order(p);
        order.addDish(pizza);
        restaurante.placeOrder(order);
        p.adicionarPedido(order);

        // Ação 3: Inscrever-se no evento
        ge.inscreverParticipante(p, palestra);

        // =================================================================
        // 3. ASSERT (Afirmar) - Verificamos se os resultados estão corretos.
        // Isso substitui a sua verificação visual no terminal.
        // =================================================================

        // Afirmamos que o histórico de consultas do participante tem tamanho 1.
        Assertions.assertEquals(1, p.getHistoricoConsultas().size());

        // Afirmamos que o histórico de pedidos tem tamanho 1.
        Assertions.assertEquals(1, p.getHistoricoPedidos().size());

        // Afirmamos que o histórico de eventos tem tamanho 1.
        Assertions.assertEquals(1, p.getHistoricoEventos().size());

        // O teste final: Vamos calcular os gastos e verificar se o total está correto.
        //                           75.0 (consulta) + 50.0 (pedido) + 100.0 (evento); // Total esperado: 225.0
        double expectedTotalGastos = 75.0 + 50.0 + 100.0; // Total esperado: 225.0

        // Criamos métodos privados para pegar os gastos (poderiam estar na própria SemanaVidaPlena)
        double actualGastoConsultas = p.getHistoricoConsultas().stream().mapToDouble(Consulta::getPreco).sum();
        double actualGastoPedidos = p.getHistoricoPedidos().stream().mapToDouble(Order::calculateTotal).sum();
        double actualGastoEventos = p.getHistoricoEventos().stream().mapToDouble(i -> i.getEvento().getPreco()).sum();

        double actualTotalGastos = actualGastoConsultas + actualGastoPedidos + actualGastoEventos;

        // Afirmamos que o total de gastos calculado é igual ao total que esperávamos.
        Assertions.assertEquals(expectedTotalGastos, actualTotalGastos);
    }
}