package com.example.diplom.profile

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.loggin.ChooseActivity
import com.example.diplom.loggin.LoginActivity
import com.example.diplom.models.*
import com.example.diplom.models.game.Game
import com.google.android.material.imageview.ShapeableImageView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

fun TextView.makeGone(){
    this.visibility=View.GONE
}
fun FragmentActivity.showDialog(
    title: String,
    description: String,
    positiveButtonText:String,
    negativeButtonText:String,
    positiveButtonFunction: (() -> Unit)? = null,
    negativeButtonFunction: (() -> Unit)? = null
) {
    val builder: AlertDialog.Builder? = this?.let {
        AlertDialog.Builder(it)
    }

    builder!!.setMessage(description).setTitle(title)

    builder.apply {
        setPositiveButton(positiveButtonText) { dialog, id ->
            positiveButtonFunction?.invoke()
        }
        setNegativeButton(negativeButtonText) { dialog, id ->
            dialog.dismiss()
        }
    }
    val dialog: AlertDialog? = builder.create()

    dialog!!.show()
}
class ProfileFragment : Fragment() {
    lateinit var user:User
    lateinit var clubTeam:Team
    lateinit var nationalTeam:Team
    private var param1: String? = null
    lateinit var logOut:TextView
    lateinit var greeting:TextView
    lateinit var clubTeamLogo: ShapeableImageView
    lateinit var clubTeamName:TextView
    lateinit var clubformOne:TextView
    lateinit var clubformTwo:TextView
    lateinit var clubformThree:TextView
    lateinit var clubformFour:TextView
    lateinit var clubformFive:TextView
    lateinit var nationfromOne:TextView
    lateinit var nationfromTwo:TextView
    lateinit var nationfromThree:TextView
    lateinit var nationfromFour:TextView
    lateinit var nationfromFive:TextView
    lateinit var nationalTeamName:TextView
    lateinit var nationTeamLogo:ShapeableImageView
    lateinit var playerRecycleView:RecyclerView
    lateinit var playerClubAdapter:PlayerAdapter
    lateinit var playerNationalAdapter:PlayerAdapter
    lateinit var playersSwitch:SwitchCompat
    lateinit var gamesSwitch:SwitchCompat
    lateinit var recycleViewGame:RecyclerView
    lateinit var gamesClubAdapter: GameAdapter
    lateinit var gameNationalAdapter: GameAdapter
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser:FirebaseUser
    lateinit var firebaseDb:DatabaseReference
    lateinit var clubCardView:CardView
    lateinit var nationalCardView:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        user= Gson().fromJson(param1,User::class.java)
        nationalTeam=user.nationalTeam!!
        clubTeam=user.clubTeam!!
        val view= inflater.inflate(R.layout.fragment_profile, container, false)
        init(view)
        setGreeting()
       setClubTeam()
        setNationalTeam()
        setPlayers(false)
       setGames(false)



