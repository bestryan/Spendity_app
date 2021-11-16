package com.example.my_expanse_budget_project

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import java.util.prefs.PreferencesFactory
import kotlin.collections.ArrayList
import kotlin.math.exp

class DBHandler(context: Context,factory: SQLiteDatabase.CursorFactory?)
    :SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    private val ExpenseTable: String = "Expense"
    private val KeyID: String = "ID"
    private val KeyDate: String = "DATE"
    private val KeyDescription: String = "DESCRIPTION"
    private val KeyCost: String = "COST"

    private val BudgetTable: String = "Budget"
    private val BudgetKeyID: String = "ID"
    private val KeyBudget: String = "BUDGET"

    companion object{
        const val DATABASE_NAME = "ExpenseMaster.db"
        const val DATABASE_VERSION = 6
    }


    override fun onCreate(db: SQLiteDatabase) {

        // create sql statement for table
        val createTable1:String = "CREATE TABLE $ExpenseTable " +
                "($KeyID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$KeyDate TEXT, $KeyDescription TEXT, $KeyCost REAL)"

        val createTable2:String = "CREATE TABLE $BudgetTable " +
                "($BudgetKeyID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$KeyBudget REAL)"

        // execute sql
        db.execSQL(createTable1)
        db.execSQL(createTable2)

        val ev = ContentValues()
        ev.put(KeyDate,"Nov 1, 2021")
        ev.put(KeyDescription,"Ryan")
        ev.put(KeyCost,"0.00")
        db.insert(ExpenseTable,null,ev)


        val bv = ContentValues()
        bv.put(KeyBudget,"0.00")
        db.insert(BudgetTable,null,bv)

    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // drop existing table
        db.execSQL("DROP TABLE IF EXISTS $ExpenseTable")
        db.execSQL("DROP TABLE IF EXISTS $BudgetTable")

        // recreate database
        onCreate(db)
    }

    fun getAllExpense():ArrayList<Expense>{
        // declare a arraylist to fill all records in expense object
        val  expenseList = ArrayList<Expense>()
        // create a sql query
        val selectQuery:String = "SELECT * FROM $ExpenseTable"
        // get database for readable
        val db = this.readableDatabase
        // run query and put result in cursor
        val cursor = db.rawQuery(selectQuery,null)
        // move through cursor and read all the records
        if(cursor.moveToFirst()){
            // loop to read all possible records
            do {
                // create a expense object
                val expense = Expense()
                // read values from cursor and fill expense object
                expense.id = cursor.getInt(0)
                expense.date = cursor.getString(1)
                expense.description = cursor.getString(2)
                expense.cost = cursor.getDouble(3)
                // add expense object to array list
                expenseList.add(expense)
            }while (cursor.moveToNext())
        }

        // String cap = nam.substring(0, 1).toUpperCase() + nam.substring(1).toLowerCase();

        // return arraylist of expense object
        // close cursor and database
        cursor.close()
        db.close()
        return expenseList
    }

    fun getAllExpenseOrderbyCost():ArrayList<Expense>{
        // declare a arraylist to fill all records in expense object
        val  expenseList = ArrayList<Expense>()
        // create a sql query
        val selectQuery:String = "SELECT * FROM $ExpenseTable ORDER BY $KeyCost"
        // get database for readable
        val db = this.readableDatabase
        // run query and put result in cursor
        val cursor = db.rawQuery(selectQuery,null)
        // move through cursor and read all the records
        if(cursor.moveToFirst()){
            // loop to read all possible records
            do {
                // create a expense object
                val expense = Expense()
                // read values from cursor and fill expense object
                expense.id = cursor.getInt(0)
                expense.date = cursor.getString(1)
                expense.description = cursor.getString(2)
                expense.cost = cursor.getDouble(3)
                // add expense object to array list
                expenseList.add(expense)
            }while (cursor.moveToNext())
        }
        // return arraylist of expense object
        // close cursor and database
        cursor.close()
        db.close()
        return expenseList
    }

    fun getAllExpenseOrderbyDate():ArrayList<Expense>{
        // declare a arraylist to fill all records in expense object
        val  expenseList = ArrayList<Expense>()
        // create a sql query
        val selectQuery:String = "SELECT * FROM $ExpenseTable ORDER BY $KeyDate"
        // get database for readable
        val db = this.readableDatabase
        // run query and put result in cursor
        val cursor = db.rawQuery(selectQuery,null)
        // move through cursor and read all the records
        if(cursor.moveToFirst()){
            // loop to read all possible records
            do {
                // create a expense object
                val expense = Expense()
                // read values from cursor and fill expense object
                expense.id = cursor.getInt(0)
                expense.date = cursor.getString(1)
                expense.description = cursor.getString(2)
                expense.cost = cursor.getDouble(3)
                // add expense object to array list
                expenseList.add(expense)
            }while (cursor.moveToNext())
        }
        // return arraylist of expense object
        // close cursor and database
        cursor.close()
        db.close()
        return expenseList
    }

    fun getBudget():ArrayList<Budget>{
        val budgetList = ArrayList<Budget>()
        val selectQuery:String = "SELECT * FROM $BudgetTable"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            do {
                val budget = Budget()
                budget.id = cursor.getInt(0)
                budget.budget = cursor.getDouble(1)
                budgetList.add(budget)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return budgetList
    }

    fun addExpense(expense: Expense) {
        // get writable db
        val db = this.writableDatabase
        // create content value object
        val cv = ContentValues()
        cv.put(KeyDate,expense.date)
        cv.put(KeyDescription,expense.description)
        cv.put(KeyCost,expense.cost)
        // use insert method
        db.insert(ExpenseTable,null,cv)
        // close db
        db.close()
    }

    fun addBudget(budget: Budget) {
        // get writable db
        val db = this.writableDatabase
        // create content value object
        val cv = ContentValues()
        cv.put(KeyBudget,budget.budget)
        // use insert method
        db.insert(BudgetTable,null,cv)
        // close db
        db.close()
    }


    fun updateBudget(budget: Budget){
        // get writable db
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KeyBudget,budget.budget)
        val id:Int = db.update(BudgetTable,cv,"$KeyID=?",arrayOf(budget.id.toString()))
        db.close()
    }


    fun deleteExpense(id: Int) {
        // get writable db
        val db = this.writableDatabase
        db.delete(ExpenseTable,"$KeyID=?", arrayOf(id.toString()))
        // close db
        db.close()
    }

    fun deleteBudget(id: Int) {
        // get writable db
        val db = this.writableDatabase
        db.delete(BudgetTable,"$KeyID=?", arrayOf(id.toString()))
        // close db
        db.close()
    }

    fun getExpense(id: Int): Expense {
        // get readable db
        val db = this.readableDatabase
        // create expense object to fill data
        val expense = Expense()
        // create cursor based on query
        val cursor = db.query(ExpenseTable, arrayOf(KeyID,KeyDate,KeyDescription,KeyCost),"$KeyID=?",
            arrayOf(id.toString()),null,null,null)
        // check cursor and read value and put in contact
        if (cursor!=null){
            cursor.moveToFirst()
            expense.id = cursor.getInt(0)
            expense.date = cursor.getString(1)
            expense.description = cursor.getString(2)
            expense.cost = cursor.getDouble(3)
        }
        // close cursor and db
        cursor.close()
        db.close()
        return expense
    }

    fun getBudget(id: Int): Budget {
        // get readable db
        val db = this.readableDatabase
        // create expense object to fill data
        val budget = Budget()
        // create cursor based on query
        val cursor = db.query(BudgetTable, arrayOf(BudgetKeyID,KeyBudget),"$KeyID=?",
            arrayOf(id.toString()),null,null,null)
        // check cursor and read value and put in contact
        if (cursor!=null){
            cursor.moveToFirst()
            budget.id = cursor.getInt(0)
            budget.budget = cursor.getDouble(1)
        }
        // close cursor and db
        cursor.close()
        db.close()
        return budget
    }


    fun updateExpense(expense: Expense) {
        // get writable db
        val db = this.writableDatabase
        // create content value and put values from contact object
        val cv = ContentValues()
        cv.put(KeyDate,expense.date)
        cv.put(KeyDescription,expense.description)
        cv.put(KeyCost,expense.cost)

        val id:Int = db.update(ExpenseTable,cv,"$KeyID=?", arrayOf(expense.id.toString()))
        // close db
        db.close()
    }

    fun getSumExpense():String{
        var total = "0"
        val db = this.readableDatabase
        val selectQuery:String = "SELECT SUM(COST) AS TOTAL FROM $ExpenseTable;"
        val cursor = db.rawQuery(selectQuery,null)
        if (cursor.moveToFirst()) {
            total = cursor.getString(0)
        }
        cursor.close()
        db.close()
        return total
    }

    fun getSumBudget():String{
        var total = "0"
        val db = this.readableDatabase
        val selectQuery:String = "SELECT SUM(BUDGET) AS TOTAL FROM $BudgetTable;"
        val cursor = db.rawQuery(selectQuery,null)
        if (cursor.moveToFirst()) {
            total = cursor.getString(0)
        }
        cursor.close()
        db.close()
        return total
    }

}