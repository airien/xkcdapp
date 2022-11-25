package no.hanne.xkcd.core.exception

class RepositoryException(metadata: String?, cause: Throwable) : XkcdException(metadata, cause)
