import data.Student
import data.studentList
import kotlinx.html.H1
import kotlinx.html.InputType
import kotlinx.html.dom.append
import kotlinx.html.js.*
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.events.Event
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.clear

var ascending = true

fun main() {
    document.getElementById("root")!!
        .append {
            h1 {
                attributes += "id" to "title"
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
            div {
                attributes += "id" to "selectColor"
                + "Title color:"
                arrayListOf("blue", "yellow", "brown").map {
                    label {
                        input(InputType.radio, name = "color") {
                            attributes += "value" to it
                            onClickFunction = changeElementColor(it, "title")
                        }
                        +it
                    }
                }
            }
            div {
                attributes += "id" to "selectColorActive"
                + "Student list color:"
                arrayListOf("white", "yellow", "brown").map {
                    label {
                        input(InputType.radio, name = "color") {
                            attributes += "value" to it
                            onClickFunction = changeElementColor(it, "listStudents")
                        }
                        +it
                    }
                }
            }
        }
}

private fun changeElementColor(it: String, id: String): (Event) -> Unit {
    return { e ->
        val element = document.getElementById(id)!!
        element.setAttribute("style", "color:${it}")
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
