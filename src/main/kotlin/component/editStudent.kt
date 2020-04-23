package component
import data.Lesson
import data.Student
import hoc.withDisplayName
import react.*
import react.dom.*
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.router.dom.redirect

import kotlin.browser.document

interface EditStudentProps : RProps {
    var student: Student
    var index: Int
    var onClickRenameStudent: (Int, String, String) -> Unit
}

val fEditStudent =
    functionalComponent<EditStudentProps> {
        h2 {
            + "Edit student :: ${it.student.firstname} ${it.student.surname}"
        }
        div {
            span{
                + "First Name: "
            }
            input(type = InputType.text) {
                attrs {
                    id = "renameStudent-input-1"
                }
            }
            span{
                + "Surname: "
            }
            input(type = InputType.text) {
                attrs {
                    id = "renameStudent-input-2"
                }
            }
            button {
                +"Save"
                attrs.onClickFunction = {_ ->
                    val name = document.getElementById("renameStudent-input-1") as HTMLInputElement
                    val surname = document.getElementById("renameStudent-input-2") as HTMLInputElement
                    it.onClickRenameStudent(it.index, name.value, surname.value)
                }
            }
        }
    }

fun RBuilder.editStudent(
    student: Student,
    index: Int,
    onClickRenameStudent: (Int, String, String) -> Unit
) = child(
    withDisplayName("Edit Student", fEditStudent)
) {
    attrs.student = student
    attrs.index = index
    attrs.onClickRenameStudent = onClickRenameStudent
}