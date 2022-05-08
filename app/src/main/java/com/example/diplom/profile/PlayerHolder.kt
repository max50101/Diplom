package com.example.diplom.profile

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.playerinfo.PlayerActivity
import com.example.diplom.R
import com.example.diplom.models.Player
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson

class PlayerHolder (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    lateinit var mPlayer: Player
    lateinit var mPhoto: ShapeableImageView
    lateinit var mName:TextView
    lateinit var mAge:TextView
    lateinit var mNumber:TextView
    lateinit var mPosition:TextView

    var mposition=0
    override fun onClick(p0: View?) {
        val intent= Intent(context, PlayerActivity::class.java)
        intent.putExtra("player", Gson().toJson(mPlayer))
        context.startActivity(intent)

    }
    init{
        itemView.setOnClickListener(this)
        mPhoto=itemView.findViewById<ShapeableImageView>(R.id.photo)
        mName=itemView.findViewById(R.id.name)
        mAge=itemView.findViewById(R.id.age)
        mPosition=itemView.findViewById(R.id.position)
        mNumber=itemView.findViewById(R.id.number)
    }
    fun bindTask(player: Player, position:Int){

        mposition=position
        mPlayer=player
        mName.setText(player.name!!.take(18))
        mAge.text=context.getString(R.string.age)+player.age
        when(player.position){
            "Goalkeeper"->mPosition.text=context.getString(R.string.position)+context.getString(R.string.goalkeeper)
            "Defender"->mPosition.text=context.getString(R.string.position)+context.getString(R.string.defender)
            "Midfielder"->mPosition.text=context.getString(R.string.position)+context.getString(R.string.midfielder)
            "Attacker"->mPosition.text=context.getString(R.string.position)+context.getString(R.string.attacker)
        }
        if(player.number!=null) {
            mNumber.setText("№ ${player.number}")
        }
        Glide.with(context)
            .load(player.photo)
            .fitCenter()
            .into(mPhoto)
    }
}
