package com.example.minutanutricionalapp2.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun todayKey(): String = LocalDate.now().format(DateTimeFormatter.ISO_DATE)