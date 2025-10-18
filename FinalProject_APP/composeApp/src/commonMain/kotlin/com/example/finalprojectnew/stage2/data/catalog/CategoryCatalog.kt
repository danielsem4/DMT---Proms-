package com.example.finalprojectnew.stage2.data.catalog

import com.example.finalprojectnew.stage2.domain.model.CategoryKey
import com.example.finalprojectnew.stage2.presentation.category.generic.ProductUi
import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.*

object CategoryCatalog {

    fun titleOf(key: CategoryKey): String = when (key) {
        CategoryKey.MILK       -> "מוצרי חלב"
        CategoryKey.MEAT       -> "מוצרי בשר"
        CategoryKey.VEGETABLES -> "ירקות"
        CategoryKey.FRUITS     -> "פירות"
        CategoryKey.FROZEN     -> "קפואים"
        CategoryKey.DRY        -> "יבשים ותבלינים"
        CategoryKey.PASTRIES   -> "מאפים"
        CategoryKey.CLEANING   -> "ניקיון וחד''פ"
    }

    fun itemsOf(key: CategoryKey): List<ProductUi> = when (key) {
        CategoryKey.MILK -> listOf(
            ProductUi(id = "creamCheese28",   title = "גבינת שמנת 28%",              iconRes = Res.drawable.stage2_shamenet_cheese),
            ProductUi(id = "white500",        title = "גבינה לבנה 500 גרם",          iconRes = Res.drawable.white_cheese),
            ProductUi(id = "white250",        title = "גבינה לבנה 250 גרם",          iconRes = Res.drawable.white_cheese),
            ProductUi(id = "milk1p",          title = "חלב 1 ליטר 1%",               iconRes = Res.drawable.stage2milk1precent),
            ProductUi(id = "milk3p",          title = "חלב 1 ליטר 3%",               iconRes = Res.drawable.milk3precent),
            ProductUi(id = "sweetCream38",    title = "שמנת מתוקה 38%",               iconRes = Res.drawable.stage2_milk_sweet_shamenet),
            ProductUi(id = "bulgarian250",    title = "גבינה בולגרית 250 גרם",       iconRes = Res.drawable.bulgarian_cheese),
            ProductUi(id = "tsfatit5_250",    title = "גבינה צפתית מעודנת 5% - 250 גרם", iconRes = Res.drawable.stage2_tsfatit5precent, outOfStock = true),
            ProductUi(id = "yellowSliced250", title = "גבינה צהובה פרוסה 250 גרם",   iconRes = Res.drawable.yellow_cheese),
            ProductUi(id = "yellowGrated500", title = "גבינה צהובה מגורדת 500 גרם",  iconRes = Res.drawable.stage2_yellowcheese_megurad)
        )

        CategoryKey.MEAT -> listOf(
            ProductUi(id = "shnitzelKg",     title = "שניצלים (קילו)",        iconRes = Res.drawable.stage2_meat_shnitzel),
            ProductUi(id = "legs4",          title = "כרעיים (4 יחידות)",     iconRes = Res.drawable.stage2_meat_chickenshok),
            ProductUi(id = "breastKg",       title = "חזה עוף שלם (קילו)",    iconRes = Res.drawable.stage2_meat_chickenbreast),
            ProductUi(id = "pargit",         title = "פרגיות",                iconRes = Res.drawable.stage2_meat_pargit),
            ProductUi(id = "beefGround500",  title = "בשר טחון (500 גרם)",    iconRes = Res.drawable.stage2_meat_tahun)
        )

        CategoryKey.VEGETABLES -> listOf(
            ProductUi(id = "tomato",        title = "עגבניות (חצי קילו)", iconRes = Res.drawable.tomato),
            ProductUi(id = "cherryTomato",  title = "עגבניות שרי",        iconRes = Res.drawable.stage2_vegetables_cherrytomato),
            ProductUi(id = "cucumber",      title = "מלפפון (חצי קילו)",  iconRes = Res.drawable.cucumber),
            ProductUi(id = "broccoli",      title = "ברוקולי טרי",        iconRes = Res.drawable.broccoli, outOfStock = true),
            ProductUi(id = "carrot250",     title = "גזר (250 גרם)",      iconRes = Res.drawable.stage2_vegetables_carrot),
            ProductUi(id = "onion",         title = "בצלים",              iconRes = Res.drawable.stage2_vegetables_onion),
            ProductUi(id = "celery",        title = "סלרי",               iconRes = Res.drawable.stage2_vegetables_cellery),
            ProductUi(id = "lemon",         title = "לימון",              iconRes = Res.drawable.lemon)
        )

        CategoryKey.FRUITS -> listOf(
            ProductUi(id = "apricot",     title = "חבילת משמשים",                 iconRes = Res.drawable.stage2_fruits_mishmish),
            ProductUi(id = "banana",      title = "בננה",  subtitle = "100 גרם לבננה", iconRes = Res.drawable.stage2_fruits_banana),
            ProductUi(id = "nectarine",   title = "נקטרינה",                iconRes = Res.drawable.stage2_fruits_peach),
            ProductUi(id = "greenApple",  title = "תפוח סמיט",              iconRes = Res.drawable.stage2_fruits_greenapple),
            ProductUi(id = "redApple",    title = "תפוח פינק ליידי",        iconRes = Res.drawable.stage2_fruits_redapple),
            ProductUi(id = "melon",       title = "מלון",                   iconRes = Res.drawable.stage2_fruits_melon),
            ProductUi(id = "grapes",      title = "ענבים", subtitle = "500 גרם", iconRes = Res.drawable.stage2_fruits_grapes)
        )

        CategoryKey.FROZEN -> listOf(
            ProductUi(id = "broccoliFrozen", title = "ברוקולי קפוא",          iconRes = Res.drawable.stage2_frozen_brocoli),
            ProductUi(id = "peas",           title = "אפונה קפואה",           iconRes = Res.drawable.stage2_frozen_afuna),
            ProductUi(id = "peaCarrotMix",   title = "לקט אפונה וגזר",        iconRes = Res.drawable.stage2_frozen_carrotafuna),
            ProductUi(id = "cauliflower",    title = "כרובית קפואה",          iconRes = Res.drawable.stage2_frozen_kruvit),
            ProductUi(id = "greenBeans",     title = "שעועית ירוקה קפואה",    iconRes = Res.drawable.stage2_frozen_beans),
            ProductUi(id = "borekasCheese",  title = "בורקס גבינה",           iconRes = Res.drawable.stage2_frozen_borekascheese),
            ProductUi(id = "borekasPotato",  title = "בורקס תפוח אדמה",       iconRes = Res.drawable.stage2_frozen_borekastapha),
            ProductUi(id = "jahnun",         title = "ג׳חנון",                iconRes = Res.drawable.stage2_frozen_jahnun)
        )

        CategoryKey.DRY -> listOf(
            ProductUi(id = "canned_corn",    title = "תירס שימורים",    iconRes = Res.drawable.stage2_dry_cannedcorn),
            ProductUi(id = "pickles_vinegar", title = "מלפפון חמוץ בחומץ (250 גרם)", iconRes = Res.drawable.stage2_dry_pickle),
            ProductUi(id = "pickles_salt",   title = "מלפפון חמוץ במלח (250 גרם)",  iconRes = Res.drawable.stage2_dry_pickle),
            ProductUi(id = "tuna",           title = "טונה",                        iconRes = Res.drawable.stage2_dry_tuna),
            ProductUi(id = "baby_corn",      title = "תירס גמדי",                   iconRes = Res.drawable.stage2_dry_littlecorn),
            ProductUi(id = "sardines",       title = "סרדינים",                     iconRes = Res.drawable.stage2_dry_sardine),
            ProductUi(id = "olive_oil",      title = "שמן זית",                     iconRes = Res.drawable.olive_oil),
            ProductUi(id = "sunflower_oil",  title = "שמן חמניות",                  iconRes = Res.drawable.stage2_dry_oilhamania),
            ProductUi(id = "canola_oil",     title = "שמן קנולה",                   iconRes = Res.drawable.kanola_oil),
            ProductUi(id = "soy_oil",        title = "שמן סויה",                    iconRes = Res.drawable.stage2_dry_oilsoya),
            ProductUi(id = "coconut_oil",    title = "שמן קוקוס",                   iconRes = Res.drawable.stage2_dry_oilcoconut),
            ProductUi(id = "olives_canned",  title = "שימורי זיתים",               iconRes = Res.drawable.olives),
            ProductUi(id = "salt",           title = "מלח",                         iconRes = Res.drawable.salt),
            ProductUi(id = "sugar",          title = "סוכר",                        iconRes = Res.drawable.suger),
            ProductUi(id = "black_pepper",   title = "פלפל שחור",                   iconRes = Res.drawable.black_pepper),
            ProductUi(id = "flour",          title = "קמח",                         iconRes = Res.drawable.flour),
            ProductUi(id = "cacao",          title = "קקאו",                        iconRes = Res.drawable.cacao),
            ProductUi(id = "baking_powder",  title = "אבקת אפייה",                  iconRes = Res.drawable.baking_powder),
            ProductUi(id = "vanil",          title = "תמצית וניל",                  iconRes = Res.drawable.vanil),
            ProductUi(id = "dry_tomatopaste",title = "רסק עגבניות",                 iconRes = Res.drawable.stage2_dry_tomatopaste),
            ProductUi(id = "dry_hummus",     title = "חומוס גרגרים",                iconRes = Res.drawable.stage2_dry_hummus)
        )

        CategoryKey.PASTRIES -> listOf(
            ProductUi(id = "nosugar_bread", title = "לחם מקמח מלא דל סוכר", iconRes = Res.drawable.stage2_bakery_nosuger_bread),
            ProductUi(id = "regular_bread", title = "לחם מקמח רגיל",        iconRes = Res.drawable.stage2_bakery_bread),
            ProductUi(id = "challah",       title = "חלה",                   iconRes = Res.drawable.stage2_bakery_halla),
            ProductUi(id = "cookies_gf",    title = "עוגיות ללא גלוטן",      iconRes = Res.drawable.stage2_bakery_cookies),
            ProductUi(id = "yeast_cake",    title = "עוגת שמרים",            iconRes = Res.drawable.stage2_bakery_cake)
        )

        CategoryKey.CLEANING -> listOf(
            ProductUi(id = "bleach",         title = "אקונומיקה",      iconRes = Res.drawable.stage2_clean_economica),
            ProductUi(id = "gloves",         title = "כפפות",          iconRes = Res.drawable.stage2_clean_gloves),
            ProductUi(id = "dish_soap",      title = "סבון כלים",       iconRes = Res.drawable.stage2_clean_dishsabon),
            ProductUi(id = "window_cleaner", title = "מנקה חלונות",    iconRes = Res.drawable.stage2_clean_forwindows),
            ProductUi(id = "garbage_bags",   title = "שקיות זבל",       iconRes = Res.drawable.stage2_clean_garbagebag),
            ProductUi(id = "scotch_pack",    title = "חבילת סקוצ׳ים",   iconRes = Res.drawable.stage2_clean_scotch),
            ProductUi(id = "clean_cups",     title = "חבילת כוסות חד''פ",   iconRes = Res.drawable.stage2_clean_cups)
        )
    }
}

