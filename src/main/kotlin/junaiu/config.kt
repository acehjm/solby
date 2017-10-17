package junaiu

import org.jetbrains.exposed.sql.Database
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.config.ApplicationConfig
import org.jetbrains.ktor.host.applicationHostEnvironment

/**
 * Description
 *
 * @author dyoon
 * @date 2017-10-17
 */

fun connectToDB() = applicationHostEnvironment {
    val db = config.config("database")
    println(db)
    println("url: ${db.property("url").getString()}")
//    Database.connect(
//            url = db.prop("url"),
//            driver = db.prop("driver"),
//            user = db.prop("user"),
//            password = db.prop("password")
//    )
}

fun ApplicationConfig.prop(prop: String): String = this.property(prop).getString()