package com.example.criminalintent.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime

class CrimeViewController(val dataSet: List<Crime>) : RecyclerView.Adapter<CrimeViewController.ViewHolder>() {
    private var holderType = 0
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val crimeTitle: TextView = itemView.findViewById<TextView>(R.id.crime_title)
        val crimeDate: TextView = itemView.findViewById<TextView>(R.id.crime_date)
         init {
             itemView.setOnClickListener(this)
         }
         override fun onClick(p0: View?) {
             Toast.makeText(p0?.context,crimeTitle.text.toString(),Toast.LENGTH_SHORT).show()
         }
     }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = if(holderType == 0)
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime,parent,false)) else
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime_special,parent,false))



    override fun getItemViewType(position: Int): Int {
        holderType = if(dataSet[position].requiresPolice == 0) 0 else 1
        return holderType
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(holder,position)

    }
    override fun getItemCount(): Int = dataSet.size


    private fun bind(holder: ViewHolder, position: Int){
        holder.apply {
            crimeTitle.text = dataSet[position].title
            crimeDate.text = dataSet[position].date.toString()
        }
    }



}