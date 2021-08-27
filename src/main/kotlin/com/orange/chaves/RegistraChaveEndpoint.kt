package com.orange.chaves

import com.orange.KeymanagerGrpcServiceGrpc
import com.orange.RegistraChavePixRequest
import com.orange.RegistraChavePixResponse
import com.orange.exception.ErroHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@ErroHandler
@Singleton
class RegistraChaveEndpoint(@Inject private val service: NovaChavePixService) :
    KeymanagerGrpcServiceGrpc.KeymanagerGrpcServiceImplBase() {
    override fun registra(
        request: RegistraChavePixRequest,
        responseObserver: StreamObserver<RegistraChavePixResponse>
    ) {
        val novaChave = request.toModel()
        service.registra(novaChave, responseObserver).let {
            responseObserver.onNext(
                RegistraChavePixResponse.newBuilder()
                    .setClienteId(it?.clienteId)
                    .setPixId(it?.id)
                    .build()
            )
        }

        responseObserver.onCompleted()
    }
}