package omni.toolbox.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.widget.Toast
import androidx.core.content.getSystemService
import omni.toolbox.MainActivity
import omni.toolbox.model.Tool

object ShortcutUtils {
    fun pinShortcut(context: Context, tool: Tool) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val shortcutManager = context.getSystemService<ShortcutManager>()
            if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported) {
                val intent = Intent(context, MainActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    putExtra("route", tool.route)
                }

                // Use a proper resource ID for the icon if possible, but here we might need to
                // handle ImageVector to Icon conversion or just use a generic app icon.
                // For simplicity in this toolbox, we'll use the app's launcher icon.
                val pinShortcutInfo = ShortcutInfo.Builder(context, tool.route)
                    .setShortLabel(tool.name)
                    .setLongLabel(tool.name)
                    .setIcon(Icon.createWithResource(context, context.applicationInfo.icon))
                    .setIntent(intent)
                    .build()

                shortcutManager.requestPinShortcut(pinShortcutInfo, null)
            } else {
                Toast.makeText(context, "Pinning shortcuts is not supported on this device/launcher", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Pinning shortcuts requires Android 8.0 or higher", Toast.LENGTH_SHORT).show()
        }
    }
}
