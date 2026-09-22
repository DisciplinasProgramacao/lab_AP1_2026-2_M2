import java.util.LinkedList;
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

/**
 * 
 * Documentação criada com auxílio de agente de código (GitHub Copilot)
 * 
 */

/**
 * Representa uma pessoa física cliente de um banco. Uma pessoa pode ter 
 * diversas contas bancárias. 
 * <p>
 * @author Joao Caram
 * @version 1.0
 */
public class PessoaFisica {

    /**
     * Saldo médio mínimo para isenção de tarifa.
     */
    private static final double SALDO_ISENCAO = 1000d;

    /**
     * Tarifa padrão aplicada quando o saldo médio do cliente está abaixo do limite.
     */
    private static final double TARIFA = 23d;

    /**
     * Percentual adicional aplicado quando o cliente está em situação financeira crítica.
     */
    private static final double TAXA_STATUS = 0.1d;

    private String nome;
    private String CPF;
    private LinkedList<ContaCorrente> contas;

    /**
     * Construtor uma nova pessoa física. Os dados não estão sendo validados (ou seja, 
     * pode ser strings vazias ou fora de um formato padrão esperado)
     * @param nome nome do cliente
     * @param CPF número do CPF do cliente (sem validação)
     */
    public PessoaFisica(String nome, String CPF) {
        this.nome = nome;
        this.CPF = CPF;
        contas = new LinkedList<>();
    }

    /**
     * Retorna o CPF (identificador) do cliente
     * @return CPF do cliente (string sem validação)
     */
    public String getCPF() {
        return CPF;
    }

    /**
     * Adiciona uma conta corrente para o cliente.
     * @param conta conta corrente a ser vinculada ao cliente
     * @return quantidade total de contas após a tentativa de inclusão
     */
    public int adicionarConta(ContaCorrente conta) {
        if (conta != null && conta.titular().equals(CPF)) {
            contas.add(conta);
        }
        return contas.size();
    }

    /**
     * Retorna o somatório do saldo de todas as contas correntes do cliente, 
     * sejam elas positivas ou negativas.
     * @return Double, que pode ser positivo ou negativo
     */
    public double saldoTotal() {
        double saldo = 0;
        for (ContaCorrente contaCorrente : contas) {
            saldo += contaCorrente.saldo();
        }
        return saldo;
    }

    /**
     * Calcula o crédito restante do cliente, de acordo com as regras do problema. 
     * @return Double não negativo.
     */
    public double credito() {
        double credito = 0;
        for (ContaCorrente contaCorrente : contas) {
            credito += contaCorrente.limiteTotal();
            if (contaCorrente.saldo() < 0) {
                credito += contaCorrente.saldo();
            }
        }
        return credito;
    }

    /**
     * Avalia a situação financeira do cliente pela proporção de contas com saldo negativo.
     * <p>
     * A resposta deve ser:
     * <ul>
     *   <li>até 10% de contas negativas: "Bom"</li>
     *   <li>acima de 10% e até 50%: "Regular"</li>
     *   <li>acima de 50% e até 75%: "Ruim"</li>
     *   <li>acima de 75%: "Péssimo"</li>
     * </ul>
     * @return String com o status financeiro do cliente
     */
    public String status() {
        //TODO
        return "";
    }

    /**
     * Calcula a tarifa aplicada ao cliente. Pode haver isenção de tarifa,
     * ou uma taxa que aumenta a tarifa, de acordo com os requisitos do problema.
     * @return Double não negativo.
     */
    public double tarifa() {
        //TODO
        return 0d;
    }

    /**
     * Retorna um resumo do cliente com nome, CPF, status financeiro,
     * quantidade de contas, saldo total e tarifa aplicada.     
     * @return String com a descrição formatada do cliente
     */
    public String relatorio() {
        return String.format(
            "Cliente %s (%s) - %s\n " +
            "\tTotal de contas :%d\n" +
            "\tSaldo total: R$ %.2f\n" +
            "TARIFA: R$ %.2f",
            nome, CPF, status(), contas.size(), saldoTotal(), tarifa()
        );
    }
}