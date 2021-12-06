package br.com.projects.bin2dec.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.pow

class ConverterViewModel : ViewModel() {
    private var _error = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = _error

    private var _buttonIsEnabled = MutableLiveData<Boolean>()
    val buttonIsEnabled: LiveData<Boolean>
        get() = _buttonIsEnabled

    private var _conversionResult = MutableLiveData<Int>()
    val conversionResult: LiveData<Int>
        get() = _conversionResult

    fun convertNumber(binary: String) {
        var num = binary.toLong()
        var decimal = 0
        var i = 0
        while (num.toInt() != 0) {
            decimal += ((num % 10) * 2.0.pow(i.toDouble())).toInt()
            num /= 10
            ++i
        }
        _conversionResult.value = decimal
    }

    fun inputVerify(binary: String) {
        if (binary.isEmpty()) _error.value = Error(true, null)
        else if (
            binary.contains(" ") ||
            binary.contains("-") ||
            binary.contains(".") ||
            binary.contains(",") ||
            binary.contains("2") ||
            binary.contains("3") ||
            binary.contains("4") ||
            binary.contains("5") ||
            binary.contains("6") ||
            binary.contains("7") ||
            binary.contains("8") ||
            binary.contains("9")
        ) _error.value = Error(true, "Invalid input!")
        else {
            _error.value = Error(false, null)
        }
        _buttonIsEnabled.value = _error.value?.hasError == false
    }
}

data class Error(
    val hasError: Boolean = false,
    val message: String? = null
)