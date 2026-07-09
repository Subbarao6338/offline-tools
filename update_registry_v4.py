import re

def add_tool(name, route, hub, icon):
    with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
        content = f.read()
    if f'"{route}"' not in content:
        new_tool = f'        Tool("{name}", {icon}, "{route}", "{hub}", isVisibleOnHome = false, isSubTool = true),\n'
        # Add to the sub-tools section (arbitrarily after AI TOOLS)
        content = content.replace('// --- AI SUB-TOOLS ---', '// --- NEW SUB-TOOLS ---\n' + new_tool + '\n        // --- AI SUB-TOOLS ---')
    with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
        f.write(content)

# Registry
add_tool("Ballistics Calc", "ballistics", "Science Lab", "Icons.Default.Science")
add_tool("Cliff Height", "cliff_height", "Outdoor & Adventure", "Icons.Default.Landscape")
add_tool("Triangulation", "triangulate", "Outdoor & Adventure", "Icons.Default.Explore")
add_tool("Water Purifier", "water_purify", "Outdoor & Adventure", "Icons.Default.LocalDrink")
add_tool("Emergency Whistle", "whistle", "Audio Lab", "Icons.Default.Campaign")
add_tool("White Noise", "white_noise", "Audio Lab", "Icons.Default.NightsStay")
add_tool("Lightning Distance", "lightning", "Weather Center", "Icons.Default.FlashOn")
add_tool("Solar Aligner", "solar_panel", "Outdoor & Adventure", "Icons.Default.WbSunny")
add_tool("Clinometer", "clinometer", "Outdoor & Adventure", "Icons.Default.Architecture")
