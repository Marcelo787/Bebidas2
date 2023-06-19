package com.example.bebidas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bebidas.databinding.FragmentListaMarcasBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ListaLivrosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaMarcasFragment : Fragment() {
    private var _binding: FragmentListaMarcasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_marcas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterMarcas = AdapterMarcas()
        binding.recyclerViewMarcas.adapter = adapterMarcas
        binding.recyclerViewMarcas.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {

    }
}