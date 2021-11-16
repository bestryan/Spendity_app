package com.example.my_expanse_budget_project

class Budget {

    // declare and initialise variables
    var id:Int = 0
    var budget:Double? = null

    // empty constructor
    constructor(){}

    // constructor with all the parameters
    constructor(id:Int,budget:Double){
        this.id = id
        this.budget = budget
    }

    // constructor with name only
    constructor(budget:Double){
        this.budget = budget
    }



}