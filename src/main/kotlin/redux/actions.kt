package redux

class ChangePresent(val lesson: Int, val student: Int): RAction
class AddStudent(val name: String, val surname: String): RAction
class EditStudent(val index: Int, val firstname: String, val surname: String): RAction
class DeleteStudent(val index: Int) : RAction
class AddLesson(val name: String): RAction
class EditLesson(val index: Int, val name: String): RAction
class DeleteLesson(val index: Int): RAction