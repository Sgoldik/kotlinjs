import data.*
import react.dom.h1
import react.dom.render
import kotlin.browser.document

fun main() {
    render(document.getElementById("root")!!) {
        h1 {
            +"Students"
        }
        lessons(studentList.toTypedArray())
    }

}