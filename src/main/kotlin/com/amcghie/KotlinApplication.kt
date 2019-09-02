package com.amcghie

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

class KotlinApplication : Application<KotlinApplicationConfiguration>() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            KotlinApplication()
                .run(*args)
        }
    }

    override fun getName(): String = "KotlinApplication"

    override fun initialize(bootstrap: Bootstrap<KotlinApplicationConfiguration>) {
        bootstrap
            .objectMapper
            .registerModule(KotlinModule())
        // TODO: application initialization
    }

    override fun run(
        configuration: KotlinApplicationConfiguration,
        environment: Environment
    ) {
        // TODO: application initialization
    }
}

