import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

public class ContaCorrenteTest {

    ContaCorrente conta;
    
    @BeforeEach 
    public void setUp(){
        //Arrange
		conta = new ContaCorrente("123456789-01", 100);
    }

    @Test
    public void realizaDepositoCorretamente(){        
		//Act
		double valor = conta.depositar(200);    
		//Assert
		assertEquals(200d, valor, 0.01);
    }
    
    @Test
    public void naoRealizaDepositoNegativo(){
        //Arrange
		conta.depositar(200);
        //Act
		double valor = conta.depositar(-100);
		//Assert
		assertEquals(0d, valor, 0.01);
    }

        @Test
    public void cobraTaxaDeSaldoNegativoNoDeposito(){
        //Arrange
        conta.depositar(200);
        conta.sacar(300);
        
		//Act
		double valor = conta.depositar(100);
        
		//Assert
		assertEquals(97d, valor, 0.01);
    }


    @Test 
    public void naoRealizaSaqueNegativo(){
        //Act
		double valor = conta.sacar(-100);
		//Assert
		assertEquals(0d, valor, 0.01);
    }

    @Test 
    public void realizaSaqueSemLimite(){
        //Arrange
		conta.depositar(200);
        //Act
		double valor = conta.sacar(100);
		//Assert
		assertEquals(100d, valor, 0.01);
    }

    @Test 
    public void realizaSaqueComLimite(){
        //Arrange
		conta.depositar(200);
        //Act
		double valor = conta.sacar(250);
		//Assert
		assertEquals(250d, valor, 0.01);
    }

    @Test 
    public void naoRealizaSaqueAcimaDoLimite(){
        //Arrange
		conta.depositar(200);
        //Act
		double valor = conta.sacar(500);
		//Assert
		assertEquals(0d, valor, 0.01);
    }
}