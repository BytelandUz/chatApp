package uz.dk.f1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import uz.dk.f1.adapters.MessageAdapter
import uz.dk.f1.databinding.ActivityMessageBinding
import uz.dk.f1.modes.Message
import uz.dk.f1.modes.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var adapterMessage: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        reference = firebaseDatabase.getReference("users")

        val user = intent.getSerializableExtra("KEY_OBJ_USER") as User

        binding.btnSendMessage.setOnClickListener {
            val messageTEXT = binding.edMessage.text.toString()

            val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
            val date = simpleDateFormat.format(Date())

            val message = Message(messageTEXT,date, firebaseAuth.currentUser?.uid,user.uid)

            // har safar yangi key yaratib beradi
            val key = reference.push().key
            reference.child("${firebaseAuth.currentUser?.uid}/messages/${user.uid}/${key}").setValue(message)

            reference.child("${user.uid}/messages/${firebaseAuth.currentUser?.uid}/$key").setValue(message)
        }

        reference.child("${firebaseAuth.currentUser?.uid}/messages/${user.uid}")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = ArrayList<Message>()
                    val children = snapshot.children

                    for (child in children) {
                        val value = child.getValue(Message::class.java)
                        if (value != null) {
                            list.add(value)
                        }
                    }

                    adapterMessage = MessageAdapter(list, firebaseAuth.currentUser?.uid.toString())
                    binding.rvMessage.adapter = adapterMessage
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


    }
}