package com.example.diplom.loggin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import com.example.diplom.MainActivity
import com.example.diplom.R
import com.example.diplom.isReallyOnline
import com.example.diplom.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {
    lateinit var user: User
     var pass=""
    private lateinit var auth: FirebaseAuth
    lateinit var emailEditText:EditText
    lateinit var passEditText:EditText
    lateinit var progressBar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val signup=findViewById<TextView>(R.id.sign_up)
        emailEditText=findViewById(R.id.email)
        passEditText=findViewById(R.id.password)
        progressBar=findViewById(R.id.loginProgressBar)
        val login=findViewById<AppCompatButton>(R.id.loginBtn)
        auth = Firebase.auth
        signup.setOnClickListener{v->startSignUp(v)}
        login.setOnClickListener{v->login(v)}
    }
    fun login(view:View){
            val mEmail=emailEditText.text.toString()
            val mPass=passEditText.text.toString()
            if(mEmail.isEmpty()){
                emailEditText.setError(getString(R.string.not_inputed))
                emailEditText.requestFocus()
                return
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
                emailEditText.setError(getString(R.string.incorrect_email))
                emailEditText.requestFocus()
                return
            }
            if(mPass.isEmpty()){
                passEditText.setError(getString(R.string.not_inputed))
                passEditText.requestFocus()
                return
            }
            if(mPass.length<6){
                passEditText.setError(getString(R.string.password_error_length))
                passEditText.requestFocus()
                return
            }
            userLogin(mEmail,mPass)

    }
    fun startSignUp(view: View){
        val intent=Intent(this, SignupActivity::class.java)
        getResult.launch(intent)
    }
    private val getResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode==Activity.RESULT_OK){
            val value= Gson().fromJson( it.data?.getStringExtra("user"),User::class.java)
            user=value
            val password= it.data?.getStringExtra("password").toString()
            pass=password
            user.email?.let { it1 -> userLogin(it1,pass) }
        }
    }

    fun userLogin(email:String,password:String){
        progressBar.visibility=View.VISIBLE
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
                progressBar.visibility=View.GONE
                val intent=Intent(applicationContext,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                finish()
                startActivity(intent)
            }else{
                progressBar.visibility=View.GONE
                Toast.makeText(applicationContext,getString(R.string.unsucsessful_login),Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(FirebaseAuth.getInstance().currentUser!=null){
            val intent=Intent(applicationContext,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            finish()
            startActivity(intent)
        }
    }

}