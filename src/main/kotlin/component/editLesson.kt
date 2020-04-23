package component
import data.Lesson
import hoc.withDisplayName
import react.*
import react.dom.*
import org.w3c.dom.events.Event
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.router.dom.navLink
import react.router.dom.route
import kotlin.browser.document

interface EditLessonProps : RProps {
    var lesson: Lesson
    var index: Int
    var onClickRenameLesson: (Int, String) -> Unit
}

val fEditLesson =
    functionalComponent<EditLessonProps> {
        h2 {
            + "Edit lesson :: ${it.lesson.name}"
        }
        div {
            span{
                + "Enter new lesson name: "
            }
            input(type = InputType.text) {
                attrs {
                    id = "renameLesson-input"
                }

            }
            button {
                +"Save"
                attrs.onClickFunction = {_ ->
                    val element = document.getElementById("renameLesson-input") as HTMLInputElement
                    it.onClickRenameLesson(it.index, element.value)
                }
            }
        }
    }

fun RBuilder.editLesson(
    lesson: Lesson,
    index: Int,
    onClickRenameLesson: (Int, String) -> Unit
) = child(
    withDisplayName("Edit Lesson", fEditLesson)
) {
    attrs.lesson = lesson
    attrs.index = index
    attrs.onClickRenameLesson = onClickRenameLesson
}