package com.baranonat.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.baranonat.besinkitabi.databinding.FragmentDetayBesinBinding
import com.baranonat.besinkitabi.util.gorselIndir
import com.baranonat.besinkitabi.util.placeHolderYap
import com.baranonat.besinkitabi.viewmodel.BesinDetayiViewModel


class DetayBesinFragment : Fragment() {
    private var _binding: FragmentDetayBesinBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:BesinDetayiViewModel
    private var besinId=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetayBesinBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            besinId=DetayBesinFragmentArgs.fromBundle(it).besinId

        }
        viewModel=ViewModelProvider(this)[BesinDetayiViewModel::class.java]
        viewModel.roomVerisiniAl(besinId)
        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner){

            binding.isimTextView.text=it.besinIsim
            binding.kaloriTextView.text=it.besinKalori
            binding.karbonhidratTextView.text=it.besinKarbonhidrat
            binding.proteinTextView.text=it.besinProtein
            binding.yagTextView.text=it.besinYag
            binding.besinImageView.gorselIndir(it.besinGorsel, placeHolderYap(requireContext()))


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}