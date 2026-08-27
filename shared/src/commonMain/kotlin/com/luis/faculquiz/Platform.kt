package com.luis.faculquiz

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform