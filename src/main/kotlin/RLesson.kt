import data.*
import org.w3c.dom.events.Event
import react.*
import react.dom.h2

interface RLessonProps : RProps {
    var students: Array<Student>
}

interface RLessonState: RState {
    var present: Array<Boolean>
}

class RLesson: RComponent<RLessonProps, RLessonState>() {
    override fun componentWillMount() {
        state.apply{
            present = Array(props.students.size) { false }
        }
    }

    override fun RBuilder.render () {
        h2 {
            +"OOP"
        }
        studentList(props.students, state.present, onClick())
    }

    fun onClick() = { index: Int ->
        { _: Event ->
            setState {
                present[index] = !present[index]
            }
        }
    }


}

fun RBuilder.lessons (students: Array<Student>) =
    child(RLesson::class) {
        attrs.students = students
    }
