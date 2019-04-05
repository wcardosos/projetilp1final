
/**
 * Representa uma Casa do tabuleiro.
 * Possui uma posição (i,j) e pode conter uma Peça.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 * @author Wagner Cardoso &lt;wagnersilva@cc.ci.ufpb.br&gt;
 */
public class Casa {

    private int x;
    private int y;
    private Peca peca;

    public Casa(int x, int y) {
        this.x = x;
        this.y = y;
        this.peca = null;
    }

    /**
     * @param casaVerificacao Uma casa para a verificação
     * @return se uma casa dada como parâmetro tem a mesma posição.
     */
    public boolean verificacaoMesmaPosicao(Casa casaVerificacao) {
        return (x == casaVerificacao.getX() && y == casaVerificacao.getY());
    }
    
    /**
     * @return a posição X da casa
     */
    public int getX() {
        return x;
    }
    
    /**
     * @return a posição Y da casa
     */
    public int getY() {
        return y;
    }
    
    /**
     * @param peca a Peca a ser posicionada nesta casa.
     */
    public void colocarPeca(Peca peca) {
        this.peca = peca;
    }
    
    /**
     * Remove a peça posicionada nesta casa, se houver.
     */
    public void removerPeca() {
        peca = null;
    }
    
    /**
     * @return a Peca posicionada nesta Casa, ou Null se a casa estiver livre.
     */
    public Peca getPeca() {
        return peca;
    }
    
    /**
     * @return true se existe uma peça nesta casa, caso contrario false.
     */
    public boolean possuiPeca() {
        return peca != null;
    }
    
}
