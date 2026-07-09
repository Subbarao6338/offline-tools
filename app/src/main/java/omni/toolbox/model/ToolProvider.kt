package omni.toolbox.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class BadgeType { NONE, NEW, PREMIUM }

data class Tool(
    val name: String,
    val icon: ImageVector,
    val route: String,
    val category: String,
    val color: Color = Color.Unspecified,
    val badge: BadgeType = BadgeType.NONE,
    val description: String? = null,
    val isVisibleOnHome: Boolean = true,
    val subToolRoutes: List<String>? = null,
    val isSubTool: Boolean = false
)

object ToolProvider {
    val tools = listOf(
        // ==========================================
        // --- TOP-LEVEL MAIN TOOLS (VISIBLE) ---
        // ==========================================

        // --- DASHBOARD & SYSTEM ---
        Tool("Omni Dashboard", Icons.Default.Dashboard, "dashboard", "System Monitor", Color(0xFF39FF14),
            description = "Real-time system health and diagnostic hub."),
        Tool("PowerBench", Icons.Default.Speed, "power_bench", "System Monitor", Color(0xFFF44336),
            description = "Hardware performance profiling and stress tests."),
        Tool("Developer Console", Icons.Default.Code, "developer_console", "Developer Tools", Color(0xFF3F51B5),
            description = "Hardware inspection and app management suite."),
        Tool("Terminal Emulator", Icons.Default.Terminal, "terminal", "Developer Tools", Color(0xFF212121),
            description = "Local shell terminal emulator."),
        Tool("Security Vault", Icons.Default.Lock, "security_vault", "Security Vault", Color(0xFF607D8B),
            description = "Cryptographic tools and secure data locker."),
        Tool("Quick Tiles", Icons.Default.Widgets, "quick_tiles", "Daily Helpers", Color(0xFF4CAF50),
            description = "Create custom settings shade tiles."),
        Tool("Automation", Icons.Default.SettingsInputComponent, "automation", "Daily Helpers", Color(0xFF673AB7),
            description = "Root-free system automation and macros."),
        Tool("Telemetry & Stats", Icons.Default.Analytics, "telemetry_stats", "System Monitor", color = Color(0xFF00E676),
            description = "System telemetry and backup recovery hub."),

        // --- AI & DATA ---
        Tool("Gemini AI Hub", Icons.Default.AutoAwesome, "ai_companion", "AI Companion", Color(0xFF673AB7),
            description = "Multimodal AI assistant and generative lab."),
        Tool("Face Swap AI", Icons.Default.Face, "face_swap", "AI Companion", Color(0xFFFF4081),
            description = "Swap faces in images using AI."),
        Tool("Cloud Sync Hub", Icons.Default.CloudSync, "cloud_sync", "Data Science", color = Color(0xFF2196F3),
            description = "Multi-account cross-cloud data synchronization."),
        Tool("Web Crawler Pro", Icons.Default.Search, "web_scraper", "Data Science", color = Color(0xFF00BCD4),
            description = "Advanced web scraper with Notion integration."),

        // ==========================================
        // --- GROUP TOOLS (VISIBLE) ---
        // ==========================================

        // --- MEDIA ---
        Tool("Audio Tools", Icons.Default.LibraryMusic, "audio_tools_group", "Audio Lab", Color(0xFFE91E63),
            description = "Audio editing and processing tools.",
            subToolRoutes = listOf(
                "add_sfx", "aud_conv", "aud_eq_v2", "aud_info_v2", "audio_loop", "binaural", "bpm",
                "chord_lib", "guitar_tuner", "key_bpm_finder", "m_3d_audio", "m_8d_audio", "m_audio_compressor",
                "m_audio_cutter", "m_audio_editor", "m_audio_joiner", "m_audio_mixer", "m_audio_normalizer",
                "m_audio_pan", "m_audio_pitch", "m_audio_splitter", "m_audio_tag_editor", "m_bass_booster",
                "m_echo_effect", "m_equalizer", "m_karaoke_maker", "m_mute_audio", "m_reverse_audio",
                "m_ringtone_maker", "m_silence_remover", "m_speech_to_text", "m_speed_changer",
                "m_text_to_speech", "m_voice_changer", "m_volume_booster", "metronome", "noise_generator",
                "record_audio", "silence_generator", "sound_mastering", "voice_memo", "wave_generator",
                "ai_noise_remover", "ai_stems_splitter", "ai_voice_mimic", "aud_master_pro",
                "audio_noise_remover", "echo_remover", "reverb_remover", "vocal_autotuner", "vocal_remover"
            )),
        Tool("Video Tools", Icons.Default.VideoLibrary, "video_tools_group", "Video Lab", Color(0xFFF44336),
            description = "Professional video processing tools.",
            subToolRoutes = listOf(
                "frame_grabber", "m_video_to_audio", "mix_video_audio", "vid_annotator", "vid_edit_pro",
                "vid_thumb", "video_compress", "video_delete", "video_flip", "video_loop", "video_reverse",
                "video_sfx", "video_silence", "video_speed_changer", "video_splitter", "video_stabilizer",
                "video_to_gif", "video_trim", "video_volume_booster", "digital_magnifier", "mirror_tool", "video_merger"
            )),
        Tool("Image Tools", Icons.Default.PhotoLibrary, "image_tools_group", "Image Studio", Color(0xFF2196F3),
            description = "Image editing and optimization tools.",
            subToolRoutes = listOf(
                "batch_img_pro_v2", "exif_viewer", "image_ai_tools", "image_base64", "image_bg_remover",
                "image_collage", "image_color_picker", "image_compare", "image_crop", "image_cutting",
                "image_delete_exif", "image_draw", "image_draw_bg", "image_edit_exif", "image_filter",
                "image_format_conv", "image_layers_bg", "image_layers_img", "image_mask_filter",
                "image_noise_gen", "image_ocr", "image_open_project", "image_palette", "image_preview",
                "image_resize_conv", "image_resize_limits", "image_resize_weight", "image_single_edit",
                "image_stacking", "image_stitching", "image_to_svg", "image_to_webp", "image_wallpapers",
                "image_watermark", "image_web_load", "multi_crop", "multi_image_resize", "pixel_art",
                "profile_photo_maker", "webp_to_images", "file_conv"
            )),
        Tool("GIF & Animation", Icons.Default.Animation, "gif_tools_group", "GIF & Animation", Color(0xFF9C27B0),
            description = "Tools for GIF, APNG and JXL animations.",
            subToolRoutes = listOf(
                "apng_to_images", "apng_to_jxl", "gif_to_images", "gif_to_jxl", "gif_to_webp",
                "image_to_apng", "images_to_apng", "images_to_gif", "images_to_jxl", "jpeg_to_jxl",
                "jxl_to_images", "jxl_to_jpeg"
            )),

        // --- EDUCATION ---
        Tool("Science Lab", Icons.Default.Science, "science_group", "Science Lab", Color(0xFF4CAF50),
            description = "Science and physics exploration tools.",
            subToolRoutes = listOf(
                "constants", "constellations", "dna_viz", "eq_solver", "force_calc", "physics_formulas",
                "periodic_table", "planet_finder", "pokedex", "prime", "solar_system", "star_map", "unit_circle"
            )),
        Tool("Math Tools", Icons.Default.Functions, "math_group", "Math Hub", Color(0xFF607D8B),
            description = "Advanced mathematical calculators.",
            subToolRoutes = listOf("binary_calc", "fraction_calc", "matrix_calc", "sci_calc", "stats", "truth_table")),

        // --- UTILITIES ---
        Tool("Daily Helpers", Icons.Default.Build, "util_group", "Daily Helpers", Color(0xFF4CAF50),
            description = "Everyday utility tools.",
            subToolRoutes = listOf(
                "clock", "date_calc", "flashlight", "protractor", "qr_gen", "qr_scanner", "ruler",
                "stopwatch", "tiles_widgets", "vibration", "wifi_qr", "panchangam", "zodiac", "size_guide"
            )),
        Tool("Productivity", Icons.Default.Task, "prod_group", "Productivity", Color(0xFF3F51B5),
            description = "Stay organized and focused.",
            subToolRoutes = listOf(
                "checklist", "daily_journal", "daily_quotes", "kanban", "note", "pomodoro", "task_board", "time_logger"
            )),
        Tool("Calculators", Icons.Default.Calculate, "calc_group", "Daily Helpers", Color(0xFFFF9800),
            description = "Practical calculators for daily use.",
            subToolRoutes = listOf(
                "calculator", "discount", "tip", "unit_compare", "unit_price", "volume_calc", "billing"
            )),
        Tool("Unit Converters", Icons.Default.SwapHoriz, "conv_group", "Daily Helpers", Color(0xFF2196F3),
            description = "Convert between various units.",
            subToolRoutes = listOf("base_conv", "converter", "crypto_conv", "currency", "torque_conv")),
        Tool("Engineering", Icons.Default.Architecture, "engineering_group", "Engineering Lab", Color(0xFF607D8B),
            description = "Specialized engineering calculators.",
            subToolRoutes = listOf(
                "antenna_calc", "circuit_calc", "electronics_tools", "filter_design", "logic_gates",
                "ohms_law", "pcb_trace", "resistor_code", "signal_gen_pro", "smart_hub"
            )),

        // --- GAMES ---
        Tool("Game Collection", Icons.Default.Gamepad, "game_group", "Games & Fun", Color(0xFFFF9800),
            description = "Simple offline games.",
            subToolRoutes = listOf(
                "coin_flip", "dice_roller", "memory_game", "number_guessing", "random", "tic_tac_toe",
                "snake", "ludo", "carroms", "chess", "game_of_life", "clash_deck", "roulette",
                "dino_jump", "2048", "sudoku", "minesweeper"
            )),

        // --- DEVICE ---
        Tool("System Monitor", Icons.Default.Dns, "system_group", "System Monitor", color = Color(0xFF607D8B),
            description = "Monitor hardware and system status.",
            subToolRoutes = listOf(
                "app_info", "battery", "cpu_info", "ram_info", "device", "device_id",
                "process_manager", "storage", "system_lab", "update_check"
            )),
        Tool("Sensors & Tools", Icons.Default.SettingsInputComponent, "sensor_group", "System Monitor", color = Color(0xFF673AB7),
            description = "Access and test device sensors.",
            subToolRoutes = listOf(
                "altimeter", "barometer", "compass", "gforce_meter", "level", "light", "metal",
                "sensor_data", "sensors_list", "spl_meter", "thermal_info"
            )),

        // --- DEVELOPER ---
        Tool("Dev Expert", Icons.Default.Code, "dev_group", "Developer Tools", color = Color(0xFF3F51B5),
            description = "Advanced developer utilities.",
            subToolRoutes = listOf(
                "ascii_table", "base_conv", "base64", "crontab_gen", "hex_viewer", "jwt_tool",
                "markdown_preview", "regex_tester", "url_encoder", "app_inspector", "terminal"
            )),
        Tool("Text Mastery", Icons.Default.TextFields, "text_group", "Developer Tools", color = Color(0xFF9C27B0),
            description = "Powerful text processing tools.",
            subToolRoutes = listOf(
                "anagram", "case_converter", "lorem", "morse", "morse_decoder", "text_diff",
                "word_counter", "word_frequency", "word_rank_calc"
            )),

        // --- DATA ---
        Tool("Data Tools", Icons.Default.Storage, "data_tools_group", "Data Science", color = Color(0xFF009688),
            description = "Process and visualize data.",
            subToolRoutes = listOf(
                "data_viz", "json", "yaml_to_json", "anomaly_detection", "data_profiling",
                "data_statistics", "data_visualisations", "synthetic_data_gen", "data_quality",
                "data_cleaning", "data_transformation"
            )),

        // --- WEB ---
        Tool("Web & Online", Icons.Default.Language, "web_tools_group", "AI Companion", color = Color(0xFF2196F3),
            description = "Web exploration and SEO tools.",
            subToolRoutes = listOf("hub", "media_grabber", "meta_anal", "web")),
        Tool("Perchance AI Hub", Icons.Default.AutoAwesome, "perchance_tools", "AI Companion", Color(0xFFE91E63),
            description = "Access AI creative tools from Perchance.",
            subToolRoutes = listOf(
                "per_character", "per_hub", "per_image", "per_image_pro", "per_necs_story",
                "per_story", "per_text_gen", "per_text_rewrite"
            )),

        // --- AI TOOLS ---
        Tool("AI Expert", Icons.Default.AutoAwesome, "ai_group", "AI Companion", Color(0xFF673AB7),
            description = "Intelligent AI assistant tools.",
            subToolRoutes = listOf(
                "ai_chat", "ai_code", "ai_doc_translator", "ai_grammar", "ai_image",
                "ai_obj_detect", "ai_sentiment", "ai_summarizer", "ai_text_ext", "ai_translate",
                "face_swap", "video_noise_remover", "ai_tryon"
            )),

        // --- SECURITY ---
        Tool("Privacy & Security", Icons.Default.Security, "security_group", "Security Vault", color = Color(0xFF607D8B),
            description = "Tools to keep your digital life safe.",
            subToolRoutes = listOf(
                "app_locker", "app_permissions", "cipher_tools", "password_gen", "password_manager",
                "perm_manager", "privacy_check", "sec_adguard", "sec_nextdns", "sec_bitwarden", "sec_ente"
            )),

        // --- DOCUMENTS ---
        Tool("PDF Toolkit", Icons.Default.PictureAsPdf, "pdf_tools_group", "Productivity", Color(0xFFF44336),
            description = "Comprehensive PDF editing tools.",
            subToolRoutes = listOf(
                "images_to_pdf", "pdf_compress", "pdf_crop", "pdf_extract_images", "pdf_flatten",
                "pdf_grayscale", "pdf_merge", "pdf_metadata", "pdf_ocr", "pdf_page_numbers",
                "pdf_preview", "pdf_print", "pdf_protect", "pdf_rearrange", "pdf_remove_pages",
                "pdf_repair", "pdf_rotate", "pdf_signature", "pdf_split", "pdf_unlock",
                "pdf_watermark", "pdf_zip", "pdf_html_to_pdf", "pdf_scan_to_pdf", "pdf_fill_forms",
                "pdf_word_to_pdf", "pdf_excel_to_pdf", "pdf_text_to_pdf", "pdf_qr_to_pdf",
                "pdf_barcode_to_pdf", "pdf_invert", "pdf_to_mdx", "pdf_to_mhtml",
                "pdf_remove_pages", "pdf_page_numbers"
            )),
        Tool("Office & Files", Icons.Default.Description, "docs_group", "Productivity", color = Color(0xFF607D8B),
            description = "Manage documents and files.",
            subToolRoutes = listOf(
                "csv_to_json", "doc_scanner", "duplicate_finder", "file_explorer",
                "file_shredder", "sql_format", "storage_cleaner", "zip_unzip", "docs_online", "markitdown"
            )),

        // --- NETWORK ---
        Tool("Network Lab", Icons.Default.NetworkCheck, "net_group", "Network Lab", color = Color(0xFF00BCD4),
            description = "Network diagnostic and info tools.",
            subToolRoutes = listOf(
                "device_discovery", "dns_lookup", "mqtt_tester", "my_ip", "network_info", "ping",
                "port_checker", "port_scanner", "speed_test", "subnet_calc", "wake_on_lan", "whois",
                "wifi_anal", "http_request", "ssh_client"
            )),

        // --- FINANCE ---
        Tool("Finance Hub", Icons.Default.MonetizationOn, "finance_group", "Finance Hub", Color(0xFFFF9800),
            description = "Complete financial toolkit.",
            subToolRoutes = listOf(
                "coin_tracker", "compound_interest", "currency_trends", "dividend_calc", "expense_tracker",
                "gst_calc", "inflation_calc", "loan_calc", "mortgage_calc", "nft_viewer", "retirement_planner", "roi_calc",
                "salary_calc", "sip_calc", "stock_profit", "tax_calc", "wallet_explorer", "cagr_calc", "dcf_calc"
            )),

        // --- HEALTH ---
        Tool("Health & Vitality", Icons.Default.Favorite, "health_group", "Health & Vitality", Color(0xFFE91E63),
            description = "Track your health and habits.",
            subToolRoutes = listOf(
                "bmi", "bmr", "calorie_calc", "macro_splitter", "eye_exercise", "habit_tracker",
                "medication_tracker", "meditation", "period_tracker", "posture_check", "sleep_tracker",
                "step_counter", "stretch_guide", "water", "water_reminder", "yoga_guide",
                "heart_rate", "blood_pressure", "blood_sugar"
            )),

        // --- WEATHER ---
        Tool("Weather Center", Icons.Default.Cloud, "weather_group", "Weather Center", color = Color(0xFF03A9F4),
            description = "Forecasts and environment data.",
            subToolRoutes = listOf(
                "air_quality", "light_pollution", "moon_phase", "rain_radar", "uv_index",
                "weather_forecast", "weather_prediction", "tides"
            )),

        // --- TRAVEL ---
        Tool("Travel & Adventure", Icons.Default.Landscape, "outdoor_group", "Outdoor & Adventure", Color(0xFF8BC34A),
            description = "Essential tools for explorers.",
            subToolRoutes = listOf(
                "survival_guide", "beacon_nav", "path_tracking", "altitude_graph", "area_calc",
                "campfire_guide", "gps_status", "hiking_trails", "knots_guide", "packing_list",
                "route_planner", "signal_mirror", "sos", "travel_budget", "world_clock", "world_map"
            )),

        // --- DESIGN ---
        Tool("Design & Creative", Icons.Default.Palette, "design_tools_group", "Design & Creative", Color(0xFF00BCD4),
            description = "Design and creative exploration tools.",
            subToolRoutes = listOf(
                "color_conv_pro", "color_harmonies", "color_info", "color_mixing", "color_shading",
                "edit_palette", "generate_palette", "image_histogram", "material_you_palette",
                "drawing_board", "signature_maker"
            )),

        // --- DIY ---
        Tool("DIY & Home", Icons.Default.Home, "diy_home_group", "DIY & Home", color = Color(0xFF795548),
            description = "Home maintenance and auto tools.",
            subToolRoutes = listOf(
                "car_maintenance", "fuel", "fuel_consumption", "plant_care", "recipe_scaler", "speedometer"
            )),

        // --- LIFESTYLE ---
        Tool("Fashion & Size Hub", Icons.Default.Checkroom, "size_fit_group", "Fashion & Lifestyle", Color(0xFFE91E63),
            description = "Explore global fashion styles, regional size guides, and cultural heritage.",
            subToolRoutes = listOf(
                "clothing_sizes", "shoe_sizes", "ring_sizes", "bra_calculator", "body_measurements",
                "dress_guide", "clothes_guide", "headwear_guide", "footwear_guide", "accessories_guide",
                "fashion_guide", "traditional_fashion", "modern_fashion", "tribal_fashion", "indian_fashion",
                "world_fashion", "all_countries_sizes", "topwear_guide", "bottomwear_guide", "waistwear_guide"
            )),

        // --- SOCIAL ---
        Tool("Social Presence", Icons.Default.Share, "social_media_group", "Social Presence", color = Color(0xFFE91E63),
            description = "Manage your social profiles.",
            subToolRoutes = listOf("bio_linker", "profile_photo_maker", "social_preview")),

        // ==========================================
        // --- SUB-TOOLS (HIDDEN FROM HOME) ---
        // ==========================================

        // --- NEW SUB-TOOLS ---
        Tool("Triangulation", Icons.Default.Explore, "triangulate", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),

        // --- NEW SUB-TOOLS ---
        Tool("Water Purifier", Icons.Default.LocalDrink, "water_purify", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),

        // --- NEW SUB-TOOLS ---
        Tool("Emergency Whistle", Icons.Default.Campaign, "whistle", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),

        // --- NEW SUB-TOOLS ---
        Tool("White Noise", Icons.Default.NightsStay, "white_noise", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),

        // --- NEW SUB-TOOLS ---
        Tool("Lightning Distance", Icons.Default.FlashOn, "lightning", category = "Weather Center", isVisibleOnHome = false, isSubTool = true),

        // --- NEW SUB-TOOLS ---
        Tool("Solar Aligner", Icons.Default.WbSunny, "solar_panel", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),

        // --- NEW SUB-TOOLS ---
        Tool("Clinometer", Icons.Default.Architecture, "clinometer", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),

        // --- MEDIA SUB-TOOLS ---
        Tool("3D Audio", Icons.Default.Headset, "m_3d_audio", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("8D Audio", Icons.Default.Headset, "m_8d_audio", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Binaural Beats", Icons.Default.Headset, "binaural", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Add SFX", Icons.Default.AutoAwesome, "add_sfx", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("APNG to images", Icons.Default.Collections, "apng_to_images", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("APNG to JXL", Icons.Default.Animation, "apng_to_jxl", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Compressor", Icons.Default.Compress, "m_audio_compressor", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Converter", Icons.Default.Transform, "aud_conv", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Cutter", Icons.Default.ContentCut, "m_audio_cutter", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Editor", Icons.Default.Edit, "m_audio_editor", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Equalizer", Icons.Default.Equalizer, "aud_eq_v2", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Info", Icons.Default.Info, "aud_info_v2", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Joiner", Icons.Default.Link, "m_audio_joiner", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Loop", Icons.Default.Loop, "audio_loop", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Mixer", Icons.Default.Tune, "m_audio_mixer", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Normalizer", Icons.AutoMirrored.Filled.VolumeUp, "m_audio_normalizer", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Pan", Icons.AutoMirrored.Filled.AltRoute, "m_audio_pan", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Splitter", Icons.AutoMirrored.Filled.AltRoute, "m_audio_splitter", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Tag Editor", Icons.AutoMirrored.Filled.Label, "m_audio_tag_editor", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Background Remover", Icons.Default.LayersClear, "image_bg_remover", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Base64 Image Tools", Icons.Default.Code, "image_base64", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Bass Booster", Icons.Default.Speaker, "m_bass_booster", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Batch Image Pro", Icons.Default.Collections, "batch_img_pro_v2", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("BPM Counter", Icons.Default.Favorite, "bpm", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Chord Library", Icons.Default.LibraryMusic, "chord_lib", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Collage Maker", Icons.Default.AutoAwesomeMosaic, "image_collage", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Color Picker", Icons.Default.Palette, "image_color_picker", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Compare", Icons.Default.Compare, "image_compare", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Crop", Icons.Default.Crop, "image_crop", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Delete EXIF", Icons.Default.NoPhotography, "image_delete_exif", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Delete Segment", Icons.Default.Delete, "video_delete", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Digital Magnifier", Icons.Default.ZoomIn, "digital_magnifier", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Draw", Icons.Default.Brush, "image_draw", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Draw on background", Icons.Default.Brush, "image_draw_bg", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Echo Effect", Icons.Default.SettingsBackupRestore, "m_echo_effect", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Edit EXIF", Icons.Default.CameraAlt, "image_edit_exif", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Equalizer", Icons.Default.Equalizer, "m_equalizer", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Exif Viewer", Icons.Default.CameraAlt, "exif_viewer", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("File Format Converter", Icons.Default.Transform, "file_conv", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Filter", Icons.Default.FilterHdr, "image_filter", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Format Conversion", Icons.Default.Transform, "image_format_conv", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("GIF to images", Icons.Default.Collections, "gif_to_images", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("GIF to JXL", Icons.Default.Transform, "gif_to_jxl", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("GIF to WEBP", Icons.Default.Transform, "gif_to_webp", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("Guitar Tuner", Icons.Default.MusicNote, "guitar_tuner", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Image Cutting", Icons.Default.GridOn, "image_cutting", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Image Preview", Icons.Default.Image, "image_preview", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Image Stacking", Icons.Default.Layers, "image_stacking", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Image Stitching", Icons.Default.ViewArray, "image_stitching", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Image to APNG", Icons.Default.Transform, "image_to_apng", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to APNG", Icons.Default.Transform, "images_to_apng", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to GIF", Icons.Default.Animation, "images_to_gif", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to JXL", Icons.Default.Animation, "images_to_jxl", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to SVG", Icons.Default.Architecture, "image_to_svg", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to WEBP", Icons.Default.Transform, "image_to_webp", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("JPEG to JXL", Icons.Default.Transform, "jpeg_to_jxl", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("JXL to images", Icons.Default.Collections, "jxl_to_images", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("JXL to JPEG", Icons.Default.Transform, "jxl_to_jpeg", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("Karaoke Maker", Icons.Default.Mic, "m_karaoke_maker", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Key BPM Finder", Icons.Default.MusicNote, "key_bpm_finder", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Layers on background", Icons.Default.Layers, "image_layers_bg", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Layers on image", Icons.Default.Layers, "image_layers_img", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Loop Video", Icons.Default.Loop, "video_loop", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Mask Filter", Icons.Default.Texture, "image_mask_filter", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Metronome", Icons.Default.AvTimer, "metronome", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Mirror Tool", Icons.Default.CameraFront, "mirror_tool", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Mix Video Audio", Icons.Default.Tune, "mix_video_audio", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Multi Crop", Icons.Default.Crop, "multi_crop", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Multi Image Resize", Icons.Default.PhotoSizeSelectLarge, "multi_image_resize", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Mute Audio", Icons.AutoMirrored.Filled.VolumeOff, "m_mute_audio", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Noise Generation", Icons.Default.BlurOn, "image_noise_gen", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Noise Generator", Icons.Default.GraphicEq, "noise_generator", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("OCR", Icons.AutoMirrored.Filled.ManageSearch, "image_ocr", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Open project", Icons.Default.FolderOpen, "image_open_project", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Palette Tools", Icons.Default.ColorLens, "image_palette", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Pitch Changer", Icons.Default.Height, "m_audio_pitch", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Pixel Art Maker", Icons.Default.Grid4x4, "pixel_art", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Record Audio", Icons.Default.Mic, "record_audio", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Resize and Convert", Icons.Default.PhotoSizeSelectLarge, "image_resize_conv", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Resize by Limits", Icons.Default.AspectRatio, "image_resize_limits", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Resize by Weight", Icons.Default.Scale, "image_resize_weight", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Reverse Audio", Icons.Default.History, "m_reverse_audio", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Reverse Video", Icons.Default.History, "video_reverse", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Ringtone Maker", Icons.Default.Notifications, "m_ringtone_maker", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Silence Generator", Icons.Default.DoNotDisturbOn, "silence_generator", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Silence Remover", Icons.Default.SpeakerNotesOff, "m_silence_remover", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Silence Video", Icons.AutoMirrored.Filled.VolumeOff, "video_silence", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Single Edit", Icons.Default.Edit, "image_single_edit", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Smart Tools", Icons.Default.Psychology, "image_ai_tools", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Sound Mastering", Icons.Default.Insights, "sound_mastering", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Speech to Text", Icons.Default.Mic, "m_speech_to_text", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Speed Changer", Icons.Default.FastForward, "m_speed_changer", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Text to Speech", Icons.Default.RecordVoiceOver, "m_text_to_speech", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Thumbnail Extractor", Icons.Default.Image, "vid_thumb", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Compressor", Icons.Default.Compress, "video_compress", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Editor", Icons.Default.Edit, "video_trim", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Editor Pro", Icons.Default.Movie, "vid_edit_pro", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Flip", Icons.Default.Flip, "video_flip", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Frame Annotator", Icons.Default.Edit, "vid_annotator", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Frame Grabber", Icons.Default.CropOriginal, "frame_grabber", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video SFX", Icons.Default.AutoAwesome, "video_sfx", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Speed", Icons.Default.FastForward, "video_speed_changer", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Splitter", Icons.AutoMirrored.Filled.AltRoute, "video_splitter", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Stabilizer", Icons.Default.Camera, "video_stabilizer", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video to Audio", Icons.Default.VideoLibrary, "m_video_to_audio", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video To GIF", Icons.Default.Gif, "video_to_gif", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Volume", Icons.AutoMirrored.Filled.VolumeUp, "video_volume_booster", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Merger", Icons.Default.Merge, "video_merger", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Voice Changer", Icons.Default.Face, "m_voice_changer", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Voice Memo", Icons.Default.SettingsVoice, "voice_memo", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Volume Booster", Icons.AutoMirrored.Filled.VolumeUp, "m_volume_booster", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Wallpapers Export", Icons.Default.Wallpaper, "image_wallpapers", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Watermarking", Icons.AutoMirrored.Filled.BrandingWatermark, "image_watermark", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Wave Generator", Icons.Default.Waves, "wave_generator", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Noise Generator", Icons.Default.GraphicEq, "noise_generator", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Web Image Loading", Icons.Default.CloudDownload, "image_web_load", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("WEBP to images", Icons.Default.Collections, "webp_to_images", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Noise Remover", Icons.Default.BlurOff, "ai_noise_remover", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Stems Splitter", Icons.Default.MusicNote, "ai_stems_splitter", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Voice Mimic", Icons.Default.RecordVoiceOver, "ai_voice_mimic", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Master Pro", Icons.Default.SettingsVoice, "aud_master_pro", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Noise Remover", Icons.Default.GraphicEq, "audio_noise_remover", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Echo Remover", Icons.Default.SettingsBackupRestore, "echo_remover", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Reverb Remover", Icons.Default.Waves, "reverb_remover", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Vocal Autotuner", Icons.Default.MusicNote, "vocal_autotuner", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Vocal Remover", Icons.Default.MicOff, "vocal_remover", category = "Audio Lab", isVisibleOnHome = false, isSubTool = true),

        // --- EDUCATION SUB-TOOLS ---
        Tool("Constants Table", Icons.Default.Functions, "constants", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Constellations", Icons.Default.Stars, "constellations", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("DNA Visualizer", Icons.Default.Hub, "dna_viz", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Equation Solver", Icons.Default.Functions, "eq_solver", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Force Calculator", Icons.Default.Speed, "force_calc", category = "Engineering Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Formula Sheet", Icons.Default.Functions, "physics_formulas", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Fraction Calc", Icons.Default.Percent, "fraction_calc", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Matrix Calc", Icons.Default.Grid4x4, "matrix_calc", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Periodic Table", Icons.Default.GridOn, "periodic_table", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Planet Finder", Icons.Default.BrightnessHigh, "planet_finder", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Pokedex", Icons.Default.CatchingPokemon, "pokedex", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Prime Checker", Icons.Default.Filter7, "prime", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Solar System", Icons.Default.Public, "solar_system", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Star Map", Icons.Default.AutoAwesome, "star_map", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Statistics", Icons.Default.BarChart, "stats", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Truth Table Gen", Icons.AutoMirrored.Filled.ListAlt, "truth_table", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Unit Circle", Icons.Default.InvertColors, "unit_circle", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),

        // --- UTILITIES SUB-TOOLS ---
        Tool("Antenna Calc", Icons.Default.SettingsInputAntenna, "antenna_calc", category = "Engineering Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Binary Calc", Icons.Default.Numbers, "binary_calc", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Calculator", Icons.Default.Calculate, "calculator", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Checklist", Icons.Default.Checklist, "checklist", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Circuit Calc", Icons.Default.Memory, "circuit_calc", category = "Engineering Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Clock", Icons.Default.Schedule, "clock", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Crypto Converter", Icons.Default.CurrencyExchange, "crypto_conv", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Currency Converter", Icons.Default.CurrencyExchange, "currency", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Daily Journal", Icons.Default.EditNote, "daily_journal", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Daily Quotes", Icons.Default.FormatQuote, "daily_quotes", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Date Calc", Icons.Default.CalendarToday, "date_calc", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Discount Calc", Icons.Default.Percent, "discount", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Electronics Tools", Icons.Default.ElectricalServices, "electronics_tools", category = "Engineering Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Filter Designer", Icons.Default.FilterList, "filter_design", category = "Engineering Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Flashlight", Icons.Default.FlashlightOn, "flashlight", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Kanban Board", Icons.Default.ViewWeek, "kanban", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Logic Gates", Icons.Default.SettingsInputComponent, "logic_gates", category = "Engineering Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Mortgage Calc", Icons.Default.Home, "mortgage_calc", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Nature Tiles", Icons.Default.Widgets, "tiles_widgets", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Note Pad", Icons.Default.NoteAlt, "note", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Ohm's Law", Icons.Default.ElectricalServices, "ohms_law", category = "Engineering Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Panchangam", Icons.Default.CalendarMonth, "panchangam", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Zodiac Finder", Icons.Default.AutoAwesome, "zodiac", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Fashion & Size Hub", Icons.Default.Checkroom, "size_guide", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("PCB Trace Width", Icons.Default.Straighten, "pcb_trace", category = "Engineering Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Pomodoro", Icons.Default.HourglassEmpty, "pomodoro", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Protractor", Icons.Default.Architecture, "protractor", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("QR Generator", Icons.Default.QrCode, "qr_gen", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("QR Scanner", Icons.Default.QrCodeScanner, "qr_scanner", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Resistor Color Code", Icons.Default.Architecture, "resistor_code", category = "Engineering Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Ruler", Icons.Default.Straighten, "ruler", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Scientific Calc", Icons.Default.Functions, "sci_calc", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Signal Gen", Icons.Default.GraphicEq, "signal_gen_pro", category = "Engineering Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Smart Hub", Icons.Default.Hub, "smart_hub", category = "Engineering Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Stopwatch", Icons.Default.Timer, "stopwatch", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Task Board", Icons.Default.Dashboard, "task_board", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Time Logger", Icons.Default.HistoryToggleOff, "time_logger", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Tip Calc", Icons.Default.Receipt, "tip", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Torque Converter", Icons.Default.SyncAlt, "torque_conv", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Unit Converter", Icons.Default.SwapHoriz, "converter", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Unit Price Calc", Icons.Default.PriceCheck, "unit_price", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Unit Price Comparison", Icons.AutoMirrored.Filled.CompareArrows, "unit_compare", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Vibration Test", Icons.Default.Vibration, "vibration", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),
        Tool("Billing & Invoices", Icons.Default.Receipt, "billing", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Volume Calc", Icons.Default.VerticalAlignBottom, "volume_calc", category = "Math Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Wifi QR Generator", Icons.Default.Wifi, "wifi_qr", category = "Daily Helpers", isVisibleOnHome = false, isSubTool = true),

        // --- GAMES SUB-TOOLS ---
        Tool("Carroms", Icons.Default.Gamepad, "carroms", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Chess", Icons.Default.Gamepad, "chess", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Coin Flip", Icons.Default.Paid, "coin_flip", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Dice Roller", Icons.Default.Casino, "dice_roller", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Ludo", Icons.Default.Gamepad, "ludo", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Memory Game", Icons.Default.Extension, "memory_game", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Number Guessing", Icons.Default.QuestionMark, "number_guessing", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Random Gen", Icons.Default.Casino, "random", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Snake", Icons.Default.Gamepad, "snake", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Tic Tac Toe", Icons.Default.Close, "tic_tac_toe", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Game of Life", Icons.Default.Grid4x4, "game_of_life", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Clash Deck", Icons.Default.Style, "clash_deck", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Roulette", Icons.Default.Casino, "roulette", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Dino Jump", Icons.Default.Gamepad, "dino_jump", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("2048", Icons.Default.Grid4x4, "2048", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Sudoku", Icons.Default.GridOn, "sudoku", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),
        Tool("Minesweeper", Icons.Default.GridOn, "minesweeper", category = "Games & Fun", isVisibleOnHome = false, isSubTool = true),

        // --- DEVICE SUB-TOOLS ---
        Tool("Altimeter", Icons.Default.Landscape, "altimeter", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("App Info", Icons.Default.Apps, "app_info", category = "System Monitor", isVisibleOnHome = false, isSubTool = true),
        Tool("Barometer", Icons.Default.Compress, "barometer", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Battery", Icons.Default.BatteryFull, "battery", category = "System Monitor", isVisibleOnHome = false, isSubTool = true),
        Tool("Compass", Icons.Default.Explore, "compass", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("CPU Info", Icons.Default.Memory, "cpu_info", category = "System Monitor", isVisibleOnHome = false, isSubTool = true),
        Tool("Device Info", Icons.Default.Info, "device", category = "System Monitor", isVisibleOnHome = false, isSubTool = true),
        Tool("G-Force Meter", Icons.Default.Speed, "gforce_meter", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Hardware ID", Icons.Default.PermDeviceInformation, "device_id", category = "System Monitor", isVisibleOnHome = false, isSubTool = true),
        Tool("Level", Icons.Default.Architecture, "level", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Light Meter", Icons.Default.LightMode, "light", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Metal Detector", Icons.Default.CompassCalibration, "metal", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Process Manager", Icons.Default.Dns, "process_manager", category = "System Monitor", isVisibleOnHome = false, isSubTool = true),
        Tool("RAM Info", Icons.Default.Memory, "ram_info", category = "System Monitor", isVisibleOnHome = false, isSubTool = true),
        Tool("Sensor Data", Icons.Default.SettingsInputComponent, "sensor_data", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Sensors List", Icons.AutoMirrored.Filled.List, "sensors_list", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("SPL Meter", Icons.AutoMirrored.Filled.VolumeUp, "spl_meter", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Storage", Icons.Default.Storage, "storage", category = "System Monitor", isVisibleOnHome = false, isSubTool = true),
        Tool("System Lab Core", Icons.Default.Science, "system_lab", category = "System Monitor", isVisibleOnHome = false, isSubTool = true),
        Tool("Thermal Info", Icons.Default.DeviceThermostat, "thermal_info", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Update Check", Icons.Default.SystemUpdate, "update_check", category = "System Monitor", isVisibleOnHome = false, isSubTool = true),

        // --- DATA SUB-TOOLS ---
        Tool("Data Visualizer", Icons.Default.BarChart, "data_viz", category = "Data Science", isVisibleOnHome = false, isSubTool = true),
        Tool("JSON Format", Icons.Default.DataObject, "json", category = "Data Science", isVisibleOnHome = false, isSubTool = true),
        Tool("YAML to JSON", Icons.Default.Transform, "yaml_to_json", category = "Data Science", isVisibleOnHome = false, isSubTool = true),
        Tool("Anomaly Detection", Icons.Default.Warning, "anomaly_detection", category = "Data Science", isVisibleOnHome = false, isSubTool = true),
        Tool("Data Profiling", Icons.Default.AccountBox, "data_profiling", category = "Data Science", isVisibleOnHome = false, isSubTool = true),
        Tool("Statistics", Icons.AutoMirrored.Filled.ShowChart, "data_statistics", category = "Data Science", isVisibleOnHome = false, isSubTool = true),
        Tool("Visualisations", Icons.Default.PieChart, "data_visualisations", category = "Data Science", isVisibleOnHome = false, isSubTool = true),
        Tool("Synthetic Data Gen", Icons.Default.Science, "synthetic_data_gen", category = "Data Science", isVisibleOnHome = false, isSubTool = true),
        Tool("Data Quality", Icons.Default.CheckCircle, "data_quality", category = "Data Science", isVisibleOnHome = false, isSubTool = true),
        Tool("Data Cleaning", Icons.Default.CleaningServices, "data_cleaning", category = "Data Science", isVisibleOnHome = false, isSubTool = true),
        Tool("Data Transformation", Icons.Default.Transform, "data_transformation", category = "Data Science", isVisibleOnHome = false, isSubTool = true),

        // --- WEB SUB-TOOLS ---
        Tool("AI Image Pro", Icons.Default.Image, "per_image_pro", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Text Gen", Icons.Default.TextFields, "per_text_gen", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Text Rewriter", Icons.Default.Edit, "per_text_rewrite", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Media Grabber", Icons.Default.Download, "media_grabber", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Metatag Analyzer", Icons.Default.Search, "meta_anal", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("NECS Story", Icons.Default.AutoAwesome, "per_necs_story", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Epic Bookmarx", Icons.Default.Hub, "hub", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Perchance Character Maker", Icons.Default.Person, "per_character", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Perchance General Hub", Icons.Default.Hub, "per_hub", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Perchance Image Generator", Icons.Default.Image, "per_image", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Perchance Story Writer", Icons.Default.AutoAwesome, "per_story", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Web Search", Icons.Default.Search, "web", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),

        // --- NEW SUB-TOOLS ---
        Tool("Ballistics Calc", Icons.Default.Science, "ballistics", category = "Science Lab", isVisibleOnHome = false, isSubTool = true),

        // --- NEW SUB-TOOLS ---
        Tool("Cliff Height", Icons.Default.Landscape, "cliff_height", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),

        // --- AI SUB-TOOLS ---
        Tool("AI Chat Assistant", Icons.AutoMirrored.Filled.Chat, "ai_chat", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Code Helper", Icons.Default.Code, "ai_code", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Document Translator", Icons.Default.Translate, "ai_doc_translator", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Grammar Checker", Icons.Default.Spellcheck, "ai_grammar", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Image Generator", Icons.Default.Image, "ai_image", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Object Detector", Icons.Default.CenterFocusStrong, "ai_obj_detect", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Sentiment Analysis", Icons.Default.Mood, "ai_sentiment", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Text Summarizer", Icons.Default.Summarize, "ai_summarizer", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Text Extractor", Icons.Default.TextFields, "ai_text_ext", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Translator", Icons.Default.Translate, "ai_translate", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Noise Remover", Icons.Default.VideoSettings, "video_noise_remover", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),
        Tool("Virtual Try-On", Icons.Default.Face, "ai_tryon", category = "AI Companion", isVisibleOnHome = false, isSubTool = true),

        // --- SECURITY SUB-TOOLS ---
        Tool("App Locker", Icons.Default.Lock, "app_locker", category = "Security Vault", isVisibleOnHome = false, isSubTool = true),
        Tool("App Permissions", Icons.Default.Security, "app_permissions", category = "Security Vault", isVisibleOnHome = false, isSubTool = true),
        Tool("Cipher", Icons.Default.Security, "cipher_tools", category = "Security Vault", isVisibleOnHome = false, isSubTool = true),
        Tool("Password Manager", Icons.Default.Password, "password_manager", category = "Security Vault", isVisibleOnHome = false, isSubTool = true),
        Tool("Permission Manager", Icons.Default.ManageAccounts, "perm_manager", category = "Security Vault", isVisibleOnHome = false, isSubTool = true),
        Tool("Privacy Check", Icons.Default.PrivacyTip, "privacy_check", category = "Security Vault", isVisibleOnHome = false, isSubTool = true),
        Tool("Strong Password Gen", Icons.Default.VpnKey, "password_gen", category = "Security Vault", isVisibleOnHome = false, isSubTool = true),
        Tool("AdGuard DNS", Icons.Default.Shield, "sec_adguard", category = "Security Vault", isVisibleOnHome = false, isSubTool = true),
        Tool("NextDNS", Icons.Default.Dns, "sec_nextdns", category = "Security Vault", isVisibleOnHome = false, isSubTool = true),
        Tool("Bitwarden", Icons.Default.Lock, "sec_bitwarden", category = "Security Vault", isVisibleOnHome = false, isSubTool = true),
        Tool("Ente Auth", Icons.Default.VpnKey, "sec_ente", category = "Security Vault", isVisibleOnHome = false, isSubTool = true),

        // --- DOCUMENTS SUB-TOOLS ---
        Tool("Compress PDF", Icons.Default.Compress, "pdf_compress", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Crop PDF", Icons.Default.Crop, "pdf_crop", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("CSV to JSON", Icons.Default.Transform, "csv_to_json", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Doc Scanner", Icons.Default.Scanner, "doc_scanner", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Duplicate Finder", Icons.Default.ContentCopy, "duplicate_finder", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Extract Images PDF", Icons.Default.Image, "pdf_extract_images", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("File Explorer", Icons.Default.Folder, "file_explorer", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("File Shredder", Icons.Default.DeleteForever, "file_shredder", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Flatten PDF", Icons.Default.LayersClear, "pdf_flatten", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Grayscale PDF", Icons.Default.ColorLens, "pdf_grayscale", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to PDF", Icons.Default.Collections, "images_to_pdf", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Merge PDF", Icons.Default.Merge, "pdf_merge", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Page Numbers", Icons.Default.FormatListNumbered, "pdf_page_numbers", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("PDF Metadata", Icons.Default.Info, "pdf_metadata", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("PDF to Text (OCR)", Icons.Default.TextFields, "pdf_ocr", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Preview PDF", Icons.Default.Preview, "pdf_preview", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Print PDF", Icons.Default.Print, "pdf_print", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Protect PDF", Icons.Default.Lock, "pdf_protect", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Rearrange PDF", Icons.Default.Reorder, "pdf_rearrange", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Remove PDF pages", Icons.Default.Delete, "pdf_remove_pages", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Repair PDF", Icons.Default.Build, "pdf_repair", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Rotate PDF", Icons.AutoMirrored.Filled.RotateRight, "pdf_rotate", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("PDF Signature", Icons.Default.Draw, "pdf_signature", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Split PDF", Icons.AutoMirrored.Filled.CallSplit, "pdf_split", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("SQL Formatter", Icons.Default.Storage, "sql_format", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Online Document Tools", Icons.Default.Cloud, "docs_online", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Storage Cleaner", Icons.Default.CleaningServices, "storage_cleaner", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Unlock PDF", Icons.Default.LockOpen, "pdf_unlock", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Watermarking PDF", Icons.AutoMirrored.Filled.BrandingWatermark, "pdf_watermark", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Zip PDF", Icons.Default.FolderZip, "pdf_zip", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("HTML to PDF", Icons.Default.Html, "pdf_html_to_pdf", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Scan to PDF", Icons.Default.CameraAlt, "pdf_scan_to_pdf", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Fill Forms", Icons.Default.EditNote, "pdf_fill_forms", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Word to PDF", Icons.Default.Description, "pdf_word_to_pdf", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Excel to PDF", Icons.Default.TableChart, "pdf_excel_to_pdf", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Text to PDF", Icons.Default.TextFields, "pdf_text_to_pdf", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("QR to PDF", Icons.Default.QrCode, "pdf_qr_to_pdf", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Barcode to PDF", Icons.Default.QrCode, "pdf_barcode_to_pdf", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Invert PDF", Icons.Default.InvertColors, "pdf_invert", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Zip/Unzip", Icons.Default.FolderZip, "zip_unzip", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("PDF to MDX", Icons.Default.Description, "pdf_to_mdx", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("PDF to MHTML", Icons.Default.Html, "pdf_to_mhtml", category = "Productivity", isVisibleOnHome = false, isSubTool = true),
        Tool("Batch Converter", Icons.Default.Transform, "markitdown", category = "Productivity", isVisibleOnHome = false, isSubTool = true),

        // --- NETWORK SUB-TOOLS ---
        Tool("Device Discovery", Icons.Default.Search, "device_discovery", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("DNS Lookup", Icons.Default.Dns, "dns_lookup", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("MQTT Tester", Icons.Default.NetworkCheck, "mqtt_tester", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("My IP", Icons.Default.Public, "my_ip", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Network Details", Icons.Default.NetworkCheck, "network_info", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Ping", Icons.Default.SettingsEthernet, "ping", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Port Checker", Icons.Default.Dns, "port_checker", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Port Scanner", Icons.Default.Search, "port_scanner", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Speed Test", Icons.Default.Speed, "speed_test", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Subnet Calc", Icons.Default.SettingsEthernet, "subnet_calc", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Wake On LAN", Icons.Default.SettingsPower, "wake_on_lan", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Whois", Icons.Default.QuestionMark, "whois", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("WIFI Analyzer", Icons.Default.Wifi, "wifi_anal", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("HTTP Request", Icons.Default.Http, "http_request", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("SSH Client", Icons.Default.Terminal, "ssh_client", category = "Network Lab", isVisibleOnHome = false, isSubTool = true),

        // --- FINANCE SUB-TOOLS ---
        Tool("Coin Tracker", Icons.Default.MonetizationOn, "coin_tracker", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("CAGR Calculator", Icons.AutoMirrored.Filled.TrendingUp, "cagr_calc", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Compound Interest", Icons.AutoMirrored.Filled.TrendingUp, "compound_interest", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Currency Trends", Icons.Default.Timeline, "currency_trends", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("DCF Calculator", Icons.Default.AccountBalanceWallet, "dcf_calc", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Dividend Calc", Icons.Default.Payments, "dividend_calc", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Expense Tracker", Icons.Default.AccountBalanceWallet, "expense_tracker", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("GST Calculator", Icons.Default.RequestQuote, "gst_calc", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Inflation Calc", Icons.Default.MoneyOff, "inflation_calc", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Loan Calculator", Icons.Default.AccountBalance, "loan_calc", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("NFT Viewer", Icons.Default.Token, "nft_viewer", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Retirement Planner", Icons.Default.EventAvailable, "retirement_planner", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("ROI Calculator", Icons.AutoMirrored.Filled.ShowChart, "roi_calc", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Salary Calc", Icons.Default.Work, "salary_calc", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("SIP Calculator", Icons.Default.PieChart, "sip_calc", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Stock Profit", Icons.AutoMirrored.Filled.TrendingUp, "stock_profit", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Tax Calculator", Icons.Default.MoneyOff, "tax_calc", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),
        Tool("Wallet Explorer", Icons.Default.AccountBalanceWallet, "wallet_explorer", category = "Finance Hub", isVisibleOnHome = false, isSubTool = true),

        // --- HEALTH SUB-TOOLS ---
        Tool("BMI Calc", Icons.Default.AccessibilityNew, "bmi", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("BMR Calculator", Icons.Default.Calculate, "bmr", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Calorie Calc", Icons.Default.Restaurant, "calorie_calc", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Macro Splitter", Icons.Default.Restaurant, "macro_splitter", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Eye Exercise", Icons.Default.Visibility, "eye_exercise", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Habit Tracker", Icons.Default.EventRepeat, "habit_tracker", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Medication Tracker", Icons.Default.MedicalServices, "medication_tracker", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Meditation Timer", Icons.Default.SelfImprovement, "meditation", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Period Tracker", Icons.Default.CalendarMonth, "period_tracker", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Posture Checker", Icons.Default.Accessibility, "posture_check", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Sleep Tracker", Icons.Default.Bedtime, "sleep_tracker", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Step Counter", Icons.AutoMirrored.Filled.DirectionsRun, "step_counter", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Stretching Guide", Icons.Default.SelfImprovement, "stretch_guide", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Water Reminder", Icons.Default.NotificationsActive, "water_reminder", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Water Tracker", Icons.Default.LocalDrink, "water", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Heart Rate Monitor", Icons.Default.Favorite, "heart_rate", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Blood Pressure", Icons.Default.MonitorHeart, "blood_pressure", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Blood Sugar", Icons.Default.Bloodtype, "blood_sugar", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),
        Tool("Yoga Guide", Icons.Default.SelfImprovement, "yoga_guide", category = "Health & Vitality", isVisibleOnHome = false, isSubTool = true),

        // --- WEATHER SUB-TOOLS ---
        Tool("Air Quality", Icons.Default.Air, "air_quality", category = "Weather Center", isVisibleOnHome = false, isSubTool = true),
        Tool("Light Pollution", Icons.Default.NightsStay, "light_pollution", category = "Weather Center", isVisibleOnHome = false, isSubTool = true),
        Tool("Moon Phase", Icons.Default.Brightness3, "moon_phase", category = "Weather Center", isVisibleOnHome = false, isSubTool = true),
        Tool("Rain Radar", Icons.Default.Water, "rain_radar", category = "Weather Center", isVisibleOnHome = false, isSubTool = true),
        Tool("UV Index", Icons.Default.WbSunny, "uv_index", category = "Weather Center", isVisibleOnHome = false, isSubTool = true),
        Tool("Weather Forecast", Icons.Default.WbCloudy, "weather_forecast", category = "Weather Center", isVisibleOnHome = false, isSubTool = true),
        Tool("Tides", Icons.Default.Water, "tides", category = "Weather Center", isVisibleOnHome = false, isSubTool = true),
        Tool("Weather Prediction", Icons.Default.Cloud, "weather_prediction", category = "Weather Center", isVisibleOnHome = false, isSubTool = true),

        // --- TRAVEL SUB-TOOLS ---
        Tool("Survival Guide", Icons.Default.AutoStories, "survival_guide", category = "Survival Guide", isVisibleOnHome = false, isSubTool = true, description = "Comprehensive wilderness survival techniques."),
        Tool("Beacon Navigation", Icons.Default.Explore, "beacon_nav", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true, description = "Radar-style beacon tracking and navigation."),
        Tool("Altitude Graph", Icons.AutoMirrored.Filled.ShowChart, "altitude_graph", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Area Calculator", Icons.Default.SquareFoot, "area_calc", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Campfire Guide", Icons.Default.LocalFireDepartment, "campfire_guide", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Emergency SOS", Icons.Default.Sos, "sos", category = "Survival Guide", isVisibleOnHome = false, isSubTool = true),
        Tool("GPS Status", Icons.Default.GpsFixed, "gps_status", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Hiking Trails", Icons.AutoMirrored.Filled.DirectionsRun, "hiking_trails", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Knots Guide", Icons.Default.InvertColors, "knots_guide", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Packing List", Icons.Default.Checklist, "packing_list", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Route Planner", Icons.Default.Directions, "route_planner", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Signal Mirror", Icons.Default.FlashlightOn, "signal_mirror", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Travel Budgeter", Icons.Default.AttachMoney, "travel_budget", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("World Clock", Icons.Default.Public, "world_clock", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("World Map", Icons.Default.Map, "world_map", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),
        Tool("Path Tracking", Icons.Default.Route, "path_tracking", category = "Outdoor & Adventure", isVisibleOnHome = false, isSubTool = true),

        // --- DEVELOPER SUB-TOOLS ---
        Tool("Anagram Finder", Icons.Default.SortByAlpha, "anagram", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("ASCII Table", Icons.AutoMirrored.Filled.Notes, "ascii_table", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Base64 Tool", Icons.Default.Code, "base64", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Case Converter", Icons.Default.TextFields, "case_converter", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Crontab Gen", Icons.Default.Schedule, "crontab_gen", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Hex Viewer", Icons.Default.Numbers, "hex_viewer", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("JWT Tool", Icons.Default.Key, "jwt_tool", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Lorem Ipsum", Icons.AutoMirrored.Filled.Notes, "lorem", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Markdown Preview", Icons.Default.Description, "markdown_preview", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Morse Code", Icons.Default.Language, "morse", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Morse Decoder", Icons.Default.Language, "morse_decoder", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Regex Tester", Icons.Default.Code, "regex_tester", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Text Diff", Icons.Default.Difference, "text_diff", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("URL Encoder", Icons.Default.Link, "url_encoder", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("App Inspector", Icons.Default.Android, "app_inspector", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Word Counter", Icons.Default.Abc, "word_counter", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Word Frequency", Icons.AutoMirrored.Filled.ShowChart, "word_frequency", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Word Rank Calculator", Icons.Default.Calculate, "word_rank_calc", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Base Converter", Icons.Default.Numbers, "base_conv", category = "Developer Tools", isVisibleOnHome = false, isSubTool = true),

        // --- DESIGN SUB-TOOLS ---
        Tool("Color Converter", Icons.Default.Palette, "color_conv_pro", category = "Design & Creative", isVisibleOnHome = false, isSubTool = true),
        Tool("Color Harmonies", Icons.Default.Palette, "color_harmonies", category = "Design & Creative", isVisibleOnHome = false, isSubTool = true),
        Tool("Color Info", Icons.Default.Info, "color_info", category = "Design & Creative", isVisibleOnHome = false, isSubTool = true),
        Tool("Color Mixing", Icons.Default.InvertColors, "color_mixing", category = "Design & Creative", isVisibleOnHome = false, isSubTool = true),
        Tool("Color Shading", Icons.Default.Gradient, "color_shading", category = "Design & Creative", isVisibleOnHome = false, isSubTool = true),
        Tool("Edit Palette", Icons.Default.Edit, "edit_palette", category = "Design & Creative", isVisibleOnHome = false, isSubTool = true),
        Tool("Generate Palette", Icons.Default.ColorLens, "generate_palette", category = "Design & Creative", isVisibleOnHome = false, isSubTool = true),
        Tool("Histogram", Icons.Default.BarChart, "image_histogram", category = "Design & Creative", isVisibleOnHome = false, isSubTool = true),
        Tool("Material You", Icons.Default.AutoAwesome, "material_you_palette", category = "Design & Creative", isVisibleOnHome = false, isSubTool = true),
        Tool("Drawing Board", Icons.Default.Brush, "drawing_board", category = "Design & Creative", isVisibleOnHome = false, isSubTool = true),
        Tool("Signature Maker", Icons.Default.Draw, "signature_maker", category = "Design & Creative", isVisibleOnHome = false, isSubTool = true),

        // --- DIY SUB-TOOLS ---
        Tool("Car Maintenance", Icons.Default.Build, "car_maintenance", category = "DIY & Home", isVisibleOnHome = false, isSubTool = true),
        Tool("Fuel Consumption", Icons.Default.LocalGasStation, "fuel_consumption", category = "DIY & Home", isVisibleOnHome = false, isSubTool = true),
        Tool("Fuel Cost", Icons.Default.LocalGasStation, "fuel", category = "DIY & Home", isVisibleOnHome = false, isSubTool = true),
        Tool("Plant Care", Icons.Default.Eco, "plant_care", category = "DIY & Home", isVisibleOnHome = false, isSubTool = true),
        Tool("Recipe Scaler", Icons.Default.Scale, "recipe_scaler", category = "DIY & Home", isVisibleOnHome = false, isSubTool = true),
        Tool("Speedometer", Icons.Default.Speed, "speedometer", category = "DIY & Home", isVisibleOnHome = false, isSubTool = true),

        // --- LIFESTYLE SUB-TOOLS ---
        Tool("Fashion Materials", Icons.Default.Checkroom, "fashion_materials", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),

        Tool("Clothing Sizes", Icons.Default.Checkroom, "clothing_sizes", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Shoe Sizes", Icons.Default.DirectionsRun, "shoe_sizes", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Ring Sizes", Icons.Default.RadioButtonUnchecked, "ring_sizes", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Bra Calculator", Icons.Default.Calculate, "bra_calculator", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Body Measurements", Icons.Default.Straighten, "body_measurements", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Dress Guide", Icons.Default.Checkroom, "dress_guide", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Clothes Guide", Icons.Default.Checkroom, "clothes_guide", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Headwear Guide", Icons.Default.Checkroom, "headwear_guide", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Footwear Guide", Icons.Default.DirectionsRun, "footwear_guide", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Accessories Guide", Icons.Default.Watch, "accessories_guide", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Fashion Guide", Icons.Default.AutoAwesome, "fashion_guide", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Traditional Fashion", Icons.Default.HistoryEdu, "traditional_fashion", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Modern Fashion", Icons.Default.Checkroom, "modern_fashion", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Tribal Fashion", Icons.Default.Diversity3, "tribal_fashion", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Indian Fashion", Icons.Default.Festival, "indian_fashion", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("World Fashion", Icons.Default.Public, "world_fashion", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("All Countries Sizes", Icons.Default.Language, "all_countries_sizes", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Topwear Guide", Icons.Default.Checkroom, "topwear_guide", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Bottomwear Guide", Icons.Default.Checkroom, "bottomwear_guide", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Waistwear Guide", Icons.Default.Checkroom, "waistwear_guide", category = "Fashion & Lifestyle", isVisibleOnHome = false, isSubTool = true),

        // --- SOCIAL SUB-TOOLS ---
        Tool("Bio Linker", Icons.Default.Link, "bio_linker", category = "Social Presence", isVisibleOnHome = false, isSubTool = true),
        Tool("Profile Photo Maker", Icons.Default.AccountCircle, "profile_photo_maker", category = "Image Studio", isVisibleOnHome = false, isSubTool = true),
        Tool("Social Preview", Icons.Default.Share, "social_preview", category = "Social Presence", isVisibleOnHome = false, isSubTool = true)
    )
}
