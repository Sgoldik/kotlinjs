package container

import component.*
import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import redux.*
import react.redux.rConnect

interface AnyFullDispatchProps : RProps {
    var onClick: (Int) -> (Event) -> Unit
}

interface AnyFullStateProps<O, S> : RProps {
    var subobjs: Map<Int, S>
    var presents: Map<Int, Boolean>?
}

interface AnyFullOwnProps<O> : RProps {
    var obj: Pair<Int, O>
}

private fun getLessonsVisible(
    lessons: Map<Int, Lesson>,
    presents: Map<Int, Boolean>?,
    filter: VisibilityFilter
): Map<Int, Lesson> = when (filter) {
    VisibilityFilter.SHOW_ALL -> lessons
    VisibilityFilter.SHOW_ABSENT -> {
        lessons.filter {
            !presents!!.getValue(it.key)
        }
    }
    VisibilityFilter.SHOW_PRESENT -> {
        lessons.filter {
            presents!!.getValue(it.key)
        }
    }
}

private fun getStudentsVisible(
    students: Map<Int, Student>,
    presents: Map<Int, Boolean>?,
    filter: VisibilityFilter
): Map<Int, Student> = when (filter) {
    VisibilityFilter.SHOW_ALL -> students
    VisibilityFilter.SHOW_ABSENT -> {
        students.filter {
            !presents!!.getValue(it.key)
        }
    }
    VisibilityFilter.SHOW_PRESENT -> {
        students.filter {
            presents!!.getValue(it.key)
        }
    }
}

val lessonFullContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            AnyFullOwnProps<Lesson>,
            AnyFullStateProps<Lesson, Student>,
            AnyFullDispatchProps,
            AnyFullProps<Lesson, Student>>(
        { state, ownProps ->
            subobjs = getStudentsVisible(state.students, state.presents[ownProps.obj.first], state.visibilityFilter)
            presents = state.presents[ownProps.obj.first]
        },
        { dispatch, ownProps ->
            onClick =
                { index ->
                    {
                        dispatch(ChangePresent(ownProps.obj.first, index))
                    }
                }
        }
    )(
        withDisplayName(
            "LessonFull",
            fAnyFull<Lesson, Student>(RBuilder::student)
        )
            .unsafeCast<RClass<AnyFullProps<Lesson, Student>>>()
    )

val studentFullContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            AnyFullOwnProps<Student>,
            AnyFullStateProps<Student, Lesson>,
            AnyFullDispatchProps,
            AnyFullProps<Student, Lesson>>(
        { state, ownProps ->
            subobjs = getLessonsVisible(state.lessons, state.presentsStudent(ownProps.obj.first), state.visibilityFilter)
            presents = state.presentsStudent(ownProps.obj.first)
        },
        { dispatch, ownProps ->
            onClick = { index ->
                {
                    dispatch(ChangePresent(index, ownProps.obj.first))
                }
            }
        }
    )(
        withDisplayName(
            "StudentFull",
            fAnyFull<Student, Lesson>(RBuilder::lesson)
        )
            .unsafeCast<RClass<AnyFullProps<Student, Lesson>>>()
    )