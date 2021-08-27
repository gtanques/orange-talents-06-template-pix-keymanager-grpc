package com.orange.exception

import io.grpc.Status
import javax.inject.Singleton

@Singleton
class ChavePixJaCadastradaHandler : ExceptionHandler<ChavePixJaCadastradaException> {
    override fun handle(e: ChavePixJaCadastradaException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.ALREADY_EXISTS
                .withDescription(e.message)
                .withCause(e)
        )
    }

    override fun supports(e: Exception): Boolean {
        return e is ChavePixJaCadastradaException
    }
}

class ChavePixJaCadastradaException(
    override val message: String
) : Exception()