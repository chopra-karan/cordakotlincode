package com.template.schema

import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

object AccountSchema

/**
 * An IOUState schema.
 */
object AccountSchemaV1 : MappedSchema(
        schemaFamily = AccountSchema.javaClass,
        version = 1,
        mappedTypes = listOf(PersistentAccount::class.java)) {
    @Entity
    @Table(name = "account_states")
    class PersistentAccount(
            @Column(name = "accountId")
            var accountId: String,

            @Column(name = "value")
            var value: Int,

            @Column(name = "name")
            var name: String,

            @Column(name = "linear_id")
            var linearId: UUID
    ) : PersistentState() {
        // Default constructor required by hibernate.
        constructor(): this("", 0, "", UUID.randomUUID())
    }
}