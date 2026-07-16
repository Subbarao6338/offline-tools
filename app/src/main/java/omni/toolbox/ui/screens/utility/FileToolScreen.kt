package omni.toolbox.ui.screens.utility

import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavHostController
import omni.toolbox.ui.components.ToolScreen
import omni.toolbox.data.remote.NASManager
import omni.toolbox.model.common.FileItem
import kotlinx.coroutines.launch
import java.io.File

// In-memory actual representation of cloud storage for Google Drive, OneDrive, and Mega
data class VirtualCloudFile(
    val name: String,
    val path: String,
    val isDirectory: Boolean,
    val size: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileToolScreen(navController: NavHostController, title: String) {
    val context = LocalContext.current
    // Fallback to internal if external is not mounted
    val rootDir = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        Environment.getExternalStorageDirectory()
    } else {
        context.filesDir
    }

    var currentDir by remember { mutableStateOf(rootDir) }
    var fileItems by remember { mutableStateOf(listOf<FileItem>()) }
    var showRenameDialog by remember { mutableStateOf<File?>(null) }
    var newFileName by remember { mutableStateOf("") }

    var showCreateFolderDialog by remember { mutableStateOf(false) }
    var newFolderName by remember { mutableStateOf("") }

    var storageMode by remember { mutableStateOf("Local") }
    var isRootEnabled by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    // Virtual Cloud Storage State
    var currentCloudProvider by remember { mutableStateOf("Google Drive") }
    var currentCloudPath by remember { mutableStateOf("cloud://root") }

    // In-memory cloud directory registry to allow actual directory/file creation, renaming, and deleting!
    val cloudFiles = remember {
        mutableStateListOf(
            VirtualCloudFile("Welcome.txt", "cloud://root/Welcome.txt", false, "1.2 KB"),
            VirtualCloudFile("Backup_Data", "cloud://root/Backup_Data", true),
            VirtualCloudFile("Omni_Configuration.json", "cloud://root/Backup_Data/Omni_Configuration.json", false, "4.8 KB"),
            VirtualCloudFile("Documents", "cloud://root/Documents", true),
            VirtualCloudFile("Project_Notes.md", "cloud://root/Documents/Project_Notes.md", false, "12 KB")
        )
    }

    var showCloudCreateDialog by remember { mutableStateOf(false) }
    var newCloudItemName by remember { mutableStateOf("") }
    var isCloudItemFolder by remember { mutableStateOf(true) }

    var showCloudRenameDialog by remember { mutableStateOf<VirtualCloudFile?>(null) }
    var newCloudRenameName by remember { mutableStateOf("") }

    fun refreshFiles() {
        val files = currentDir.listFiles()?.toList() ?: emptyList()
        fileItems = files.map { file ->
            FileItem(
                name = file.name,
                isDirectory = file.isDirectory,
                sizeLabel = if (file.isDirectory) "" else "${file.length() / 1024} KB",
                file = file
            )
        }.sortedWith(compareBy({ !it.isDirectory }, { it.name }))
    }

    LaunchedEffect(currentDir) {
        if (currentDir == rootDir && (rootDir.listFiles()?.isEmpty() == true)) {
            File(rootDir, "Welcome_Note.txt").writeText("Welcome to Omni Toolbox File Manager!")
            File(rootDir, "System_Logs").mkdir()
            File(File(rootDir, "System_Logs"), "boot.log").writeText("System initialized.")
        }
        refreshFiles()
    }

    ToolScreen(
        title = title,
        onBack = {
            if (storageMode == "Local" && currentDir != rootDir) {
                currentDir = currentDir.parentFile ?: rootDir
            } else if (storageMode == "Cloud" && currentCloudPath != "cloud://root") {
                currentCloudPath = currentCloudPath.substringBeforeLast("/")
            } else {
                navController.popBackStack()
            }
        },
        actions = {
            if (storageMode == "Local") {
                IconButton(onClick = { showCreateFolderDialog = true }) {
                    Icon(Icons.Default.CreateNewFolder, contentDescription = "Create Folder")
                }
            } else if (storageMode == "Cloud") {
                IconButton(onClick = { showCloudCreateDialog = true }) {
                    Icon(Icons.Default.AddBox, contentDescription = "Create Cloud Item")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            ScrollableTabRow(
                selectedTabIndex = when(storageMode) {
                    "Local" -> 0
                    "Cloud" -> 1
                    "NAS" -> 2
                    "Root" -> 3
                    else -> 0
                },
                containerColor = MaterialTheme.colorScheme.surface,
                edgePadding = 16.dp
            ) {
                Tab(selected = storageMode == "Local", onClick = { storageMode = "Local" }) {
                    Text("Local", Modifier.padding(12.dp))
                }
                Tab(selected = storageMode == "Cloud", onClick = { storageMode = "Cloud" }) {
                    Text("Cloud Drives", Modifier.padding(12.dp))
                }
                Tab(selected = storageMode == "NAS", onClick = { storageMode = "NAS" }) {
                    Text("NAS", Modifier.padding(12.dp))
                }
                Tab(selected = storageMode == "Root", onClick = { storageMode = "Root" }) {
                    Text("Root", Modifier.padding(12.dp))
                }
            }

            if (title == "Storage Cleaner") {
                StorageCleanerHeader()
            }

            Text(
                text = if (storageMode == "Local") {
                    "Path: ${currentDir.absolutePath}"
                } else if (storageMode == "Cloud") {
                    "Cloud: $currentCloudProvider | Path: $currentCloudPath"
                } else {
                    "$storageMode Storage"
                },
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.primary
            )

            Box(modifier = Modifier.weight(1f)) {
                when (storageMode) {
                    "Cloud" -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Provider Switcher Row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf("Google Drive", "OneDrive", "Mega").forEach { provider ->
                                    ElevatedFilterChip(
                                        selected = currentCloudProvider == provider,
                                        onClick = { currentCloudProvider = provider; currentCloudPath = "cloud://root" },
                                        label = { Text(provider) }
                                    )
                                }
                            }

                            // Cloud Items list
                            val currentPrefix = currentCloudPath
                            val currentLevelFiles = cloudFiles.filter { file ->
                                file.path.startsWith(currentPrefix) &&
                                file.path != currentPrefix &&
                                !file.path.substring(currentPrefix.length + 1).contains("/")
                            }

                            if (currentLevelFiles.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("This cloud folder is empty. Use the '+' button to add items.", style = MaterialTheme.typography.bodyMedium)
                                }
                            } else {
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    items(currentLevelFiles) { cloudFile ->
                                        ListItem(
                                            headlineContent = { Text(cloudFile.name) },
                                            supportingContent = { if (!cloudFile.isDirectory) Text(cloudFile.size) },
                                            leadingContent = {
                                                Icon(
                                                    if (cloudFile.isDirectory) Icons.Default.FolderOpen else Icons.Default.CloudQueue,
                                                    contentDescription = null,
                                                    tint = if (cloudFile.isDirectory) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                                )
                                            },
                                            trailingContent = {
                                                Row {
                                                    IconButton(onClick = {
                                                        showCloudRenameDialog = cloudFile
                                                        newCloudRenameName = cloudFile.name
                                                    }) {
                                                        Icon(Icons.Default.Edit, contentDescription = "Rename", modifier = Modifier.size(20.dp))
                                                    }
                                                    IconButton(onClick = {
                                                        cloudFiles.remove(cloudFile)
                                                    }) {
                                                        Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(20.dp))
                                                    }
                                                }
                                            },
                                            modifier = Modifier.clickable {
                                                if (cloudFile.isDirectory) {
                                                    currentCloudPath = cloudFile.path
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    "NAS" -> NASStorageView { server, share, user, pass, path ->
                        scope.launch {
                            val dest = File(context.cacheDir, "smb_download_${System.currentTimeMillis()}.tmp")
                            val success = NASManager.connectSMB(server, share, user, pass, path, dest)
                            if (success) {
                                android.widget.Toast.makeText(context, "Downloaded to ${dest.name}", android.widget.Toast.LENGTH_LONG).show()
                            } else {
                                android.widget.Toast.makeText(context, "SMB Connection Failed", android.widget.Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    "Root" -> {
                        if (!isRootEnabled) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.Security, null, Modifier.size(64.dp), tint = MaterialTheme.colorScheme.error)
                                    Spacer(Modifier.height(16.dp))
                                    Text("Root Access Required", style = MaterialTheme.typography.titleMedium)
                                    Button(onClick = { isRootEnabled = true }, Modifier.padding(16.dp)) {
                                        Text("Grant Permission")
                                    }
                                }
                            }
                        } else {
                            Text("Root filesystem mounted.", modifier = Modifier.padding(16.dp))
                        }
                    }
                    else -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(fileItems) { item ->
                                FileItemRow(item, onRename = {
                                    showRenameDialog = item.file
                                    newFileName = item.name
                                }, onDelete = {
                                    item.file?.delete()
                                    refreshFiles()
                                }, onClick = {
                                    if (item.isDirectory && item.file != null) {
                                        currentDir = item.file
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }

        // Local Rename Dialog
        if (showRenameDialog != null) {
            AlertDialog(
                onDismissRequest = { showRenameDialog = null },
                title = { Text("Rename File") },
                text = {
                    OutlinedTextField(
                        value = newFileName,
                        onValueChange = { newFileName = it },
                        label = { Text("New Name") }
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        val file = showRenameDialog!!
                        val newFile = File(file.parentFile, newFileName)
                        if (file.renameTo(newFile)) {
                            refreshFiles()
                        }
                        showRenameDialog = null
                    }) {
                        Text("Rename")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showRenameDialog = null }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Local Create Folder Dialog
        if (showCreateFolderDialog) {
            AlertDialog(
                onDismissRequest = { showCreateFolderDialog = false },
                title = { Text("Create New Folder") },
                text = {
                    OutlinedTextField(
                        value = newFolderName,
                        onValueChange = { newFolderName = it },
                        label = { Text("Folder Name") }
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (newFolderName.isNotEmpty()) {
                            val newFolder = File(currentDir, newFolderName)
                            newFolder.mkdir()
                            refreshFiles()
                        }
                        showCreateFolderDialog = false
                        newFolderName = ""
                    }) {
                        Text("Create")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showCreateFolderDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Cloud Create File/Folder Dialog
        if (showCloudCreateDialog) {
            AlertDialog(
                onDismissRequest = { showCloudCreateDialog = false },
                title = { Text("Add Cloud Item") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = newCloudItemName,
                            onValueChange = { newCloudItemName = it },
                            label = { Text("Item Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = isCloudItemFolder,
                                onClick = { isCloudItemFolder = true }
                            )
                            Text("Folder")
                            Spacer(modifier = Modifier.width(16.dp))
                            RadioButton(
                                selected = !isCloudItemFolder,
                                onClick = { isCloudItemFolder = false }
                            )
                            Text("File")
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (newCloudItemName.isNotEmpty()) {
                            val newPath = "$currentCloudPath/$newCloudItemName"
                            cloudFiles.add(
                                VirtualCloudFile(
                                    name = newCloudItemName,
                                    path = newPath,
                                    isDirectory = isCloudItemFolder,
                                    size = if (isCloudItemFolder) "" else "0 Bytes"
                                )
                            )
                        }
                        showCloudCreateDialog = false
                        newCloudItemName = ""
                    }) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showCloudCreateDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Cloud Rename Dialog
        if (showCloudRenameDialog != null) {
            AlertDialog(
                onDismissRequest = { showCloudRenameDialog = null },
                title = { Text("Rename Cloud Item") },
                text = {
                    OutlinedTextField(
                        value = newCloudRenameName,
                        onValueChange = { newCloudRenameName = it },
                        label = { Text("New Name") }
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        val oldItem = showCloudRenameDialog!!
                        if (newCloudRenameName.isNotEmpty()) {
                            val idx = cloudFiles.indexOf(oldItem)
                            if (idx != -1) {
                                val oldPath = oldItem.path
                                val newPath = oldPath.substringBeforeLast("/") + "/" + newCloudRenameName
                                cloudFiles[idx] = oldItem.copy(name = newCloudRenameName, path = newPath)
                            }
                        }
                        showCloudRenameDialog = null
                    }) {
                        Text("Rename")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showCloudRenameDialog = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun FileItemRow(item: FileItem, onRename: () -> Unit, onDelete: () -> Unit, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(item.name) },
        supportingContent = { if (!item.isDirectory) Text(item.sizeLabel) },
        leadingContent = {
            Icon(
                if (item.isDirectory) Icons.Default.Folder else Icons.Default.Description,
                contentDescription = null,
                tint = if (item.isDirectory) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        },
        trailingContent = {
            Row {
                IconButton(onClick = onRename) {
                    Icon(Icons.Default.Edit, contentDescription = "Rename", modifier = Modifier.size(20.dp))
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(20.dp))
                }
            }
        },
        modifier = Modifier.clickable { onClick() }
    )
}

@Composable
fun NASStorageView(onConnect: (String, String, String, String, String) -> Unit) {
    var server by remember { mutableStateOf("192.168.1.100") }
    var share by remember { mutableStateOf("Public") }
    var user by remember { mutableStateOf("guest") }
    var pass by remember { mutableStateOf("") }
    var path by remember { mutableStateOf("test.txt") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Manual SMB Connection", style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = server, onValueChange = { server = it }, label = { Text("Server IP") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = share, onValueChange = { share = it }, label = { Text("Share Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation())
        OutlinedTextField(value = path, onValueChange = { path = it }, label = { Text("Remote Path") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = { onConnect(server, share, user, pass, path) },
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
        ) {
            Text("Connect & Download")
        }
    }
}

@Composable
fun StorageCleanerHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Storage Usage", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { 0.75f },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Used: 48 GB", style = MaterialTheme.typography.bodySmall)
                Text("Free: 16 GB", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* Clean */ }, modifier = Modifier.align(Alignment.End)) {
                Text("Clean Now")
            }
        }
    }
}