        logOut.setOnClickListener{
            logout()
        }
        playersSwitch.setOnCheckedChangeListener{i1,isChecked->switchPlayers(i1,isChecked)}
        gamesSwitch.setOnCheckedChangeListener{i1,isChecked->switchGames(i1,isChecked)}
        clubCardView.setOnClickListener{v->changeClubTeam(v)}
        return view
    }
    fun changeClubTeam(view:View){
        requireActivity().showDialog(
            title = requireContext().getString(R.string.change),
            description = requireContext().getString(R.string.change_club_team),
            positiveButtonText=requireContext().getString(R.string.yes),
            negativeButtonText=requireContext().getString(R.string.no),
            positiveButtonFunction = { changeClubTeam() },
            negativeButtonFunction = { changeNationalTeam() }
        )

    }
    fun changeClubTeam(){
        val intent= Intent(activity, ChooseActivity::class.java)
        intent.putExtra("value",1)
        startActivityForResult(intent,1 )
    }
    fun changeNationalTeam(){
        val intent= Intent(activity, ChooseActivity::class.java)
        intent.putExtra("value",2)
        startActivityForResult(intent,2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data==null){return}
        val gson= Gson().fromJson<Team>(data.getStringExtra("team"),Team::class.java)
        if(requestCode==1) {
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("clubTeam").setValue(gson).addOnCompleteListener {
                if(it.isSuccessful){
                    firebaseAuth= FirebaseAuth.getInstance()
                    firebaseUser=firebaseAuth.currentUser!!
                    val firebaseReference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
                    firebaseReference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            user = snapshot.getValue(User::class.java)!!
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                requireFragmentManager().beginTransaction().detach(this@ProfileFragment).commitNow();
                                requireFragmentManager().beginTransaction().apply {
                                    replace(R.id.flFragment,ProfileFragment.newInstance(Gson().toJson(user)))
                                    commit()
                                }
                            } else {
                                requireFragmentManager().beginTransaction().detach(this@ProfileFragment).attach(ProfileFragment.newInstance(Gson().toJson(user))).commit();
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                }
            }

        }else{
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("nationalTeam").setValue(gson).addOnCompleteListener {
                if(it.isSuccessful){
                    restart()

                }
            }
        }
    }
    fun restart(){
        firebaseAuth= FirebaseAuth.getInstance()
        firebaseUser=firebaseAuth.currentUser!!
        val firebaseReference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
        firebaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    requireFragmentManager().beginTransaction().detach(this@ProfileFragment).commitNow();
                    requireFragmentManager().beginTransaction().apply {
                        replace(R.id.flFragment,ProfileFragment.newInstance(Gson().toJson(user)))
                        commit()
                    }
                } else {
                    requireFragmentManager().beginTransaction().detach(this@ProfileFragment).attach(ProfileFragment.newInstance(Gson().toJson(user))).commit();
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun switchPlayers(buttonView:CompoundButton,isChecked:Boolean){
        if(isChecked){
            if(this::playerNationalAdapter.isInitialized){
                playerRecycleView.adapter=playerNationalAdapter
            }else{
                setPlayers(true)
            }
        }else{
            if(this::playerClubAdapter.isInitialized){
                playerRecycleView.adapter=playerClubAdapter
            }else{
                setPlayers(false)
            }
        }
    }
    fun switchGames(buttonView: CompoundButton,isChecked: Boolean){
        if(isChecked){
            if(this::gameNationalAdapter.isInitialized){
                recycleViewGame.adapter=gameNationalAdapter
            }else{
                setGames(true)
            }
        }else{
            if(this::gamesClubAdapter.isInitialized){
                recycleViewGame.adapter=gamesClubAdapter
            }else{
                setGames(false)
            }
        }
    }
    fun init(view:View) {
        greeting = view.findViewById<TextView>(R.id.welcome_name)
        logOut = view.findViewById<TextView>(R.id.log_out)
        clubTeamName = view.findViewById(R.id.club_team_name)
        clubTeamLogo = view.findViewById(R.id.club_logo)
        nationTeamLogo = view.findViewById(R.id.national_logo)
        nationalTeamName = view.findViewById(R.id.national_team_name)
        clubformOne = view.findViewById(R.id.clubform1)
        clubformTwo = view.findViewById(R.id.clubform2)
        clubformThree = view.findViewById(R.id.clubform3)
        clubformFour = view.findViewById(R.id.clubform4)
        clubformFive = view.findViewById(R.id.clubform5)
        nationfromOne = view.findViewById(R.id.nationalForm)
        nationfromTwo = view.findViewById(R.id.nationalForm2)
        nationfromThree = view.findViewById(R.id.nationalForm3)
        nationfromFour = view.findViewById(R.id.nationalForm4)
        nationfromFive = view.findViewById(R.id.nationalForm5)
        playerRecycleView = view.findViewById(R.id.players)
        playerRecycleView.layoutManager = LinearLayoutManager(context)
        recycleViewGame=view.findViewById(R.id.games_playes)
        recycleViewGame.layoutManager=LinearLayoutManager(context)
        playersSwitch=view.findViewById(R.id.switch1)
        gamesSwitch=view.findViewById(R.id.switch2)
        clubCardView=view.findViewById(R.id.club_team)
        nationalCardView=view.findViewById(R.id.national_team)

    }
    fun logout(){
        FirebaseAuth.getInstance().signOut()
        Thread.sleep(1000)
        val intent= Intent(context,LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        requireActivity().finish()

    }
    private fun setGreeting(){
        greeting.setText("${getString(R.string.welcome)} ${user.name}")
    }
    private fun setClubTeam(){
        clubTeamName.text=clubTeam.name
        context?.let {
            Glide.with(it)
                .load(clubTeam.logo)
                .fitCenter()
                .into(clubTeamLogo)
        }
        val textViewList= mutableListOf<TextView>(clubformOne,clubformTwo,clubformThree,clubformFour,clubformFive)
        initializeForm(textViewList,clubTeam,false)
    }
    private fun setNationalTeam(){
        nationalTeamName.text=nationalTeam.name
        context?.let {
            Glide.with(it)
                .load(nationalTeam.logo)
                .fitCenter()
                .into(nationTeamLogo)
        }
        val textViewList= mutableListOf<TextView>(nationfromOne,nationfromTwo,nationfromThree,nationfromFour,nationfromFive)
        initializeForm(textViewList,nationalTeam,true)
    }
    private fun initializeForm(textViewList: MutableList<TextView>,team:Team,isNational:Boolean){
        GlobalScope.launch(Dispatchers.IO){
            val resultList=when(isNational){
                true-> getTeamForm(team.id.toString(),team.leagueId!!,"2020")
                false-> getTeamForm(team.id.toString(),team.leagueId!!,"2021")
            }

            launch(Dispatchers.Main){
                var textViewListIndex=0
                var currentIndex=resultList.size-5
                for(i in currentIndex until resultList.size){
                    textViewList[textViewListIndex].text= resultList[i].toString()
                    textViewList[textViewListIndex].setTextColor(Color.BLACK)
                    if(resultList[i].equals('L')){
                        textViewList[textViewListIndex].setBackgroundColor(Color.parseColor("#FF3333"))
                    }else if(resultList[i].equals('D')){
                        textViewList[textViewListIndex].setBackgroundColor(Color.parseColor("#FFFFCC"))
                    }else if(resultList[i].equals('W')){
                        textViewList[textViewListIndex].setBackgroundColor(Color.parseColor("#00FF80"))

                    }
                    textViewListIndex++
                }
            }
        }
    }
    private fun setPlayers(isNational: Boolean){
        GlobalScope.launch(Dispatchers.IO){
            var resultList= mutableListOf<Player>()
            when(isNational) {
                true -> nationalTeam.id?.let { getPlayerList(it) }?.let { resultList.addAll(it) }
                false -> clubTeam.id?.let { getPlayerList(it) }?.let { resultList.addAll(it) }
            }

            launch(Dispatchers.Main) {
                when(isNational){
                    true->{playerNationalAdapter= context?.let { PlayerAdapter(it,resultList) }!!
                            playerRecycleView.adapter=playerNationalAdapter
                    }else->{
                        playerClubAdapter= context?.let { PlayerAdapter(it,resultList) }!!
                        playerRecycleView.adapter=playerClubAdapter
                    }
                }
            }

        }

    }
    private fun setGames(isNational: Boolean){
        GlobalScope.launch(Dispatchers.IO){
        var resultList= mutableListOf<Game>()
        val formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd")
        when(isNational) {
            true -> nationalTeam.id?.let {
                getGamesByTeam(
                    it,LocalDate.now().minusDays(1).minusYears(1).format(formatter),
                    LocalDate.now().minusDays(1).format(formatter),"2020")
            }?.let { resultList.addAll(it) }
            false -> clubTeam.id?.let {
                getGamesByTeam(it,LocalDate.now().minusDays(1).minusMonths(2).format(formatter),LocalDate.now().minusDays(1).format(
                formatter),"2021") }?.let { resultList.addAll(it) }
        }
            launch (Dispatchers.Main ){
                when(isNational){
                    true->{ gameNationalAdapter= context?.let { GameAdapter(it,resultList) }!!
                        recycleViewGame.adapter=gameNationalAdapter
                    }
                    false->{
                        gamesClubAdapter= context?.let { GameAdapter(it,resultList) }!!
                        recycleViewGame.adapter=gamesClubAdapter

                    }
                }
            }
        }

    }
    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}