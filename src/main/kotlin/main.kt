import data.Student
import data.studentList
import kotlinx.html.H1
import kotlinx.html.dom.append
import kotlinx.html.js.h1
import kotlinx.html.js.li
import kotlinx.html.js.ol
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.dom.clear

var ascending = true

fun main() {
    document.getElementById("root")!!
        .append {
            h1 {
                +"Students"
                onClickFunction = onCLickFunction()
            }
            ol {
                attributes += "id" to "listStudents"
                studentList.map {
                    li {
                        attributes += "id" to "${it.surname}"
                        onClickFunction = absentFunction(it)
                        +"${it.firstname} ${it.surname}"
                    }
                }
            }
        }
}

private fun absentFunction(it: Student): (Event) -> Unit {
    return { event ->
        var id = document.getElementById(it.surname)!!
        if (id.className != "absent")
                id.className = "absent"
        else {
            id.className = ""
        }
    }
}

private fun H1.onCLickFunction(): (Event) -> Unit {
    return {
        val listStudents = document.getElementById("listStudents")!!
        listStudents.clear()
        listStudents.append {
            if (ascending)
                studentList.sortBy { it.firstname }
            else
                studentList.sortByDescending { it.firstname }
            ascending = !ascending
            studentList.map {

                li {
                    attributes += "id" to "${it.surname}"
                    onClickFunction = absentFunction(it)
                    +"${it.firstname} ${it.surname}"
                }
            }
        }
    }
}
