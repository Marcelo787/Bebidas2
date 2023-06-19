package com.example.bebidas
import android.net.Uri
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
    private var marca: Marcas?= null
    private var _binding: FragmentEditarMarcaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditarMarcaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar


        val marca = EditarMarcaFragmentArgs.fromBundle(requireArguments()).marcas

        if (marca != null) {
            binding.editTextNomeMarca.setText(marca.nome)

        }

        this.marca = marca
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
                voltaLista()
                true
            }
            else -> false
        }
    }

    private fun voltaLista() {
        findNavController().navigate(R.id.action_editarMarcaFragment_to_listaMarcasFragment)
    }

    private fun guardar() {
        val nome_Marca = binding.editTextNomeMarca.text.toString()
        if (nome_Marca.isBlank()) {
            binding.editTextNomeMarca.error = getString(R.string.Nome_Marca_obrigatorio)
            binding.editTextNomeMarca.requestFocus()
            return
        }

        if (marca == null) {
            val marcas = Marcas(
                nome_Marca
            )

            insereMarca(marcas)

        } else {
            val marca = marca!!
            marca.nome = nome_Marca

            alteraMarca(marca)
        }
    }

    private fun alteraMarca(marcas: Marcas) {
        val enderecoMarcas = Uri.withAppendedPath(BebidasContentProvider.ENDERECO_MARCAS, marcas.id.toString())
        val marcasAlteradas = requireActivity().contentResolver.update(enderecoMarcas, marcas.toContentValues(), null, null)

        if (marcasAlteradas == 1) {
            Toast.makeText(requireContext(), R.string.marca_guardada_com_sucesso, Toast.LENGTH_LONG).show()
            voltaLista()
        } else {
            binding.editTextNomeMarca.error = getString(R.string.erro_guardar_marca)
        }
    }

    private fun insereMarca(
        marcas: Marcas
    ) {

        val id = requireActivity().contentResolver.insert(
            BebidasContentProvider.ENDERECO_MARCAS,
            marcas.toContentValues()
        )

        if (id == null) {
            binding.editTextNomeMarca.error = getString(R.string.erro_guardar_marca)
            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.marca_guardada_com_sucesso),
            Toast.LENGTH_SHORT
        ).show()
        voltaLista()
    }
}