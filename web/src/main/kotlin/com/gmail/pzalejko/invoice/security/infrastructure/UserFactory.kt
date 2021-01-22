package com.gmail.pzalejko.invoice.security.infrastructure

import com.gmail.pzalejko.invoice.security.model.InvoiceUser
import io.quarkus.security.credential.PasswordCredential
import org.apache.http.auth.BasicUserPrincipal
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.HashMap
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

@ApplicationScoped
class UserFactory {

    @Inject
    @field: Default
    lateinit var passwordFactory: PasswordFactory

    fun to(username: String, password: CharArray, accountId: Int, roles: Set<String>): Map<String, AttributeValue> {
        val map: MutableMap<String, AttributeValue> = HashMap()

        map["accountId"] = toNumAttr(accountId)
        map["username"] = toStringAttr(username)
        map["password"] = toStringAttr(passwordFactory.encryptPassword(password))
        map["roles"] = toRoles(roles)

        return map
    }

    fun from(request: Map<String, AttributeValue>): InvoiceUser {
        val username = request["username"]!!.s();
        val password = request["password"]!!.s().toCharArray();
        val accountId = request["accountId"]!!.n().toInt();
        val roles = request["roles"]!!.ss().toMutableSet()

        val principal = BasicUserPrincipal(username)
        val credential = PasswordCredential(password)
        return InvoiceUser(principal, credential, roles, accountId);
    }

    private fun toStringAttr(value: String): AttributeValue {
        return AttributeValue.builder().s(value).build()
    }

    private fun toNumAttr(value: Number): AttributeValue {
        return AttributeValue.builder().n(value.toString()).build()
    }

    private fun toRoles(roles: Set<String>): AttributeValue {
        return AttributeValue.builder().ss(roles).build()
    }
}