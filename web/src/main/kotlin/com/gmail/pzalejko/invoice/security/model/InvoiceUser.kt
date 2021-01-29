package com.gmail.pzalejko.invoice.security.model

import io.quarkus.security.credential.Credential
import io.quarkus.security.identity.SecurityIdentity
import io.smallrye.mutiny.Uni
import java.security.Permission
import java.security.Principal

data class InvoiceUser(val _principal: Principal,   val _credential: Credential, val _roles: MutableSet<String>, val accountId: Long) : SecurityIdentity {

    override fun getPrincipal(): Principal {
        return _principal
    }

    override fun isAnonymous(): Boolean {
        return false
    }

    override fun getRoles(): MutableSet<String> {
        return _roles;
    }

    override fun hasRole(role: String?): Boolean {
        return _roles.contains(role)
    }

    override fun <T : Credential?> getCredential(credentialType: Class<T>?): T? {
        if(credentialType!!.isAssignableFrom(_credential.javaClass)){
            return _credential as T
        }
        return null
    }

    override fun getCredentials(): MutableSet<Credential> {
        return setOf(_credential).toMutableSet()
    }

    override fun <T : Any?> getAttribute(name: String?): T {
        TODO("Not yet implemented")
    }

    override fun getAttributes(): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun checkPermission(permission: Permission?): Uni<Boolean> {
        TODO("Not yet implemented")
    }
}