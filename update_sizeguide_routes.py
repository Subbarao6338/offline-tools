import re

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
    content = f.read()

if '"size_fit_group"' in content:
    lines = content.split('\n')
    new_lines = []
    for line in lines:
        if '"size_fit_group"' in line and 'subToolRoutes = listOf(' in line:
            if '"fashion_materials"' not in line:
                line = line.replace('))', ', "fashion_materials"))')
        new_lines.append(line)
    content = '\n'.join(new_lines)

# Also need to add the material tool to the subtools
material_tool = '        Tool("Fashion Materials", Icons.Default.Checkroom, "fashion_materials", "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),\n'
if '"fashion_materials"' not in content:
    content = content.replace('// --- LIFESTYLE SUB-TOOLS ---', '// --- LIFESTYLE SUB-TOOLS ---\n' + material_tool)

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
    f.write(content)

with open('app/src/main/java/omni/toolbox/navigation/AppNavigation.kt', 'r') as f:
    nav_content = f.read()

if 'route == "fashion_materials"' not in nav_content:
    nav_content = nav_content.replace('route == "size_guide" -> SizeGuideScreen(navController)',
                                      'route == "fashion_materials" -> SizeGuideScreen(navController, initialMainTab = 11)\n        route == "size_guide" -> SizeGuideScreen(navController)')

with open('app/src/main/java/omni/toolbox/navigation/AppNavigation.kt', 'w') as f:
    f.write(nav_content)
