package hu.toliver.whacook.whacook

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform