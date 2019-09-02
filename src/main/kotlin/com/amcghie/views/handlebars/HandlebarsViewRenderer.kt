package com.amcghie.views.handlebars

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.HelperRegistry
import com.github.jknack.handlebars.cache.GuavaTemplateCache
import com.github.jknack.handlebars.cache.NullTemplateCache
import com.github.jknack.handlebars.cache.TemplateCache
import com.github.jknack.handlebars.helper.DefaultHelperRegistry
import com.google.common.cache.CacheBuilder
import io.dropwizard.views.View
import io.dropwizard.views.ViewRenderer
import java.io.FileNotFoundException
import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.util.Locale
import javax.ws.rs.WebApplicationException

/**
 * A [ViewRenderer] which renders Handlebars (`.hbs`) templates.
 */
class HandlebarsViewRenderer(helperRegistry: HelperRegistry = DefaultHelperRegistry()) : ViewRenderer {

    private var handlebars: Handlebars = Handlebars()
        .with(helperRegistry)

    override fun isRenderable(view: View): Boolean {
        return view.templateName.endsWith(".hbs")
    }

    @Throws(IOException::class, WebApplicationException::class)
    override fun render(view: View, locale: Locale, output: OutputStream) {
        try {
            OutputStreamWriter(output, view.charset.orElse(Charsets.UTF_8))
                .use { writer ->
                    handlebars
                        .compile(view.templateName)
                        .apply(view, writer)
                }
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException("Template ${view.templateName} not found.")
        }
    }

    override fun configure(options: MutableMap<String, String>) {
        handlebars = handlebars
            .with(createCache(options))
            .prettyPrint(options.getOrDefault("prettyPrint", "false").toBoolean())
            .infiniteLoops(options.getOrDefault("infiniteLoops", "false").toBoolean())

    }

    override fun getConfigurationKey(): String = "handlebars"

    private fun createCache(options: MutableMap<String, String>): TemplateCache {
        return if (options.getOrDefault("useCache", "true").toBoolean()) {
            GuavaTemplateCache(
                CacheBuilder
                    .newBuilder()
                    .build()
            )
        } else {
            NullTemplateCache.INSTANCE
        }
    }
}