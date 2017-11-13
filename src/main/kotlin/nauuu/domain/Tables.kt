package nauuu.domain

import org.jetbrains.exposed.dao.LongIdTable

/**
 * Description
 *
 * @author nauuu
 * @date 2017-11-13
 */
object Users : LongIdTable() {
    val name = varchar("name", 50)
    val email = varchar("email", 250).uniqueIndex()
    val mobile = varchar("mobile", 11).uniqueIndex()
    val gender = integer("gender")
    val password = varchar("password", 250)

    val role = reference("role", Roles)
    val group = reference("group", Groups)
}

object Groups : LongIdTable() {
    val name = varchar("name", 50)
    val desc = varchar("description", 250)
}

object Roles : LongIdTable() {
    val name = Groups.varchar("name", 50)
    val desc = Groups.varchar("description", 250)
}