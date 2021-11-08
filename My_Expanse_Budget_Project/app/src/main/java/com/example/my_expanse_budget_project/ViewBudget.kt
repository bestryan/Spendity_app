package com.example.my_expanse_budget_project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.lstView
import kotlinx.android.synthetic.main.add_edit.*
import kotlinx.android.synthetic.main.set_budget.*
import kotlinx.android.synthetic.main.view_budget.*
import java.lang.Exception

public open class ViewBudget: Activity() , View.OnClickListener {

    val dbh = DBHandler(this,null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_budget)

        btnBudgetSet.setOnClickListener(this)
        btnBack.setOnClickListener(this)

        ("$ " +dbh.getSumExpense()).also { etTotalSpend.text = it }

        ("$ " +dbh.getSumBudget()).also { etBudget.text = it }

        val balance = (dbh.getSumBudget().toString().toDouble() - dbh.getSumExpense().toString().toDouble())

        ("$ "+String.format("%.2f", balance)).also { etBalance.text = it }

    }



/*    private fun getSumExpense(): CharSequence? {
        val sum = dbh.addExpense()
    }*/


    override fun onClick(btn: View) {
        when(btn.id){
            R.id.btnBudgetSet->{
                // create reference to activity
                val viewbudget = Intent(this,SetBudget::class.java)
                // to start another activity
                startActivity(viewbudget)
            }
            R.id.btnBack->{
                // cancel code
                goBack()
            }
        }
    }




    private fun goBack() {
        val mainact = Intent(this,MainActivity::class.java)
        // to start another activity
        startActivity(mainact)
    }

}
