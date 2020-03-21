import data.Student
import org.w3c.dom.events.Event
import react.*
import react.dom.h2
import react.dom.li
import react.dom.ol

interface RStudentListProps: RProps {
    var students: Array<Student>
    var presents: Array<Boolean>
    var onClick: (Int) -> (Event) -> Unit
}

val RFStudentList =
    functionalComponent<RStudentListProps> {
        ol {
            it.students.mapIndexed {index, student ->
                li {
                    rstudent(student, it.presents[index], it.onClick(index))
                }
            }
        }
    }

fun RBuilder.studentList(students: Array<Student>, presents: Array<Boolean>, onClick: (Int) -> (Event) -> Unit) = child(RFStudentList) {
    attrs.students = students
    attrs.presents = presents
    attrs.onClick = onClick

}