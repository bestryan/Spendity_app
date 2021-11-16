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
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.roundToLong

public open class ViewBudget: Activity() , View.OnClickListener {

    val dbh = DBHandler(this,null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_budget)

        btnBudgetSet.setOnClickListener(this)
        btnBack.setOnClickListener(this)

        ("$ " +dbh.getSumExpense().toDouble().round(2)).also { etTotalSpend.text = it }

        ("$ " +dbh.getSumBudget().toDouble().round(2)).also { etBudget.text = it }

        val balance = (dbh.getSumBudget().toString().toDouble() - dbh.getSumExpense().toString().toDouble())

        ("$ "+ balance.toDouble().round(2)).also { etBalance.text = it }

    }


    fun Double.round(decimals: Int = 2): Double {
        return "%.${decimals}f".format(this).toDouble()
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
