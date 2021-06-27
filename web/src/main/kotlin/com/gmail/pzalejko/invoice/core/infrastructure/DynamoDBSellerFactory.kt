package com.gmail.pzalejko.invoice.core.infrastructure

import com.gmail.pzalejko.invoice.core.model.subject.BankAccountNumber
import com.gmail.pzalejko.invoice.core.model.subject.InvoiceSeller
import com.gmail.pzalejko.invoice.core.model.subject.SubjectAddress
import com.gmail.pzalejko.invoice.core.model.subject.SubjectPolandTaxId
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.HashMap
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DynamoDBSellerFactory {

    fun from(map: Map<String, AttributeValue>): InvoiceSeller {
        return InvoiceSeller(
            map["accountId"]!!.n().toLong(),
            map["name"]!!.s(),
            SubjectAddress(
                map["street"]!!.s(),
                map["streetNumber"]!!.s(),
                map["city"]!!.s()
            ),
            SubjectPolandTaxId(map["taxId"]!!.s()),
            BankAccountNumber(map["bankAccountNumber"]!!.s())
        )
    }

    fun to(seller: InvoiceSeller): Map<String, AttributeValue> {
        val accountId = seller.accountId
        val name = seller.name
        val street  = seller.address.street
        val streetNum = seller.address.number
        val city = seller.address.city
        val bankAccount = seller.bankAccount.number
        val taxId = seller.taxId.getTaxId()

        val map: MutableMap<String, AttributeValue> = HashMap()
        map["accountId"] = toNumAttr(accountId)
        map["name"] = toStringAttr(name)
        map["street"] = toStringAttr(street)
        map["streetNumber"] = toStringAttr(streetNum)
        map["city"] = toStringAttr(city)
        map["bankAccountNumber"] = toStringAttr(bankAccount)
        map["taxId"] = toStringAttr(taxId)

        return map
    }

    private fun toStringAttr(value: String): AttributeValue {
        return AttributeValue.builder().s(value).build()
    }

    private fun toNumAttr(value: Number): AttributeValue {
        return AttributeValue.builder().n(value.toString()).build()
    }
}