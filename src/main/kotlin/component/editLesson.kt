package component
import data.Lesson
import data.State
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
import redux.*
import kotlin.browser.document

interface EditLessonProps : RProps {
    var lesson: Lesson
    var index: Int
    var store: Store<State, RAction, WrapperAction>
}

val fEditLesson =
    functionalComponent<EditLessonProps> { props ->
        h2 {
            + "Edit lesson :: ${props.lesson.name}"
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
                    props.onClickRenameLesson(props.index, element.value)
                }
            }
        }
    }

fun EditLessonProps.onClickRenameLesson (index: Int, name: String) {
    store.dispatch(EditLesson(index, name))
}

fun RBuilder.editLesson(
    lesson: Lesson,
    index: Int,
    store: Store<State, RAction, WrapperAction>
) = child(
    withDisplayName("Edit Lesson", fEditLesson)
) {
    attrs.lesson = lesson
    attrs.index = index
    attrs.store = store
}