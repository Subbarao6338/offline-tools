with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
for line in lines:
    # Remove the extra closing parenthesis that was accidentally left by re.sub
    if line.strip() == ')':
        if len(new_lines) > 0 and new_lines[-1].strip() == ')':
             continue # Skip duplicate closing paren
    new_lines.append(line)

with open('app/src/main/java/omni/toolbox/model/SizeGuideData.kt', 'w') as f:
    f.writelines(new_lines)
