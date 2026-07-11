import re

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'r') as f:
    content = f.read()

# Fix the missing ) before val declarations
content = re.sub(r'\s+val\s+(\w+)\s*=\s*listOf\(', r'\n    )\n\n    val \1 = listOf(', content)

# But the first val shouldn't have a leading )
content = content.replace('    )\n\n    val womenCategories', '    val womenCategories')

# And the end of the object should have the last closing )
# Find the last closing ] or ) and make sure it's closed correctly.
# Let's just manually fix the known messed up parts.

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'w') as f:
    f.write(content)
