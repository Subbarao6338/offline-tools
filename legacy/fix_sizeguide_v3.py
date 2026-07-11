import re

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
for i, line in enumerate(lines):
    # Check for missing closing parens
    if 'val menCategories =' in line:
        if ')' not in lines[i-1] and ')' not in lines[i-2]:
            new_lines.append('        )\n    )\n\n')

    if 'val kidsCategories =' in line:
        if ')' not in lines[i-1] and ')' not in lines[i-2]:
            new_lines.append('        )\n    )\n\n')

    if 'val footwearCategories =' in line:
        if ')' not in lines[i-1] and ')' not in lines[i-2]:
            new_lines.append('        )\n    )\n\n')

    if 'val accessoriesCategories =' in line:
        if ')' not in lines[i-1] and ')' not in lines[i-2]:
            new_lines.append('        )\n    )\n\n')

    if 'val indianCategories =' in line:
        if ')' not in lines[i-1] and ')' not in lines[i-2]:
            new_lines.append('        )\n    )\n\n')

    if 'val worldCategories =' in line:
        if ')' not in lines[i-1] and ')' not in lines[i-2]:
            new_lines.append('        )\n    )\n\n')

    if 'val tribalCategories =' in line:
        if ')' not in lines[i-1] and ')' not in lines[i-2]:
            new_lines.append('        )\n    )\n\n')

    if 'val modernCategories =' in line:
        if ')' not in lines[i-1] and ')' not in lines[i-2]:
            new_lines.append('        )\n    )\n\n')

    if 'val materialCategories =' in line:
        if ')' not in lines[i-1] and ')' not in lines[i-2]:
            new_lines.append('        )\n    )\n\n')

    if 'val globalConversion =' in line:
        if ')' not in lines[i-1] and ')' not in lines[i-2]:
            new_lines.append('        )\n    )\n\n')

    if 'val innerwearCategories =' in line:
        if ')' not in lines[i-1] and ')' not in lines[i-2]:
            new_lines.append('        )\n    )\n\n')

    new_lines.append(line)

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'w') as f:
    f.writelines(new_lines)
