import re

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
    content = f.read()

# Add size_guide to lifestyle group if not present
if '"size_fit_group"' in content:
    lines = content.split('\n')
    new_lines = []
    for line in lines:
        if '"size_fit_group"' in line and 'subToolRoutes' in line:
            if '"size_guide"' not in line:
                line = line.replace('))', ', "size_guide"))')
        new_lines.append(line)
    content = '\n'.join(new_lines)

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
    f.write(content)
