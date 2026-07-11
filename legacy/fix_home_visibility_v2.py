import re

tools_to_show = ["Quick Tiles", "Automation", "Security Vault", "Daily Helpers", "Calculators", "Unit Converters", "Engineering"]

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
    content = f.read()

lines = content.split('\n')
new_lines = []
for line in lines:
    if any(f'Tool("{t}"' in line for t in tools_to_show):
        line = line.replace('isVisibleOnHome = false, ', '')
    new_lines.append(line)

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
    f.write('\n'.join(new_lines))
