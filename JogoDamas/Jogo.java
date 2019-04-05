 
/**
 * Armazena o tabuleiro e responsável por posicionar as pecas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 * @author Wagner Cardoso &lt;wagnersilva@cc.ci.ufpb.br&gt;
 */
import java.util.ArrayList;
import java.util.Random;

public class Jogo {
    
    private int pecasBrancas;
    private int pecasVermelhas;
    private boolean turno;
    private boolean controleEliminacaoSucessiva;
    private boolean fimDeJogo;
    private Tabuleiro tabuleiro;
    

    public Jogo() {
        controleEliminacaoSucessiva = false;
        fimDeJogo = false;
        tabuleiro = new Tabuleiro();
        pecasBrancas = 12;
        pecasVermelhas = 12;
        criarPecas();
        jogadorIniciante();
    }
    
    /**
     * Posiciona peças no tabuleiro.
     * Utilizado na inicializção do jogo.
     */
    private void criarPecas() {
        Casa casa;
        Peca peca;
        for (int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(y < 3) {
                    if(y % 2 == 0) {
                        if(x % 2 == 0) {
                            casa = tabuleiro.getCasa(x,y);
                            peca = new Peca(casa, Peca.PEDRA_BRANCA, 1);
                        }
                    } else {
                        if(x % 2 != 0) {
                            casa = tabuleiro.getCasa(x,y);
                            peca = new Peca(casa, Peca.PEDRA_BRANCA, 1);
                        }
                    }
                } else if(y > 4) {
                    if(y % 2 == 0) {
                        if(x % 2 == 0) {
                            casa = tabuleiro.getCasa(x,y);
                            peca = new Peca(casa, Peca.PEDRA_VERMELHA, -1);
                        }
                    } else {
                        if(x % 2 != 0) {
                            casa = tabuleiro.getCasa(x,y);
                            peca = new Peca(casa, Peca.PEDRA_VERMELHA, -1);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Comanda uma Peça na posicao (origemX, origemY) fazer um movimento 
     * para (destinoX, destinoY).
     * 
     * @param origemX linha da Casa de origem.
     * @param origemY coluna da Casa de origem.
     * @param destinoX linha da Casa de destino.
     * @param destinoY coluna da Casa de destino.
     */
    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Peca peca = tabuleiro.getCasa(origemX, origemY).getPeca();
        peca.verificaEliminacao(tabuleiro);
        
        if(peca.existeEliminacao()) {
            if(peca.verificaCasaEliminacao(tabuleiro.getCasa(destinoX, destinoY))) {
                eliminarPeca(peca.casaParaEliminar(tabuleiro.getCasa(destinoX, destinoY)), peca.getTipo());
                peca.mover(tabuleiro.getCasa(destinoX, destinoY));
                peca = tabuleiro.getCasa(destinoX, destinoY).getPeca();
                peca.eliminacaoSucessiva(tabuleiro);
                setControleEliminacaoSucessiva(peca.getEliminacaoSucessiva());
                
                if(!peca.getEliminacaoSucessiva()) {
                    verificaFimDeJogo();
                    setTurno();
                }
            }
        } else {
            if(peca.movimentoSimples(tabuleiro.getCasa(destinoX, destinoY))) {
                peca.mover(tabuleiro.getCasa(destinoX, destinoY));
                setTurno();
            }
        }
        
        peca.limpaPosicoesPossiveis();
    }
    
    /**
     * @param casaEliminacao Casa que possui uma peça a ser eliminada.
     * @param tipoPecaOrigem Tipo da peça de origem.
     * Elimina uma peça e decrementa o número de peças da sua cor.
     */
    public void eliminarPeca(Casa casaEliminacao, int tipoPecaOrigem) {
        if(tipoPecaOrigem < 2) {
            pecasVermelhas--;
        }
        else {
            pecasBrancas--;
        }
            
        tabuleiro.getCasa(casaEliminacao.getX(), casaEliminacao.getY()).removerPeca();
    }

    /**
     * Define o jogador que iniciará a partida.
     */

    public void jogadorIniciante() {
        Random r = new Random();
        turno = r.nextBoolean();
    }

    /**
     * Verifica se o jogo acabou.
     */
    public void verificaFimDeJogo() {
        if(pecasBrancas == 0 || pecasVermelhas == 0) {
             fimDeJogo = true;
        }
    }
    
    /**
     * @param controle Passa para o método se uma peça possui eliminações sucessivas a serem feitas.
     * Define o valor de controle de eliminações sucessivas. Esse controle serve para a interação com o usuário.
     */
    public void setControleEliminacaoSucessiva(boolean controle) {
        controleEliminacaoSucessiva = controle;
    }
    
    /**
     * @return controleEliminacaoSucessiva Retorna se há eliminações sucessivas a serem feitas.
     */
    public boolean getControleEliminacaoSucessiva() {
        return controleEliminacaoSucessiva;
    }
    
    
    /**
     * Muda o turno.
     */
    public void setTurno() {
        turno = !turno;
    }
    
    /**
     * @return Retorna o turno.
     */
    public boolean getTurno() {
        return turno;
    }
    
    
    
    /**
     * @return fimDeJogo para verificações externas.
     */
    public boolean getFimDeJogo() {
        return fimDeJogo;
    }
    
    /**
     * @return o Tabuleiro em jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}
