package com.example.facerecognition.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.facerecognition.R
import com.example.facerecognition.models.Candidate
import java.io.File

class CandidateAdapter(
    private val candidateList: List<Candidate>,
    private val onItemClick: (Candidate) -> Unit
) : RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>() {

    class CandidateViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val imgCandidate: ImageView =
            itemView.findViewById(R.id.imgCandidate)

        val txtName: TextView =
            itemView.findViewById(R.id.txtName)

        val txtApplicationNo: TextView =
            itemView.findViewById(R.id.txtApplicationNo)

        val txtDepartment: TextView =
            itemView.findViewById(R.id.txtDepartment)

        val txtExamName: TextView =
            itemView.findViewById(R.id.txtExamName)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CandidateViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_candidate,
                parent,
                false
            )

        return CandidateViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CandidateViewHolder,
        position: Int
    ) {

        val candidate = candidateList[position]

        holder.txtName.text =
            candidate.name

        holder.txtApplicationNo.text =
            "Application No: ${candidate.applicationNo}"

        holder.txtDepartment.text =
            "Department: ${candidate.department}"

        holder.txtExamName.text =
            "Exam: ${candidate.examName}"
        holder.itemView.setOnClickListener {

            onItemClick(candidate)

        }
        if (candidate.imagePath.isNotEmpty()) {

            holder.imgCandidate.setImageURI(
                Uri.fromFile(
                    File(candidate.imagePath)
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return candidateList.size
    }
}