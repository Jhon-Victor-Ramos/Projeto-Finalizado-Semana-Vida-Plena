package app;

import clinica.entities.Medico;
import core.activities.Voucher;
import semanavidaplena.SemanaVidaPlena;
import clinica.services.Clinica;
import core.Participant;
import core.activities.Evaluation;
import core.activities.Order;
import core.activities.Evento;
import eventos.services.GerenciadorDeEventos;
import restaurant.entities.Dish;
import restaurant.entities.Ingredient;
import restaurant.entities.Stock;
import restaurant.services.Restaurant;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Setup do Restaurante
        Stock stock = new Stock();
        Ingredient tomate = new Ingredient("I01", "Tomate", "unidades");
        Ingredient queijo = new Ingredient("I02", "Queijo Mussarela", "gramas");
        Ingredient massa = new Ingredient("I03", "Massa de Pizza", "unidades");
        Ingredient carne = new Ingredient("I04", "Carne Moída", "gramas");
        stock.add(tomate, 50.0);
        stock.add(queijo, 2000.0);
        stock.add(massa, 30.0);
        stock.add(carne, 5000.0);

        // Criando o cardápio (Menu)
        Dish pizza = new Dish("D01", "Pizza de Queijo", 45.0);
        pizza.addIngredientToRecipe(massa, 1.0);
        pizza.addIngredientToRecipe(queijo, 150.0);
        pizza.addIngredientToRecipe(tomate, 2.0);

        Dish lasanha = new Dish("D02", "Lasanha Bolonhesa", 55.0);
        lasanha.addIngredientToRecipe(carne, 300.0);
        lasanha.addIngredientToRecipe(queijo, 200.0);

        // Criando o Restaurante com seu estoque e cardápio
        Restaurant restaurant = new Restaurant("DON PASTELONI", stock);
        restaurant.addDishToMenu(pizza);
        restaurant.addDishToMenu(lasanha);

        // 2. Setup da Clínica
        Clinica clinica = new Clinica("Clínica Vida Saudável");
        clinica.contratarMedico(new Medico("M01", "Ana Souza", "Nutricionista"));
        clinica.contratarMedico(new Medico("M02", "Carlos Lima", "Check-up Geral"));

        // 3. Setup dos Eventos
        GerenciadorDeEventos gerenciadorDeEventos = new GerenciadorDeEventos("Eventos Vida Plena");
        gerenciadorDeEventos.adicionarEventoAoCatalogo(new Evento("E01", "Palestra de Nutrição", "Saúde", 30.0));
        gerenciadorDeEventos.adicionarEventoAoCatalogo(new Evento("E02", "Show de Jazz", "Música", 50.0));

        // 4. Setup do Cérebro Central
        SemanaVidaPlena semanaVidaPlena = new SemanaVidaPlena(restaurant, clinica, gerenciadorDeEventos);

        System.out.println("Sistema da Semana Vida Plena inicializado!");

        // --- LOOP PRINCIPAL (O EVENTO ESTÁ ACONTECENDO) ---
        while (true) {
            System.out.println("\n===== SEMANA VIDA PLENA =====");
            System.out.println("1. Entrar como Participante");
            System.out.println("2. Entrar como Gerente");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("Encerrando o sistema.");
                break;
            }

            switch (choice) {
                case 1:
                    handleParticipantMenu(scanner, semanaVidaPlena);
                    break;
                case 2:
                    handleManagerMenu(scanner, semanaVidaPlena);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    // NOVO MENU DE PARTICIPANTE
    public static void handleParticipantMenu(Scanner scanner, SemanaVidaPlena svp) {
        System.out.print("\nPor favor, digite seu nome: ");
        String name = scanner.nextLine();
        Participant currentParticipant = svp.findOrCreateParticipant(name);

        while (true) {
            System.out.println("\nO que você gostaria de fazer, " + currentParticipant.getName() + "?");
            System.out.println("1. Ir para o Restaurante");
            System.out.println("2. Ir para a Clínica");
            System.out.println("3. Ir para um Evento");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) break;

            if (choice == 1) {
                // Chama um novo metodo para o submenu do restaurante
                handleRestaurantSubMenu(scanner, currentParticipant, svp.getRestaurante(), svp);
            } else if (choice == 2) {
                // Chama um novo metodo para o submenu da clínica
                handleClinicSubMenu(scanner, currentParticipant, svp.getClinica());
            } else if (choice == 3) {
                handleEventsSubMenu(scanner, currentParticipant, svp.getGerenciadorDeEventos());
            }
        }
    }

    // SUBMENU DO RESTAURANTE
    public static void handleRestaurantSubMenu(Scanner scanner, Participant currentParticipant, Restaurant restaurant, SemanaVidaPlena svp) {
        System.out.println("\n--- BEM-VINDO AO RESTAURANTE " + restaurant.getName() + " ---");
        System.out.println("\nO que você gostaria de fazer?");
        System.out.println("1. Fazer um pedido");
        System.out.println("2. Avaliar o restaurante");
        System.out.println("3. Aplicar Voucher do evento");
        System.out.println("0. Voltar ao menu principal");
        System.out.print("—→ Escolha uma opção: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) return;

        if (choice == 1) {
            System.out.println("\nNOSSO CARDÁPIO");
            List<Dish> menu = restaurant.getMenu();
            for (int i = 0; i < menu.size(); i++) {
                System.out.printf("—> %d. %s - R$%.2f\n", (i + 1), menu.get(i).getName(), menu.get(i).getPrice());
            }

            Order order = new Order(currentParticipant);

            while (true) {
                System.out.print("* Digite o número do prato que deseja adicionar (ou 0 para finalizar o pedido): ");
                int dishChoice = scanner.nextInt() - 1;
                scanner.nextLine();

                if (dishChoice == -1) break;

                if (dishChoice >= 0 && dishChoice < menu.size()) {
                    order.addDish(menu.get(dishChoice));
                    System.out.println("└→ \u001B[0;1m'" + menu.get(dishChoice).getName() + "\u001B[0m' adicionado ao pedido.");
                } else {
                    System.out.println("Prato inválido!");
                }
            }
            restaurant.placeOrder(order);
            currentParticipant.adicionarPedido(order);
        } else if (choice == 2) {
            System.out.print("* Dê uma nota de 1 a 5: ");
            int rating = scanner.nextInt();
            scanner.nextLine();
            System.out.print("* Deixe um comentário (opcional): ");
            String comment = scanner.nextLine();

            Evaluation eval = new Evaluation(currentParticipant, rating, comment);
            restaurant.addEvaluation(eval);
            currentParticipant.adicionarAvaliacao(eval);
            System.out.println("Obrigado pela sua avaliação!");
        } else if (choice == 3) {
            handleVoucherApplication(scanner, currentParticipant, svp);
        }
    }

    public static void handleVoucherApplication (Scanner scanner, Participant currentParticipant, SemanaVidaPlena
            svp){
        System.out.print("\nPor favor, digite o código do seu voucher: ");
        String voucherId = scanner.nextLine();

        // Pedimos ao "cérebro" para encontrar o voucher
        Voucher foundVoucher = null;
        for (Voucher v : svp.getGerenciadorDeEventos().getIssuedVouchers()) {
            if (v.getId().equalsIgnoreCase(voucherId)) {
                foundVoucher = v;
                break;
            }
        }

        // Agora validamos o voucher encontrado
        if (foundVoucher == null) {
            System.out.println("Erro: Voucher não encontrado.");
        } else if (foundVoucher.isUsed()) {
            System.out.println("Erro: Este voucher já foi utilizado.");
        } else if (foundVoucher.getOwner() != currentParticipant) {
            System.out.println("Erro: Este voucher não pertence a você.");
        } else {
            // Voucher é válido!
            currentParticipant.setActiveVoucher(foundVoucher);
            System.out.println("Voucher '" + foundVoucher.getId() + "' validado! Ele será aplicado automaticamente no seu próximo pedido.");
        }
    }

    // SUBMENU DA CLÍNICA
    public static void handleClinicSubMenu(Scanner scanner, Participant currentParticipant, Clinica clinica) {
        System.out.println("\n--- BEM-VINDO À " + clinica.getName() + " ---");
        List<Medico> medicos = clinica.getMedicos();

        System.out.println("Qual consulta deseja agendar?");
        for (int i = 0; i < medicos.size(); i++) {
            System.out.printf("%d. %s (Dr(a). %s)\n", (i+1), medicos.get(i).getEspecialidade(), medicos.get(i).getNome());
        }
        System.out.print("Escolha: ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (choice >= 0 && choice < medicos.size()) {
            Medico medicoEscolhido = medicos.get(choice);
            clinica.agendarConsulta(currentParticipant, medicoEscolhido, 75.0);

            System.out.println("\nObrigado! Gostaríamos do seu feedback.");
            System.out.print("* Dê uma nota de 1 a 5 para o atendimento: ");
            int rating = scanner.nextInt();
            scanner.nextLine();
            System.out.print("* Deixe um comentário (opcional): ");
            String comment = scanner.nextLine();

            Evaluation eval = new Evaluation(currentParticipant, rating, comment);
            clinica.addEvaluation(eval);
            currentParticipant.adicionarAvaliacao(eval);
            System.out.println("Sua avaliação foi registrada. Obrigado!");
        } else {
            System.out.println("Opção inválida.");
        }
    }

    // SUBMENU DOS EVENTOS
    public static void handleEventsSubMenu(Scanner scanner, Participant currentParticipant, GerenciadorDeEventos ge) {
        System.out.println("\n--- BEM-VINDO AOS " + ge.getNome() + " ---");
        List<Evento> eventos = ge.getCatalogoEventos();

        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento disponível no momento.");
            return;
        }

        System.out.println("Qual evento você gostaria de se inscrever?");
        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            System.out.printf("%d. %s (%s) - R$%.2f\n", (i+1), evento.getNome(), evento.getTipo(), evento.getPreco());
        }
        System.out.print("Escolha: ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (choice >= 0 && choice < eventos.size()) {
            Evento eventoEscolhido = eventos.get(choice);
            ge.inscreverParticipante(currentParticipant, eventoEscolhido);

            // --- LÓGICA DE AVALIAÇÃO ADICIONADA AQUI ---
            System.out.println("\nObrigado! Gostaríamos do seu feedback sobre o evento.");
            System.out.print("* Dê uma nota de 1 a 5 para o evento: ");
            int rating = scanner.nextInt();
            scanner.nextLine();
            System.out.print("* Deixe um comentário (opcional): ");
            String comment = scanner.nextLine();

            Evaluation eval = new Evaluation(currentParticipant, rating, comment);
            ge.addEvaluation(eval);
            currentParticipant.adicionarAvaliacao(eval);
            System.out.println("Sua avaliação foi registrada. Obrigado!");

        } else {
            System.out.println("Opção inválida.");
        }
    }

    // MENU DO GERENTE
    public static void handleManagerMenu(Scanner scanner, SemanaVidaPlena svp) {
        while (true) {
            System.out.println("\n--- PAINEL DO GERENTE ---");
            System.out.println("Selecione o relatório que deseja visualizar:");

            // Relatórios Gerais
            System.out.println("1. Relatório Geral de Participantes (Atividades e Gastos)");
            System.out.println("2. Análise de Frequência (Quantos locais cada um visitou)");
            System.out.println("3. Gasto Médio por Local (Clínica, Restaurante, Eventos)");
            System.out.println("4. Comparativo de Avaliação Média por Atividade");

            // Relatórios de Integração e Conversão
            System.out.println("5. Conversão: Palestra de Saúde -> Consulta na Clínica");
            System.out.println("6. Impacto da Consulta com Nutricionista no Ticket Médio");
            System.out.println("7. Faturamento Médio da 'Jornada Plena'");
            System.out.println("8. Perfil de Interesses dos Participantes da 'Jornada Plena'");

            // Relatórios de Vouchers (Exigirão a implementação do uso de voucher)
            System.out.println("9. Análise de Gasto por Origem de Voucher");
            System.out.println("10. Análise de Tempo de Uso dos Vouchers");

            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha um relatório: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) break;

            switch (choice) {
                case 1:
                    svp.printCrossSystemReport();
                    break;
                case 2:
                    svp.analisarFrequenciaMultipla();
                    break;
                case 3:
                    svp.calcularGastoMedioPorLocal();
                    break;
                case 4:
                    svp.analisarAvaliacaoMediaPorAtividade();
                    break;
                case 5:
                    svp.analisarConversaoPalestraSaude();
                    break;
                case 6:
                    svp.compararTicketMedioNutricionista();
                    break;
                case 7:
                    svp.analisarFaturamentoJornadaPlena();
                    break;
                case 8:
                    svp.analisarPerfilJornadaPlena();
                    break;
                case 9:
                    svp.analisarGastoPorEventoDeOrigem();
                    break;
                case 10:
                    svp.analisarTempoUsoVoucher();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}