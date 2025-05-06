package com.uxdesign.ccp_frontend

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uxdesign.cpp.R

class EntregaAdapter(private val entregas: List<Entrega>) : RecyclerView.Adapter<EntregaAdapter.EntregaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntregaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_entrega, parent, false)
        return EntregaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EntregaViewHolder, position: Int) {
        val entrega = entregas[position]
        holder.bind(entrega)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetalleEntregaActivity::class.java).apply {
                putExtra("entrega_id", entrega.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return entregas.size
    }

    class EntregaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val entregaFecha: TextView = itemView.findViewById(R.id.entrega)
        private val entregaEstado: TextView = itemView.findViewById(R.id.hora)
        private val entregaOtro: TextView = itemView.findViewById(R.id.encargado)

        fun bind(entrega: Entrega) {
            entregaFecha.text = entrega.toString()
            entregaEstado.text = entrega.id
            entregaOtro.text = entrega.id
        }
    }
}