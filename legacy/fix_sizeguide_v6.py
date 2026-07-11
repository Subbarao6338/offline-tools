import re

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'r') as f:
    content = f.read()

# Replace variables with correctly formatted lists
def wrap_list(var_name, data_regex):
    global content
    match = re.search(f'val {var_name} = listOf\((.*?)\)\n\s+val', content, re.DOTALL)
    if not match:
        # Try finding until end of object
        match = re.search(f'val {var_name} = listOf\((.*?)\)\n}}', content, re.DOTALL)

    if match:
        inner = match.group(1).strip()
        # Remove trailing commas and extra parens
        if inner.endswith(')'):
             inner = inner[:-1].strip()
        if inner.endswith(')'):
             inner = inner[:-1].strip()

        # Re-wrap
        content = content.replace(match.group(0), f'val {var_name} = listOf(\n        {inner}\n    )\n\n    val' if 'val' in match.group(0) else f'val {var_name} = listOf(\n        {inner}\n    )\n}}')

# This is too complex to regex perfectly given the nested structures.
# Let's just do a simple replacement for the problematic endings.

content = content.replace('            )\n    )\n\n    val', '            )\n        )\n    )\n\n    val')
content = content.replace('            )\n    )\n\n}', '            )\n        )\n    )\n}')

# Wait, looking at the previous cat output, some were closed with just one paren
# Let's just manually fix the whole file content in a stable way.

