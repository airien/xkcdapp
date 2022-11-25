package no.hanne.xkcd.util

import java.security.MessageDigest

object Hasher {
    fun String.md5(): String {
        return hashString(this, "MD5")
    }

    fun String.sha256(): String {
        return hashString(this, "SHA-256")
    }

    private fun hashString(input: String, algorithm: String): String {
        return MessageDigest
            .getInstance(algorithm)
            .digest(input.toByteArray())
            .fold("") { str, hash -> str + "%02x".format(hash) }
    }
}