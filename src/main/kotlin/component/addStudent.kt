package component
import hoc.withDisplayName
import react.*
import react.dom.*
import org.w3c.dom.events.Event
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document

interface AddStudentProps : RProps {
    var onClickAddStudent: (Array<String>) -> Unit
}

val fAddStudent =
    functionalComponent<AddStudentProps> {
        h2 {
            +"Add student"
        }
        div {
            span { +"Enter firstname: " }
            input(type = InputType.text) {
                attrs {
                    id = "addStudent-input-1"
                }
            }
            br {}
            span { +"Enter surname: " }
            input(type = InputType.text) {
                attrs {
                    id = "addStudent-input-2"
                }
            }
            br {}
            button {
                +"Add"
                attrs.onClickFunction = { _ ->
                    val firstname = document.getElementById("addStudent-input-1") as HTMLInputElement
                    val surname = document.getElementById("addStudent-input-2") as HTMLInputElement
                    val person = arrayOf(firstname.value, surname.value)
                    it.onClickAddStudent(person)
                }
            }
        }
    }

fun RBuilder.addStudent(
    onClickAddStudent: (Array<String>) -> Unit
) = child(
    withDisplayName("Add Student", fAddStudent)
) {
    attrs.onClickAddStudent = onClickAddStudent
}