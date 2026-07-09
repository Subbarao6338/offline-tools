import re

with open('app/src/main/java/omni/toolbox/ui/screens/lifestyle/SizeGuideScreen.kt', 'r') as f:
    content = f.read()

# Update mainTabs
content = content.replace('val mainTabs = listOf("Women", "Men", "Kids", "Footwear", "Accessories", "Indian", "World", "Tribal", "Modern", "Global", "Innerwear")',
                          'val mainTabs = listOf("Women", "Men", "Kids", "Footwear", "Accessories", "Indian", "World", "Tribal", "Modern", "Global", "Innerwear", "Materials")')

# Update currentCategories logic
replacement = """        8 -> SizeGuideData.modernCategories
        9 -> SizeGuideData.globalConversion
        10 -> SizeGuideData.innerwearCategories
        else -> SizeGuideData.materialCategories"""

content = re.sub(r'8 -> SizeGuideData\.modernCategories\s+9 -> SizeGuideData\.globalConversion\s+else -> SizeGuideData\.innerwearCategories',
                 replacement, content, flags=re.DOTALL)

with open('app/src/main/java/omni/toolbox/ui/screens/lifestyle/SizeGuideScreen.kt', 'w') as f:
    f.write(content)
