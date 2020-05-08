import component.app
import data.*
import react.dom.render
import react.router.dom.browserRouter
import react.router.dom.hashRouter
import redux.*
import kotlin.browser.document
import wrapper.reduxLogger

val store = createStore(
    ::changeReducer,
    State(lessonsList, studentList, Array(lessonsList.size) { Array(studentList.size) { false } }),
    compose(
        rEnhancer(),
        applyMiddleware(
            reduxLogger.logger as Middleware<State, Action, Action, Action, Action>
        )
    )
)

val rootDiv = document.getElementById("root")

fun render () = render(rootDiv) {
    hashRouter {
        app(lessonsList, studentList, store)
    }
}

fun main() {
    render()
    store.subscribe {
        render()
    }
}
