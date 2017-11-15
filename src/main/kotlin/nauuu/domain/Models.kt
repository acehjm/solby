package nauuu.domain

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.joda.time.DateTime

/**
 * models
 *
 * @author nauuu
 * @date 2017-11-14
 */
//--------------------------------------------------------------user;
class User(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<User>(Users)

    var name by Users.name
    var number by Users.number
    var email by Users.email
    var mobile by Users.mobile
    var gender by Users.gender
    var password by Users.password

    //    var home by Folder referencedOn Users.home
    var group by Group referencedOn Users.group
    var role by Role referencedOn Users.role
}

fun User.toUser() = DUser(id.value, name, gender, number, email, mobile, password, null, null)

data class DUser(val id: Long, var name: String, var gender: Int, var number: String, var email: String,
                 var mobile: String, var password: String, var group: DGroup?, var role: DRole?)

//--------------------------------------------------------------group;
class Group(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Group>(Groups)

    var name by Groups.name
    var desc by Groups.desc
    val users by User referrersOn Users.group
}

fun Group.toGroup() = DGroup(id.value, name, desc, null)

data class DGroup(val id: Long, var name: String, var desc: String?, val users: List<DUser>?)

//--------------------------------------------------------------role;
class Role(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Role>(Roles)

    var name by Roles.name
    var desc by Roles.desc
    val users by User referrersOn Users.role
}

fun Role.toRole() = DRole(id.value, name, desc, users.map { it.toUser() })

data class DRole(val id: Long, var name: String, var desc: String?, val users: List<DUser>)

//--------------------------------------------------------------folder;
class Folder(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Folder>(Folders)

    var name by Folders.name
    var owningUser by User referencedOn Folders.owningUser
    var createdAt by Folders.createdAt
    val files by File referrersOn Files.folder
}

fun Folder.toFolder() = DFolder(id.value, name, owningUser.toUser(), createdAt, null)
data class DFolder(val id: Long, var name: String, var owningUser: DUser, var createdAt: DateTime, val files: List<DFile>?)

//--------------------------------------------------------------file;
class File(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<File>(Files)

    var name by Files.name
    var path by Files.path
    var size by Files.size
    var owningUser by User referencedOn Files.owningUser
    var createdAt by Files.createdAt
    var folder by Folder referencedOn Files.folder
}

fun File.toFile() = DFile(id.value, name, path, size, owningUser.toUser(), createdAt, null)
data class DFile(val id: Long, var name: String, var path: String, var size: Int, var owningUser: DUser,
                 var createdAt: DateTime, val folder: DFolder?)