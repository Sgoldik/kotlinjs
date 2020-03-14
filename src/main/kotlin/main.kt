import data.Student
import data.studentList
import react.dom.*
import react.dom.render
import kotlin.browser.document

fun main() {
    render(document.getElementById("root")!!) {
        h1 {
            +"Students"
        }
        rstudentlist(studentList.toTypedArray())
    }

}