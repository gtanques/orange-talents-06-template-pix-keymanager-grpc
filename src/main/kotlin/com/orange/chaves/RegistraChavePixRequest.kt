package com.orange.chaves

import com.orange.RegistraChavePixRequest
import com.orange.TipoDeChave.UNKNOWN_TIPO_DE_CHAVE
import com.orange.TipoDeConta
import com.orange.TipoDeConta.UNKNOWN_TIPO_DE_CONTA

fun RegistraChavePixRequest.paraChavePix(): NovaChavePix {
    return NovaChavePix(
        clienteId = clienteId,
        tipoDeChave = when(tipoDeChave){
                UNKNOWN_TIPO_DE_CHAVE -> null
            else -> TipoDeChave.valueOf(tipoDeChave.name)
        },
        chave = chave,
        tipoDeConta = when(tipoDeConta){
            UNKNOWN_TIPO_DE_CONTA -> null
            else -> TipoDeConta.valueOf(tipoDeConta.name)
        }
    )
}