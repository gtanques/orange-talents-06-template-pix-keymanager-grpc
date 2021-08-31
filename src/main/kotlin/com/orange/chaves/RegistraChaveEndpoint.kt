package com.orange.chaves

import com.orange.KeymanagerGrpcServiceGrpc.KeymanagerGrpcServiceImplBase
import com.orange.RegistraChavePixRequest
import com.orange.RegistraChavePixResponse
import com.orange.exception.ErroHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ErroHandler
class RegistraChaveEndpoint(@Inject private val service: NovaChavePixService) : KeymanagerGrpcServiceImplBase() {
    override fun registra(
        request: RegistraChavePixRequest,
        responseObserver: StreamObserver<RegistraChavePixResponse>
    ) {
        val novaChave = request.paraChavePix()
        val chaveCriada = service.registra(novaChave, responseObserver)

        responseObserver.onNext(
            RegistraChavePixResponse.newBuilder()
                .setClienteId(chaveCriada?.clienteId)
                .setPixId(chaveCriada?.id)
                .build()
        )

        responseObserver.onCompleted()
    }
}