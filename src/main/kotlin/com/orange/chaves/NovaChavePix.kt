package com.orange.chaves

import com.orange.TipoDeConta
import com.orange.anotacoes.ValidaChavesPix
import com.orange.anotacoes.ValidaUUID
import com.orange.chaves.TipoDeChave.ALEATORIA
import com.orange.chaves.TipoDeChave.valueOf
import com.orange.contas.ContaAssociada
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidaChavesPix
@Introspected
data class NovaChavePix(
    @ValidaUUID
    @field:NotBlank val clienteId: String,
    @field:NotNull val tipoDeChave: TipoDeChave?,
    @field:Size(max = 77) val chave: String,
    @field:NotNull val tipoDeConta: TipoDeConta?
) {

    fun paraChavePix(contaResponse: ContaAssociada): ChavePix {
        return ChavePix(
            clienteId = UUID.fromString(this.clienteId).toString(),
            tipoDeChave = valueOf(this.tipoDeChave!!.name),
            chave = if(this.tipoDeChave == ALEATORIA) UUID.randomUUID().toString() else this.chave,
            tipoDeConta = TipoDeConta.valueOf(this.tipoDeConta!!.name),
            conta = contaResponse
        )
    }

}
