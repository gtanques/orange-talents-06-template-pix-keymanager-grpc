package com.orange.chaves

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TipoDeChaveTest{
    @Nested
    inner class ALEATORIA{
        @Test
        fun `deve ser valido quando chave aleatoria for nula ou vazia`(){
            with(TipoDeChave.ALEATORIA){
                assertTrue(valida(null))
                assertTrue(valida(""))
            }
        }

        @Test
        fun `deve ser valido quando chave aleatoria possuir um valor`(){
            with(TipoDeChave.ALEATORIA){
                assertFalse(valida("um valor qualquer"))
            }
        }
    }

    @Nested
    inner class CPF{
        @Test
        fun `nao deve validar um numero de cpf correto`(){
            with(TipoDeChave.CPF){
                assertTrue(valida("59128775074"))
            }
        }

        @Test
        fun `deve validar quando um numero de cpf seja incorreto`(){
            with(TipoDeChave.CPF){
                assertFalse(valida("5912877507"))
            }
        }
    }

    @Nested
    inner class EMAIL{
        @Test
        fun `nao deve validar quando o email estiver correto`(){
            with(TipoDeChave.EMAIL){
                assertTrue(valida("naruto@gmail.com"))
            }
        }

        @Test
        fun `deve validar quando o email seja incorreto`(){
            with(TipoDeChave.EMAIL){
                assertFalse(valida("narutogmail.com"))
            }
        }
    }

    @Nested
    inner class CELULAR{
        @Test
        fun `nao deve validar quando o numero de celular estiver correto`(){
            with(TipoDeChave.CELULAR){
                assertTrue(valida("49988775566"))
            }
        }

        @Test
        fun `deve validar quando o numero de celular seja incorreto`(){
            with(TipoDeChave.CELULAR){
                assertFalse(valida("(49)98877-5566"))
            }
        }
    }

}