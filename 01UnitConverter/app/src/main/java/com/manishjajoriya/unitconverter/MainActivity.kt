package com.manishjajoriya.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manishjajoriya.unitconverter.ui.theme.UnitConverterTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UnitConverter(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Select") }
    var outputUnit by remember { mutableStateOf("Select") }
    var iExpand by remember { mutableStateOf(false) }
    var oExpand by remember { mutableStateOf(false) }
    var conversionFactor by remember { mutableFloatStateOf(0f) }

    val customTextStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = 20.sp,
        color = Color.Red
    )

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0

        if (inputUnit == "Select" || outputUnit == "Select") {
            outputValue = "Please select valid units"
            return
        }

        if (inputUnit == outputUnit) {
            conversionFactor = 1f
        } else when (inputUnit) {
            "Centimeters" -> {
                conversionFactor = when (outputUnit) {
                    "Meters" -> 0.01f
                    "Inch" -> 0.394f
                    "Foot" -> 0.0328f
                    else -> 0f
                }
            }

            "Meters" -> {
                conversionFactor = when (outputUnit) {
                    "Centimeters" -> 100f
                    "Inch" -> 39.37f
                    "Foot" -> 3.281f
                    else -> 0f
                }
            }

            "Inch" -> {
                conversionFactor = when (outputUnit) {
                    "Centimeters" -> 2.54f
                    "Meters" -> 0.0254f
                    "Foot" -> 0.0833f
                    else -> 0f
                }
            }

            "Foot" -> {
                conversionFactor = when (outputUnit) {
                    "Centimeters" -> 30.48f
                    "Meters" -> 0.3048f
                    "Inch" -> 12f
                    else -> 0f
                }
            }

            else -> conversionFactor = 0f
        }

        outputValue = String.format("%.2f", inputValueDouble * conversionFactor)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Unit Converter", Modifier.padding(bottom = 16.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                outputValue = ""
                convertUnits()
            },
            label = { Text("Enter value") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Input unit selection
            Box {
                Button(onClick = { iExpand = true }) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown, "Arrow Down")
                }
                DropdownMenu(
                    expanded = iExpand,
                    onDismissRequest = { iExpand = false },
                ) {
                    listOf("Centimeters", "Meters", "Inch", "Foot").forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                iExpand = false
                                inputUnit = unit
                                convertUnits()
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.width(16.dp))

            // Output unit selection
            Box {
                Button(onClick = { oExpand = true }) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown, "Arrow Down")
                }
                DropdownMenu(
                    expanded = oExpand,
                    onDismissRequest = { oExpand = false },
                ) {
                    listOf("Centimeters", "Meters", "Inch", "Foot").forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                oExpand = false
                                outputUnit = unit
                                convertUnits()
                            }
                        )
                    }
                }
            }
        }

        Text("Result: $outputValue $outputUnit", Modifier.padding(top = 16.dp) , style = customTextStyle)
    }

}


@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}