import re

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
    content = f.read()

# For tools without isSubTool = true, ensure isVisibleOnHome = true (implicit or explicit)
# Most top-level tools don't have isVisibleOnHome set, and it defaults to true in data class
# But we might have accidentally changed it or we want to make sure the main groups are visible.

lines = content.split('\n')
new_lines = []
for line in lines:
    if 'Tool(' in line and 'isSubTool = true' not in line:
        if 'isVisibleOnHome = false' in line:
            line = line.replace('isVisibleOnHome = false', 'isVisibleOnHome = true')
    new_lines.append(line)

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
    f.write('\n'.join(new_lines))
