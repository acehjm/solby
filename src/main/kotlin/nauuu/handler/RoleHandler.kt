package nauuu.handler

import nauuu.domain.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Description
 *
 * @author nauuu
 * @date 2017-11-14
 */
fun create(roleName: String, roleDesc: String?): Role {
    return transaction {
        Role.new {
            name = roleName
            desc = roleDesc
        }
    }
}

fun createG(groupName: String, groupDesc: String?): Group {
    return transaction {
        Group.new {
            name = groupName
            desc = groupDesc
        }
    }
}

fun update(role: Role): Role {
    return transaction {
        Role.new(role.id.value) {
            name = role.name
            desc = role.desc
        }
    }
}

fun findOne(id: Long): DRole? = transaction { Role.find { Roles.id eq id }.firstOrNull()?.toRole() }

fun findAll(): List<DRole> = transaction { Role.all().map { it.toRole() } }

fun insert() {
    transaction {
        val u = Users.insert {
            it[name] = "user_2"
            it[number] = "102"
            it[email] = "em2@em2.em"
            it[mobile] = "10033322222"
            it[gender] = 0
            it[password] = "123"
            it[role] = Role[1].id
            it[group] = Group[1].id
        }
        u
    }
}
