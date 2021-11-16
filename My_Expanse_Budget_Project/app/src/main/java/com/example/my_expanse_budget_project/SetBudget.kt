package com.example.my_expanse_budget_project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.add_edit.*
import kotlinx.android.synthetic.main.set_budget.*

class SetBudget:ViewBudget() {

    val DBHandler = DBHandler(this,null)
    var budgetList:MutableList<String> = arrayListOf()
    var idLists:MutableList<Int> = arrayListOf()
    val MENUREMOVE = Menu.FIRST+1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.set_budget)

        btnSave1.setOnClickListener(this)
        btnCancel1.setOnClickListener(this)
        registerForContextMenu(blstView)
        initAdapter()

        val extras = intent.extras
        if (extras!=null) {
            // read and assign id from intent
            val id: Int = extras.getInt("ID")
            // get expense based on id
            val budget = dbh.getBudget(id)
            // edit text populate
            etbID.setText(budget.id)
            etInput.setText(budget.budget.toString())
        }

    }

    private fun initAdapter() {
        try {
            // clear all values
            budgetList.clear()
            idLists.clear()
            // get all expense from DBHandler and go through loop
            for(bud:Budget in dbh.getBudget()){
                // read and add to expenseList
                budgetList.add("$ "+bud.budget.toString())
                idLists.add(bud.id)
                // create array adapter
                val adp = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,budgetList)
                // assign adapter to list view
                blstView.adapter = adp
            }
        }catch (e: java.lang.Exception){
            Toast.makeText(this,"Problem ${e.message.toString()}",Toast.LENGTH_LONG).show()
        }
    }


    override fun onClick(btn: View){
        when(btn.id){
            R.id.btnSave1->{
                // save code
                // read value from ID and put 0 if no value
                val bid:Int = try {
                    etbID.text.toString().toInt()
                }catch (e:Exception){
                    0
                }
               // call addBudget function if add more than one budget record
//                addBudget()

             // overwrite budget using update function
                updateBudget(1)
            }
            R.id.btnCancel1->{
                goBack()
            }
        }
    }

    private fun updateBudget(bid:Int) {
        val budegt = dbh.getBudget(bid)

        budegt.budget = etInput.text.toString().toDouble()

        dbh.updateBudget(budegt)
        goBack()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu ,
        v: View? ,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.add(Menu.NONE,MENUREMOVE,Menu.NONE,"REMOVE")
        super.onCreateContextMenu(menu , v , menuInfo)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        // adapter context menu info object
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        // code for edit and delete
        when(item.itemId) {
            MENUREMOVE -> {
                // call delete function dbHandler
                dbh.deleteBudget(idLists[info.position])
                // refresh list view after delete
                initAdapter()
            }
        }
        return super.onContextItemSelected(item)
    }


    private fun addBudget() {
        // read values from edit text and assign to contact object
        if (etInput.text.toString().isEmpty()){
            Toast.makeText(this,"Please check again, No empty Fill !!!", Toast.LENGTH_LONG).show()
        }else{
            val budget = Budget()
            budget.budget = etInput.text.toString().toDouble()
            // call dbHandler function to add
            dbh.addBudget(budget)
            Toast.makeText(this,"New Budget Created", Toast.LENGTH_LONG).show()
            goBack()
        }
    }

    private fun goBack() {
        val mainact = Intent(this,ViewBudget::class.java)
        // to start another activity
        startActivity(mainact)
    }
}
