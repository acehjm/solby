package nauuu.domain

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Description
 *
 * @author nauuu
 * @date 2017/07/03
 */

object Users : LongIdTable() {
    val name = varchar("name", 50)
    val email = varchar("email", 250).uniqueIndex()
    val city = reference("city", Cities)
}

object Cities : LongIdTable() {
    val name = varchar("name", 50)
}

class User(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<User>(Users)

    var name by Users.name
    var email by Users.email
    var city by City referencedOn Users.city
}

class City(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<City>(Cities)

    var name by Cities.name
    val users by User referrersOn Users.city
}

fun ss(): String {
    transaction {
        create(Cities, Users)

        val stPete = City.new {
            name = "St. Petersburg"
        }

        User.new {
            name = "user_007"
            email = "user007@mail.com"
            city = stPete
        }
    }
    return "Hi!Boy."
}

fun vv(id: Long): User? {
    return transaction {
        User.find { Users.id greaterEq id }.firstOrNull()
    }
}