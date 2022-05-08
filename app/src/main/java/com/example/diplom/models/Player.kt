package com.example.diplom.models

data class Player(var id:Int?=null, var name:String?=null, var age:Int?=null, var number:Int?=null, var position:String?=null, var photo:String?=null, var birth: PlayerBirth?=null,
                  var firstName:String?=null, var lastName:String?=null, var nationality:String?=null, var injured:Boolean?=null, var height:String?=null, var weight:String?=null,)
