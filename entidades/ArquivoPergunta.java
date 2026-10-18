package entidades;

import aed3.Arquivo;
import aed3.ArvoreBMais;
import aed3.ParIdId;
import java.util.ArrayList;

public class ArquivoPergunta extends Arquivo<Pergunta> {

    // relacionamento 1:N -> (idUsuario, idPergunta)
    private ArvoreBMais<ParIdId> relUsuarioPergunta;
    // índice secundário em Árvore B+ ordenado por data de criação decrescente
    private ArvoreBMais<ParDataId> indiceDataPergunta;

    public ArquivoPergunta() throws Exception {
        super("perguntas", Pergunta.class.getConstructor());
        relUsuarioPergunta = new ArvoreBMais<>(
            ParIdId.class.getConstructor(),
            5,
            "./dados/perguntas/relUsuarioPergunta.db"
        );
        indiceDataPergunta = new ArvoreBMais<>(
            ParDataId.class.getConstructor(),
            5,
            "./dados/perguntas/indiceDataPergunta.db"
        );
    }

    @Override
    public int create(Pergunta pergunta) throws Exception {
        int id = super.create(pergunta);
        pergunta.setId(id);
        relUsuarioPergunta.create(new ParIdId(pergunta.getIdUsuario(), id));
        indiceDataPergunta.create(new ParDataId(pergunta.getCriacao(), id));
        return id;
    }

    public ArrayList<Pergunta> readAllByUsuario(int idUsuario) throws Exception {
        ArrayList<Pergunta> lista = new ArrayList<>();
        ArrayList<ParIdId> pares = relUsuarioPergunta.read(new ParIdId(idUsuario, -1));
        for (ParIdId par : pares) {
            Pergunta p = super.read(par.getId2());
            if (p != null) lista.add(p);
        }
        return lista;
    }

    // Retorna as perguntas ativas mais recentes com paginação a partir da Árvore B+
    public ArrayList<Pergunta> readMaisRecentes(int pagina, int itensPorPagina) throws Exception {
        ArrayList<ParDataId> pares = indiceDataPergunta.read(null);
        ArrayList<Pergunta> ativas = new ArrayList<>();
        for (ParDataId par : pares) {
            Pergunta p = super.read(par.getId());
            if (p != null && p.isAtiva()) {
                ativas.add(p);
            }
        }

        int inicio = (pagina - 1) * itensPorPagina;
        if (inicio < 0 || inicio >= ativas.size()) {
            return new ArrayList<>();
        }
        int fim = Math.min(inicio + itensPorPagina, ativas.size());
        return new ArrayList<>(ativas.subList(inicio, fim));
    }

    public int totalPerguntasAtivas() throws Exception {
        ArrayList<ParDataId> pares = indiceDataPergunta.read(null);
        int total = 0;
        for (ParDataId par : pares) {
            Pergunta p = super.read(par.getId());
            if (p != null && p.isAtiva()) {
                total++;
            }
        }
        return total;
    }

    // exclusão lógica: apenas muda ativa para false
    public boolean arquivar(int idPergunta) throws Exception {
        Pergunta p = super.read(idPergunta);
        if (p == null) return false;
        p.setAtiva(false);
        p.setAlteracao(System.currentTimeMillis());
        return super.update(p);
    }

    @Override
    public boolean delete(int id) throws Exception {
        Pergunta p = super.read(id);
        if (p == null) return false;
        relUsuarioPergunta.delete(new ParIdId(p.getIdUsuario(), id));
        indiceDataPergunta.delete(new ParDataId(p.getCriacao(), id));
        return super.delete(id);
    }

    public void deleteAllByUsuario(int idUsuario) throws Exception {
        ArrayList<ParIdId> pares = relUsuarioPergunta.read(new ParIdId(idUsuario, -1));
        for (ParIdId par : pares) {
            Pergunta p = super.read(par.getId2());
            if (p != null) {
                indiceDataPergunta.delete(new ParDataId(p.getCriacao(), p.getId()));
            }
            super.delete(par.getId2());
            relUsuarioPergunta.delete(par);
        }
    }

    @Override
    public void close() throws java.io.IOException {
        super.close();
    }
}
