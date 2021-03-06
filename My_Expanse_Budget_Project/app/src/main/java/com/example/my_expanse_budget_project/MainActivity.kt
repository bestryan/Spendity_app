package com.example.my_expanse_budget_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_edit.*
import java.lang.Exception
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() , View.OnClickListener {
    // create DBHandler object
    var dbh:DBHandler = DBHandler(this, null)
    // create arrays for expense object and ids
    var expenseList:MutableList<String> = arrayListOf()
    var idList:MutableList<Int> = arrayListOf()
    // create menu items val
    var MENUADD = Menu.FIRST+1
    var MENUEDIT = Menu.FIRST+2
    var MENUREMOVE = Menu.FIRST+3
    var MENUSORTBYDATE = Menu.FIRST+4
    var MENUSORTBYCOST = Menu.FIRST+5
    var MENURESET = Menu.FIRST+6
    
    val space_date = 22
    val space_descrp = 22
    
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // register context menu for list view
        registerForContextMenu(lstView)
        // put initial data in list view
        initAdapter()

        btnCheckBudget.setOnClickListener(this)



    }

    private fun initAdapter() {
        try {
            // clear all values
            expenseList.clear()
            idList.clear()
            // get all expense from DBHandler and go through loop
            for(con:Expense in dbh.getAllExpense()){


                val lengthDate = con.date?.length
                val spaceToAdd = space_date - lengthDate!!
                var date = con.date
                for (i in 0..spaceToAdd){
                    date += " "
                }

                val lengthDescription = con.description?.length
                val spaceAdd = space_descrp - lengthDescription!!
                var description = con.description!!.substring(0,1).uppercase() +
                        con.description!!.substring(1).lowercase()
                for (i in 0..spaceAdd){
                    description += "\t"
                }
                
                // read and add to expenseList
                expenseList.add(date + description +"$"+ con.cost!!.round(2))
                idList.add(con.id)
                // create array adapter
                val adp = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,expenseList)
                // assign adapter to list view
                lstView.adapter = adp
            }
        }catch (e:Exception){
            Toast.makeText(this,"Problem ${e.message.toString()}",Toast.LENGTH_LONG).show()

        }
    }

    private fun orderByCostAdapter() {
        try {
            // clear all values
            expenseList.clear()
            idList.clear()
            // get all expense from DBHandler and go through loop
            for(con:Expense in dbh.getAllExpenseOrderbyCost()){

                val lengthDate = con.date?.length
                val spaceToAdd = space_date - lengthDate!!
                var date = con.date
                for (i in 0..spaceToAdd){
                    date += " "
                }

                val lengthDescription = con.description?.length
                val spaceAdd = space_descrp - lengthDescription!!
                var description = con.description!!.substring(0,1).uppercase() +
                        con.description!!.substring(1).lowercase()
                for (i in 0..spaceAdd){
                    description += "\t"
                }

                // String cap = nam.substring(0, 1).toUpperCase() + nam.substring(1).toLowerCase();
                // read and add to expenseList
                expenseList.add(date + description +"$"+ con.cost!!.round(2))
                idList.add(con.id)
                // create array adapter
                val adp = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,expenseList)
                // assign adapter to list view
                lstView.adapter = adp
            }
        }catch (e:Exception){
            Toast.makeText(this,"Problem ${e.message.toString()}",Toast.LENGTH_LONG).show()

        }
    }

    private fun orderByDateAdapter() {
        try {
            // clear all values
            expenseList.clear()
            idList.clear()
            // get all expense from DBHandler and go through loop
            for(con:Expense in dbh.getAllExpenseOrderbyDate().reversed()){

                val lengthDate = con.date?.length
                val spaceToAdd = space_date - lengthDate!!
                var date = con.date
                for (i in 0..spaceToAdd){
                    date += " "
                }

                val lengthDescription = con.description?.length
                val spaceAdd = space_descrp - lengthDescription!!
                var description = con.description!!.substring(0,1).uppercase() +
                        con.description!!.substring(1).lowercase()
                for (i in 0..spaceAdd){
                    description += "\t"
                }

                // String cap = nam.substring(0, 1).toUpperCase() + nam.substring(1).toLowerCase();
                // read and add to expenseList
                expenseList.add(date + description +"$"+ con.cost!!.round(2))
                idList.add(con.id)
                // create array adapter
                val adp = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,expenseList)
                // assign adapter to list view
                lstView.adapter = adp
            }
        }catch (e:Exception){
            Toast.makeText(this,"Problem ${e.message.toString()}",Toast.LENGTH_LONG).show()

        }
    }

    fun Double.round(decimals: Int = 2): Double {
        return "%.${decimals}f".format(this).toDouble()
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(Menu.NONE,MENUADD,Menu.NONE,"ADD")
        menu.add(Menu.NONE,MENUSORTBYDATE,Menu.NONE,"SORT BY DATE")
        menu.add(Menu.NONE,MENUSORTBYCOST,Menu.NONE,"SORT BY COST")
        menu.add(Menu.NONE,MENURESET,Menu.NONE,"RESET")

        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu ,
        v: View? ,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.add(Menu.NONE,MENUEDIT,Menu.NONE,"EDIT")
        menu.add(Menu.NONE,MENUREMOVE,Menu.NONE,"REMOVE")
        super.onCreateContextMenu(menu , v , menuInfo)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            MENUADD -> {
                // create reference to activity
                val addedit = Intent(this,AddEdit::class.java)
                // to start another activity
                startActivity(addedit)
            }
            MENUSORTBYDATE -> {
                orderByDateAdapter()
            }
            MENUSORTBYCOST -> {
                orderByCostAdapter()
            }
            MENURESET -> {
                initAdapter()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onClick(btn: View) {
        when(btn.id){
            R.id.btnCheckBudget->{
                val viewbudget = Intent(this,ViewBudget::class.java)
                startActivity(viewbudget)
            }
        }
/*        val button = findViewById<Button>(R.id.btnCheckBudget)
            button.setOnClickListener{
                val viewbudget = Intent(this,ViewBudget::class.java)
                startActivity(viewbudget)
            }*/
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // adapter context menu info object
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        // code for edit and delete
        when(item.itemId){
            MENUEDIT->{
                // create intent and start activity with data pass on
                val addEdit = Intent(this,AddEdit::class.java)
                addEdit.putExtra("ID",idList[info.position])
                startActivity(addEdit)
            }
            MENUREMOVE->{
                // call delete function dbHandler
                dbh.deleteExpense(idList[info.position])
                // refresh list view after delete
                initAdapter()
            }
        }
        return super.onContextItemSelected(item)
    }


}