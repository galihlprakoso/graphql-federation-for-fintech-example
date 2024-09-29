package com.ghackdev.transactionservice.datafetchers

import com.ghackdev.transactionservice.codegen.DgsConstants
import com.ghackdev.transactionservice.codegen.types.Account
import com.ghackdev.transactionservice.codegen.types.Transaction
import com.netflix.graphql.dgs.*
import java.util.stream.Collectors


@DgsComponent
class TransactionsDataFetcher {
    private val transactions: List<Transaction> = listOf(
        Transaction("1", "1", 150.0),
        Transaction("2", "1", 200.0),
        Transaction("3", "2", 500.5)
    )

    @DgsEntityFetcher(name = DgsConstants.ACCOUNT.TYPE_NAME)
    fun account(values: Map<String?, Any?>): Account {
        return Account(values["id"] as String, null)
    }

    @DgsQuery
    fun transactions(): List<Transaction> {
        return transactions
    }

    @DgsData(parentType = DgsConstants.ACCOUNT.TYPE_NAME, field = DgsConstants.ACCOUNT.Transactions)
    fun transactionsForAccount(dfe: DgsDataFetchingEnvironment): List<Transaction> {
        val account = dfe.getSource<Account>()
        if (account != null) {
            return transactions.stream()
                .filter { transaction: Transaction -> transaction.accountId.equals(account.id) }
                .collect(Collectors.toList())
        }

        return emptyList()
    }
}
