package org.example.multicalculator

//import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalcView()
        }
    }
    @Composable
    fun CalcView() {
        Column(modifier = Modifier.fillMaxSize().background(Color.Cyan)) {
            val displayText = remember { mutableStateOf("0") }
            val leftNumber = rememberSaveable { mutableIntStateOf(0) }
            val rightNumber = rememberSaveable { mutableIntStateOf(0) }
            val operation = rememberSaveable { mutableStateOf("") }
            val complete = rememberSaveable { mutableStateOf(false) }

            if (complete.value && operation.value.isNotEmpty()) {
                var answer = 0

                // When statement to perform the operation
                when (operation.value) {
                    "+" -> answer = leftNumber.intValue + rightNumber.intValue
                    "-" -> answer = leftNumber.intValue - rightNumber.intValue
                    "*" -> answer = leftNumber.intValue * rightNumber.intValue
                    "/" -> answer = if (rightNumber.intValue != 0) leftNumber.intValue / rightNumber.intValue else 0 // Handle division by zero
                }

                // Assign the answer to displayText
                displayText.value = answer.toString()

            } else if (operation.value.isNotEmpty() && !complete.value) {
                displayText.value = rightNumber.intValue.toString()
            } else {
                // Set displayText to leftNumber value
                displayText.value = leftNumber.intValue.toString()
                // Empty functions
            }
            fun numberPress( btnNum: Int) {
                if (complete.value) {
                    leftNumber.intValue = 0
                    rightNumber.intValue  = 0
                    operation.value = ""
                    complete.value = false
                }
                if (operation.value !== "" && !complete.value) {
                    rightNumber.intValue = (rightNumber.intValue * 10) + btnNum
                } else if (operation.value.isBlank() && !complete.value) {
                    leftNumber.intValue = leftNumber.intValue * 10 + btnNum
                }
            }

            fun operationPress(op: String) {
                if(!complete.value) {
                    operation.value = op
                }
            }

            fun equalsPress() {
                complete.value = true
            }

            Column(modifier = Modifier.fillMaxSize().padding(16.dp).background(Color.LightGray)) {
                Row(modifier = Modifier.fillMaxWidth().background(Color.Yellow).padding(4.dp)) {
                    CalcDisplay(displayText)
                }
                Row(modifier = Modifier.fillMaxWidth().background(Color.Red).padding(4.dp)) {
                    Column(modifier = Modifier.weight(2f).background(Color.DarkGray)) {
                        CalcRow(onPress = { number -> numberPress(number) }, startNum = 1, numButtons = 3)
                        CalcRow(onPress = { number -> numberPress(number) }, startNum = 4, numButtons = 3)
                        CalcRow(onPress = { number -> numberPress(number) }, startNum = 7, numButtons = 3)
                        Row {
                            CalcNumericButton(number = 0, onPress = { number -> numberPress(number) })
                            CalcEqualsButton(onPress = { equalsPress() })
                        }
                    }
                    Column(modifier = Modifier.weight(1f).background(Color.Blue).padding(16.dp, 0.dp)) {
                        CalcOperationButton(operation = "+", onPress = { op -> operationPress(op) })
                        CalcOperationButton(operation = "-", onPress = { op -> operationPress(op) })
                        CalcOperationButton(operation = "*", onPress = { op -> operationPress(op) })
                        CalcOperationButton(operation = "/", onPress = { op -> operationPress(op) })
                    }
                }
            }
        }
    }

    @Composable
    fun CalcRow( onPress: (number: Int) -> Unit, startNum: Int, numButtons: Int) {
        val endNum = startNum + numButtons
        Row(modifier = Modifier.padding(0.dp)) {
            for (i in startNum until endNum) {
                CalcNumericButton(i,  onPress = onPress)
            }
        }
    }

    @Composable
    fun CalcDisplay(display: MutableState<String>) {
        Text(
            text = display.value,
            modifier = Modifier
                .height(50.dp)
                .padding(5.dp)
                .fillMaxWidth()
        )
    }

    @Composable
    fun CalcNumericButton(number: Int, onPress: (number: Int) -> Unit) {
        Button(
            onClick = { onPress(number) },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(text = number.toString())
        }
    }

    @Composable
    fun CalcOperationButton(operation: String, onPress: (operation: String) -> Unit) {
        Button(
            onClick = { onPress(operation)},
            modifier = Modifier.padding(4.dp)
        ) {
            Text(text = operation)
        }
    }

    @Composable
    fun CalcEqualsButton(onPress: () -> Unit) {
        Button(
            onClick = { onPress() },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(text = "=")
        }
    }


}

@Preview
@Composable
fun AppAndroidPreview() {
//    App()
}