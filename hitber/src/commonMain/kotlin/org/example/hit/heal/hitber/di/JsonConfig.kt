package org.example.hit.heal.hitber.di


import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.example.hit.heal.hitber.data.model.*

val cogDataModule = SerializersModule {
    polymorphic(SixthQuestionType::class) {
        subclass(SixthQuestionType.SixthQuestionItem::class)
        subclass(SixthQuestionType.SixthQuestionImage::class)
    }

    polymorphic(SeventhQuestionType::class) {
        subclass(SeventhQuestionType.SeventhQuestionItem::class)
        subclass(SeventhQuestionType.SeventhQuestionImage::class)
    }

    polymorphic(TenthQuestionType::class) {
        subclass(TenthQuestionType.TenthQuestionItem::class)
        subclass(TenthQuestionType.TenthQuestionImage::class)
    }
}
val AppJson = Json {
    prettyPrint = true
    encodeDefaults = true
    ignoreUnknownKeys = true
    serializersModule = cogDataModule
    classDiscriminator = "type" // זה השם של השדה שיגיד איזה subtype זה, אפשר גם לשנות
}