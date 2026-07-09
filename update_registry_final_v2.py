import re

def add_route(route, screen):
    with open('app/src/main/java/omni/toolbox/navigation/AppNavigation.kt', 'r') as f:
        content = f.read()
    if f'route == "{route}"' not in content:
        # Match where direct route comparisons are happening
        match = re.search(r'route == "ballistics" -> BallisticsScreen\(navController\)', content)
        if match:
            insertion = f'\n        route == "{route}" -> {screen}(navController)'
            content = content[:match.end()] + insertion + content[match.end():]
            print(f"Added route {route}")
    with open('app/src/main/java/omni/toolbox/navigation/AppNavigation.kt', 'w') as f:
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
