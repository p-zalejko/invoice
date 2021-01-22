package com.gmail.pzalejko.invoice.security.infrastructure

import io.quarkus.elytron.security.common.BcryptUtil
import org.wildfly.security.password.Password
import org.wildfly.security.password.util.ModularCrypt
import javax.enterprise.context.ApplicationScoped
import org.wildfly.security.password.interfaces.BCryptPassword

@ApplicationScoped
class PasswordFactory {

    fun encryptPassword(password: CharArray): String = BcryptUtil.bcryptHash(password.joinToString(""))

    fun verifyPassword(pwdToVerify: CharArray, encryptedPwd: CharArray): Boolean {
        // convert encrypted password string to a password key
        val rawPassword: Password = ModularCrypt.decode(encryptedPwd)

        // create the password factory based on the bcrypt algorithm
        val factory = org.wildfly.security.password.PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT)

        // create encrypted password based on stored string
        val restored = factory.translate(rawPassword)

        // verify restored password against original
        return factory.verify(restored, pwdToVerify)
    }

}

fun main(args: Array<String>) {
    val encryptPassword = PasswordFactory().encryptPassword("abc".toCharArray())
    print(encryptPassword)
}