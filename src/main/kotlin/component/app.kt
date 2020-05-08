package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import redux.*

interface AppProps : RProps {
    var lessons: Array<Lesson>
    var students: Array<Student>
    var store: Store<State, RAction, WrapperAction>
}

interface RouteNumberResult : RProps {
    var number: String
}

fun fApp () =
    functionalComponent<AppProps> { props ->
        header {
            h1 { +"App" }
            nav {
                ul("navigation") {
                    li("navigation-item") { navLink("/lessons") { +"Lessons" } }
                    li("navigation-item") { navLink("/students") { +"Students" } }
                    li("navigation-item") { navLink("editLessons") { +"Edit Lessons" } }
                    li("navigation-item") { navLink("editStudents") { +"Edit Students" } }
                }
            }
        }

        switch {
            route("/lessons",
                exact = true,
                render = {
                    anyList(props.store.getState().lessons, "Lessons", "/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(props.store.getState().students, "Students", "/students")
                }
            )
            route("/editLessons",
                exact = true,
                render = renderEditLessons(props)
            )
            route("/editStudents",
                exact = true,
                render = renderEditStudents(props)
            )
            route("/lessons/:number",
                render = renderLesson(props)
            )
            route("/students/:number",
                render = renderStudent(props)
            )
            route("/editLessons/:number",
                render = editLesson(props)
            )
            route("/editStudents/:number",
                render = editStudent(props)
            )
        }
    }

fun AppProps.onClickAddStudent(): (Array<String>) -> Unit =
    { student: Array<String> ->
        store.dispatch(AddStudent(student[0], student[1]))
    }

fun AppProps.onClickAddLesson(): (Array<String>) -> Unit =
    { lesson: Array<String> ->
        store.dispatch(AddLesson(lesson[0]))
    }

fun AppProps.onClickRemoveLesson(): (Int) -> Unit =
    { index: Int ->
        store.dispatch(DeleteLesson(index))
    }

fun AppProps.onClickRemoveStudent(): (Int) -> Unit =
    { index: Int ->
        store.dispatch(DeleteStudent(index))
    }

fun AppProps.onClickStudent(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(index, num))
        }
    }

fun AppProps.onClickLesson(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(num, index))
        }
    }

fun RBuilder.renderLesson(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val lesson = props.store.getState().lessons.getOrNull(num)
        if (lesson != null)
            anyFull(
                RBuilder::student,
                lesson,
                props.store.getState().students,
                props.store.getState().presents[num],
                props.onClickLesson(num)
            )
        else
            p { +"No such lesson"}
    }

fun RBuilder.renderStudent(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val student = props.store.getState().students.getOrNull(num)
        if (student != null)
            anyFull(
                RBuilder::lesson,
                student,
                props.store.getState().lessons,
                props.store.getState().presents.map {
                    it[num]
                }.toTypedArray(),
                props.onClickStudent(num)
            )
        else
            p { +"No such student"}
    }

fun RBuilder.editLesson(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val lesson = props.store.getState().lessons.getOrNull(num)
        if (lesson != null)
            editLesson(
                lesson,
                num,
                props.store
            )
        else
            p { +"No such lesson" }
    }

fun RBuilder.editStudent(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val student = props.store.getState().students.getOrNull(num)
        if (student != null)
            editStudent(
                student,
                num,
                props.store
            )
        else
            p { +"No such student" }
    }

fun RBuilder.renderEditStudents(props: AppProps): () -> ReactElement =
    {
        anyEditList(
            RBuilder::anyList,
            RBuilder::addStudent,
            props.store.getState().students,
            "Edit students",
            "/editStudents",
            props.onClickAddStudent(),
            props.onClickRemoveStudent()
        )
    }

fun RBuilder.renderEditLessons(props: AppProps): () -> ReactElement =
    {
        anyEditList(
            RBuilder::anyList,
            RBuilder::addLesson,
            props.store.getState().lessons,
            "Edit lessons",
            "/editLessons",
            props.onClickAddLesson(),
            props.onClickRemoveLesson()
        )
    }

fun RBuilder.app (
    lessons: Array<Lesson>,
    students: Array<Student>,
    store: Store<State, RAction, WrapperAction>
) =
    child (
        withDisplayName("App", fApp())
    ) {
        attrs.lessons = lessons
        attrs.students = students
        attrs.store = store
    }