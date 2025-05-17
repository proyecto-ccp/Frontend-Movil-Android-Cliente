package com.uxdesign.ccp_frontend

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uxdesign.cpp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EntregaAdapter(private val pedidos: List<PedidoProcesado>) : RecyclerView.Adapter<EntregaAdapter.EntregaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntregaViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_estado_entrega, parent, false)
        return EntregaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EntregaViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.bind(pedido)


    }

    override fun getItemCount(): Int {
        return pedidos.size
    }

    class EntregaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val pedidoFecha: TextView = itemView.findViewById(R.id.fechaRealizado)
        private val entregaFecha: TextView = itemView.findViewById(R.id.fechaEntrega)
        private val pedidoEstado: TextView = itemView.findViewById(R.id.estadoPedido)

        fun bind(pedido: PedidoProcesado) {
            Log.d("FechaDebug", "Realizado: '${pedido.fechaRealizado}' | Entrega: '${pedido.fechaEntrega}'")
            val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val formatoSalida = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            val fechaFormateadaR = try {
                val fechaR = formatoEntrada.parse(pedido.fechaRealizado)
                formatoSalida.format(fechaR ?: Date())
            } catch (e: Exception) {
                pedido.fechaRealizado
            }

            val fechaFormateadaE = try {
                val fechaE = formatoEntrada.parse(pedido.fechaEntrega)
                formatoSalida.format(fechaE ?: Date())
            } catch (e: Exception) {
                pedido.fechaEntrega
            }

            pedidoFecha.text = "Fecha Realizado: $fechaFormateadaR"
            entregaFecha.text = "Fecha Entrega: $fechaFormateadaE"
            pedidoEstado.text = pedido.estadoPedido

            when (pedido.estadoPedido.lowercase(Locale.getDefault())) {
                "ENTREGADO" -> {
                    pedidoEstado.setTextColor(itemView.context.getColor(R.color.white))
                    itemView.setBackgroundColor(itemView.context.getColor(R.color.green))
                }
                "EN ALISTAMIENTO" -> {
                    pedidoEstado.setTextColor(itemView.context.getColor(R.color.white))
                    itemView.setBackgroundColor(itemView.context.getColor(R.color.yellow))
                }
                "EN TRANSITO" -> {
                    pedidoEstado.setTextColor(itemView.context.getColor(R.color.black))
                    itemView.setBackgroundColor(itemView.context.getColor(R.color.yellow))
                }
                else -> {
                    pedidoEstado.setTextColor(itemView.context.getColor(R.color.white))
                    itemView.setBackgroundColor(itemView.context.getColor(R.color.greyregistrar))
                }
            }

        }
    }
}