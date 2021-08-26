package com.orange.integracoes

import com.orange.contas.ContaAssociadaResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client(value = "\${application.client.itau}")
interface DadosContaItauClient {

    @Get(
        value = "/api/v1/clientes/{idCliente}/contas{?tipo}",
        processes = [MediaType.APPLICATION_JSON],
        produces = [MediaType.APPLICATION_JSON]
    )
    fun buscarContaPorTipo(
        @PathVariable idCliente: String,
        @QueryValue tipo: String
    ): HttpResponse<ContaAssociadaResponse>

}
