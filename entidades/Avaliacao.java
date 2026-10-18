package entidades;

import aed3.InterfaceRegistro;

public class Avaliacao implements InterfaceRegistro {

    private int     id;
    private int     idUsuario;
    private int     idResposta;
    private boolean avaliacao; // true = concordar (+1), false = discordar (-1)

    public Avaliacao() {
        this(-1, -1, -1, false);
    }

    public Avaliacao(int id, int idUsuario, int idResposta, boolean avaliacao) {
        this.id         = id;
        this.idUsuario  = idUsuario;
        this.idResposta = idResposta;
        this.avaliacao  = avaliacao;
    }

    @Override public int  getId()       { return id; }
    @Override public void setId(int id) { this.id = id; }

    public int     getIdUsuario()                  { return idUsuario; }
    public void    setIdUsuario(int idUsuario)     { this.idUsuario = idUsuario; }

    public int     getIdResposta()                 { return idResposta; }
    public void    setIdResposta(int idResposta)   { this.idResposta = idResposta; }

    public boolean isAvaliacao()                   { return avaliacao; }
    public void    setAvaliacao(boolean avaliacao) { this.avaliacao = avaliacao; }

    // implementar
    @Override
    public byte[] serialize() throws Exception {
        throw new UnsupportedOperationException("Implementar serialização da Avaliação");
    }

    // implementar
    @Override
    public void deserialize(byte[] data) throws Exception {
        throw new UnsupportedOperationException("Implementar desserialização da Avaliação");
    }
}
