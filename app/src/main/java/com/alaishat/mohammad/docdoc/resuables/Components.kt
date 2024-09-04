package com.alaishat.mohammad.docdoc.resuables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.ui.theme.FocusedTextFieldStrokeColor
import com.alaishat.mohammad.docdoc.ui.theme.Gray
import com.alaishat.mohammad.docdoc.ui.theme.Seed
import com.alaishat.mohammad.docdoc.ui.theme.TextFieldBackgroundColor
import com.alaishat.mohammad.docdoc.ui.theme.TextFieldErrorStrokeColor
import com.alaishat.mohammad.docdoc.ui.theme.UnfocusedTextFieldStrokeColor

/**
 * Created by Mohammad Al-Aishat on Aug/20/2024.
 * DocDoc Project.
 */

@Composable
fun DocDocTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
) =
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Gray,
            focusedIndicatorColor = FocusedTextFieldStrokeColor,
            unfocusedIndicatorColor = UnfocusedTextFieldStrokeColor,
            cursorColor = FocusedTextFieldStrokeColor,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = TextFieldBackgroundColor,
            errorContainerColor = TextFieldBackgroundColor,
            errorCursorColor = TextFieldErrorStrokeColor,
            errorLabelColor = TextFieldErrorStrokeColor,
            errorIndicatorColor = TextFieldErrorStrokeColor,
            errorSupportingTextColor = TextFieldErrorStrokeColor
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = RoundedCornerShape(16.dp),
        isError = isError
    )

@Composable
fun DocDocButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(16.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = Seed),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = shape,
    colors = colors,
    elevation = elevation,
    border = border,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    content = content
)

@Composable
fun WelcomeText(title: String, body: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = title, style = DocDocTheme.typography.headlineMedium, color = Seed)
        DocDocBodyText(text = body)
    }
}

@Composable
fun TitleWithSeeAllTextButtonRow(title: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = DocDocTheme.typography.titleMedium)
    }
}


@Composable
fun DoctorCard(
    name: String = "Dr. Jack Sulivan",
    specialization: String = "General",
    city: String = "Damascus",
    phone: String = "0999999999",
    degree: String = "Degree",
    gender: String = "Male",
    showGenderAndHidPhoneAndCity: Boolean = false,
    orientation: Int = LocalConfiguration.current.orientation,
    clickable: Boolean = true,
    onClick: () -> Unit = {},
    model: String = "",
) {
    val modifier =
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))


    val clickableMod = Modifier.clickable { onClick() }

    val resultMod =
        (if (clickable) modifier.then(clickableMod) else modifier)
            .padding(8.dp)

    Row(
        modifier = resultMod,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val imageModifier = if (orientation == Orientation.Vertical.ordinal) Modifier
            .fillMaxWidth(0.3f) else Modifier.requiredSize(120.dp)

        AsyncImage(
            modifier =
            imageModifier
                .clip(RoundedCornerShape(12.dp)),
            model = model,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = name, style = DocDocTheme.typography.titleSmall)
            Text(
                text = "$specialization | $degree",
                style = MaterialTheme.typography.bodySmall,
                color = Gray
            )


            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showGenderAndHidPhoneAndCity)
                    Text(
                        text = gender,
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray
                    )
                else {
                    Text(
                        text = city,
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray
                    )
                    Text(
                        text = " $phone",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray
                    )
                }
            }
        }
    }

}


@Composable
fun TitleWithInfo(title: String, info: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = title, style = DocDocTheme.typography.titleSmall)
        SelectionContainer {
            Text(
                text = info,
                style = MaterialTheme.typography.bodySmall,
                color = Gray,
                fontSize = TextUnit(16f, TextUnitType.Sp),
            )
        }
    }
}

@Composable
fun DocDocTopAppBar(
    navController: NavHostController?,
    text: String,
    onLeftIconClick: () -> Unit = { navController?.navigateUp() },
    leadingIcon: @Composable () -> Unit = {
        Icon(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { onLeftIconClick() },
            painter = painterResource(id = R.drawable.ic_back_button),
            contentDescription = "",
            tint = Color.Unspecified
        )
    },

    middleText: @Composable () -> Unit = {
        Text(text = text, style = DocDocTheme.typography.titleSmall)
    },
    trailingIcon: @Composable () -> Unit = {
        Spacer(modifier = Modifier.width(32.dp))
    }) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon()
        middleText()
        trailingIcon()
    }
}