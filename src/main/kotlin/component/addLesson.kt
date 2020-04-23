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

interface AddLessonProps : RProps {
    var onClickAddLesson: (Array<String>) -> Unit
}

val fAddLesson =
    functionalComponent<AddLessonProps> {
        h2 {
            +"Add lesson"
        }
        div {
            input(type = InputType.text) {
                attrs {
                    id = "addLesson-input"
                }

            }
            button {
                +"Add"
                attrs.onClickFunction = {_ ->
                    val element = document.getElementById("addLesson-input") as HTMLInputElement
                    val lesson = arrayOf(element.value)
                    it.onClickAddLesson(lesson)
                }

            }
        }
    }

fun RBuilder.addLesson(
    onClickAddLesson: (Array<String>) -> Unit
) = child(
    withDisplayName("Add Lesson", fAddLesson)
) {
    attrs.onClickAddLesson = onClickAddLesson
}