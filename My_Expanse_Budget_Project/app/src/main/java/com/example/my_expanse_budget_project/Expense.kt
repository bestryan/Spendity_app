package com.example.my_expanse_budget_project

class Expense {

    // declare and initialise variables
    var id:Int = 0
    var date:String? = null
    var description:String? = null
    var cost:Double? = null

    // empty constructor
    constructor(){}

    // constructor with all the parameters
    constructor(id:Int,date:String,description:String,cost:Double){
        this.id = id
        this.date = date
        this.description = description
        this.cost = cost
    }

    // constructor with name only
    constructor(description:String){
        this.description = description
    }


}