package component

import data.*
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import kotlin.reflect.KClass

interface AppProps : RProps {

}

interface AppState : RState {
    var presents: Array<Array<Boolean>>
    var lessons: Array<Lesson>
    var students: Array<Student>
}

interface RouteNumberResult : RProps {
    var number: String
}

class App : RComponent<AppProps, AppState>() {
    override fun componentWillMount() {
        state.students = studentList
        state.lessons = lessonsList
        state.presents = Array(state.lessons.size) {
            Array(state.students.size) { false }
        }
    }

    override fun RBuilder.render() {
        header {
            h1 { +"App" }
            nav {
                ul ("navigation") {
                    li ("navigation-item") { navLink("/lessons") { + "Lessons" } }
                    li ("navigation-item") { navLink("/students") { + "Students" } }
                    li ("navigation-item") { navLink("editLessons") { + "Edit Lessons"} }
                    li ("navigation-item") { navLink("editStudents") { + "Edit Students"} }
                }
            }
        }

        switch {
            route("/lessons",
                exact = true,
                render = {
                    anyList(state.lessons, "Lessons", "/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(state.students, "Students", "/students")
                }
            )
            route( "/editLessons",
                exact = true,
                render = {
                    anyEditList(
                        RBuilder::anyList,
                        RBuilder::addLesson,
                        state.lessons,
                        "Edit lessons",
                        "/editLessons",
                        onClickAddLesson(),
                        onClickRemoveLesson()
                    )
                }
            )
            route( "/editStudents",
                exact = true,
                render = {
                    anyEditList(
                        RBuilder::anyList,
                        RBuilder::addStudent,
                        state.students,
                        "Edit students",
                        "/editStudents",
                        onClickAddStudent(),
                        onClickRemoveStudent()
                    )
                }
            )
            route("/lessons/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val lesson = state.lessons.getOrNull(num)
                    if (lesson != null)
                        anyFull(
                            RBuilder::student,
                            lesson,
                            state.students,
                            state.presents[num]
                        ) { onClick(num, it) }
                    else
                        p { +"No such lesson" }
                }
            )
            route("/students/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val student = state.students.getOrNull(num)
                    if (student != null)
                        anyFull(
                            RBuilder::lesson,
                            student,
                            state.lessons,
                            state.presents.map {
                                it[num]
                            }.toTypedArray()
                        ) { onClick(it, num) }
                    else
                        p { +"No such student" }
                }
            )
            route("/editLessons/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val lesson = state.lessons.getOrNull(num)
                    if (lesson != null)
                        editLesson(
                            lesson,
                            num,
                            onClickRenameLesson()
                        )
                    else
                        p { +"No such lesson" }
                }
            )
            route("/editStudents/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val student = state.students.getOrNull(num)
                    if (student != null)
                        editStudent(
                            student,
                            num,
                            onClickRenameStudent()
                        )
                    else
                        p { +"No such student" }
                }
            )
        }
    }

    fun transform(source: Array<Array<Boolean>>) =
        Array(source[0].size){row->
            Array(source.size){col ->
                source[col][row]
            }
        }

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }

    fun onClickAddLesson() = { lesson: Array<String> ->
        setState {
            lessons += Lesson(lesson[0])
            presents += arrayOf(
                Array(state.students.size) { false }
            )
        }
    }

    fun onClickAddStudent() = { student: Array<String> ->
        setState {
            students += Student(student[0], student[1])
        }
    }

    fun onClickRemoveLesson() = { index: Int ->
        setState {
            lessons = lessons.toMutableList().apply { removeAt(index) }.toTypedArray()
            presents = presents.toMutableList().apply { removeAt(index) }.toTypedArray()
        }
    }

    fun onClickRemoveStudent() = { index: Int ->
        setState {
            students = students.toMutableList().apply { removeAt(index) }.toTypedArray()

        }
    }

    fun onClickRenameLesson() = { index: Int, newName: String ->
        setState {
            lessons[index].name = newName;
        }
    }

    fun onClickRenameStudent() = { index: Int, firstname: String, surname: String ->
        setState {
            students[index].firstname = firstname
            students[index].surname = surname
        }
    }

}

fun RBuilder.app(

) = child(App::class) {

}