package nauuu.domain

import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * ables
 *
 * @author nauuu
 * @date 2017-11-13
 */
object Users : LongIdTable() {
    val name = varchar("name", 50)
    val number = varchar("number", 50).uniqueIndex()
    val gender = integer("gender")
    val email = varchar("email", 250).uniqueIndex()
    val mobile = varchar("mobile", 11).uniqueIndex()
    val password = varchar("password", 250)
//    val home = reference("home", Folders, ReferenceOption.CASCADE)
    val role = reference("role", Roles)
    val group = reference("group", Groups)
}

object Groups : LongIdTable() {
    val name = varchar("name", 50)
    val desc = varchar("description", 250).nullable()
}

object Roles : LongIdTable() {
    val name = varchar("name", 50)
    val desc = varchar("description", 250).nullable()
}

object Folders : LongIdTable() {
    val name = varchar("name", 50)
    val owningUser = reference("own_user", Users)
    val createdAt = datetime("created_at")
}

object Files : LongIdTable() {
    val name = varchar("name", 50)
    val path = varchar("path", 250)
    val size = integer("size")
    val owningUser = reference("own_user", Users)
    val createdAt = datetime("created_at")
    val folder = reference("folder", Folders)
}
