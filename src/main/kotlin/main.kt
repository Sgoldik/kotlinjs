import data.Student
import data.studentList
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.*
import kotlinx.html.js.li
import org.w3c.dom.*
import org.w3c.dom.events.Event
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
                attributes += "id" to "selecTitletColor"
                + "Title color:"
                labelInput (options = arrayListOf("white", "yellow", "brown"), colorChangeItemId = "title")
            }
            div {
                attributes += "id" to "selectActiveColor"
                + "List students color:"
                labelInput (options = arrayListOf("white", "yellow", "brown"), colorChangeItemId = "listStudents")
            }
        }
}

fun TagConsumer<HTMLElement>.labelInput(
    classes : String? = null,
    colorChangeItemId: String,
    options: List<String>,
    block : DIV.() -> Unit = {}
) : HTMLDivElement = div (
    classes
) {
    options.forEach{
        label {
            input(InputType.radio, name = colorChangeItemId) {
                attributes += "value" to it
                onClickFunction = changeElementColor(it, colorChangeItemId)
            }
            +it
        }
   }
    block()
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
