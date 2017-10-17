package junaiu

import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Description
 *
 * @author dyoon
 * @date 2017/07/03
 */

object Users : LongIdTable() {
    val name = varchar("name", 50)
    val email = varchar("email", 250).uniqueIndex()
}

fun ss(): String {
    transaction {
        create(Users)

        Users.insert {
            it[name] = "user_005"
            it[email] = "user005@mail.com"
        }
    }
    return "Hi!Boy."
}

fun vv(id: Long): Users {
    transaction {
        Users.select { Users.id.eq(id) }.firstOrNull()
    }
    return Users
}