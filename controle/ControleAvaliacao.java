package controle;

import entidades.ArquivoAvaliacao;
import entidades.Avaliacao;
import java.util.ArrayList;

public class ControleAvaliacao {

    private ArquivoAvaliacao arqAvaliacao;
    private ControleResposta controleResposta;

    public ControleAvaliacao(ControleResposta controleResposta) throws Exception {
        this.arqAvaliacao     = new ArquivoAvaliacao();
        this.controleResposta = controleResposta;
    }

    // implementar
    public boolean avaliar(int idUsuario, int idResposta, boolean concorda) throws Exception {
        throw new UnsupportedOperationException("Implementar registro de avaliação e atualização de nota");
    }

    // implementar
    public boolean jaAvaliou(int idUsuario, int idResposta) throws Exception {
        return false;
    }

    // implementar
    public ArrayList<Avaliacao> listarPorUsuario(int idUsuario) throws Exception {
        return new ArrayList<>();
    }

    // implementar
    public boolean cancelarAvaliacao(int idAvaliacao, int idUsuarioRequisitante) throws Exception {
        throw new UnsupportedOperationException("Implementar cancelamento de avaliação e estorno de nota");
    }

    public void close() throws Exception {
        arqAvaliacao.close();
    }
}
