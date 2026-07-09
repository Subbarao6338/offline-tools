import re

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
    content = f.read()

# Fix leftover categories
content = content.replace('"Developer"', '"Developer Tools"')
content = content.replace('"Device"', '"System Monitor"')
content = content.replace('"Education"', '"Science Lab"')
content = content.replace('"Media"', '"Audio Lab"') # Default Media to Audio Lab for leftovers
content = content.replace('"Travel"', '"Outdoor & Adventure"')
content = content.replace('"Utilities"', '"Daily Helpers"')
content = content.replace('"Web"', '"AI Companion"') # Most Web tools were perchance or search

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
    f.write(content)
