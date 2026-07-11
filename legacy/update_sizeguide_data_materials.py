import re

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'r') as f:
    content = f.read()

material_data = """
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
        ),
        SizeChart(
            "Sustainable Fabrics",
            listOf("Material", "Benefits"),
            listOf(
                SizeRow(listOf("Hemp", "Requires little water and no pesticides. Very durable and anti-microbial.")),
                SizeRow(listOf("Organic Cotton", "Grown without harmful chemicals. Better for soil health and workers.")),
                SizeRow(listOf("Tencel/Lyocell", "Produced in a closed-loop process using sustainable wood pulp. Biodegradable and soft.")),
                SizeRow(listOf("Recycled Polyester", "Made from PET plastic bottles. Reduces landfill waste and energy use.")),
                SizeRow(listOf("Bamboo", "Fast-growing and renewable. Soft and moisture-wicking, though processing can vary in sustainability."))
            )
        )
    )
"""

# Insert before globalConversion or at the end of the object
content = content.replace('    val globalConversion =', material_data + '\n    val globalConversion =')

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'w') as f:
    f.write(content)
