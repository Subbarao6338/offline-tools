import re

def get_content(filename):
    try:
        with open(f'tools1/tools-main/guides/en-US/{filename}', 'r') as f:
            content = f.read()
            # Clean up markdown markers if necessary for simple text display
            content = content.replace('<!-- K:', '').replace('-->', '')
            content = content.replace('![]', '') # Remove empty image tags
            return content.strip()
    except:
        return ""

fire_content = get_content('guide_survival_chapter_fire.txt')
water_content = get_content('guide_survival_chapter_water.txt')
food_content = get_content('guide_survival_chapter_food.txt')
medical_content = get_content('guide_survival_chapter_medical.txt')

with open('app/src/main/java/omni/toolbox/ui/screens/outdoor/SurvivalGuideScreen.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
for line in lines:
    if 'SurvivalChapter("Fire"' in line:
        line = f'        SurvivalChapter("Fire", Icons.Default.LocalFireDepartment, """{fire_content[:500]}..."""),\n'
    elif 'SurvivalChapter("Water"' in line:
        line = f'        SurvivalChapter("Water", Icons.Default.WaterDrop, """{water_content[:500]}..."""),\n'
    elif 'SurvivalChapter("Food"' in line:
        line = f'        SurvivalChapter("Food", Icons.Default.Restaurant, """{food_content[:500]}..."""),\n'
    elif 'SurvivalChapter("Medical"' in line:
        line = f'        SurvivalChapter("Medical", Icons.Default.MedicalServices, """{medical_content[:500]}..."""),\n'
    new_lines.append(line)

# Since the original screen used short placeholders, we'll actually modify the ChapterDetail to show more if needed,
# but for now, let's just update the main descriptions.

with open('app/src/main/java/omni/toolbox/ui/screens/outdoor/SurvivalGuideScreen.kt', 'w') as f:
    f.writelines(new_lines)
