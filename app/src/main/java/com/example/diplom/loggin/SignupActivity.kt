package com.example.diplom.loggin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.Team
import com.example.diplom.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
class SignupActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    lateinit var teamLogo:ImageView
    lateinit var teamName:TextView
    lateinit var countryName:TextView
    lateinit var countryLogo:ImageView
    lateinit var name:EditText
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var createAcc: Button
    lateinit var progressBar:ProgressBar
    lateinit var clubTeam:Team
    lateinit var nationalTeam:Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val teamChooser=findViewById<CardView>(R.id.team_chooser)
        teamLogo=findViewById(R.id.teamLogo)
        teamName=findViewById(R.id.teamName)
        countryName=findViewById(R.id.countryName)
        countryLogo=findViewById(R.id.countryLogo)
        name=findViewById(R.id.name)
        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        createAcc=findViewById(R.id.create_account)
        progressBar=findViewById(R.id.progressBar)
        auth = Firebase.auth
        val countryChooser=findViewById<CardView>(R.id.country_chooser)
        teamChooser.setOnClickListener{
            val intent= Intent(this, ChooseActivity::class.java)
            intent.putExtra("value",1)
            startActivityForResult(intent,1)
        }
        countryChooser.setOnClickListener{
            val intent= Intent(this, ChooseActivity::class.java)
            intent.putExtra("value",2)
            startActivityForResult(intent,2)
        }
        createAcc.setOnClickListener{it->createFirebaseAccount(it)}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data==null){return}
        val gson= Gson().fromJson<Team>(data.getStringExtra("team"),Team::class.java)
        if(requestCode==1) {
            teamName.setText(gson.name)
            Glide.with(applicationContext)
                .load(gson.logo)
                .fitCenter()
                .into(teamLogo)
            clubTeam=gson
        }else{
            countryName.setText(gson.name)
            Glide.with(applicationContext)
                .load(gson.logo)
                .fitCenter()
                .into(countryLogo)
            nationalTeam=gson
        }
    }

    fun createFirebaseAccount(view:View){
            val mName=name.text.toString().trim()
            val mEmail=email.text.toString().trim()
            val mPass=password.text.toString().trim()
            if(mName.isEmpty()){
                name.setError(getString(R.string.not_inputed))
                name.requestFocus()
                return
            }
        if(mEmail.isEmpty()){
            email.setError(getString(R.string.not_inputed))
            email.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
            email.setError(getString(R.string.incorrect_email))
            email.requestFocus()
            return
        }
        if(mPass.isEmpty()){
            password.setError(getString(R.string.not_inputed))
            password.requestFocus()
            return
        }
        if(mPass.length<6){
            password.setError(getString(R.string.password_error_length))
            password.requestFocus()
            return
        }
        if(!this::nationalTeam.isInitialized){
            Toast.makeText(applicationContext,getString(R.string.not_choosed_national),Toast.LENGTH_SHORT).show()
            return
        }
        if(!this::clubTeam.isInitialized){
            Toast.makeText(applicationContext,getString(R.string.not_choosed_club),Toast.LENGTH_SHORT).show()
            return
        }
        progressBar.visibility=View.VISIBLE
        val user= User(name=mName,email=mEmail,clubTeam=clubTeam,nationalTeam=nationalTeam)
        auth.createUserWithEmailAndPassword(mEmail,mPass)
            .addOnCompleteListener(this){task->
                if(task.isSuccessful){

                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(auth.currentUser!!.uid).setValue(user).addOnCompleteListener(this){task->
                            if(task.isSuccessful){
                                Toast.makeText(applicationContext,getString(R.string.user_registred),Toast.LENGTH_SHORT).show()
                                progressBar.visibility=View.GONE
                                val intent=Intent()
                                intent.putExtra("user",Gson().toJson(user))
                                intent.putExtra("password",mPass.toString())
                                this.setResult(RESULT_OK,intent)
                                this.finish()

                            }else{
                                Toast.makeText(applicationContext,getString(R.string.unsucsessful),Toast.LENGTH_SHORT).show()
                            }

                        }

                }else{
                    Toast.makeText(applicationContext,getString(R.string.unsucsessful),Toast.LENGTH_SHORT).show()
                }

            }
    }


}