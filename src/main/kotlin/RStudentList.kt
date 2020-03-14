import data.Student
import react.*
import react.dom.li
import react.dom.ol

interface RStudentListProps: RProps {
    var students: Array<Student>
}

class RStudentList: RComponent<RStudentListProps, RState>() {
    override fun RBuilder.render() {
        ol {
            props.students.map {
                li {
                    rstudent(it)
                }
            }
        }
    }
}

fun RBuilder.rstudentlist(students: Array<Student>) =
    child(
        functionalComponent<RStudentListProps> {
            ol {
                students.map {
                    li {
                        rstudent(it)
                    }
                }
            }
        }
    ) {
        attrs.students = students
    }
