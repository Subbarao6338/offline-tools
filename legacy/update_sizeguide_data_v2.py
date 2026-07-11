import re

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'r') as f:
    content = f.read()

# Update modernCategories with detailed descriptions
new_modern_rows = """
                SizeRow(listOf("Minimalist", "Neutral colors, clean lines, and quality basics. Focuses on the 'less is more' philosophy, prioritizing functionality and form.")),
                SizeRow(listOf("Bohemian", "Flowy fabrics, earthy tones, and ethnic prints. Inspired by free-spirited lifestyles, often featuring embroidery, fringe, and layers.")),
                SizeRow(listOf("Streetwear", "Oversized fits, sneakers, and graphic tees. Originated from skate and surf culture, now a global fashion phenomenon influenced by hip-hop.")),
                SizeRow(listOf("Preppy", "Polo shirts, blazers, and pleated skirts. Inspired by Ivy League style, emphasizing a neat, groomed, and classic aesthetic.")),
                SizeRow(listOf("Grunge", "Flannel shirts, distressed denim, and combat boots. A subculture-driven style from the 90s, characterized by a 'messy' and unkempt look.")),
                SizeRow(listOf("Dark Academia", "Tweed blazers, turtlenecks, and Oxford shoes. Centers around higher education, writing, poetry, and classical Greek and Gothic themes.")),
                SizeRow(listOf("Cottagecore", "Floral prints, puff sleeves, and linen fabrics. An aesthetic that idealizes rural life and nature, emphasizing simplicity and traditional crafts.")),
                SizeRow(listOf("Cyberpunk", "Tech-wear, neon accents, and futuristic materials. A sci-fi inspired look that combines high-tech gear with urban street style."))
"""

content = re.sub(r'val modernCategories = listOf\(.*?SizeChart\(.*?"Modern Fashion Aesthetics".*?listOf\((.*?)\)\s+\)',
                 r'val modernCategories = listOf(\n        SizeChart(\n            "Modern Fashion Aesthetics",\n            listOf("Aesthetic", "Encyclopedia Entry"),\n            listOf(' + new_modern_rows + r'            )\n        )',
                 content, flags=re.DOTALL)

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'w') as f:
    f.write(content)
