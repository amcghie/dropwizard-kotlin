package com.amcghie

import com.amcghie.feature.hello.HelloController
import com.amcghie.views.handlebars.HandlebarsViewRenderer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import io.dropwizard.views.ViewBundle



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
        bootstrap
            .addBundle(
                ViewBundle<KotlinApplicationConfiguration>(
                    setOf(HandlebarsViewRenderer())
                )
            )
    }

    override fun run(
        configuration: KotlinApplicationConfiguration,
        environment: Environment
    ) {
        environment
            .jersey()
            .register(HelloController())
    }
}

