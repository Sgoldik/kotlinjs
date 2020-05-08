package component

import data.Lesson
import hoc.withDisplayName
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import react.functionalComponent
import redux.WrapperAction

interface AnyEditListProps<O> : RProps {
    var objs: Array<O>
    var onClickAddItem: (Array<String>) -> Unit
    var onClickRemoveItem: (Int) -> Unit
}

fun <T> fAnyEditList(
    list: RBuilder.(Array<T>, String, String) -> ReactElement,
    addItem: RBuilder.((Array<String>) -> Unit) -> ReactElement,
    name: String,
    path: String
) =
    functionalComponent<AnyEditListProps<T>> {
        list(it.objs, name, path)
        h2 {
            + "Remove"
        }
        div ("remove-buttons"){
            it.objs.mapIndexed { index, obj ->
                button {
                    +"Remove ${obj.toString()}"
                    attrs.onClickFunction = { _ ->
                        it.onClickRemoveItem(index)
                    }
                }
            }
        }
        addItem(it.onClickAddItem)
    }

fun <T> RBuilder.anyEditList(
    list: RBuilder.(Array<T>, String, String) -> ReactElement,
    addItem: RBuilder.((Array<String>) -> Unit) -> ReactElement,
    anys: Array<T>,
    name: String,
    path: String,
    onClickAddItem: (Array<String>) -> Unit,
    onClickRemoveItem: (Int) -> Unit
) = child(
    withDisplayName(name, fAnyEditList<T>(list, addItem, name, path))
){
    attrs.objs = anys
    attrs.onClickAddItem = onClickAddItem
    attrs.onClickRemoveItem = onClickRemoveItem
}