// // a flattened item used for simple text search across all categories
data class CatalogItem(
    val id: String, // the internal name of the item
    val title: String, // the name of the item on the screen
    val subtitle: String, // an additional small description (100 g)
    val iconRes: org.jetbrains.compose.resources.DrawableResource, // the image of the item
    val category: CategoryKey,
    val outOfStock: Boolean
)

// for searching - the items need to be a long list (=allItemsFlat)
fun CategoryCatalog.allItemsFlat(): List<CatalogItem> =
    CategoryKey.entries.flatMap { key ->
        itemsOf(key).map { p -> // for each item (p) - we create a search card with this fields.
            CatalogItem(
                id         = p.id,
                title      = p.title,
                subtitle   = p.subtitle,
                iconRes    = p.iconRes,
                category   = key,
                outOfStock = p.outOfStock
            )
        }
    }

fun CategoryCatalog.search(query: String): List<CatalogItem> {
    val q = query.trim().lowercase() // remove unnecessary spaces+ convert to lowercase
    if (q.isBlank()) return emptyList() // if not written anything, nothing is returned (an empty list)
    return allItemsFlat().filter {
        it.title.lowercase().contains(q) || it.subtitle.lowercase().contains(q) // only keep those whose title or subtitle contains the text we were looking for
    }
}