new_content = """package omni.toolbox.model

data class SizeRow(val values: List<String>)
data class SizeChart(val title: String, val columns: List<String>, val rows: List<SizeRow>)

object SizeGuideData {
    val womenCategories = listOf(
        SizeChart(
            "Dresses & Suits",
            listOf("US", "UK", "EU", "IT", "FR", "JP", "AU", "CN"),
            listOf(
                SizeRow(listOf("0", "4", "32", "36", "34", "5", "4", "XXXS")),
                SizeRow(listOf("2", "6", "34", "38", "36", "7", "6", "XXS")),
                SizeRow(listOf("4", "8", "36", "40", "38", "9", "8", "XS")),
                SizeRow(listOf("6", "10", "38", "42", "40", "11", "10", "S")),
                SizeRow(listOf("8", "12", "40", "44", "42", "13", "12", "M")),
                SizeRow(listOf("10", "14", "42", "46", "44", "15", "14", "L")),
                SizeRow(listOf("12", "16", "44", "48", "46", "17", "16", "XL")),
                SizeRow(listOf("14", "18", "46", "50", "48", "19", "18", "XXL")),
                SizeRow(listOf("16", "20", "48", "52", "50", "21", "20", "XXXL"))
            )
        )
    )

    val menCategories = listOf(
        SizeChart(
            "Suits & Coats",
            listOf("US/UK", "EU/IT", "JP", "International", "AU", "KR"),
            listOf(
                SizeRow(listOf("34", "44", "S", "XS", "34", "90")),
                SizeRow(listOf("36", "46", "M", "S", "36", "95")),
                SizeRow(listOf("38", "48", "L", "M", "38", "100")),
                SizeRow(listOf("40", "50", "LL", "L", "40", "105")),
                SizeRow(listOf("42", "52", "3L", "XL", "42", "110")),
                SizeRow(listOf("44", "54", "4L", "XXL", "44", "115")),
                SizeRow(listOf("46", "56", "5L", "3XL", "46", "120"))
            )
        )
    )

    val kidsCategories = listOf(
        SizeChart(
            "Baby (0-24 Months)",
            listOf("Age", "Height (in)", "Weight (lb)", "EU (cm)", "JP"),
            listOf(
                SizeRow(listOf("0-3m", "19-23", "7-12", "50-60", "50-60")),
                SizeRow(listOf("3-6m", "24-26", "13-17", "60-70", "60-70")),
                SizeRow(listOf("6-9m", "27-28", "18-21", "70-75", "70-75")),
                SizeRow(listOf("12m", "29-30", "22-25", "80", "80")),
                SizeRow(listOf("18m", "31-32", "26-29", "86", "80-90")),
                SizeRow(listOf("24m", "33-35", "30-33", "92", "90"))
            )
        )
    )

    val footwearCategories = listOf(
        SizeChart(
            "Women's Shoes",
            listOf("US", "UK", "EU", "JP (cm)", "AU", "CN"),
            listOf(
                SizeRow(listOf("5", "3", "35.5", "21", "5", "35")),
                SizeRow(listOf("6", "4", "37", "22", "6", "36")),
                SizeRow(listOf("7", "5", "38", "23", "7", "37")),
                SizeRow(listOf("8", "6", "39", "24", "8", "38")),
                SizeRow(listOf("9", "7", "40.5", "25", "9", "39")),
                SizeRow(listOf("10", "8", "42", "26", "10", "40"))
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
        )
    )

    val indianCategories = listOf(
        SizeChart(
            "Traditional Indian (Women)",
            listOf("Style", "Dimensions", "Encyclopedia Entry"),
            listOf(
                SizeRow(listOf("Saree", "5.5 - 9 yards", "An unstitched drape consisting of 6-9 yards of fabric. Worn with a blouse and petticoat. Features diverse regional draping styles.")),
                SizeRow(listOf("Salwar Kameez", "Varies", "A long tunic (Kameez) paired with loose-fitting trousers (Salwar) and a dupatta (scarf). Popular across South Asia.")),
                SizeRow(listOf("Lehenga Choli", "Ankle Length", "A three-piece ensemble: a long pleated skirt (Lehenga), a fitted blouse (Choli), and a dupatta.")),
                SizeRow(listOf("Anarkali", "Floor Length", "A long, frock-style top that flares from the waist, paired with slim-fit leggings (Churidar). Named after a legendary courtesan.")),
                SizeRow(listOf("Gharara", "Ankle Length", "Wide-legged trousers that are gathered and ruched at the knee, creating a dramatic flare. Traditional Lucknawi attire.")),
                SizeRow(listOf("Pheran", "Knee Length", "A loose, embroidered tunic worn in Kashmir, traditionally made of wool to protect against the cold."))
            )
        )
    )

    val worldCategories = listOf(
        SizeChart(
            "World Heritage Styles",
            listOf("Region", "Garment", "Encyclopedia Entry"),
            listOf(
                SizeRow(listOf("Japan", "Kimono", "A T-shaped, straight-lined robe with wide sleeves. It is wrapped around the body, left over right, and secured by an obi sash. Historically made of silk.")),
                SizeRow(listOf("Scotland", "Kilt", "A knee-length non-bifurcated skirt with pleats at the rear, originating in the Highlands. Worn as part of Highland dress with specific clan tartans.")),
                SizeRow(listOf("Korea", "Hanbok", "Vibrant traditional attire with simple lines and no pockets. Features the jeogori (jacket) and chima (wrap-around skirt) for a graceful silhouette.")),
                SizeRow(listOf("Mexico", "Huipil", "A loose-fitting tunic, usually made of two or three rectangular pieces of fabric joined together. Often decorated with elaborate embroidery and lace.")),
                SizeRow(listOf("West Africa", "Dashiki", "A colorful garment that covers the top half of the body. Often features intricate embroidery around the neck and sleeves.")),
                SizeRow(listOf("Vietnam", "Ao Dai", "A long split tunic worn over silk trousers. Symbolizes Vietnamese elegance and is common for formal occasions.")),
                SizeRow(listOf("China", "Qipao/Cheongsam", "A body-hugging one-piece dress with a high neck and side slits. Originated in 1920s Shanghai as a modern take on Manchu dress.")),
                SizeRow(listOf("Germany", "Dirndl", "A traditional Alpine dress consisting of a bodice, blouse, full skirt, and apron. The bow on the apron indicates marital status."))
            )
        )
    )

    val tribalCategories = listOf(
        SizeChart(
            "Tribal & Indigenous",
            listOf("Group", "Garment", "Encyclopedia Entry"),
            listOf(
                SizeRow(listOf("Maasai", "Shuka", "Brightly colored (usually red) wrapped cloths worn by the Maasai people of East Africa. Highly durable and symbolizes Maasai culture.")),
                SizeRow(listOf("Quechua", "Poncho", "An outer garment designed to keep the body warm, made of wool or alpaca. Common in the Andes, often featuring geometric patterns.")),
                SizeRow(listOf("Sami", "Gakti", "Traditional clothing with distinct color bands and embroidery. Design varies by region and indicates the wearer's origin and status.")),
                SizeRow(listOf("Inuit", "Parka", "A heavy, insulated coat with a fur-lined hood, originally made from seal or caribou skin to survive Arctic conditions.")),
                SizeRow(listOf("Zulu", "Isidwaba", "A traditional leather skirt made of cowhide, worn by married women in Zulu culture. Symbolizes respect and marriage.")),
                SizeRow(listOf("Maori", "Korowai", "A finely woven cloak decorated with tassels (hukahuka). It is a symbol of prestige and is worn during important ceremonies.")),
                SizeRow(listOf("Navajo", "Velvet Shirt", "Traditionally worn by Navajo men and women, often in rich colors like turquoise or burgundy, paired with silver jewelry."))
            )
        )
    )

    val modernCategories = listOf(
        SizeChart(
            "Modern Fashion Aesthetics",
            listOf("Aesthetic", "Encyclopedia Entry"),
            listOf(
                SizeRow(listOf("Minimalist", "Neutral colors, clean lines, and quality basics. Focuses on the 'less is more' philosophy, prioritizing functionality and form.")),
                SizeRow(listOf("Bohemian", "Flowy fabrics, earthy tones, and ethnic prints. Inspired by free-spirited lifestyles, often featuring embroidery, fringe, and layers.")),
                SizeRow(listOf("Streetwear", "Oversized fits, sneakers, and graphic tees. Originated from skate and surf culture, now a global fashion phenomenon influenced by hip-hop.")),
                SizeRow(listOf("Preppy", "Polo shirts, blazers, and pleated skirts. Inspired by Ivy League style, emphasizing a neat, groomed, and classic aesthetic.")),
                SizeRow(listOf("Grunge", "Flannel shirts, distressed denim, and combat boots. A subculture-driven style from the 90s, characterized by a 'messy' and unkempt look.")),
                SizeRow(listOf("Dark Academia", "Tweed blazers, turtlenecks, and Oxford shoes. Centers around higher education, writing, poetry, and classical Greek and Gothic themes.")),
                SizeRow(listOf("Cottagecore", "Floral prints, puff sleeves, and linen fabrics. An aesthetic that idealizes rural life and nature, emphasizing simplicity and traditional crafts.")),
                SizeRow(listOf("Cyberpunk", "Tech-wear, neon accents, and futuristic materials. A sci-fi inspired look that combines high-tech gear with urban street style."))
            )
        )
    )

    val materialCategories = listOf(
        SizeChart(
            "Common Textiles",
            listOf("Material", "Origin", "Characteristics"),
            listOf(
                SizeRow(listOf("Cotton", "Natural (Plant)", "Breathable, soft, and durable. Absorbs moisture well, making it ideal for warm weather.")),
                SizeRow(listOf("Silk", "Natural (Insect)", "Luxurious, smooth, and has a natural sheen. Known for its strength and temperature-regulating properties.")),
                SizeRow(listOf("Wool", "Natural (Animal)", "Warm, resilient, and moisture-wicking. Naturally flame-resistant and provides excellent insulation.")),
                SizeRow(listOf("Linen", "Natural (Plant)", "Very strong, absorbent, and dries faster than cotton. Has a crisp texture and becomes softer with wash.")),
                SizeRow(listOf("Polyester", "Synthetic", "Durable, resistant to shrinking and wrinkling. Dries quickly but is less breathable than natural fibers.")),
                SizeRow(listOf("Nylon", "Synthetic", "Exceptional strength and elasticity. Used in activewear, hosiery, and outdoor gear.")),
                SizeRow(listOf("Rayon/Viscose", "Semi-Synthetic", "Made from wood pulp. Mimics the feel of silk, cotton, or wool. Highly absorbent and soft.")),
                SizeRow(listOf("Leather", "Natural (Animal)", "Durable and flexible material created by tanning animal rawhide. Develops a unique patina over time."))
            )
        )
    )

    val globalConversion = listOf(
        SizeChart(
            "Global Dress Size Mapping",
            listOf("US", "UK", "EU", "IT", "FR", "JP", "AU", "CN", "KR"),
            listOf(
                SizeRow(listOf("0", "4", "32", "36", "34", "5", "4", "XXXS", "33")),
                SizeRow(listOf("2", "6", "34", "38", "36", "7", "6", "XXS", "44")),
                SizeRow(listOf("4", "8", "36", "40", "38", "9", "8", "XS", "55")),
                SizeRow(listOf("6", "10", "38", "42", "40", "11", "10", "S", "66")),
                SizeRow(listOf("8", "12", "40", "44", "42", "13", "12", "M", "77")),
                SizeRow(listOf("10", "14", "42", "46", "44", "15", "14", "L", "88")),
                SizeRow(listOf("12", "16", "44", "48", "46", "17", "16", "XL", "99"))
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
        )
    )
}
"""

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'w') as f:
    f.write(new_content)
