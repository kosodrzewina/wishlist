package com.example.wishlist

import android.app.*
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private const val CHANNEL_ID = "id_channel_default"

object Notifications {
    fun createChannel(context: Context) {
        val channel =
            NotificationChannel(
                CHANNEL_ID, "Product locations", NotificationManager.IMPORTANCE_DEFAULT
            )

        context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    fun createNotification(context: Context, product: Product): Notification {
        val encodedProductId =
            URLEncoder.encode(product.productIdImagePath, StandardCharsets.UTF_8.toString())
        val productDetailIntent = Intent(
            Intent.ACTION_VIEW,
            "https://example.com/encodedProductId=$encodedProductId".toUri(),
            context,
            MainActivity::class.java
        )
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(productDetailIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_outline_location_on_24)
            .setContentTitle("You are in ${product.name} area!")
            .setContentText("Tap to go to the product page")
            .setContentIntent(pendingIntent)
            .build()
    }
}