package com.orange.chaves

import com.orange.RegistraChavePixResponse
import com.orange.exception.ChavePixJaCadastradaException
import com.orange.exception.ContaNaoEncontradaException
import com.orange.integracoes.DadosContaItauClient
import io.grpc.stub.StreamObserver
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
@Validated
class NovaChavePixService(
    @Inject private val repository: ChavePixRepository,
    @Inject private val dadosContaItauClient: DadosContaItauClient
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun registra(
        @Valid novaChavePix: NovaChavePix,
        responseObserver: StreamObserver<RegistraChavePixResponse>
    ): ChavePix? {
        if (repository.existsByChave(novaChavePix.chave)) {
            throw ChavePixJaCadastradaException("ChavePix '${novaChavePix.chave}' existente")
        }

        val response = dadosContaItauClient.buscarContaPorTipo(novaChavePix.clienteId, novaChavePix.tipoDeConta!!.name)
        if(response.status.code != 200){
            throw ContaNaoEncontradaException("Cliente não encontrado no Itau")
        }

        logger.info(response.toString())
        val conta = response.body()!!.toModel()
        val chave = novaChavePix.paraChavePix(conta)

        repository.save(chave)
        return chave
    }
}
