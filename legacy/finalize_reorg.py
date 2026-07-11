import re

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
    content = f.read()

# Make sub-tools hidden if they are already sub-tools
# Actually, the user wants "around 30 tools" on home. Currently we have 45.
# Let's identify some less important main tools and hide them.

tools_to_hide = [
    "Quick Tiles", "Automation", "Telemetry & Stats", "Cloud Sync Hub", "Web Crawler Pro",
    "Security Vault", "Daily Helpers", "Calculators", "Unit Converters", "Engineering",
    "System Monitor", "Sensors & Tools", "Dev Expert", "Text Mastery", "Data Tools",
    "Web & Online", "Privacy & Security", "Office & Files", "Network Lab", "Weather Center",
    "DIY & Home", "Social Presence"
]

lines = content.split('\n')
new_lines = []
for line in lines:
    if any(f'Tool("{t}"' in line for t in tools_to_hide) and 'isVisibleOnHome = false' not in line:
        if 'description =' in line:
             line = line.replace('description =', 'isVisibleOnHome = false, description =')
        else:
             # Tool(name, icon, route, cat, color)
             line = line.replace('Color(', 'isVisibleOnHome = false, Color(')
    new_lines.append(line)

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
    f.write('\n'.join(new_lines))
