package com.application.shoprye

import android.annotation.TargetApi
import android.app.PendingIntent
import android.content.*
import android.os.Build
import com.application.shoprye.data.RyeJobDatabase
import com.application.shoprye.repositories.ShoppingCartRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class MailClientChosenReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val mainRepository = ShoppingCartRepository(RyeJobDatabase.getInstance(context).shoppingCartDao())
        GlobalScope.launch {
            mainRepository.removeAllShoppingCartEntries()
        }
    }

    companion object {

        private const val EXTRA_RECEIVER_TOKEN = "receiver_token"
        private val LOCK = Any()

        private var sTargetChosenReceiveAction: String? = null
        private var mailClientChosenReceiver: MailClientChosenReceiver? = null
        val isSupported: Boolean
            get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1


        @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        fun getSharingSenderIntent(context: Context): IntentSender {

            synchronized(LOCK) {
                if (sTargetChosenReceiveAction == null) {
                    sTargetChosenReceiveAction = context.packageName
                        .toString() + "/" + MailClientChosenReceiver::class.java.getName() + "_ACTION"
                }
                if (mailClientChosenReceiver != null) {
                    context.unregisterReceiver(mailClientChosenReceiver)
                }
                mailClientChosenReceiver = MailClientChosenReceiver()
                context.registerReceiver(
                    mailClientChosenReceiver,
                    IntentFilter(sTargetChosenReceiveAction)
                )
            }

            val intent = Intent(sTargetChosenReceiveAction)
            intent.setPackage(context.packageName)
            intent.setClass(context.applicationContext, MailClientChosenReceiver::class.java)
            intent.putExtra(EXTRA_RECEIVER_TOKEN, mailClientChosenReceiver.hashCode())

            val callback = PendingIntent.getBroadcast(
                context, 0, intent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                else PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_ONE_SHOT
            )
            return callback.intentSender
        }
    }
}