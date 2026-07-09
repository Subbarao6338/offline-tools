package omni.toolbox.model

data class SizeRow(val values: List<String>)
data class SizeChart(val title: String, val columns: List<String>, val rows: List<SizeRow>)

object SizeGuideData {
    val womenCategories = listOf(
        SizeChart(
            "Dresses & Suits",
            listOf("US", "UK", "EU", "IT", "FR", "JP"),
            listOf(
                SizeRow(listOf("0", "4", "32", "36", "34", "5")),
                SizeRow(listOf("2", "6", "34", "38", "36", "7")),
                SizeRow(listOf("4", "8", "36", "40", "38", "9")),
                SizeRow(listOf("6", "10", "38", "42", "40", "11")),
                SizeRow(listOf("8", "12", "40", "44", "42", "13")),
                SizeRow(listOf("10", "14", "42", "46", "44", "15")),
                SizeRow(listOf("12", "16", "44", "48", "46", "17")),
                SizeRow(listOf("14", "18", "46", "50", "48", "19")),
                SizeRow(listOf("16", "20", "48", "52", "50", "21"))
            )
        ),
        SizeChart(
            "Tops & Sweaters",
            listOf("US", "UK", "EU", "IT", "JP"),
            listOf(
                SizeRow(listOf("XXS", "2", "30", "34", "3")),
                SizeRow(listOf("XS", "4", "32", "36", "5")),
                SizeRow(listOf("S", "6", "34", "38", "7")),
                SizeRow(listOf("M", "8", "36", "40", "9")),
                SizeRow(listOf("L", "10", "38", "42", "11")),
                SizeRow(listOf("XL", "12", "40", "44", "13")),
                SizeRow(listOf("XXL", "14", "42", "46", "15"))
            )
        ),
        SizeChart(
            "Pants & Skirts",
            listOf("US", "UK", "EU", "IT", "JP"),
            listOf(
                SizeRow(listOf("0", "4", "32", "36", "5")),
                SizeRow(listOf("2", "6", "34", "38", "7")),
                SizeRow(listOf("4", "8", "36", "40", "9")),
                SizeRow(listOf("6", "10", "38", "42", "11")),
                SizeRow(listOf("8", "12", "40", "44", "13")),
                SizeRow(listOf("10", "14", "42", "46", "15")),
                SizeRow(listOf("12", "16", "44", "48", "17"))
            )
        ),
        SizeChart(
            "Jeans (Waist)",
            listOf("Size", "Waist (in)", "Waist (cm)"),
            listOf(
                SizeRow(listOf("24", "24", "61")),
                SizeRow(listOf("25", "25", "63.5")),
                SizeRow(listOf("26", "26", "66")),
                SizeRow(listOf("27", "27", "68.5")),
                SizeRow(listOf("28", "28", "71")),
                SizeRow(listOf("29", "29", "73.5")),
                SizeRow(listOf("30", "30", "76")),
                SizeRow(listOf("31", "31", "78.5")),
                SizeRow(listOf("32", "32", "81"))
            )
        ),
        SizeChart(
            "Shoes",
            listOf("US", "UK", "EU", "JP (cm)"),
            listOf(
                SizeRow(listOf("5", "3", "35.5", "21")),
                SizeRow(listOf("5.5", "3.5", "36", "21.5")),
                SizeRow(listOf("6", "4", "37", "22")),
                SizeRow(listOf("6.5", "4.5", "37.5", "22.5")),
                SizeRow(listOf("7", "5", "38", "23")),
                SizeRow(listOf("7.5", "5.5", "38.5", "23.5")),
                SizeRow(listOf("8", "6", "39", "24")),
                SizeRow(listOf("8.5", "6.5", "40", "24.5")),
                SizeRow(listOf("9", "7", "40.5", "25")),
                SizeRow(listOf("9.5", "7.5", "41", "25.5")),
                SizeRow(listOf("10", "8", "42", "26"))
            )
        )
    )

