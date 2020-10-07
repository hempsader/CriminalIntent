package com.example.criminalintent.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime
import java.text.SimpleDateFormat
import java.util.*

class CrimeViewController(val dataSet: List<Crime>) : RecyclerView.Adapter<CrimeViewController.GenericHolder>() {

     abstract class GenericHolder(itemView: View):RecyclerView.ViewHolder(itemView){
         val crimeTitle: TextView = itemView.findViewById(R.id.crime_title)
         val crimeDate: TextView = itemView.findViewById(R.id.crime_date)
     }


    //main viewholder
    inner class ViewHolder(itemView: View): GenericHolder(itemView),View.OnClickListener{
        val imageSolved: ImageView = itemView.findViewById(R.id.crime_solved_image)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            Toast.makeText(p0?.context,crimeTitle.text.toString(),Toast.LENGTH_SHORT).show()
        }
     }

    inner class ViewHolderSpecial(itemView: View): GenericHolder(itemView), View.OnClickListener{
        val crimeButton: Button = itemView.findViewById(R.id.button_police)
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
    ): GenericHolder = when(viewType){
        0 -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime,parent,false))
        else ->  ViewHolderSpecial(LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime_special,parent,false))
    }

    //if we need two versions of viewholder
 //   override fun getItemViewType(position: Int): Int = if(dataSet[position].requiresPolice) 1 else 0



    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: GenericHolder, position: Int) {
        when(holder){
            is ViewHolderSpecial -> bindSpecial(holder,position)
            is ViewHolder -> bindNormal(holder,position)
        }
    }

    private fun bindNormal(holder: ViewHolder, position: Int){
        holder.apply {
            val dateFormated = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(dataSet[position].date)
            crimeTitle.text = dataSet[position].title
            crimeDate.text = dateFormated.toString()
            if(!dataSet[position].solved) imageSolved.visibility = View.INVISIBLE
        }
    }

    private fun bindSpecial(holder: ViewHolderSpecial, position: Int) {

        holder.apply {
            crimeTitle.text = dataSet[position].title

            crimeButton.setOnClickListener {
                Toast.makeText(it.context,"Calling police...", Toast.LENGTH_SHORT).show()
            }
        }
    }




}