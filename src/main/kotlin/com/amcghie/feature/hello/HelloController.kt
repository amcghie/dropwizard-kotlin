package com.amcghie.feature.hello

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/hello")
@Produces(MediaType.TEXT_HTML)
class HelloController {

    @GET
    fun renderHello() : HelloView {
        return HelloView()
    }
}