package no.hanne.xkcd.core.models.network

data class NetworkingConstants(
    val debug: Boolean = java.lang.Boolean.parseBoolean("true"),
    val enableLogging: Boolean = false,
    val applicationId: String = "no.hanne.xkcd",
    val buildType: String = "debug",
    val versionCode: Int = 1,
    val versionName: String = "1.0",
    val xkcdUrl: String = "https://xkcd.com/",
    val explainUrl: String = "https://www.explainxkcd.com/wiki/index.php/",
    val typesenceUrl: String = "https://qtg5aekc2iosjh93p.a1.typesense.net/multi_search?use_cache=true",
    val typesenceUrlAppKey: String? = "replaceMe/"
)
