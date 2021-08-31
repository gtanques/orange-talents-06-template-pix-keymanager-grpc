package com.orange.exception

import com.fasterxml.jackson.annotation.JsonSubTypes
import io.micronaut.aop.Around
import kotlin.annotation.AnnotationTarget.*

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(CLASS, FILE, FUNCTION)
@Around
@JsonSubTypes.Type(InterceptorExceptionHandler::class)
annotation class ErroHandler
