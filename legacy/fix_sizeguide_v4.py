with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'r') as f:
    content = f.read()

# The issue is that I added extra characters that messed up the existing lists.
# Let's restore the basic structure first.
# I'll use a more robust way to fix the list definitions.

import re

# Each val XXX = listOf( ... ) should end with )
# But I might have added some before them.

parts = re.split(r'val (\w+) = listOf\(', content)
header = parts[0]
new_content = header

for i in range(1, len(parts), 2):
    var_name = parts[i]
    var_content = parts[i+1]

    # Find where the next val or end of object is
    # For simplicity, let's just make sure each var_content ends with a proper closing
    # But wait, var_content contains all the charts.

    # Let's just fix the specific syntax errors reported by KSP
    # Example: e: file:///app/app/src/main/java/omni/toolbox/model/SizeGuideData.kt:89:5 Expecting ')'
    pass

# Actually, the safest way is to rewrite the file with known good structure.
