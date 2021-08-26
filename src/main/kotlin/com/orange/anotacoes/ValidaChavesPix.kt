package com.orange.anotacoes

import com.orange.chaves.NovaChavePix
import javax.inject.Singleton
import javax.validation.*
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@ReportAsSingleViolation
@Constraint(validatedBy = [ValidaChavesPixValidator::class])
@Retention(RUNTIME)
@Target(CLASS, TYPE)
annotation class ValidaChavesPix(
    val message: String = "chave inválida: (\${validatedValue.tipoDeChave})",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

@Singleton
class ValidaChavesPixValidator : ConstraintValidator<ValidaChavesPix, NovaChavePix> {
    override fun isValid(
        value: NovaChavePix?,
        context: ConstraintValidatorContext?
    ): Boolean {
        if (value?.tipoDeChave == null) {
            return false
        }

        return value.tipoDeChave.valida(value.chave)
    }

}
