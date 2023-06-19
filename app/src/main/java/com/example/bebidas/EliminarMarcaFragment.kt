package com.example.bebidas

import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bebidas.databinding.FragmentEliminarMarcaBinding
import com.google.android.material.snackbar.Snackbar

class EliminarMarcaFragment : Fragment() {
    private lateinit var marcas: Marcas
    private var _binding: FragmentEliminarMarcaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarMarcaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        marcas = EliminarMarcaFragmentArgs.fromBundle(requireArguments()).marcas

        binding.textViewMarca.text = marcas.nome

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminar()
                true
            }
            R.id.action_cancelar -> {
                voltaLista()
                true
            }
            else -> false
        }
    }

    private fun voltaLista() {
        findNavController().navigate(R.id.action_eliminarMarcaFragment_to_listaMarcasFragment)
    }

    private fun eliminar() {
        val enderecoMarca = Uri.withAppendedPath(BebidasContentProvider.ENDERECO_MARCAS, marcas.id.toString())
        val numMarcasEliminadas = requireActivity().contentResolver.delete(enderecoMarca, null, null)

        if (numMarcasEliminadas == 1) {
            Toast.makeText(requireContext(), getString(R.string.marca_eliminada_com_sucesso), Toast.LENGTH_LONG).show()
            voltaLista()
        } else {
            Snackbar.make(binding.textViewMarca, getString(R.string.erro_eliminar_marca), Snackbar.LENGTH_INDEFINITE)
        }
    }
}