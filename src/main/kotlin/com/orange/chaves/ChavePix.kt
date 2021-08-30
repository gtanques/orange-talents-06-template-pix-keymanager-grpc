package com.orange.chaves

import com.orange.TipoDeConta
import com.orange.contas.ContaAssociada
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "uk_chave_pix", columnNames = ["chave"])])
data class ChavePix(
    @Column(nullable = false)
    val clienteId: String,

    @field:NotNull @Enumerated(EnumType.STRING) @Column(nullable = false)
    val tipoDeChave: TipoDeChave,

    @Column(nullable = false, unique = true)
    val chave: String,

    @field:NotNull @Enumerated(EnumType.STRING)
    val tipoDeConta: TipoDeConta,

    @Embedded
    val conta: ContaAssociada,
){
    @Id
    val id: String = UUID.randomUUID().toString()

    @Column(updatable = false, nullable = false)
    @field:NotNull
    private val criadoEm : Instant = Instant.now()
}


