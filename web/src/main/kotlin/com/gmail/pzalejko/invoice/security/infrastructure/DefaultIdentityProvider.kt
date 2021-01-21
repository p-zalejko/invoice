package com.gmail.pzalejko.invoice.security.infrastructure

import io.quarkus.security.AuthenticationFailedException
import io.quarkus.security.identity.AuthenticationRequestContext
import io.quarkus.security.identity.IdentityProvider
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.identity.request.UsernamePasswordAuthenticationRequest
import io.smallrye.mutiny.Uni
import java.util.function.Supplier
import javax.annotation.Priority
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Alternative
import javax.enterprise.inject.Default
import javax.inject.Inject

@Alternative
@Priority(1)
@ApplicationScoped
class DefaultIdentityProvider : IdentityProvider<UsernamePasswordAuthenticationRequest> {

    @Inject
    @field: Default
    lateinit var repository: DynamoDbUserRepository

    override fun getRequestType(): Class<UsernamePasswordAuthenticationRequest> {
        return UsernamePasswordAuthenticationRequest::class.java
    }

    override fun authenticate(
        request: UsernamePasswordAuthenticationRequest,
        context: AuthenticationRequestContext
    ): Uni<SecurityIdentity?>? {
        return context.runBlocking(Supplier {
            val user = request.username;
            val pwd = request.password;

            return@Supplier repository.findUser(user, pwd.password)
                ?: throw AuthenticationFailedException("user does not exist")

        })
    }
}