    val menCategories = listOf(
        SizeChart(
            "Suits & Coats",
            listOf("US/UK", "EU/IT", "JP", "International"),
            listOf(
                SizeRow(listOf("34", "44", "S", "XS")),
                SizeRow(listOf("36", "46", "M", "S")),
                SizeRow(listOf("38", "48", "L", "M")),
                SizeRow(listOf("40", "50", "LL", "L")),
                SizeRow(listOf("42", "52", "3L", "XL")),
                SizeRow(listOf("44", "54", "4L", "XXL")),
                SizeRow(listOf("46", "56", "5L", "3XL"))
            )
        ),
        SizeChart(
            "Dress Shirts",
            listOf("US/UK (in)", "EU/IT (cm)", "JP"),
            listOf(
                SizeRow(listOf("14", "36", "S")),
                SizeRow(listOf("14.5", "37", "S")),
                SizeRow(listOf("15", "38", "M")),
                SizeRow(listOf("15.5", "39", "M")),
                SizeRow(listOf("16", "41", "L")),
                SizeRow(listOf("16.5", "42", "L")),
                SizeRow(listOf("17", "43", "XL")),
                SizeRow(listOf("17.5", "44", "XL")),
                SizeRow(listOf("18", "45", "XXL"))
            )
        ),
        SizeChart(
            "Casual Shirts & T-Shirts",
            listOf("Size", "Chest (in)", "Chest (cm)"),
            listOf(
                SizeRow(listOf("XS", "32-34", "81-86")),
                SizeRow(listOf("S", "35-37", "89-94")),
                SizeRow(listOf("M", "38-40", "96-102")),
                SizeRow(listOf("L", "41-43", "104-109")),
                SizeRow(listOf("XL", "44-46", "112-117")),
                SizeRow(listOf("XXL", "47-49", "119-124")),
                SizeRow(listOf("3XL", "50-52", "127-132"))
            )
        ),
        SizeChart(
            "Pants & Jeans (Waist)",
            listOf("US/UK (in)", "EU (cm)", "IT", "FR"),
            listOf(
                SizeRow(listOf("28", "71", "44", "36")),
                SizeRow(listOf("30", "76", "46", "38")),
                SizeRow(listOf("32", "81", "48", "40")),
                SizeRow(listOf("34", "86", "50", "42")),
                SizeRow(listOf("36", "91", "52", "44")),
                SizeRow(listOf("38", "96", "54", "46")),
                SizeRow(listOf("40", "101", "56", "48"))
            )
        ),
        SizeChart(
            "Shoes",
            listOf("US", "UK", "EU", "JP (cm)"),
            listOf(
                SizeRow(listOf("7", "6", "40", "25")),
                SizeRow(listOf("7.5", "6.5", "40.5", "25.5")),
                SizeRow(listOf("8", "7", "41", "26")),
                SizeRow(listOf("8.5", "7.5", "41.5", "26.5")),
                SizeRow(listOf("9", "8", "42", "27")),
                SizeRow(listOf("9.5", "8.5", "42.5", "27.5")),
                SizeRow(listOf("10", "9", "43", "28")),
                SizeRow(listOf("10.5", "9.5", "43.5", "28.5")),
                SizeRow(listOf("11", "10", "44", "29")),
                SizeRow(listOf("12", "11", "45", "30"))
            )
        )
    )

    val kidsCategories = listOf(
        SizeChart(
            "Baby (0-24 Months)",
            listOf("Age", "Height (in)", "Weight (lb)", "EU (cm)"),
            listOf(
                SizeRow(listOf("0-3m", "19-23", "7-12", "50-60")),
                SizeRow(listOf("3-6m", "24-26", "13-17", "60-70")),
                SizeRow(listOf("6-9m", "27-28", "18-21", "70-75")),
                SizeRow(listOf("12m", "29-30", "22-25", "80")),
                SizeRow(listOf("18m", "31-32", "26-29", "86")),
                SizeRow(listOf("24m", "33-35", "30-33", "92"))
            )
        ),
        SizeChart(
            "Toddler (2T-5T)",
            listOf("Size", "Age", "Height (in)", "Height (cm)"),
            listOf(
                SizeRow(listOf("2T", "2y", "33-35", "84-89")),
                SizeRow(listOf("3T", "3y", "36-38", "91-97")),
                SizeRow(listOf("4T", "4y", "39-41", "99-104")),
                SizeRow(listOf("5T", "5y", "42-44", "107-112"))
            )
        ),
        SizeChart(
            "Big Kids (6-14 Years)",
            listOf("US Size", "Age", "Height (in)", "Height (cm)"),
            listOf(
                SizeRow(listOf("6", "6y", "45-47", "114-119")),
                SizeRow(listOf("7", "7y", "48-49", "122-124")),
                SizeRow(listOf("8", "8y", "50-52", "127-132")),
                SizeRow(listOf("10", "10y", "53-55", "135-140")),
                SizeRow(listOf("12", "12y", "56-58", "142-147")),
                SizeRow(listOf("14", "14y", "59-61", "150-155"))
            )
        )
    )

    val innerwearCategories = listOf(
        SizeChart(
            "Women Bras (Band Size)",
            listOf("US/UK", "EU", "IT", "FR", "JP"),
            listOf(
                SizeRow(listOf("30", "65", "0", "80", "65")),
                SizeRow(listOf("32", "70", "1", "85", "70")),
                SizeRow(listOf("34", "75", "2", "90", "75")),
                SizeRow(listOf("36", "80", "3", "95", "80")),
                SizeRow(listOf("38", "85", "4", "100", "85")),
                SizeRow(listOf("40", "90", "5", "105", "90")),
                SizeRow(listOf("42", "95", "6", "110", "95"))
            )
        ),
        SizeChart(
            "Women Panties",
            listOf("Size", "US", "UK", "EU", "Hip (in)"),
            listOf(
                SizeRow(listOf("XS", "4", "6", "34", "34-35")),
                SizeRow(listOf("S", "5", "8", "36", "36-37")),
                SizeRow(listOf("M", "6", "10", "38", "38-39")),
                SizeRow(listOf("L", "7", "12", "40", "40-41")),
                SizeRow(listOf("XL", "8", "14", "42", "42-43")),
                SizeRow(listOf("XXL", "9", "16", "44", "45-46"))
            )
        ),
        SizeChart(
            "Men Underwear",
            listOf("Size", "Waist (in)", "Waist (cm)"),
            listOf(
                SizeRow(listOf("S", "28-30", "71-76")),
                SizeRow(listOf("M", "32-34", "81-86")),
                SizeRow(listOf("L", "36-38", "91-97")),
                SizeRow(listOf("XL", "40-42", "102-107")),
                SizeRow(listOf("2XL", "44-46", "112-117")),
                SizeRow(listOf("3XL", "48-50", "122-127"))
            )
        )
    )
}
