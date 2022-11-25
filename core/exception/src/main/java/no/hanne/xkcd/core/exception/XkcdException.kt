package no.hanne.xkcd.core.exception

open class XkcdException(private val localized: String?, cause: Throwable) : Exception(cause) {
    override fun getLocalizedMessage(): String? {
        return localized
    }
    override fun toString(): String {
        return "Error happened because $cause with data $localizedMessage\n$message"
    }
}
