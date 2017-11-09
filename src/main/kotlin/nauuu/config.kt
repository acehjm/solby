package nauuu

import io.ktor.application.ApplicationEnvironment
import io.ktor.config.ApplicationConfig
import org.jetbrains.exposed.sql.Database

/**
 * Description
 *
 * @author nauuu
 * @date 2017-10-17
 */
fun connectDB(env: ApplicationEnvironment) {
    val db = env.config.config("database")
    Database.connect(
            url = db.property("url").getString(),
            driver = db.property("driver").getString(),
            user = db.property("user").getString(),
            password = db.property("password").getString()
    )
}

fun ApplicationConfig.prop(prop: String): String = this.property(prop).getString()