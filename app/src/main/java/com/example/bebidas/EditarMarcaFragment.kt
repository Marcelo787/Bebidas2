package com.example.bebidas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bebidas.databinding.FragmentEditarMarcaBinding

class EditarMarcaFragment : Fragment() {
    private var _binding: FragmentEditarMarcaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditarMarcaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaMarcas()
                true
            }
            else -> false
        }
    }

    private fun voltaListaMarcas() {
        findNavController().navigate(R.id.action_editarMarcaFragment_to_listaMarcasFragment)
    }

    private fun guardar() {
        val nome_Marca = binding.editTextNomeMarca.text.toString()
        if (nome_Marca.isBlank()) {
            binding.editTextNomeMarca.error = getString(R.string.Nome_Marca_obrigatorio)
            binding.editTextNomeMarca.requestFocus()
            return
        }

        val marca = Marcas(
            nome_Marca
        )

        val id = requireActivity().contentResolver.insert(
            BebidasContentProvider.ENDERECO_MARCAS,
            marca.toContentValues()
        )

        if (id == null) {
            binding.editTextNomeMarca.error = getString(R.string.erro_guardar_marca)
            return
        }

        Toast.makeText(requireContext(), getString(R.string.marca_guardada_com_sucesso), Toast.LENGTH_SHORT).show()
        voltaListaMarcas()
    }
}