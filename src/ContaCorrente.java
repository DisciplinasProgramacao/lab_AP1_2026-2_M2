/** 
* MIT License
*
* Copyright(c) 2024-26 João Caram <caram@pucminas.br>
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

public class ContaCorrente {
    private static final double TAXA_LIMITE = 0.03;  
    private static final double LIMITE_PADRAO = 10;  
    private static int ultimaConta = 10_000;
	private int numero;
    private String cpfTitular;
    private double saldoAtual;
    private double limite;
    
    /**
     * Encapsula a inicialização de uma conta corrente.
     * @param numero Número da conta (de 10.000 a 99.999)
     * @param CPF CPF do cliente (string sem validação). 
     * @param limite Limite de saque para a conta do cliente (>0). Em caso de valor inválido, o limite fica zerado.
     */
    private void setUp(String CPF, double limite){
        numero = ultimaConta++;;
        this.cpfTitular = CPF;
        this.saldoAtual = 0d;
        if(limite < 0)
            limite = 0;
        this.limite = limite;

    }

    /**
     * Construtor, recebe o número da conta (10.000 < numero < 99.999). Coloca em 99.999 em caso de
     * problemas. CPF sem validação. Esta conta está sendo criada com saldo 0 e limite de R$200.
     * @param numero Número da conta (de 10.000 a 99.999). Caso seja inválido, a conta terá o número 99.999
     * @param CPF CPF do cliente (string sem validação)
     */
    public ContaCorrente(String CPF) {
        setUp(CPF, LIMITE_PADRAO);
    }
    
    /**
     * Construtor, recebe o número da conta (10.000 < numero < 99.999). Coloca em 99.999 em caso de
     * problemas. CPF sem validação. A conta está sendo criada com saldo 0 e limite definido pelo usuário.
     * @param numero Número da conta (de 10.000 a 99.999). Caso seja inválido, a conta terá o número 99.999
     * @param CPF CPF do cliente (string sem validação)
     * @param limite Limite de saque para a conta do cliente (>0). Em caso de valor inválido, o limite fica zerado.
     */
    public ContaCorrente(String CPF, double limite) {
        setUp(CPF, limite);
    }

    /**
     * Retorna o CPF do titular da conta, para efeitos de conferência.
     * @return String com o CPF do titular da conta
     */
    public String titular(){
        return cpfTitular;
    }

    /**
     * Retorna o número (identificador da conta)
     * @return Inteiro com 5 dígitos, ou seja, >= 10_000
     */
    public int getNumero(){
        return  numero;
    }

    /**
     *  Realiza a operação de saque, atualizando o saldo e considerando o limite.
     * Ignora a operação em caso de parâmetro negativo ou valor maior que o saldo + limite atuais.
     * @param valor Valor a ser sacado (deve ser > 0 )
     * @return Valor efetivamente sacado. Pode ser 0 se o saque não foi efetuado.
     */
    public double sacar(double valor) {
        double resposta = 0;
		if (valor > 0 && (valor <= (saldoAtual + limite))) {
            saldoAtual -= valor;
			resposta = valor;
        }
		return resposta;
    }
    
    /**
     *  Realiza a operação de depósito sempre que o valor efetivo for maior que 0. O valor efetivo é
     *  calculado pelo valor do depósito, descontando um possível valor de taxa de uso de limite. 
     *  Se o valor efetivo não for positivo, a operação é ignorada.
     *  @param valor Valor a ser depositado (deve ser >0 )
     * @return Valor efetivamente depositado, descontando possíveis taxas.
     */
    public double depositar(double valor) {
        double valorEfetivo = valor - valorTaxa();
        if (valorEfetivo > 0) {
            saldoAtual += valorEfetivo;			
        }
		return valorEfetivo > 0 ? valorEfetivo : 0;
    }

	/**
     * Calcula a taxa devida sobre o uso do limite da conta. 
     * Se o saldo for positivo (limite não utilizado), retorna 0.
     * @return Valor da taxa a ser cobrada pelo uso do limite (sempre >=0)
     */
    private double valorTaxa() {
        double valor = 0;
        if (saldoAtual < 0) {
            valor = Math.abs(saldoAtual) * TAXA_LIMITE;
        }
        return valor;
    }
	
    /**
     * Retorna o saldo atual da conta, sem incluir o limite (método getter)
     * @return Valor double com o saldo atual da conta, sem incluir o limite.
     */
    public double saldo() {
        return saldoAtual;
    }

    /**
     * Retorna o limite total desta conta. <b>ATENÇÃO</b>: limite total não é o limite <i>restante</i>. 
     * É o limite disponível quando a conta foi criada
     * @return Double não negativo com o limite total da conta.
     */
    public double limiteTotal() {
        return limite;
    }
    
    /**
     * Gera um resumo da conta em string.
     * @return String contendo o resumo com número, CPF, saldo atual e limite;
     */
    public String toString(){
        return String.format("Conta %d, titular %s. Saldo de R$ %.2f e limite de R$ %.2f",
                                     numero, cpfTitular, saldoAtual, limite);
    }
}