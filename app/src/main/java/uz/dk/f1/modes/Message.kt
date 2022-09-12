package uz.dk.f1.modes

class Message {
    var message: String? = null
    var date: String? = null
    var fromUi: String? = null
    var ToUi: String? = null

    constructor()

    constructor(message: String?, date: String?, fromUi: String?, ToUi: String?) {
        this.message = message
        this.date = date
        this.fromUi = fromUi
        this.ToUi = ToUi
    }

}