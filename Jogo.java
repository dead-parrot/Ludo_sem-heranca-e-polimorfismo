/**
 * Implementa as mecânicas e regras do jogo Ludo.
 *
 * @author Alan Moraes / alan@ci.ufpb.br
 * @author Victor Koehler / koehlervictor@cc.ci.ufpb.br
 */
public class Jogo {

    // Tabuleiro do jogo
    private final Tabuleiro tabuleiro;
    
    // Dados do jogo.
    private final Dado[] dados;
    
    //indice guarita
    int j=0;
    
    //array cores/turno
    private String[] turno={"VERDE","VERMELHO","AZUL","AMARELO"};
    private int turnos = 0;
    
    //teste
    
    private boolean rolou = false;
    
    private Guarita minhaGuarita;
    
    /**
     * Construtor padrão do Jogo Ludo.
     * Isto é, um jogo de Ludo convencional com dois dados.
     */
    public Jogo() {
        this(2);
    }
    
    /**
     * Construtor do Jogo Ludo para inserção de um número arbitrário de dados.
     * @param numeroDados Número de dados do jogo.
     */
    public Jogo(int numeroDados) {
        this.tabuleiro = new Tabuleiro();
        this.dados = new Dado[numeroDados];
        
        for (int i = 0; i < this.dados.length; i++) {
            // remover parâmetro do construtor para dado não batizado
            this.dados[i] = new Dado(i);
        }

        inicializaJogo();
    }

    /**
     * Construtor do Jogo Ludo para inserção de dados arbitrários.
     * Útil para inserir dados "batizados" e fazer testes.
     * @param dados Dados
     */
    public Jogo(Dado[] dados) {
        this.tabuleiro = new Tabuleiro();
        this.dados = dados;
        assert dados.length > 0; // TO BE REMOVED

        inicializaJogo();
    }

    private void tirarDaGuarita(String Cor)
    {
        Guarita guarita = tabuleiro.getGuarita(Cor);
        Casa casaGuarita;
        Casa casaInicio;
        Peca peca;
        if(guarita.getNumeroDePecas() > 0)
        { 
            casaGuarita = guarita.getCasa(guarita.getNumeroDePecas()-1);//Sempre vamos tirar a peça que está por último
            peca = casaGuarita.getPeca();
            casaInicio = tabuleiro.getCasaInicio(Cor);
            colocarPeca(peca, casaInicio);     
        }
    }
    
    private void voltarPraGuarita(Peca algumaPeca)
    {
        //Sempre vamos devolver a peca para a primeira posição livre
        
        Guarita guarita;
        int tamanhoDaGuarita;
        String corDaPeca = algumaPeca.getCor();
        guarita = tabuleiro.getGuarita(corDaPeca);
        tamanhoDaGuarita = guarita.getNumeroDePecas();// Se 3 peças dentro, o primeiro espaço livre está na posição 3 do Array 
        algumaPeca.mover(guarita.getCasa(tamanhoDaGuarita));
        
    }
    
    private void colocarPeca(Peca algumaPeca, Casa aterisagem)
    {
        if (!aterisagem.possuiPeca())
        {           
            //System.out.println("O loop tá aqui pq não tinha peça na casa");
            // Finalmente, a variável casaN contém a casa que a peça deve ser inserida.
            algumaPeca.mover(aterisagem);
        }
        else 
        {
            //System.out.println("A cor da peça dessa casa é "+ (proximaCasa.getPeca()).getCor());
            
            if(aterisagem.getPeca().getCor() != algumaPeca.getCor())//Cores diferentes = inimigas
            {
                voltarPraGuarita(aterisagem.getPeca());
                algumaPeca.mover(aterisagem);
            }
            
            else if(aterisagem.getPeca().getCor() == algumaPeca.getCor() && aterisagem.ehCasaFinal())//Cores iguais na última casa
            {
                algumaPeca.mover(aterisagem);
                aterisagem.maisUmaPeca();
            }
        }
    }
    
