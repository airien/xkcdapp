package no.hanne.xkcd.navigation

enum class Route(val destination: String, val parameter: String? = null) {
    Home("/home"),
    Login("/profile"),
    Settings("/settings"),
    Artist("/artist"),
    Artists("/artists"),
    Splash("/splash"),
    AddArtist("/artist/add"),
    Items("/items"),
    Item("/item"),
    AddItem("/items/add"),
}
fun Route.withParameters(values: List<Any?>): String {
    return this.destination.plus(values.joinToString(separator = "/"))
}

fun Route.withParameter(value: String): String {
    return this.destination.replace("{${this.parameter}}", value)
}
