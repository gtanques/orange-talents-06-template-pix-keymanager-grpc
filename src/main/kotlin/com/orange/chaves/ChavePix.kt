package com.orange.chaves

import com.orange.TipoDeConta
import com.orange.contas.ContaAssociada
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "uk_chave_pix", columnNames = ["chave"])])
data class ChavePix(
    @Id
    val id : String = UUID.randomUUID().toString(),

    val clienteId: String,

    @field:NotNull @Enumerated(EnumType.STRING)
    val tipoDeChave: TipoDeChave,

    val chave: String,

    @field:NotNull @Enumerated(EnumType.STRING)
    val tipoDeConta: TipoDeConta,

    @Embedded
    val conta: ContaAssociada,
)


