package com.orange.chaves

import com.orange.KeymanagerGrpcServiceGrpc.KeymanagerGrpcServiceBlockingStub
import com.orange.KeymanagerGrpcServiceGrpc.newBlockingStub
import com.orange.RegistraChavePixRequest
import com.orange.TipoDeChave
import com.orange.TipoDeConta
import com.orange.contas.ContaAssociadaResponse
import com.orange.contas.InstituicaoResponse
import com.orange.contas.TitularResponse
import com.orange.integracoes.DadosContaItauClient
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.http.HttpResponse
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*
import javax.inject.Inject

@MicronautTest(transactional = false)
internal class RegistraChaveEndpointTest(
) {
    @Inject
    lateinit var repository: ChavePixRepository
    @Inject
    lateinit var serviceGrpc: KeymanagerGrpcServiceBlockingStub
    @Inject
    lateinit var itauClient: DadosContaItauClient

    var uuid = UUID.randomUUID().toString()
    private val dadosContaResponse: ContaAssociadaResponse = ContaAssociadaResponse(
        tipo = TipoDeConta.CONTA_CORRENTE.name,
        instituicao = InstituicaoResponse("UNIBANCO ITAU SA", "60701190"),
        agencia = "0001",
        numero = "291900",
        titular = TitularResponse("Yuri Matheus", "86135457004")
    )

    @BeforeEach
    fun setup() {
        repository.deleteAll()
    }

    @Test
    fun `deve registrar nova chave pix`() {
        // cenario
        `when`(itauClient.buscarContaPorTipo(idCliente = uuid, tipo = TipoDeConta.CONTA_CORRENTE.name))
            .thenReturn(HttpResponse.ok(dadosContaResponse))

        // acao
        val response = serviceGrpc.registra(
            RegistraChavePixRequest.newBuilder()
                .setClienteId(uuid)
                .setTipoDeChave(TipoDeChave.EMAIL)
                .setChave("yuri@gmail.com")
                .setTipoDeConta(TipoDeConta.CONTA_CORRENTE)
                .build()
        )

        // validacao
        with(response) {
            assertEquals(uuid, clienteId)
            assertNotNull(pixId)
        }
    }

    @Test
    fun `nao deve registrar chave pix quando chave existente`() {
        `when`(itauClient.buscarContaPorTipo(idCliente = uuid, tipo = TipoDeConta.CONTA_CORRENTE.name))
            .thenReturn(HttpResponse.ok(dadosContaResponse))

        val response = serviceGrpc.registra(
            RegistraChavePixRequest.newBuilder()
                .setClienteId(uuid)
                .setTipoDeChave(TipoDeChave.EMAIL)
                .setChave("yuri@gmail.com")
                .setTipoDeConta(TipoDeConta.CONTA_CORRENTE)
                .build()
        )

        val chaveDuplicada = response



    }

    @Test
    fun `nao deve registrar chave pix quando nao encontrar conta vinculada`() {
    }

    @Test
    fun `nao deve registrar chave pix quando parametros invalidos`() {
    }

    @MockBean(DadosContaItauClient::class)
    fun itauClientMock(): DadosContaItauClient? {
        return Mockito.mock(DadosContaItauClient::class.java)
    }

    @Factory
    class Registrar {
        @Bean
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): KeymanagerGrpcServiceBlockingStub {
            return newBlockingStub(channel)
        }
    }

}