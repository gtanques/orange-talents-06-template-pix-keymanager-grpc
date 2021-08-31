package com.orange.contas

data class ContaAssociadaResponse(
    val tipo: String,
    val instituicao: InstituicaoResponse,
    val agencia: String,
    val numero: String,
    val titular: TitularResponse
) {
    fun toModel(): ContaAssociada {
        return ContaAssociada(
            instituicao = this.instituicao.nome,
            nomeDoTitular = this.titular.nome,
            cpfDoTitular = this.titular.cpf,
            agencia = this.agencia,
            numero = this.numero,
            ispb = this.instituicao.ispb
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContaAssociadaResponse

        if (tipo != other.tipo) return false
        if (instituicao != other.instituicao) return false
        if (agencia != other.agencia) return false
        if (numero != other.numero) return false
        if (titular != other.titular) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tipo.hashCode()
        result = 31 * result + instituicao.hashCode()
        result = 31 * result + agencia.hashCode()
        result = 31 * result + numero.hashCode()
        result = 31 * result + titular.hashCode()
        return result
    }
}

data class InstituicaoResponse(val nome: String, val ispb: String)
data class TitularResponse(val nome: String, val cpf: String)