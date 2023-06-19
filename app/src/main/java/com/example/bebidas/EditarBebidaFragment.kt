package com.example.bebidas

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.bebidas.databinding.FragmentEditarBebidaBinding
import java.util.*

private const val ID_LOADER_MARCAS = 0

class EditarBebidaFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var bebida: Bebidas?= null
    private var _binding: FragmentEditarBebidaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditarBebidaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_MARCAS, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val bebida = EditarBebidaFragmentArgs.fromBundle(requireArguments()).bebida

        if (bebida != null) {
            binding.editTextNome.setText(bebida.nome)
            binding.editTextDescricaoDaBebida.setText(bebida.descricao)

        }

        this.bebida = bebida
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
                voltaListaBebidas()
                true
            }
            else -> false
        }
    }

    private fun voltaListaBebidas() {
        findNavController().navigate(R.id.action_novaBebidaFragment_to_listaBebidasFragment)
    }

    private fun guardar() {
        val nome_da_bebida = binding.editTextNome.text.toString()
        if (nome_da_bebida.isBlank()) {
            binding.editTextNome.error = getString(R.string.nome_da_bebida_obrigatorio)
            binding.editTextNome.requestFocus()
            return
        }

        val desc_da_bebida = binding.editTextDescricaoDaBebida.text.toString()
        if (desc_da_bebida.isBlank()) {
            binding.editTextDescricaoDaBebida.error = getString(R.string.desc_da_bebida_nao_e_obrigatoria)
            binding.editTextDescricaoDaBebida.requestFocus()
            return
        }

        val marcaId = binding.spinnerMarcas.selectedItemId

        if (bebida == null) {
            val bebida = Bebidas(
                nome_da_bebida,
                desc_da_bebida,
                Marcas("?", marcaId)

            )

            insereBebida(bebida)
        } else {
            val bebida = bebida!!
            bebida.nome = nome_da_bebida
            bebida.idMarca = Marcas("?", marcaId)
            bebida.descricao = desc_da_bebida

            alteraBebida(bebida)
        }
    }

    private fun alteraBebida(bebidas: Bebidas) {
        val enderecoBebida = Uri.withAppendedPath(BebidasContentProvider.ENDERECO_BEBIDAS, bebidas.id.toString())
        val bebidaAlterada = requireActivity().contentResolver.update(enderecoBebida, bebidas.toContentValues(), null, null)

        if (bebidaAlterada == 1) {
            Toast.makeText(requireContext(), R.string.bebida_guardada_com_sucesso, Toast.LENGTH_LONG).show()
            voltaListaBebidas()
        } else {
            binding.editTextNome.error = getString(R.string.erro_guardar_bebida)
        }
    }

    private fun insereBebida(
        bebidas: Bebidas
    ) {
        val id = requireActivity().contentResolver.insert(
            BebidasContentProvider.ENDERECO_BEBIDAS,
            bebidas.toContentValues()
        )

        if (id == null) {
            binding.editTextNome.error = getString(R.string.erro_guardar_bebida)
            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.bebida_guardada_com_sucesso),
            Toast.LENGTH_SHORT
        ).show()
        voltaListaBebidas()
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            BebidasContentProvider.ENDERECO_MARCAS,
            TabelaMarcas.CAMPOS,
            null, null,
            TabelaMarcas.CAMPO_NOME
        )
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        binding.spinnerMarcas.adapter = null
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data == null) {
            binding.spinnerMarcas.adapter = null
            return
        }

        binding.spinnerMarcas.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaMarcas.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )

        mostraMarcaSelecionadaSpinner()
    }

    private fun mostraMarcaSelecionadaSpinner() {
        if (bebida == null) return

        val idMarca = bebida!!.idMarca.id

        val ultimaMarca = binding.spinnerMarcas.count - 1
        for (i in 0..ultimaMarca) {
            if (idMarca == binding.spinnerMarcas.getItemIdAtPosition(i)) {
                binding.spinnerMarcas.setSelection(i)
                return
            }
        }
    }
}