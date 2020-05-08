package component

import data.Lesson
import data.State
import data.Student
import hoc.withDisplayName
import react.*
import react.dom.*
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.router.dom.redirect
import redux.EditStudent
import redux.RAction
import redux.Store
import redux.WrapperAction
import kotlin.browser.document

interface EditStudentProps : RProps {
    var student: Student
    var index: Int
    // var onClickRenameStudent: (Int, String, String) -> Unit
    var store: Store<State, RAction, WrapperAction>
}

val fEditStudent =
    functionalComponent<EditStudentProps> { props ->
        h2 {
            + "Edit student :: ${props.student.firstname} ${props.student.surname}"
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
                    props.onClickRenameStudent(props.index, name.value, surname.value)
                }
            }
        }
    }

fun EditStudentProps.onClickRenameStudent(index: Int, firstname: String, surname: String) { 
        store.dispatch(EditStudent(index, firstname, surname))
    }

fun RBuilder.editStudent(
    student: Student,
    index: Int,
    store: Store<State, RAction, WrapperAction>
) = child(
    withDisplayName("Edit Student", fEditStudent)
) {
    attrs.student = student
    attrs.index = index
    attrs.store = store
}