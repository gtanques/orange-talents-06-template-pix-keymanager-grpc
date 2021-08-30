package com.orange.chaves

import com.orange.KeymanagerGrpcServiceGrpc.KeymanagerGrpcServiceImplBase
import com.orange.RegistraChavePixRequest
import com.orange.RegistraChavePixResponse
import com.orange.exception.ErroHandler
import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@ErroHandler
@Singleton
class RegistraChaveEndpoint(@Inject private val service: NovaChavePixService) : KeymanagerGrpcServiceImplBase() {
    override fun registra(
        request: RegistraChavePixRequest,
        responseObserver: StreamObserver<RegistraChavePixResponse>
    ) {
        try{
            val novaChave = request.paraChavePix()
            val chaveCriada = service.registra(novaChave, responseObserver)

            responseObserver.onNext(
                    RegistraChavePixResponse.newBuilder()
                        .setClienteId(chaveCriada?.clienteId)
                        .setPixId(chaveCriada?.id)
                        .build())

        }catch (e: ConstraintViolationException){
            responseObserver.onError(
                Status.INVALID_ARGUMENT
                    .withDescription("dados inválidos!")
                    .asRuntimeException())
        }

        responseObserver.onCompleted()
    }
}