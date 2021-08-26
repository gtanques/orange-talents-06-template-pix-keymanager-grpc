package com.orange.chaves

import com.orange.integracoes.DadosContaItauClient

import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
class NovaChavePixService(
    @Inject private val repository: ChavePixRepository,
    @Inject private val dadosContaItauClient: DadosContaItauClient
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun registra(@Valid novaChavePix: NovaChavePix): ChavePix {

        if (repository.existsByChave(novaChavePix.chave)) {
            throw IllegalStateException("ChavePix '${novaChavePix.chave}' existente")
        }

        val response = dadosContaItauClient.buscarContaPorTipo(novaChavePix.clienteId!!, novaChavePix.tipoDeConta!!.name)
        logger.info(response.toString())
        val conta = response.body()?.toModel() ?: throw IllegalStateException("Cliente não encontrado no Itau")
        val chave = novaChavePix.toModel(conta)
        repository.save(chave)
        return chave
    }

}
