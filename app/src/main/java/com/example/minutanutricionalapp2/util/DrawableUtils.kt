package com.example.minutanutricionalapp2.util

import android.content.Context
import java.text.Normalizer
import java.util.Locale

private fun String.slug(): String {
    val noExt = substringBeforeLast(".")
    val norm = Normalizer.normalize(noExt, Normalizer.Form.NFD)
        .replace("\\p{M}+".toRegex(), "")              // quita tildes
        .lowercase(Locale.ROOT)
        .replace("[^a-z0-9]+".toRegex(), "_")          // todo lo no alfanumérico → _
        .trim('_')
    return norm
}

/**
 * Busca un drawable por nombre, tolerando extensión, tildes y espacios.
 * Si no lo encuentra, intenta con el título como fallback.
 */
fun drawableIdByName(context: Context, rawName: String?, fallbackTitle: String? = null): Int {
    if (rawName.isNullOrBlank() && fallbackTitle.isNullOrBlank()) return 0
    val pck = context.packageName
    val res = context.resources

    val candidates = buildList {
        if (!rawName.isNullOrBlank()) {
            add(rawName)
            add(rawName.substringBeforeLast('.'))
            add(rawName.slug())
        }
        if (!fallbackTitle.isNullOrBlank()) {
            add(fallbackTitle!!)
            add(fallbackTitle.substringBeforeLast('.'))
            add(fallbackTitle.slug())
        }
    }

    for (name in candidates) {
        val id = res.getIdentifier(name, "drawable", pck)
        if (id != 0) return id
    }
    return 0
}
