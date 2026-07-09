import re

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
    content = f.read()

# Fix the mixed named/positioned arguments
# We need to make sure 'category' is NOT a named argument if it's the 4th argument,
# OR make everything after it named.

# The current format in many lines is Tool(name, icon, route, HubName, isVisibleOnHome = false, ...)
# HubName is currently a positioned argument at index 3.

lines = content.split('\n')
new_lines = []
for line in lines:
    if 'Tool(' in line:
        # If it has a named argument later, make category named too.
        # But category is already at position 4 (index 3).
        # Kotlin allows: Tool(p1, p2, p3, p4, named1=v1)
        # It DOES NOT allow: Tool(p1, p2, p3, named1=v1, p4)

        # Let's check the failing lines.
        # example: Tool("Triangulation", Icons.Default.Explore, "triangulate", "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true)
        # Wait, that looks correct.
        # But maybe isVisibleOnHome is NOT the next argument after category?

        # data class Tool(
        #    val name: String,             // 0
        #    val icon: ImageVector,        // 1
        #    val route: String,            // 2
        #    val category: String,         // 3
        #    val color: Color = ...,       // 4
        #    val badge: BadgeType = ...,   // 5
        #    val description: String? = ..., // 6
        #    val isVisibleOnHome: Boolean = true, // 7
        #    val subToolRoutes: List<String>? = null, // 8
        #    val isSubTool: Boolean = false // 9
        # )

        # My generated lines:
        # Tool("Name", icon, "route", "Category", isVisibleOnHome = false, isSubTool = true)
        # Here, "Category" is index 3.
        # The next argument should be 'color'.
        # But I provided 'isVisibleOnHome' which is index 7.
        # Once you use a named argument, ALL subsequent arguments MUST be named.
        # OR you must provide them in order.

        # To fix "Mixing named and positioned arguments is not allowed",
        # I should name the category too if I skip 'color'.

        if 'isVisibleOnHome =' in line or 'description =' in line:
            if 'category =' not in line:
                line = line.replace(', "System Monitor"', ', category = "System Monitor"')
                line = line.replace(', "Developer Tools"', ', category = "Developer Tools"')
                line = line.replace(', "Audio Lab"', ', category = "Audio Lab"')
                line = line.replace(', "Video Lab"', ', category = "Video Lab"')
                line = line.replace(', "Image Studio"', ', category = "Image Studio"')
                line = line.replace(', "GIF & Animation"', ', category = "GIF & Animation"')
                line = line.replace(', "AI Companion"', ', category = "AI Companion"')
                line = line.replace(', "Data Science"', ', category = "Data Science"')
                line = line.replace(', "Science Lab"', ', category = "Science Lab"')
                line = line.replace(', "Math Hub"', ', category = "Math Hub"')
                line = line.replace(', "Daily Helpers"', ', category = "Daily Helpers"')
                line = line.replace(', "Productivity"', ', category = "Productivity"')
                line = line.replace(', "Engineering Lab"', ', category = "Engineering Lab"')
                line = line.replace(', "Games & Fun"', ', category = "Games & Fun"')
                line = line.replace(', "Network Lab"', ', category = "Network Lab"')
                line = line.replace(', "Security Vault"', ', category = "Security Vault"')
                line = line.replace(', "Finance Hub"', ', category = "Finance Hub"')
                line = line.replace(', "Health & Vitality"', ', category = "Health & Vitality"')
                line = line.replace(', "Weather Center"', ', category = "Weather Center"')
                line = line.replace(', "Outdoor & Adventure"', ', category = "Outdoor & Adventure"')
                line = line.replace(', "Survival Guide"', ', category = "Survival Guide"')
                line = line.replace(', "Design & Creative"', ', category = "Design & Creative"')
                line = line.replace(', "DIY & Home"', ', category = "DIY & Home"')
                line = line.replace(', "Fashion & Lifestyle"', ', category = "Fashion & Lifestyle"')
                line = line.replace(', "Social Presence"', ', category = "Social Presence"')

    new_lines.append(line)

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
    f.write('\n'.join(new_lines))
