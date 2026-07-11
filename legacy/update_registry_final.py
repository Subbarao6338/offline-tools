import re

def add_route(route, screen):
    with open('app/src/main/java/omni/toolbox/navigation/AppNavigation.kt', 'r') as f:
        content = f.read()
    if f'route == "{route}"' not in content:
        # Insert before a common marker
        content = content.replace('route == "ballistics"', f'route == "{route}" -> {screen}(navController)\n        route == "ballistics"')
    with open('app/src/main/java/omni/toolbox/navigation/AppNavigation.kt', 'w') as f:
        f.write(content)

def add_tool(name, route, hub, icon):
    with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
        content = f.read()
    if f'"{route}"' not in content:
        new_tool = f'        Tool("{name}", {icon}, "{route}", "{hub}", isVisibleOnHome = false, isSubTool = true),\n'
        # Add to the sub-tools section
        content = content.replace('// --- MEDIA SUB-TOOLS ---', '// --- NEW SUB-TOOLS ---\n' + new_tool + '\n        // --- MEDIA SUB-TOOLS ---')
    with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
        f.write(content)

# Routes
routes = [
    ("triangulate", "TriangulationScreen"),
    ("water_purify", "WaterPurificationScreen"),
    ("whistle", "WhistleScreen"),
    ("white_noise", "WhiteNoiseScreen"),
    ("lightning", "LightningScreen"),
    ("solar_panel", "SolarPanelScreen"),
    ("clinometer", "ClinometerScreen")
]

for r, s in routes:
    add_route(r, s)

# Registry
add_tool("Triangulation", "triangulate", "Outdoor & Adventure", "Icons.Default.Explore")
add_tool("Water Purifier", "water_purify", "Outdoor & Adventure", "Icons.Default.LocalDrink")
add_tool("Emergency Whistle", "whistle", "Audio Lab", "Icons.Default.Campaign")
add_tool("White Noise", "white_noise", "Audio Lab", "Icons.Default.NightsStay")
add_tool("Lightning Distance", "lightning", "Weather Center", "Icons.Default.FlashOn")
add_tool("Solar Aligner", "solar_panel", "Outdoor & Adventure", "Icons.Default.WbSunny")
add_tool("Clinometer", "clinometer", "Outdoor & Adventure", "Icons.Default.Architecture")
