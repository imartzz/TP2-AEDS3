import controle.ControleAvaliacao;
import controle.ControlePergunta;
import controle.ControleResposta;
import controle.ControleUsuario;

import java.io.File;

// script utilitário para pré-popular o banco para demonstração no vídeo
public class PovoarDados {

    private static void limparDiretorio(File pasta) {
        if (pasta.exists() && pasta.isDirectory()) {
            File[] arquivos = pasta.listFiles();
            if (arquivos != null) {
                for (File f : arquivos) {
                    if (f.isDirectory()) limparDiretorio(f);
                    f.delete();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Limpando dados antigos...");
        limparDiretorio(new File("./dados"));

        new File("./dados/usuarios").mkdirs();
        new File("./dados/perguntas").mkdirs();
        new File("./dados/respostas").mkdirs();
        new File("./dados/avaliacoes").mkdirs();

        try {
            ControleUsuario cu = new ControleUsuario();
            ControlePergunta cp = new ControlePergunta();
            ControleResposta cr = new ControleResposta();
            ControleAvaliacao ca = new ControleAvaliacao(cr);

            System.out.println("Cadastrando usuários...");
            int idArthur = cu.cadastrar("Arthur", "arthur@puc.br", "1234", "Cor favorita?", "Azul");
            int idRafael = cu.cadastrar("Rafael", "rafael@puc.br", "1234", "Nome do pet?", "Rex");

            System.out.println("Criando perguntas...");
            // Criando 6 perguntas para demonstrar a paginação (5 por página)
            int p1 = cp.criar(idArthur, "Como funciona o Hash Extensivel em AEDS 3?", "hash;aeds;disco");
            Thread.sleep(100);
            int p2 = cp.criar(idRafael, "Qual a diferenca entre Arvore B e Arvore B+?", "arvore;indices;bmais");
            Thread.sleep(100);
            int p3 = cp.criar(idArthur, "O que e a lista de registros excluidos (primeiroLivre)?", "crud;exclusao;arquivo");
            Thread.sleep(100);
            int p4 = cp.criar(idRafael, "Como calcular o custo de I/O em arquivos binarios?", "desempenho;disco;io");
            Thread.sleep(100);
            int p5 = cp.criar(idArthur, "Melhores praticas para serializacao binaria em Java?", "java;serializacao;bytes");
            Thread.sleep(100);
            int p6 = cp.criar(idRafael, "Pergunta mais recente: Duvida sobre o algoritmo de LZW?", "compressao;lzw;dados");

            System.out.println("Criando respostas para demonstrar ordenacao por nota...");
            // Perguntas com respostas
            int r1 = cr.criar(p1, idRafael, "O Hash Extensivel usa um diretorio em memoria/disco que aponta para cestos.");
            int r2 = cr.criar(p1, idRafael, "Resposta redundante que pode ser moderada pelo autor.");
            int r3 = cr.criar(p2, idArthur, "A principal diferenca da B+ e que os dados estao apenas nas folhas encadeadas.");

            System.out.println("Criando avaliacoes...");
            // Arthur avalia resposta do Rafael positivamente
            ca.avaliar(idArthur, r1, true);

            cu.close();
            cp.close();
            cr.close();
            ca.close();

            System.out.println("\n--- BANCO POVOADO COM SUCESSO! ---");
            System.out.println("Usuarios prontos:");
            System.out.println("1) arthur@puc.br (senha: 1234)");
            System.out.println("2) rafael@puc.br (senha: 1234)");
            System.out.println("Total de perguntas: 6 (Pagina 1 tera 5, Pagina 2 tera 1)");
            System.out.println("Total de respostas: 3 (Com votos para demonstrar ordenacao e moderacao)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
