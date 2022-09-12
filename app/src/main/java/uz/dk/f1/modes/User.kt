package uz.dk.f1.modes

import java.io.Serializable

class User: Serializable {
    var email: String? = null
    var displayInfo: String? = null
    var phoneNumbber: String? = null
    var photoUrl: String? = null
    var uid: String? = null
    var isOnline: Boolean? = null

    constructor()

    constructor(
        email: String?,
        displayInfo: String?,
        phoneNumbber: String?,
        photoUrl: String?,
        uid: String?,
        isOnline: Boolean?,
    ) {
        this.email = email
        this.displayInfo = displayInfo
        this.phoneNumbber = phoneNumbber
        this.photoUrl = photoUrl
        this.uid = uid
        this.isOnline = isOnline
    }


}