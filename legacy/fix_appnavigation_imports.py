with open('app/src/main/java/omni/toolbox/navigation/AppNavigation.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
added_imports = False
for line in lines:
    new_lines.append(line)
    if 'import omni.toolbox.ui.screens.outdoor.*' in line and not added_imports:
        new_lines.append('import omni.toolbox.ui.screens.outdoor.CliffHeightScreen\n')
        new_lines.append('import omni.toolbox.ui.screens.outdoor.TriangulationScreen\n')
        new_lines.append('import omni.toolbox.ui.screens.outdoor.WaterPurificationScreen\n')
        new_lines.append('import omni.toolbox.ui.screens.science.BallisticsScreen\n')
        new_lines.append('import omni.toolbox.ui.screens.audio.WhistleScreen\n')
        new_lines.append('import omni.toolbox.ui.screens.audio.WhiteNoiseScreen\n')
        new_lines.append('import omni.toolbox.ui.screens.outdoor.LightningScreen\n')
        new_lines.append('import omni.toolbox.ui.screens.outdoor.SolarPanelScreen\n')
        new_lines.append('import omni.toolbox.ui.screens.sensor.ClinometerScreen\n')
        added_imports = True

with open('app/src/main/java/omni/toolbox/navigation/AppNavigation.kt', 'w') as f:
    f.writelines(new_lines)
