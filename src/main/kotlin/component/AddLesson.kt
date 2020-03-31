package component
import react.*
import react.dom.*
import org.w3c.dom.events.Event
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement

interface AddLessonState : RState {
    var lesson: String
}
interface AddLessonProps : RProps {
    var onClickAddLesson: (String) -> (Event) -> Unit
}

class AddLesson : RComponent<AddLessonProps, AddLessonState>() {
    override fun RBuilder.render() {
        h2 {
            +"Add lesson"
        }
        div {
            input(type = InputType.text) {
                attrs {
                    onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            lesson = target.value
                        }
                    }
                }
            }
            button {
                +"Add"
                attrs.onClickFunction = props.onClickAddLesson(state.lesson)
            }
        }
    }
}

fun RBuilder.AddLesson(
    onClickAddLesson: (String) -> (Event) -> Unit
) = child(AddLesson::class) {
    attrs.onClickAddLesson = onClickAddLesson
}