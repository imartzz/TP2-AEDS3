package visao;

import controle.ControleAvaliacao;
import controle.ControlePergunta;
import controle.ControleResposta;
import controle.ControleUsuario;
import java.util.Scanner;

public class VisaoMinhasAvaliacoes {

    private final Scanner          scanner;
    private final ControleUsuario  controleUsuario;
    private final ControlePergunta controlePergunta;
    private final ControleResposta controleResposta;
    private final ControleAvaliacao controleAvaliacao;

    public VisaoMinhasAvaliacoes(Scanner scanner,
                                 ControleUsuario controleUsuario,
                                 ControlePergunta controlePergunta,
                                 ControleResposta controleResposta,
                                 ControleAvaliacao controleAvaliacao) {
        this.scanner           = scanner;
        this.controleUsuario   = controleUsuario;
        this.controlePergunta  = controlePergunta;
        this.controleResposta  = controleResposta;
        this.controleAvaliacao = controleAvaliacao;
    }

    // implementar
    public void exibir() {
        System.out.println("\n============================");
        System.out.println("      AJUDA AÍ 1.0");
        System.out.println("============================");
        System.out.println("> Início > Minha área > Minhas avaliações");
        System.out.println("\n[Em desenvolvimento] Funcionalidade de Minhas Avaliações em implementação.");
        System.out.println("Pressione ENTER para retornar...");
        scanner.nextLine();
    }
}
