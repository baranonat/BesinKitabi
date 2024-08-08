package com.baranonat.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.baranonat.besinkitabi.adapter.BesinAdapter
import com.baranonat.besinkitabi.databinding.FragmentBesinListeBinding
import com.baranonat.besinkitabi.service.BesinAPI
import com.baranonat.besinkitabi.viewmodel.BesinListesiViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BesinListeFragment : Fragment() {
    private var _binding: FragmentBesinListeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:BesinListesiViewModel
    private  var besinAdapter=BesinAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBesinListeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProvider(this)[BesinListesiViewModel::class.java]
            viewModel.refreshData()

        binding.besinListeRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.besinListeRecyclerView.adapter=besinAdapter
        binding.swipeRefreshlayout.setOnRefreshListener {
            viewModel.refreshDataFromInternet()
            binding.besinTextView.visibility=View.GONE
            binding.besinListeRecyclerView.visibility=View.GONE
            binding.besinprogressBar.visibility=View.VISIBLE
            binding.swipeRefreshlayout.isRefreshing=false
    }
            observeLiveData()
        }
    private fun observeLiveData(){

        viewModel.besinler.observe(viewLifecycleOwner){
            besinAdapter.besinListesiniGuncelle(it)
            binding.besinListeRecyclerView.visibility=View.VISIBLE

        }
        viewModel.besinHataMesaji.observe(viewLifecycleOwner){
            if(it){
                binding.besinTextView.visibility=View.VISIBLE
                binding.besinListeRecyclerView.visibility=View.GONE
            }else{
                binding.besinTextView.visibility=View.GONE
            }
        }
            viewModel.besinYukleniyor.observe(viewLifecycleOwner){
                if(it){
                    binding.besinTextView.visibility=View.GONE
                    binding.besinListeRecyclerView.visibility=View.GONE
                    binding.besinprogressBar.visibility=View.VISIBLE
                }else{
                    binding.besinprogressBar.visibility=View.GONE
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}