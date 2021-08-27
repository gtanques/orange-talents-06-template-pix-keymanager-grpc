package com.orange.exception

import io.grpc.BindableService
import io.grpc.stub.StreamObserver
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@InterceptorBean(ErroHandler::class)
class InterceptorExceptionHandler(@Inject private val resolver: ExceptionHandlerResolver)
    : MethodInterceptor<BindableService, Any?> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun intercept(context: MethodInvocationContext<BindableService, Any?>): Any? {
        return try {
            context.proceed()
        } catch (e: Exception) {

            logger.error("[INTERCEPTANDO] - ${e.javaClass.name}, [METODO]: ${context.targetMethod}", e)

            val handler = resolver.resolve(e)
            val status = handler.handle(e)

            GrpcEndpointArguments(context).response()
                .onError(status.asRuntimeException())

            null
        }

    }

    private class GrpcEndpointArguments(val context : MethodInvocationContext<BindableService, Any?>) {
        fun response(): StreamObserver<*> = context.parameterValues[1] as StreamObserver<*>
    }
}
