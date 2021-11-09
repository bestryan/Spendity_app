package com.example.my_expanse_budget_project

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.add_edit.*
import java.text.DateFormat
import java.time.Month
import java.time.Year
import java.util.*
import kotlin.math.exp

public class AddEdit: Activity() , View.OnClickListener {


    // create DBHandler object
    val dbh = DBHandler(this,null)

    // declare and create calendar object
    var curDate:Calendar = Calendar.getInstance()
    // DateFormat object
    val dateFormat:DateFormat = DateFormat.getDateInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_edit)
        // set on click listener
        btnSave.setOnClickListener(this)
        btnCancel.setOnClickListener(this)

        etDate.setOnClickListener(this)
        updateDate()

        val extras = intent.extras
        if (extras!=null) {
            // read and assign id from intent
            val id: Int = extras.getInt("ID")
            // get expense based on id
            val expense = dbh.getExpense(id)
            // edit text populate
            etID.setText(expense.id.toString())
            etDate.setText(expense.date)
            etDescription.setText(expense.description)
            etCost.setText(expense.cost.toString())
        }
    }

    override fun onClick(btn: View) {
        // code to run for buttons
        when(btn.id){
            R.id.btnSave->{
                // save code
                // read value from ID and put 0 if no value
                val eid:Int = try {
                    etID.text.toString().toInt()
                }catch (e:Exception){
                    0
                }
                // decide add or update
                if (eid == 0 ){
                    addExpense()
                }else{
                    editExpense(eid)
                }
            }

            R.id.btnCancel->{
                // cancel code
                goBack()
            }

            R.id.etDate->{
                DatePickerDialog(this,this.d, curDate.get(Calendar.YEAR),
                curDate.get(Calendar.MONDAY),curDate.get(Calendar.DAY_OF_MONTH)).show()
            }
        }
    }

    private val d: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener{ _: DatePicker, year,month,day->
            // set new data as per selection
            curDate.set(Calendar.YEAR,year)
            curDate.set(Calendar.MONTH,month)
            curDate.set(Calendar.DAY_OF_MONTH,day)
            updateDate()
        }

    private fun updateDate() {
        etDate.text = dateFormat.format(curDate.time)
    }


    private fun editExpense(eid:Int) {
        // create contact object and fill new values
        val expense = dbh.getExpense(eid)
        expense.date = etDate.text.toString()
        expense.description = etDescription.text.toString()
        expense.cost = etCost.text.toString().toDouble()
        // call dbHandler update function
        dbh.updateExpense(expense)
        // display confirmation and go to Main Activity
        Toast.makeText(this,"Expense record has been updated",Toast.LENGTH_LONG).show()
        goBack()
    }

    private fun addExpense() {
        // read values from edit text and assign to contact object
        if (etDate.text.toString().isEmpty() || etDescription.text.toString().isEmpty() || etCost.text.toString().isEmpty()){
            /*this.etDate.text.toString()==null && etDescription.text.toString()==null && etCost.text.toString().toDouble()==null*/
            Toast.makeText(this,"Please check again, No empty Fill !!!",Toast.LENGTH_LONG).show()
        }else{
            val expense = Expense()
            expense.date = etDate.text.toString()
            expense.description = etDescription.text.toString()
            expense.cost = etCost.text.toString().toDouble()
            // call dbHandler function to add
            dbh.addExpense(expense)
            Toast.makeText(this,"New Expense Created",Toast.LENGTH_LONG).show()
            goBack()
        }
    }

    private fun goBack() {
        val mainact = Intent(this,MainActivity::class.java)
        // to start another activity
        startActivity(mainact)
    }


}
