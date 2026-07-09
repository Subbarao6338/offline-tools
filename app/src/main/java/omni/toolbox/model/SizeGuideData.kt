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
            "Plus Size",
            listOf("US", "UK", "EU", "IT", "JP"),
            listOf(
                SizeRow(listOf("16W", "20", "48", "52", "21")),
                SizeRow(listOf("18W", "22", "50", "54", "23")),
                SizeRow(listOf("20W", "24", "52", "56", "25")),
                SizeRow(listOf("22W", "26", "54", "58", "27")),
                SizeRow(listOf("24W", "28", "56", "60", "29")),
                SizeRow(listOf("26W", "30", "58", "62", "31"))
            )
        ),
        SizeChart(
            "Petite (Height < 5'4\")",
            listOf("US Petite", "US Standard", "Bust (in)", "Waist (in)", "Hips (in)"),
            listOf(
                SizeRow(listOf("0P", "0", "32", "24", "34.5")),
                SizeRow(listOf("2P", "2", "33", "25", "35.5")),
                SizeRow(listOf("4P", "4", "34", "26", "36.5")),
                SizeRow(listOf("6P", "6", "35", "27", "37.5")),
                SizeRow(listOf("8P", "8", "36", "28", "38.5")),
                SizeRow(listOf("10P", "10", "37.5", "29.5", "40")),
                SizeRow(listOf("12P", "12", "39", "31", "41.5"))
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
            "Big & Tall",
            listOf("US Size", "Chest (in)", "Waist (in)", "Neck (in)"),
            listOf(
                SizeRow(listOf("1XB", "46-48", "42-44", "17-17.5")),
                SizeRow(listOf("2XB", "50-52", "46-48", "18-18.5")),
                SizeRow(listOf("3XB", "54-56", "50-52", "19-19.5")),
                SizeRow(listOf("4XB", "58-60", "54-56", "20-20.5")),
                SizeRow(listOf("LT", "42-44", "36-38", "16-16.5")),
                SizeRow(listOf("XLT", "46-48", "40-42", "17-17.5")),
                SizeRow(listOf("2XLT", "50-52", "44-46", "18-18.5"))
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

    val footwearCategories = listOf(
        SizeChart(
            "Women's Shoes",
            listOf("US", "UK", "EU", "JP (cm)"),
            listOf(
                SizeRow(listOf("5", "3", "35.5", "21")),
                SizeRow(listOf("6", "4", "37", "22")),
                SizeRow(listOf("7", "5", "38", "23")),
                SizeRow(listOf("8", "6", "39", "24")),
                SizeRow(listOf("9", "7", "40.5", "25")),
                SizeRow(listOf("10", "8", "42", "26"))
            )
        ),
        SizeChart(
            "Men's Shoes",
            listOf("US", "UK", "EU", "JP (cm)"),
            listOf(
                SizeRow(listOf("7", "6", "40", "25")),
                SizeRow(listOf("8", "7", "41", "26")),
                SizeRow(listOf("9", "8", "42", "27")),
                SizeRow(listOf("10", "9", "43", "28")),
                SizeRow(listOf("11", "10", "44", "29")),
                SizeRow(listOf("12", "11", "45", "30"))
            )
        ),
        SizeChart(
            "Kids' Shoes",
            listOf("US", "UK", "EU", "Length (cm)"),
            listOf(
                SizeRow(listOf("10C", "9.5", "27", "17.0")),
                SizeRow(listOf("11C", "10.5", "28", "17.8")),
                SizeRow(listOf("12C", "11.5", "30", "18.7")),
                SizeRow(listOf("13C", "12.5", "31", "19.5")),
                SizeRow(listOf("1Y", "13.5", "32", "20.4")),
                SizeRow(listOf("2Y", "1.5", "33", "21.2")),
                SizeRow(listOf("3Y", "2.5", "35", "22.0"))
            )
        )
    )

    val accessoriesCategories = listOf(
        SizeChart(
            "Rings",
            listOf("US", "UK", "EU", "Diam (mm)", "Circ (mm)"),
            listOf(
                SizeRow(listOf("3", "F", "44", "14.1", "44.2")),
                SizeRow(listOf("4", "H", "47", "14.9", "46.8")),
                SizeRow(listOf("5", "J 1/2", "49", "15.7", "49.3")),
                SizeRow(listOf("6", "M", "52", "16.5", "51.9")),
                SizeRow(listOf("7", "O", "54", "17.3", "54.4")),
                SizeRow(listOf("8", "Q", "57", "18.1", "57.0")),
                SizeRow(listOf("9", "S", "59", "19.0", "59.5")),
                SizeRow(listOf("10", "T 1/2", "62", "19.8", "62.1")),
                SizeRow(listOf("11", "V 1/2", "65", "20.6", "64.6")),
                SizeRow(listOf("12", "Y", "67", "21.4", "67.2"))
            )
        ),
        SizeChart(
            "Hats & Caps",
            listOf("Size", "Circ (in)", "Circ (cm)"),
            listOf(
                SizeRow(listOf("S", "21 1/4 - 21 5/8", "54-55")),
                SizeRow(listOf("M", "22 - 22 3/8", "56-57")),
                SizeRow(listOf("L", "22 3/4 - 23 1/8", "58-59")),
                SizeRow(listOf("XL", "23 1/2 - 23 7/8", "60-61")),
                SizeRow(listOf("XXL", "24 1/4 - 24 5/8", "62-63"))
            )
        ),
        SizeChart(
            "Belts",
            listOf("Size", "Waist (in)", "Waist (cm)"),
            listOf(
                SizeRow(listOf("XS", "26-28", "66-71")),
                SizeRow(listOf("S", "30-32", "76-81")),
                SizeRow(listOf("M", "34-36", "86-91")),
                SizeRow(listOf("L", "38-40", "96-101")),
                SizeRow(listOf("XL", "42-44", "106-111"))
            )
        ),
        SizeChart(
            "Gloves",
            listOf("Size", "Hand Circ (in)", "Hand Circ (cm)"),
            listOf(
                SizeRow(listOf("S", "7 - 7.5", "18-19")),
                SizeRow(listOf("M", "8 - 8.5", "20-22")),
                SizeRow(listOf("L", "9 - 9.5", "23-24")),
                SizeRow(listOf("XL", "10 - 10.5", "25-27"))
            )
        )
    )

    val culturalCategories = listOf(
        SizeChart(
            "Traditional Indian (Women)",
            listOf("Style", "Length", "Description"),
            listOf(
                SizeRow(listOf("Saree", "5.5 - 9 yards", "Unstitched drape, worn with blouse & petticoat.")),
                SizeRow(listOf("Salwar Kameez", "Varies", "Tunic (Kameez) paired with trousers (Salwar).")),
                SizeRow(listOf("Lehenga Choli", "Ankle Length", "Long skirt (Lehenga) with cropped blouse (Choli).")),
                SizeRow(listOf("Anarkali", "Floor Length", "Frock-style top with slim fitted bottoms."))
            )
        ),
        SizeChart(
            "Traditional Indian (Men)",
            listOf("Style", "Occasion", "Description"),
            listOf(
                SizeRow(listOf("Kurta Pajama", "Casual/Formal", "Knee-length tunic with loose trousers.")),
                SizeRow(listOf("Sherwani", "Wedding/Gala", "Long coat-like garment, heavy embroidery.")),
                SizeRow(listOf("Dhoti", "Ceremonial", "Unstitched rectangular cloth wrapped around waist.")),
                SizeRow(listOf("Nehru Jacket", "Semi-Formal", "Sleeveless waistcoat with mandarin collar."))
            )
        ),
        SizeChart(
            "World Heritage Styles",
            listOf("Region", "Garment", "Significance"),
            listOf(
                SizeRow(listOf("Japan", "Kimono", "T-shaped, straight-lined robe with wide sleeves.")),
                SizeRow(listOf("Scotland", "Kilt", "Knee-length non-bifurcated skirt, tartan pattern.")),
                SizeRow(listOf("Mexico", "Huipil", "Loose-fitting tunic, usually made of 2-3 webbings.")),
                SizeRow(listOf("West Africa", "Dashiki", "Colorful garment covering the top half of body.")),
                SizeRow(listOf("Korea", "Hanbok", "Traditional attire with vibrant colors, simple lines."))
            )
        ),
        SizeChart(
            "Tribal & Indigenous",
            listOf("Group", "Style", "Feature"),
            listOf(
                SizeRow(listOf("Maasai", "Shuka", "Brightly colored (usually red) wrapped cloths.")),
                SizeRow(listOf("Quechua", "Poncho", "Outer garment designed to keep the body warm.")),
                SizeRow(listOf("Sami", "Gakti", "Traditional clothing with distinct color bands.")),
                SizeRow(listOf("Inuit", "Parka", "Heavy coat with fur-lined hood for extreme cold."))
            )
        )
    )

    val styleGuides = listOf(
        SizeChart(
            "Body Types (Women)",
            listOf("Type", "Recommended Styles"),
            listOf(
                SizeRow(listOf("Hourglass", "Wrap dresses, V-necks, High-waisted bottoms.")),
                SizeRow(listOf("Pear", "A-line skirts, Boat necks, Statement necklaces.")),
                SizeRow(listOf("Apple", "Empire waists, Shift dresses, Straight-leg pants.")),
                SizeRow(listOf("Rectangle", "Peplum tops, Ruffles, Belts to define waist.")),
                SizeRow(listOf("Inverted Triangle", "Wide-leg pants, Halter necks, Full skirts."))
            )
        ),
        SizeChart(
            "Modern Fashion Aesthetics",
            listOf("Aesthetic", "Core Elements"),
            listOf(
                SizeRow(listOf("Minimalist", "Neutral colors, Clean lines, Quality basics.")),
                SizeRow(listOf("Bohemian", "Flowy fabrics, Earthy tones, Ethnic prints.")),
                SizeRow(listOf("Streetwear", "Oversized fits, Sneakers, Graphic tees.")),
                SizeRow(listOf("Preppy", "Polo shirts, Blazers, Pleated skirts.")),
                SizeRow(listOf("Grunge", "Flannel shirts, Distressed denim, Combat boots."))
            )
        ),
        SizeChart(
            "Fabric Care Guide",
            listOf("Fabric", "Wash", "Iron"),
            listOf(
                SizeRow(listOf("Cotton", "Machine wash warm", "High heat, steam")),
                SizeRow(listOf("Silk", "Hand wash cold", "Low heat, no steam")),
                SizeRow(listOf("Wool", "Dry clean / Hand wash", "Low heat, press cloth")),
                SizeRow(listOf("Linen", "Machine wash cold", "High heat, while damp")),
                SizeRow(listOf("Polyester", "Machine wash warm", "Low heat"))
            )
        )
    )

    val innerwearCategories = listOf(
        SizeChart(
            "Women Bras (Band)",
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
