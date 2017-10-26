package nauuu

import org.jetbrains.ktor.config.ApplicationConfig
import org.jetbrains.ktor.host.applicationHostEnvironment

/**
 * Description
 *
 * @author nauuu
 * @date 2017-10-17
 */

fun connectToDB() = applicationHostEnvironment {

    val db = config.config("database")
    println("url: ${db.property("url").getString()}")
//    Database.connect(
//            url = db.prop("url"),
//            driver = db.prop("driver"),
//            user = db.prop("user"),
//            password = db.prop("password")
//    )
}

fun ApplicationConfig.prop(prop: String): String = this.property(prop).getString()