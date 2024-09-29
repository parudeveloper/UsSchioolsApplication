package com.usschioolsapplication.ui.schoollist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.usschioolsapplication.databinding.ItemListSchoolBinding
import com.usschioolsapplication.domine.HighSchoolListItem
import com.usschioolsapplication.ui.schooldetails.SchoolDetailsActivity

class SchoolListAdapter(private val highSchoolList: ArrayList<HighSchoolListItem>) :
    RecyclerView.Adapter<SchoolListAdapter.SchoolListViewHolder>() {
    private lateinit var binding: ItemListSchoolBinding

    class SchoolListViewHolder(private val itemListSchoolBinding: ItemListSchoolBinding) :
        RecyclerView.ViewHolder(itemListSchoolBinding.root) {
        fun bindData(highSchoolListItem: HighSchoolListItem, position: Int) {
            with(itemListSchoolBinding) {
                schoolName.text = highSchoolListItem.school_name
                location.text = buildString {
                    append("Location: ")
                    append(highSchoolListItem.primary_address_line_1)
                    append(", ")
                    append(highSchoolListItem.city)
                    append(", ")
                    append(highSchoolListItem.state_code)
                    append(", ")
                    append(highSchoolListItem.zip)
                }
                phoneNumber.text = highSchoolListItem.phone_number
                email.text = highSchoolListItem.school_email
                website.text = highSchoolListItem.website

                itemView.setOnClickListener(){
                    val intent = Intent(it.context,SchoolDetailsActivity::class.java)
                    intent.putExtra("highSchoolListData", highSchoolListItem);
                    it.context.startActivity(intent)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemListSchoolBinding.inflate(layoutInflater, parent, false)
        return SchoolListViewHolder(binding)
    }

    override fun getItemCount(): Int = highSchoolList.size


    override fun onBindViewHolder(holder: SchoolListViewHolder, position: Int) {
        holder.bindData(highSchoolList[position], position)
    }

}