package com.ghackdev.accountservice.datafetchers

import com.ghackdev.accountservice.codegen.DgsConstants
import com.ghackdev.accountservice.codegen.types.Account
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsEntityFetcher
import com.netflix.graphql.dgs.DgsQuery

@DgsComponent
class AccountsDataFetcher {
    private val accounts: List<Account> = java.util.List.of(
        Account("1", "John Doe", 1000.0),
        Account("2", "Jane Smith", 2500.5)
    )

    @DgsQuery
    fun accounts(): List<Account> {
        return accounts
    }

    @DgsEntityFetcher(name = DgsConstants.ACCOUNT.TYPE_NAME)
    fun account(values: Map<String?, Any?>): Account {
        return accounts.stream()
            .filter { account: Account -> account.id.equals(values["id"]) }
            .findFirst()
            .orElse(null)
    }
}