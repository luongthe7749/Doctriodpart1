package com.example.doctriodpart1

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_register_.*
import java.security.MessageDigest
import java.util.regex.Pattern

class Register_Activity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference("Users")

        button_signup.setOnClickListener {
            val username : String = editText_UserName.text.toString().trim()
            val email : String = editText_Email.text.toString().trim()
            val password : String = editText_Pass.text.toString().trim()
            val confirmpassword : String = editText_ConfirmPass.text.toString().trim()

            if (TextUtils.isEmpty(username)) {
                editText_UserName.error = "user name is not valid"
                editText_UserName.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editText_Email.error = "Email is not valid"
                editText_Email.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                editText_Pass.error = "password is not valid"
                editText_Pass.requestFocus()
                return@setOnClickListener
            }
            if (!confirmpassword.equals(password)) {
                editText_ConfirmPass.error = "confirm password is not equal to password"
                editText_ConfirmPass.requestFocus()
                return@setOnClickListener
            }
            SignUpUser(username,email,password)
        }

        imageView_Back.setOnClickListener{
            startActivity(Intent(applicationContext,DashBoardActivity::class.java))
        }

    }

    private fun SignUpUser(username: String, email: String,password: String){

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val uid = user!!.uid
                    val userMap = HashMap<String,String>()
                    userMap["name"] = username
                    userMap["age"] = "Empty"
                    userMap["blood"] = "Empty"
                    userMap["gender"] = "Empty"
                    userMap["height"] = "Empty"
                    userMap["weight"] = "Empty"


                    mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid)


                    user?.sendEmailVerification()

                    mDatabase.setValue(userMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Anhthe123", "Email sent.")
                            Toast.makeText(baseContext, "Please Check email to verify account",
                                Toast.LENGTH_LONG).show()
                            startActivity(Intent(applicationContext,DashBoardActivity::class.java))
                            finish()
                        }
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("anhthe123", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "" +
                            ""+task.exception,
                        Toast.LENGTH_LONG).show()
                }
            }
    }
}
