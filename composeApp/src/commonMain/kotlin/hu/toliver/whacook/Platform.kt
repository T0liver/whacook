package hu.toliver.whacook

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform