package com.baranonat.besinkitabi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.baranonat.besinkitabi.databinding.RecyclerRowBinding
import com.baranonat.besinkitabi.model.Besin
import com.baranonat.besinkitabi.util.gorselIndir
import com.baranonat.besinkitabi.util.placeHolderYap
import com.baranonat.besinkitabi.view.BesinListeFragmentDirections

class BesinAdapter(val besinListesi:ArrayList<Besin>):RecyclerView.Adapter<BesinAdapter.BesinHolder>() {
    class BesinHolder(val binding:RecyclerRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinHolder {
     val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BesinHolder(binding)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    fun besinListesiniGuncelle(yeniBesinListesi:List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: BesinHolder, position: Int) {
        holder.binding.isim.text=besinListesi[position].besinIsim
        holder.binding.kalori.text=besinListesi[position].besinKalori
        holder.itemView.setOnClickListener {

            val action=BesinListeFragmentDirections.actionBesinListeFragmentToDetayBesinFragment(besinListesi[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.ImageView.gorselIndir(besinListesi[position].besinGorsel, placeHolderYap(holder.itemView.context))

    }
}