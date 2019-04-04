/**
 * Representa uma Peça do jogo.
 * Possui uma casa e um tipo associado.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 * @author Wagner Cardoso &lt;wagnersilva@cc.ci.ufpb.br&gt;
 */

import java.util.ArrayList;

public class Peca {

    public static final int PEDRA_BRANCA = 0;
    public static final int DAMA_BRANCA = 1;
    public static final int PEDRA_VERMELHA = 2;
    public static final int DAMA_VERMELHA = 3;

    private Casa casa;
    private int direcao;
    private int tipo;
    private boolean dama;
    private boolean eliminacaoSucessiva;
    private ArrayList<Casa> posicoesPossiveis;

    public Peca(Casa casa, int tipo, int direcao) {
        this.casa = casa;
        this.tipo = tipo;
        this.direcao = direcao;
        dama = false;
        eliminacaoSucessiva = false;
        posicoesPossiveis = new ArrayList();
        casa.colocarPeca(this);
    }
    
    /**
     * Movimenta a peça para uma nova casa.
     * @param destino Nova casa que irá conter esta peca.
     */
    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
        verificaDama();
    }

    /**
     * Valor    Tipo
     *   0   Branca (Pedra)
     *   1   Branca (Dama)
     *   2   Vermelha (Pedra)
     *   3   Vermelha (Dama)
     * @return o tipo da peca.
     */
    public int getTipo() {
        return tipo;
    }
    
    /**
     * Define o valor para o atributo dama.
     * Se o valor de dama for falso, vira verdadeiro e muda seu tipo;
     * Se for verdadeiro, vira false e muda seu tipo;
     * Após a verificação de dama, muda-se a direção da peça.
     */
    
    public void setDama() {
        if(!dama) {
            dama = true;
            tipo++;
        }
        else {
            dama = false;
            tipo--;
        }
        
        direcao = -direcao;
        
    }
    
    /**
     * @return o valor de dama.
     */
    public boolean getDama() {
        return dama;
    }
    
    /**
     * @return true se há peças para eliminação, false se o contrário.
     */
    public boolean existeEliminacao() {
        if(posicoesPossiveis.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Adiciona uma casa onde a peça pode ir após uma eliminação.
     */
    public void adicionaPosicao(Casa casa) {
        posicoesPossiveis.add(casa);
    }
    
    /**
     * @param destino Casa para onde se quer fazer o movimento.
     * @return se uma movimentação simples pode ser feita.
     */
    public boolean movimentoSimples(Casa destino) {
        boolean podeMover = false;
        if(destino.getPeca() == null) {
            if(destino.getY() == casa.getY() + direcao) {
                if(destino.getX() == casa.getX() + 1 || destino.getX() == casa.getX() - 1) {
                    podeMover = true;
                }
            }
        }
        
        return podeMover;
    }
    
    /**
     * Limpa o ArrayList que contém as posições que a peça pode se mover após uma eliminação.
     */
    public void limpaPosicoesPossiveis() {
        if(posicoesPossiveis.size() > 0) {
            posicoesPossiveis.clear();
        }
    }
    
    /**
     * @param casaPecaAdversaria Casa em uma das adjacências da peça.
     * @param casaEliminacao Casa a qual uma peça pode ir após uma possível eliminação.
     * Analisa se em casaPecaAdversaria possui uma peça adversária e se a casa após uma possível eliminação
     * está vazia. Se estiver vazia, adiciona a casa ao ArrayList de posições possíveis.
     */
    public void analisaEliminacoes(Casa casaPecaAdversaria, Casa casaEliminacao) {
        if(tipo < 2) {
              if(casaPecaAdversaria.getPeca() != null && (casaPecaAdversaria.getPeca().getTipo() == 2 || casaPecaAdversaria.getPeca().getTipo() == 3)) {
                  if(casaEliminacao.getPeca() == null) {
                      adicionaPosicao(casaEliminacao);
                  }
                   
              }
        } else {
             if(casaPecaAdversaria.getPeca() != null && (casaPecaAdversaria.getPeca().getTipo() == 0 || casaPecaAdversaria.getPeca().getTipo() == 1)) {
                 if(casaEliminacao.getPeca() == null) {
                     adicionaPosicao(casaEliminacao);
                 }    
             }
        }
        
    }
    
    /**
     * @param tabuleiro Tabuleiro do jogo para fazer as verificações.
     * Verifica nas adjacências da peça (de acordo com as regras do jogo de damas) se há a possibilidade
     * de eliminação de uma peça adversária, utilizando o método analisaEliminacoes.
     */
    public void verificaEliminacao(Tabuleiro tabuleiro) {
        int origemX = casa.getX();
        int origemY = casa.getY();
        Casa casaPecaAdversaria, casaLivre;
        
        if(limite(origemX - 1) && limite(origemY + 1) && limite(origemX - 2) && limite(origemY + 2)) {
            casaPecaAdversaria = tabuleiro.getCasa(origemX - 1, origemY + 1);
            casaLivre = tabuleiro.getCasa(origemX - 2, origemY + 2);
            analisaEliminacoes(casaPecaAdversaria, casaLivre);
        }
        
        if(limite(origemX + 1) && limite(origemY + 1) && limite(origemX + 2) && limite(origemY + 2)) {
            casaPecaAdversaria = tabuleiro.getCasa(origemX + 1, origemY + 1);
            casaLivre = tabuleiro.getCasa(origemX + 2, origemY + 2);
            analisaEliminacoes(casaPecaAdversaria, casaLivre);
        }
        
        if(limite(origemX - 1) && limite(origemY - 1) && limite(origemX - 2) && limite(origemY - 2)) {
            casaPecaAdversaria = tabuleiro.getCasa(origemX - 1, origemY - 1);
            casaLivre = tabuleiro.getCasa(origemX - 2, origemY - 2);
            analisaEliminacoes(casaPecaAdversaria, casaLivre);
        }
        
        if(limite(origemX + 1) && limite(origemY - 1) && limite(origemX + 2) && limite(origemY - 2)) {
            casaPecaAdversaria = tabuleiro.getCasa(origemX + 1, origemY - 1);
            casaLivre = tabuleiro.getCasa(origemX + 2, origemY - 2);
            analisaEliminacoes(casaPecaAdversaria, casaLivre);
        }
    }
    
    /**
     * @param destino Casa para onde a peça irá após a eliminação
     * @return peça adversária que deve ser eliminada.
     */
    public Casa casaParaEliminar(Casa destino) {
        int posicaoXEliminacao = -1, posicaoYEliminacao = -1;
        Casa casaEliminacao;
        
        if(destino.getX() > casa.getX()) {
            posicaoXEliminacao = casa.getX() + 1;
        }
        else if(destino.getX() < casa.getX()) {
            posicaoXEliminacao = casa.getX() - 1;
        }
        
        if(destino.getY() > casa.getY()) {
            posicaoYEliminacao = casa.getY() + 1;
        } else if(destino.getY() < casa.getY()) {
            posicaoYEliminacao = casa.getY() - 1;
        }
        
        return casaEliminacao = new Casa(posicaoXEliminacao, posicaoYEliminacao);
    }
    
    /**
     * @param tabuleiro Tabuleiro para o parâmetro do método verificaEliminacao.
     * Verifica se podem ser feitas eliminações sucessivas.
     */
    public void eliminacaoSucessiva(Tabuleiro tabuleiro){
        limpaPosicoesPossiveis();
        
        verificaEliminacao(tabuleiro);
        setEliminacaoSucessiva();
    }
    
    /**
     * Define o valor de eliminacaoSucessiva.
     */
    public void setEliminacaoSucessiva() {
        eliminacaoSucessiva = existeEliminacao();
    }
    
    /**
     * @return se a peça pode fazer eliminações sucessivas.
     */
    public boolean getEliminacaoSucessiva() {
        return eliminacaoSucessiva;
    }
    
    /**
     * @return a direcao da peça.
     */
    public int getDirecao() {
        return direcao;
    }
    
    /**
     * @return true se uma peça dada como parâmetro está no ArrayList de posições possíveis após a eliminação.
     */
    public boolean verificaCasaEliminacao(Casa destinoEliminacao) {
        for(Casa casa : posicoesPossiveis) {
            if(casa.verificacaoMesmaPosicao(destinoEliminacao)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Verifica se a peça chegou numa posição para virar dama. Se a peca já for dama, ela vira uma peça simples.
     */
    public void verificaDama() {
        if((tipo == 0 || tipo == 3) && casa.getY() == 7) {
            setDama();
        }
        else if((tipo == 2 || tipo == 1) && casa.getY() == 0) {
            setDama();
        }
    }
    
    /**
     * @param pos Posição dada para a verificação
     * Verifica se uma posição dada está dentro das posições do tabuleiro.
     * @return true se a posição dada está, false caso o contrário.
     */
    public boolean limite(int pos) {
        if (pos < 0) {
            return false;
        } else if (pos > 7) {
            return false;
        } else {
            return true;
        }
    }
}
