package com.example.bucketlist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BucketListItem (
    bucketItem: BucketItem,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp, // Rounded corners
    onCheckedChange: (Boolean) -> Unit,
    navController: NavController
    //onDeleteClick: () -> Unit
){
    Box(
        modifier = modifier
            .background(color = Color.White)
            .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(cornerRadius))
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = bucketItem.isCompleted,
                modifier = Modifier.padding(horizontal = 8.dp),
                onCheckedChange = onCheckedChange
            )
            Column (
              modifier = Modifier
            ) {
                Text(
                    text = bucketItem.name
                )
                val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

                Text (
                    text = dateFormatter.format(Date(bucketItem.dueDate)),
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            // SOURCE: https://stackoverflow.com/questions/71715341/how-to-custom-left-right-align-items-in-android-jetpack-compose-row
            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = {
                    navController.navigate(Screen.AddEditScreen.route
                    + "?itemId=${bucketItem.id}"
                    )
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit item",
                    modifier = Modifier.padding(horizontal = 12.dp)

                )
            }
            //Text( FIX LATER
             //   text = String.toString(bucketItem.dueDate)
            //)
        }
    }

}