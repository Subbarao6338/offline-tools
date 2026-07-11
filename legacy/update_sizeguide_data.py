import re

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'r') as f:
    content = f.read()

# Update worldCategories with more detailed descriptions
new_world_rows = """
                SizeRow(listOf("Japan", "Kimono", "A T-shaped, straight-lined robe with wide sleeves. It is wrapped around the body, left over right, and secured by an obi sash. Historically made of silk.")),
                SizeRow(listOf("Scotland", "Kilt", "A knee-length non-bifurcated skirt with pleats at the rear, originating in the Highlands. Worn as part of Highland dress with specific clan tartans.")),
                SizeRow(listOf("Korea", "Hanbok", "Vibrant traditional attire with simple lines and no pockets. Features the jeogori (jacket) and chima (wrap-around skirt) for a graceful silhouette.")),
                SizeRow(listOf("Mexico", "Huipil", "A loose-fitting tunic, usually made of two or three rectangular pieces of fabric joined together. Often decorated with elaborate embroidery and lace.")),
                SizeRow(listOf("West Africa", "Dashiki", "A colorful garment that covers the top half of the body. Often features intricate embroidery around the neck and sleeves.")),
                SizeRow(listOf("Vietnam", "Ao Dai", "A long split tunic worn over silk trousers. Symbolizes Vietnamese elegance and is common for formal occasions.")),
                SizeRow(listOf("China", "Qipao/Cheongsam", "A body-hugging one-piece dress with a high neck and side slits. Originated in 1920s Shanghai as a modern take on Manchu dress.")),
                SizeRow(listOf("Germany", "Dirndl", "A traditional Alpine dress consisting of a bodice, blouse, full skirt, and apron. The bow on the apron indicates marital status."))
"""

content = re.sub(r'val worldCategories = listOf\(.*?SizeChart\(.*?"World Heritage Styles".*?listOf\((.*?)\)\s+\)\s+\)',
                 r'val worldCategories = listOf(\n        SizeChart(\n            "World Heritage Styles",\n            listOf("Region", "Garment", "Encyclopedia Entry"),\n            listOf(' + new_world_rows + r'            )\n        )\n    )',
                 content, flags=re.DOTALL)

# Update tribalCategories
new_tribal_rows = """
                SizeRow(listOf("Maasai", "Shuka", "Brightly colored (usually red) wrapped cloths worn by the Maasai people of East Africa. Highly durable and symbolizes Maasai culture.")),
                SizeRow(listOf("Quechua", "Poncho", "An outer garment designed to keep the body warm, made of wool or alpaca. Common in the Andes, often featuring geometric patterns.")),
                SizeRow(listOf("Sami", "Gakti", "Traditional clothing with distinct color bands and embroidery. Design varies by region and indicates the wearer's origin and status.")),
                SizeRow(listOf("Inuit", "Parka", "A heavy, insulated coat with a fur-lined hood, originally made from seal or caribou skin to survive Arctic conditions.")),
                SizeRow(listOf("Zulu", "Isidwaba", "A traditional leather skirt made of cowhide, worn by married women in Zulu culture. Symbolizes respect and marriage.")),
                SizeRow(listOf("Maori", "Korowai", "A finely woven cloak decorated with tassels (hukahuka). It is a symbol of prestige and is worn during important ceremonies.")),
                SizeRow(listOf("Navajo", "Velvet Shirt", "Traditionally worn by Navajo men and women, often in rich colors like turquoise or burgundy, paired with silver jewelry."))
"""

content = re.sub(r'val tribalCategories = listOf\(.*?SizeChart\(.*?"Tribal & Indigenous".*?listOf\((.*?)\)\s+\)\s+\)',
                 r'val tribalCategories = listOf(\n        SizeChart(\n            "Tribal & Indigenous",\n            listOf("Group", "Garment", "Encyclopedia Entry"),\n            listOf(' + new_tribal_rows + r'            )\n        )\n    )',
                 content, flags=re.DOTALL)

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'w') as f:
    f.write(content)
