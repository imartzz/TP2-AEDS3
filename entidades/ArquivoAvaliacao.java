package entidades;

import aed3.Arquivo;
import aed3.ArvoreBMais;
import aed3.ParIdId;
import java.util.ArrayList;

public class ArquivoAvaliacao extends Arquivo<Avaliacao> {

    // relacionamento 1:N -> (idUsuario, idAvaliacao)
    private ArvoreBMais<ParIdId> relUsuarioAvaliacao;
    // relacionamento 1:N -> (idResposta, idAvaliacao)
    private ArvoreBMais<ParIdId> relRespostaAvaliacao;

    public ArquivoAvaliacao() throws Exception {
        super("avaliacoes", Avaliacao.class.getConstructor());
        relUsuarioAvaliacao = new ArvoreBMais<>(
            ParIdId.class.getConstructor(),
            5,
            "./dados/avaliacoes/relUsuarioAvaliacao.db"
        );
        relRespostaAvaliacao = new ArvoreBMais<>(
            ParIdId.class.getConstructor(),
            5,
            "./dados/avaliacoes/relRespostaAvaliacao.db"
        );
    }

    // implementar
    @Override
    public int create(Avaliacao a) throws Exception {
        throw new UnsupportedOperationException("Implementar inclusão de avaliação nas árvores B+");
    }

    // implementar
    public ArrayList<Avaliacao> readAllByUsuario(int idUsuario) throws Exception {
        throw new UnsupportedOperationException("Implementar leitura de avaliações por usuário");
    }

    // implementar
    public Avaliacao buscarPorUsuarioEResposta(int idUsuario, int idResposta) throws Exception {
        return null;
    }

    // implementar
    @Override
    public boolean delete(int id) throws Exception {
        throw new UnsupportedOperationException("Implementar exclusão de avaliação nas árvores B+");
    }

    @Override
    public void close() throws java.io.IOException {
        super.close();
    }
}
