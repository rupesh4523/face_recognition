package com.example.facerecognition.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.facerecognition.R
import com.example.facerecognition.models.VerificationLog

class VerificationLogAdapter(
    private val logList: List<VerificationLog>
) : RecyclerView.Adapter<VerificationLogAdapter.LogViewHolder>() {

    class LogViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val txtCandidateName =
            itemView.findViewById<TextView>(
                R.id.txtCandidateName
            )

        val txtApplicationNo =
            itemView.findViewById<TextView>(
                R.id.txtApplicationNo
            )

        val txtResult =
            itemView.findViewById<TextView>(
                R.id.txtResult
            )

        val txtConfidence =
            itemView.findViewById<TextView>(
                R.id.txtConfidence
            )

        val txtDateTime =
            itemView.findViewById<TextView>(
                R.id.txtDateTime
            )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LogViewHolder {

        val view =
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_verification_log,
                parent,
                false
            )

        return LogViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: LogViewHolder,
        position: Int
    ) {

        val log =
            logList[position]

        holder.txtCandidateName.text =
            log.candidateName

        holder.txtApplicationNo.text =
            "Application No : ${log.applicationNo}"

        holder.txtResult.text =
            "Result : ${log.result}"

        holder.txtConfidence.text =
            "Confidence : %.2f%%"
                .format(log.confidence)

        holder.txtDateTime.text =
            log.dateTime
    }

    override fun getItemCount(): Int {
        return logList.size
    }
}