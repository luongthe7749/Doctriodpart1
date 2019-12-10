package com.example.doctriodpart1

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.util.*


class DashBoardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var progressBarr : ProgressBar
    lateinit var callbackManager: CallbackManager
    val MY_REQUEST_CODE : Int = 1234
    lateinit var providers : List<AuthUI.IdpConfig>
    lateinit var  sharePreferences : SharedPreferences

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        radio_Button_Remember.isChecked = true
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        callbackManager  = CallbackManager.Factory.create()
        //

        progressBarr = findViewById(R.id.progressBarrrr) as ProgressBar

        textViewRegister.setOnClickListener {
            startActivity(Intent(applicationContext,Register_Activity::class.java))
            finish()
        }

        button_signin.setOnClickListener {
            progressBarr.visibility = View.VISIBLE
            SignInUser()

        }

        textViewForgotPassword.setOnClickListener {
            val dialog : Dialog = Dialog(this,R.style.DialogTheme)
            dialog.setContentView(R.layout.dialog_forgotpass)
            dialog.show()

            val Button_Reset_Pass : Button = dialog.findViewById(R.id.button_Reset_Pass) as Button
            val EditText_Email_Forgot : EditText = dialog.findViewById(R.id.editText_Email_Forgot) as EditText
            val ImageView_Back_Forgot : ImageView = dialog.findViewById(R.id.imageView_Back_Forgot) as ImageView

            ImageView_Back_Forgot.setOnClickListener{
                dialog.dismiss()
            }
            Button_Reset_Pass.setOnClickListener{
                val emaill = EditText_Email_Forgot.text.toString().trim()
//            || !Patterns.EMAIL_ADDRESS.matcher(emaill).matches()
                if (emaill.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emaill).matches()){
                    EditText_Email_Forgot.error = "Email is not valid"
                    return@setOnClickListener
                }
                auth.sendPasswordResetEmail(emaill).addOnCompleteListener{task : Task<Void> ->
                    if(task.isComplete){
                       dialog.dismiss()
                        Toast.makeText(applicationContext,"Please check your email to reset your password and login",Toast.LENGTH_LONG).show()
                    }
                }

            }

        }

//        printHashKey(this)


        buttonGGorFB.setOnClickListener{
            showSignin()
        }
        providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()) as List<AuthUI.IdpConfig>


        sharePreferences  = getSharedPreferences("Forn_Loginn", Context.MODE_PRIVATE)
        editText_Email.setText(sharePreferences.getString("usernamee",""))
        editText_Pass.setText(sharePreferences.getString("passwordd",""))
        radio_Button_Remember.isChecked = sharePreferences.getBoolean("checkedd",false)
    }


    private fun showSignin() {
        startActivityForResult(
            AuthUI
                .getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),MY_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                //Toast.makeText(applicationContext,user!!.email,Toast.LENGTH_LONG).show()
                startActivity(Intent(applicationContext,NavigationActivity::class.java))
            } else {
                Toast.makeText(applicationContext,"Error onActivityResult",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun SignInUser() {

        val username : String = editText_Email.text.toString().trim()
        val password : String = editText_Pass.text.toString().trim()

        if (username.isEmpty()) {
            progressBarr.visibility = View.GONE
            editText_Email.error = "User name is not valid"
            editText_Email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            editText_Email.error = "Email is not valid"
            progressBarr.visibility = View.GONE
            editText_Email.requestFocus()
            return
        }
        if (password.isEmpty()) {
            editText_Pass.error = "password is not valid"
            progressBarr.visibility = View.GONE
            editText_Pass.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->

                val editor : SharedPreferences.Editor = sharePreferences.edit()
                if(radio_Button_Remember.isChecked == true){
                    editor.putString("username", username)
                    editor.putString("password", password)
                    editor.putBoolean("checked", true)
                    editor.commit()
                }
                else{

                    editor.remove("username")
                    editor.remove("password")
                    editor.putBoolean("checked", false)
                    editor.commit()
                }

                progressBarr.visibility = View.INVISIBLE

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("anhthe123", "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("anhthe123", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Login failed.",
                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }

                // ...
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun updateUI(currentUser: FirebaseUser?){
        progressBarr.visibility = View.INVISIBLE
        if (currentUser != null){
            if(currentUser.isEmailVerified){
                startActivity(Intent(applicationContext,NavigationActivity::class.java))
                finish()
                Toast.makeText(baseContext, "Login Sucessfully.",
                    Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(baseContext, "Please check email to verify",
                    Toast.LENGTH_SHORT).show()
            }

        }else{
        }
    }
}


