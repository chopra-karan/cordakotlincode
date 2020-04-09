package com.template.states

import com.template.contracts.AccountContract
import com.template.schema.AccountSchemaV1
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState


@BelongsToContract(AccountContract::class)
data class AccountState(val accountId :String,
                    val value: Int,
                    val name: String,
                    val party: Party,
                    override val linearId: UniqueIdentifier = UniqueIdentifier()):
        LinearState, QueryableState {
    /** The public keys of the involved parties. */
    override val participants: List<AbstractParty> get() = listOf(party)

    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is AccountSchemaV1 -> AccountSchemaV1.PersistentAccount(
                    this.accountId,
                    this.value,
                    this.name,
                    this.linearId.id
            )
            else -> throw IllegalArgumentException("Unrecognised schema $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(AccountSchemaV1)

}