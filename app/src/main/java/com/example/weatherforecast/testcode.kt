package com.example.weatherforecast

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


data class User(
    val name:String?=null,
    val email:String?=null,
    val age: Int?=0,

)

class Repo{
    val use= User("mith","mith@gmail.com",0)

}

class Test{
val arr=arrayOf(10,20,20)
  val result= findSum(arr)

    private fun findsum(arr: Array<Int>) {

    }

    fun findSum(arr1: Array<Int>):Int{
        return 10
    }
}

fun myTest()
{
    runBlocking {
        val nameFlow = flow {
            emit("John")
            delay(1000)
            emit("Mike")
        }

        val ageFlow = flow {
            emit(20)
            delay(2000)
            emit(30)
        }
        nameFlow.combine(ageFlow){name,age->
            Pair(name,age)
        }.collect { (name,age)->
            println("$name : $age")
        }




    }

}
