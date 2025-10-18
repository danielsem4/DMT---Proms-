package com.example.finalprojectnew.stage2.data.catalog

import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

// mapper between imageKey (from the Product model) and the actual DrawableResource
object ImageKeyMapper {
    fun map(imageKey: String): DrawableResource? = when (imageKey) {
        "creamCheese28"   -> Res.drawable.stage2_shamenet_cheese
        "white500"        -> Res.drawable.white_cheese
        "milk1p"          -> Res.drawable.stage2milk1precent
        "milk3p"          -> Res.drawable.milk3precent
        "bulgarian250"    -> Res.drawable.bulgarian_cheese
        "tsfatit5_250"    -> Res.drawable.stage2_tsfatit5precent
        "yellowSliced250" -> Res.drawable.yellow_cheese
        "yellowGrated500" -> Res.drawable.stage2_yellowcheese_megurad
        "white250"        -> Res.drawable.white_cheese
        "sweetCream38"    -> Res.drawable.stage2_milk_sweet_shamenet

        "shnitzelKg"     -> Res.drawable.stage2_meat_shnitzel
        "legs4"          -> Res.drawable.stage2_meat_chickenshok
        "breastKg"       -> Res.drawable.stage2_meat_chickenbreast
        "pargit"         -> Res.drawable.stage2_meat_pargit
        "beefGround500"  -> Res.drawable.stage2_meat_tahun

        "tomato"        -> Res.drawable.tomato
        "cucumber"      -> Res.drawable.cucumber
        "broccoli"      -> Res.drawable.broccoli
        "cherryTomato"  -> Res.drawable.stage2_vegetables_cherrytomato
        "carrot250"     -> Res.drawable.stage2_vegetables_carrot
        "onion"         -> Res.drawable.stage2_vegetables_onion
        "celery"        -> Res.drawable.stage2_vegetables_cellery
        "lemon"         -> Res.drawable.lemon

        "apricot"     -> Res.drawable.stage2_fruits_mishmish
        "banana"      -> Res.drawable.stage2_fruits_banana
        "greenApple"  -> Res.drawable.stage2_fruits_greenapple
        "melon"       -> Res.drawable.stage2_fruits_melon
        "redApple"    -> Res.drawable.stage2_fruits_redapple
        "nectarine"   -> Res.drawable.stage2_fruits_peach
        "grapes"      -> Res.drawable.stage2_fruits_grapes

        "broccoliFrozen"     -> Res.drawable.stage2_frozen_brocoli
        "peas"               -> Res.drawable.stage2_frozen_afuna
        "peaCarrotMix"       -> Res.drawable.stage2_frozen_carrotafuna
        "cauliflower"        -> Res.drawable.stage2_frozen_kruvit
        "greenBeans"         -> Res.drawable.stage2_frozen_beans
        "borekasCheese"      -> Res.drawable.stage2_frozen_borekascheese
        "borekasPotato"      -> Res.drawable.stage2_frozen_borekastapha
        "jahnun"             -> Res.drawable.stage2_frozen_jahnun

        "canned_corn"       -> Res.drawable.stage2_dry_cannedcorn
        "pickles_vinegar"   -> Res.drawable.stage2_dry_pickle
        "tuna"              -> Res.drawable.stage2_dry_tuna
        "baby_corn"         -> Res.drawable.stage2_dry_littlecorn
        "pickles_salt"      -> Res.drawable.stage2_dry_pickle
        "sardines"          -> Res.drawable.stage2_dry_sardine
        "olive_oil"         -> Res.drawable.olive_oil
        "sunflower_oil"     -> Res.drawable.stage2_dry_oilhamania
        "canola_oil"        -> Res.drawable.kanola_oil
        "soy_oil"           -> Res.drawable.stage2_dry_oilsoya
        "coconut_oil"       -> Res.drawable.stage2_dry_oilcoconut
        "olives_canned"     -> Res.drawable.olives
        "salt"              -> Res.drawable.salt
        "black_pepper"      -> Res.drawable.black_pepper
        "flour"             -> Res.drawable.flour
        "cacao"             -> Res.drawable.cacao
        "baking_powder"     -> Res.drawable.baking_powder
        "sugar"             -> Res.drawable.suger
        "vanil"             -> Res.drawable.vanil
        "dry_tomatopaste"   -> Res.drawable.stage2_dry_tomatopaste
        "dry_hummus"        -> Res.drawable.stage2_dry_hummus

        "nosugar_bread" -> Res.drawable.stage2_bakery_nosuger_bread
        "regular_bread" -> Res.drawable.stage2_bakery_bread
        "challah"       -> Res.drawable.stage2_bakery_halla
        "cookies_gf"    -> Res.drawable.stage2_bakery_cookies
        "yeast_cake"    -> Res.drawable.stage2_bakery_cake

        "bleach"         -> Res.drawable.stage2_clean_economica
        "gloves"         -> Res.drawable.stage2_clean_gloves
        "dish_soap"      -> Res.drawable.stage2_clean_dishsabon
        "window_cleaner" -> Res.drawable.stage2_clean_forwindows
        "garbage_bags"   -> Res.drawable.stage2_clean_garbagebag
        "scotch_pack"    -> Res.drawable.stage2_clean_scotch
        "clean_cups"     -> Res.drawable.stage2_clean_cups

        else -> null
    }}
