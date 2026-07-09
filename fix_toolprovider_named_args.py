import re

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
    content = f.read()

# Fix the mixed named/positioned arguments
# Replace 'isVisibleOnHome = false, Color(' with 'color = Color(' and move isVisibleOnHome
# Actually, just naming 'color =' is enough.

content = content.replace('isVisibleOnHome = false, Color(', 'color = Color(')
# And make sure isVisibleOnHome = false is added at the end or as a named arg.
# My previous script might have left it messy.

# Let's do a cleaner approach for the whole file.
lines = content.split('\n')
new_lines = []
for line in lines:
    if 'Tool(' in line:
        # If it contains isVisibleOnHome = false but NOT color =, let's fix it.
        if 'isVisibleOnHome = false' in line and 'color =' not in line:
             line = line.replace('Color(', 'color = Color(')

        # Also check for other named args like description
        if 'description =' in line and 'color =' not in line and 'Color(' in line:
             line = line.replace('Color(', 'color = Color(')

    new_lines.append(line)

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
    f.write('\n'.join(new_lines))
