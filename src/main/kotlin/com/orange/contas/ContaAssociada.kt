package com.orange.contas

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class ContaAssociada(
    @field:NotBlank @Column(nullable = false)
    val instituicao: String,
    @field:NotBlank @Column(nullable = false)
    val nomeDoTitular: String,
    @field:NotBlank @Column(nullable = false)
    val cpfDoTitular: String,
    @field:NotBlank @Column(nullable = false)
    val agencia: String,
    @field:NotBlank @Column(nullable = false)
    val numero: String,
    @field:NotBlank @Column(nullable = false)
    val ispb: String
)
