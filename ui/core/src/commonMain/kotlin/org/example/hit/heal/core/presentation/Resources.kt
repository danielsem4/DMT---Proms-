package org.example.hit.heal.core.presentation

import dmt_proms.ui.core.generated.resources.Res
import dmt_proms.ui.core.generated.resources.app_name
import dmt_proms.ui.core.generated.resources.how_do_you_feel
import dmt_proms.ui.core.generated.resources.login
import dmt_proms.ui.core.generated.resources.next
import dmt_proms.ui.core.generated.resources.previous
import org.jetbrains.compose.resources.getString

/**
 * Resources used in the application.
 * These resources include icons, strings, and other assets
 * This object made to enable the resources to be used in different modules.
 */

object Resources {

    object Icon {}

    object String {
        val appName = Res.string.app_name
        val login = Res.string.login
        val previous = Res.string.previous
        val next = Res.string.next
        val howDoYouFeel = Res.string.how_do_you_feel
    }

}