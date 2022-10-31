interface UserRepository {
    fun changeUserName(newName: String): Boolean
    fun changeUserPhoto(newPhoto: Bitmap): Boolean
    fun changeUserAge(newAge: Int): Boolean
}