    private void inicializaJogo() {

        // AQUI SUGERE-SE QUE SE INSIRA A INICIALIZAÇÃO DO JOGO
        // ISTO É, A INSERÇÃO DAS PEÇAS NO TABULEIRO E AS DEFINIÇÕES DOS CAMPOS
        // SE NECESSÁRIO, MODIFIQUE A ASSINATURA DO MÉTODO
        
        
        
        //
        // TRECHO DE EXEMPLO
        //
        
        
        // Vamos inicializar a guarita verde colocando as 4 peças do jogador verde nela.
        //
        // Guarita = espaço onde fica as peças fora do jogo;
        // Consulte a imagem "referencia.png" acompanhada na pasta dessa classe.
        Guarita guarita;
        
        guarita = tabuleiro.getGuarita("VERDE");
        for (Casa casaGuarita : guarita.getTodasAsCasas()) {
            Peca novaPeca = new Peca("VERDE");
            novaPeca.mover(casaGuarita);
        }
        guarita = tabuleiro.getGuarita("AZUL");
        for (Casa casaGuarita : guarita.getTodasAsCasas()) {
            Peca novaPeca = new Peca("AZUL");
            novaPeca.mover(casaGuarita);
        }
        guarita = tabuleiro.getGuarita("VERMELHO");
        for (Casa casaGuarita : guarita.getTodasAsCasas()) {
            Peca novaPeca = new Peca("VERMELHO");
            novaPeca.mover(casaGuarita);
        }
        guarita = tabuleiro.getGuarita("AMARELO");
        for (Casa casaGuarita : guarita.getTodasAsCasas()) {
            Peca novaPeca = new Peca("AMARELO");
            novaPeca.mover(casaGuarita);
        }
        
    }

    /**
     * Método invocado pelo usuário através da interface gráfica ou da linha de comando para jogar os dados.
     * Aqui deve-se jogar os dados e fazer todas as verificações necessárias.
     */
    public void rolarDados() {

        // AQUI SE IMPLEMENTARÁ AS REGRAS DO JOGO.
        // TODA VEZ QUE O USUÁRIO CLICAR NO DADO DESENHADO NA INTERFACE GRÁFICA,
        // ESTE MÉTODO SERÁ INVOCADO.
        
        
        //
        // TRECHO DE EXEMPLO
        //
        if (rolou)
            return;
        
        // Aqui percorremos cada dado para lançá-lo individualmente.
        for (Dado dado : dados) {
            dado.rolar();
            //System.out.println("Valor: " + dado.getValor());
            //System.out.println("Tamanho do array: " + dados.length);
        }
        
        
        //String Jogador = getJogadorDaVez();
        if(dados[0].getValor() == dados[1].getValor()){
            tirarDaGuarita(getJogadorDaVez());                    
        }
                
        minhaGuarita = tabuleiro.getGuarita(getJogadorDaVez());            
        if (minhaGuarita.getNumeroDePecas() == 4)
        {
            rolou = false;
        }
        else
        {
            rolou = true;
        }
        
        if(turnos == 3)
            turnos = 0;
        else
            turnos++;
    }
    
    /**
     * Método invocado pelo usuário através da interface gráfica ou da linha de comando quando escolhida uma peça.
     * Aqui deve-se mover a peça e fazer todas as verificações necessárias.
     * @param casa Casa escolhida pelo usuário/jogador.
     */
    public void escolherCasa(Casa casa) {

        // AQUI SE IMPLEMENTARÁ AS REGRAS DO JOGO.
        // TODA VEZ QUE O USUÁRIO CLICAR EM UMA CASA DESENHADA NA INTERFACE GRÁFICA,
        // ESTE MÉTODO SERÁ INVOCADO.
        
        System.out.println("Será que vai pra próxima");
        
        if(!rolou)//teste
            return;
        
        //
        // TRECHO DE EXEMPLO
        //
        
        // Perguntamos à casa se ela possui uma peça. 
        // Se não possuir, não há nada para se fazer.
        if (!casa.possuiPeca()) {
            return;
        }
        
        
        // Perguntamos à casa qual é a peça.
        Peca peca = casa.getPeca();

        // Percorremos cada dado, somando o valor nele à variável somaDados.
        int somaDados = 0;
        for (Dado dado : dados) {
            somaDados += dado.getValor();
        }
        
        // Percorreremos N casas.
        Casa proximaCasa = casa;
        for (int i = 0; i < somaDados && proximaCasa != null; i++) {
            proximaCasa = proximaCasa.getCasaSeguinte();
        }
        
        colocarPeca(peca, proximaCasa);
        
        rolou = false;//teste
        
    }
    
    /**
     * Retorna o jogador que deve jogar os dados ou escolher uma peça.
     * @return Cor do jogador.
     */
    public String getJogadorDaVez() {
        String[] turno={"VERDE","VERMELHO","AZUL","AMARELO"};
        return turno[turnos];
    }
    
    /**
     * O tabuleiro deste jogo.
     * @return O tabuleiro deste jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * Retorna o i-ésimo dado deste jogo entre 0 (inclusivo) e N (exclusivo).
     * Consulte getQuantidadeDeDados() para verificar o valor de N
     * (isto é, a quantidade de dados presentes).
     * @param i Indice do dado.
     * @return O i-ésimo dado deste jogo.
     */
    public Dado getDado(int i) {
        return dados[i];
    }
    
    /**
     * Obtém a quantidade de dados sendo usados neste jogo.
     * @return Quantidade de dados.
     */
    public int getQuantidadeDados() {
        return dados.length;
    }
}
