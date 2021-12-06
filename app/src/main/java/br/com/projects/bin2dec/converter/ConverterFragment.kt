package br.com.projects.bin2dec.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.projects.bin2dec.databinding.ConverterFragmentBinding

class ConverterFragment : Fragment() {
    private lateinit var binding: ConverterFragmentBinding
    private lateinit var viewModel: ConverterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ConverterFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ConverterViewModel::class.java]
        setupButton()
        setupView()
        return binding.root
    }

    private fun setupButton() = with(binding) {
        viewModel.buttonIsEnabled.observe(viewLifecycleOwner) { isEnabled ->
            buttonConvert.isEnabled = isEnabled
        }
        buttonConvert.setOnClickListener {
            viewModel.convertNumber(edtBinary.text.toString())
        }
    }

    private fun setupView() = with(binding) {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error.hasError) edtLayoutBinary.error = error.message
            else edtLayoutBinary.error = null
        }
        edtBinary.doOnTextChanged { text, _, _, _ ->
            viewModel.inputVerify(text.toString())
        }
        viewModel.conversionResult.observe(viewLifecycleOwner) { result ->
            txtResult.text = result.toString()
        }
    }
}