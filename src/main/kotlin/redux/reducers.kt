package redux

import data.*

fun changeReducer(state: State, action: RAction) =
    when (action) {
        is ChangePresent -> State (
            state.lessons,
            state.students,
            state.presents.mapIndexed { indexLesson, lesson ->
                if (indexLesson == action.lesson)
                    lesson.mapIndexed { indexStudent, student ->
                        if (indexStudent == action.student)
                            !student
                        else student
                    }.toTypedArray()
                else
                    lesson
            }.toTypedArray()
        )
        is AddStudent -> State (
            state.lessons,
            state.students.plus(Student(action.name, action.surname)),
            state.presents.mapIndexed { index, _ ->
                state.presents[index].plus(arrayOf(false))
            }.toTypedArray()
        )
        is EditStudent -> State (
            state.lessons,
            state.students.mapIndexed { indexStudent, student ->
                 if (indexStudent == action.index) {
                     Student(action.firstname, action.surname)
                 } else
                     student

            }.toTypedArray(),
            state.presents
        )
        is DeleteStudent -> State (
            state.lessons,
            state.students.toMutableList().apply { removeAt(action.index) }.toTypedArray(),
            state.presents
        )
        is AddLesson -> State (
            state.lessons.plus(Lesson(action.name)),
            state.students,
            state.presents.plus(
                arrayOf(
                    Array(state.students.size) { false })
            )
        )
        is EditLesson -> State (
            state.lessons.mapIndexed { indexLesson, lesson ->
                if (indexLesson == action.index) {
                    Lesson(action.name)
                } else
                    lesson
            }.toTypedArray(),
            state.students,
            state.presents
        )
        is DeleteLesson -> State (
            state.lessons.toMutableList().apply { removeAt(action.index) }.toTypedArray(),
            state.students,
            state.presents.toMutableList().apply { removeAt(action.index) }.toTypedArray()
        )
        else -> state

    }