package com.example.bebidas

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bebidas.databinding.FragmentMenuPrincipalBinding
/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MenuPrincipalFragment : Fragment() {

    private var _binding: FragmentMenuPrincipalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuPrincipalBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSobre.setOnClickListener {
            findNavController().navigate(R.id.action_MenuPrincipalFragment_to_SobreFragment)
        }

        binding.buttonBebidas.setOnClickListener {
            findNavController().navigate(R.id.action_MenuPrincipalFragment_to_listaBebidasFragment)
        }

        binding.buttonMarcas.setOnClickListener {
            findNavController().navigate(R.id.action_MenuPrincipalFragment_to_listaMarcasFragment)
        }

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_main
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}