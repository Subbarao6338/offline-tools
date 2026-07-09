import re

hub_to_routes = {
    "Audio Lab": ["whistle", "white_noise"],
    "Outdoor & Adventure": ["triangulate", "water_purify", "solar_panel", "clinometer", "cliff_height"],
    "Weather Center": ["lightning"],
    "Science Lab": ["ballistics"]
}

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
    content = f.read()

# Find the group tools (main tools that have subToolRoutes) and append the new routes
lines = content.split('\n')
new_lines = []
for line in lines:
    if 'subToolRoutes = listOf(' in line:
        # Match hub by route name or category in nearby lines
        # This is tricky because the line itself might not have the hub name.
        # Let's try to match by route name of the group
        if '"audio_tools_group"' in line:
            line = line.replace('))', ', "whistle", "white_noise"))')
        elif '"outdoor_group"' in line:
            line = line.replace('))', ', "triangulate", "water_purify", "solar_panel", "clinometer", "cliff_height"))')
        elif '"weather_group"' in line:
            line = line.replace('))', ', "lightning"))')
        elif '"science_group"' in line:
            line = line.replace('))', ', "ballistics"))')
    new_lines.append(line)

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
    f.write('\n'.join(new_lines))
