package uz.dk.f1


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import uz.dk.f1.adapters.UserAdapter
import uz.dk.f1.databinding.ActivityRealBinding
import uz.dk.f1.modes.User
import kotlin.time.Duration.Companion.milliseconds

class RealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRealBinding

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var referance: DatabaseReference

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var userAdapter: UserAdapter
    var list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        referance = firebaseDatabase.getReference("users")

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        val user = User(currentUser?.email.toString(),
            currentUser?.displayName.toString(),
            currentUser?.phoneNumber,
            currentUser?.photoUrl.toString(),
            currentUser?.uid,true)

        //firebase ga  yozish uchun
//        referance.child(currentUser?.uid.toString()).setValue(user)

        // firebase dan o'qib olsih uchun
        referance.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                val filterList = arrayListOf<User>()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(User::class.java)

                    if (value != null && currentUser?.uid != value.uid) {
                        list.add(value)
                    }
                    if (value != null && currentUser?.uid == value.uid) {
                        filterList.add(value)
                    }
                }

                if (filterList.isEmpty()) {
                    referance.child(firebaseAuth.currentUser?.uid.toString()).setValue(user)
                }

                userAdapter = UserAdapter(list) { user ->
                    val intent = Intent(this@RealActivity,MessageActivity::class.java)
                    intent.putExtra("KEY_OBJ_USER",user)
                    startActivity(intent)
                }

                binding.userRv.adapter = userAdapter

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    override fun onStart() {
        referance.child("${firebaseAuth.currentUser?.uid.toString()}/online").setValue(true)
        super.onStart()
    }

    override fun onDestroy() {
        referance.child("${firebaseAuth.currentUser?.uid.toString()}/online").setValue(false)
        super.onDestroy()
    }
}