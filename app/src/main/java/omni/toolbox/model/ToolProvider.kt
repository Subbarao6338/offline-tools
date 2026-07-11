package omni.toolbox.model

import android.content.Context
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
    var tools: List<Tool> = listOf(
        // ==========================================
        // --- 39 TOP-LEVEL MAIN TOOLS (VISIBLE) ---
        // ==========================================

        // 1. AI
        Tool("AI Hub", Icons.Default.AutoAwesome, "ai_group", "AI", Color(0xFF673AB7),
            description = "Multimodal AI assistant and generative lab.",
            subToolRoutes = listOf(
                "ai_chat", "ai_code", "ai_doc_translator", "ai_grammar", "ai_image",
                "ai_obj_detect", "ai_sentiment", "ai_summarizer", "ai_text_ext", "ai_translate",
                "face_swap", "video_noise_remover", "ai_tryon", "ai_companion", "perchance_tools"
            )),

        // 2. Automation
        Tool("Automation Lab", Icons.Default.SettingsInputComponent, "automation_group", "Automation", Color(0xFF673AB7),
            description = "System automation and macros without root.",
            subToolRoutes = listOf("automation")),

        // 3. Calculators
        Tool("Calculators Hub", Icons.Default.Calculate, "calc_group", "Calculators", Color(0xFFFF9800),
            description = "Practical calculators for daily use.",
            subToolRoutes = listOf("calculator", "discount", "tip", "unit_compare", "unit_price", "volume_calc", "billing", "area_calc", "bra_calculator", "underwear_calculator", "dress_calculator", "ring_calculator", "arm_calculator", "body_calculator", "kids_calculator", "word_rank_calc", "bangle_calculator")),

        // 4. Cloud & Local File Explorer
        Tool("Cloud & Local Files", Icons.Default.Folder, "file_explorer_group", "Cloud & Local File Explorer", Color(0xFF2196F3),
            description = "Manage local files and synchronize with multi-account cloud storage.",
            subToolRoutes = listOf("file_explorer", "cloud_sync")),

        // 5. General
        Tool("General Utilities", Icons.Default.Widgets, "general_group", "General", Color(0xFF4CAF50),
            description = "Everyday essential utilities.",
            subToolRoutes = listOf("tiles_widgets", "wifi_qr", "qr_scanner", "qr_gen")),

        // 6. Converters
        Tool("Converters Hub", Icons.Default.SwapHoriz, "conv_group", "Converters", Color(0xFF2196F3),
            description = "Convert between various unit, currency, and file systems.",
            subToolRoutes = listOf("base_conv", "converter", "crypto_conv", "currency", "torque_conv", "file_conv", "file_checksum")),

        // 7. Data Lab
        Tool("Data Lab Hub", Icons.Default.Storage, "data_tools_group", "Data Lab", Color(0xFF009688),
            description = "Process, visualize, and sanitize files and datasets.",
            subToolRoutes = listOf(
                "data_viz", "json", "yaml_to_json", "anomaly_detection", "data_profiling",
                "data_statistics", "data_visualisations", "synthetic_data_gen", "data_quality",
                "data_cleaning", "data_transformation", "web_scraper"
            )),

        // 8. Date & Time
        Tool("Date & Time Hub", Icons.Default.Schedule, "date_time_group", "Date & Time", Color(0xFF4CAF50),
            description = "Precise time tracking, timezone, and calendar tools.",
            subToolRoutes = listOf("clock", "date_calc", "stopwatch", "world_clock", "panchangam", "zodiac")),

        // 9. Design & Creative
        Tool("Design & Creative Studio", Icons.Default.Palette, "design_tools_group", "Design & Creative", Color(0xFF00BCD4),
            description = "Design, paint, sign, and build palettes.",
            subToolRoutes = listOf(
                "color_conv_pro", "color_harmonies", "color_info", "color_mixing", "color_shading",
                "edit_palette", "generate_palette", "image_histogram", "material_you_palette",
                "drawing_board", "signature_maker"
            )),

        // 10. Developer
        Tool("Developer Suite", Icons.Default.Code, "dev_group", "Developer", Color(0xFF3F51B5),
            description = "Advanced software developer inspect and formatting tools.",
            subToolRoutes = listOf(
                "ascii_table", "base64", "crontab_gen", "hex_viewer", "jwt_tool", "markdown_preview",
                "regex_tester", "url_encoder", "app_inspector", "terminal", "anagram", "case_converter",
                "lorem", "morse", "morse_decoder", "text_diff", "word_counter", "word_frequency",
                "developer_console"
            )),

        // 11. Documents
        Tool("Documents & Office", Icons.Default.Description, "docs_group", "Documents", Color(0xFF607D8B),
            description = "Comprehensive office tools, scanners, compress, and PDF toolkit.",
            subToolRoutes = listOf(
                "csv_to_json", "doc_scanner", "duplicate_finder", "file_shredder", "sql_format",
                "storage_cleaner", "zip_unzip", "docs_online", "markitdown", "images_to_pdf",
                "pdf_compress", "pdf_crop", "pdf_extract_images", "pdf_flatten", "pdf_grayscale",
                "pdf_merge", "pdf_metadata", "pdf_ocr", "pdf_page_numbers", "pdf_preview", "pdf_print",
                "pdf_protect", "pdf_rearrange", "pdf_remove_pages", "pdf_repair", "pdf_rotate",
                "pdf_signature", "pdf_split", "pdf_unlock", "pdf_watermark", "pdf_zip", "pdf_html_to_pdf",
                "pdf_scan_to_pdf", "pdf_fill_forms", "pdf_word_to_pdf", "pdf_excel_to_pdf", "pdf_text_to_pdf",
                "pdf_qr_to_pdf", "pdf_barcode_to_pdf", "pdf_invert", "pdf_to_mdx", "pdf_to_mhtml"
            )),

        // 12. Education
        Tool("Education Lab", Icons.Default.Science, "education_group", "Education", Color(0xFF4CAF50),
            description = "Explore interactive learning, chemical elements, stars, and formulas.",
            subToolRoutes = listOf("constants", "constellations", "dna_viz", "physics_formulas", "periodic_table",
                "planet_finder", "pokedex", "prime", "solar_system", "star_map", "unit_circle", "antenna_calc", "filter_design", "logic_gates", "pcb_trace", "resistor_code", "signal_gen_pro", "force_calc", "ohms_law", "circuit_calc", "electronics_tools", "smart_hub")),

        // 13. Finance
        Tool("Finance Toolkit", Icons.Default.MonetizationOn, "finance_group", "Finance", Color(0xFFFF9800),
            description = "Financial planner, inflation, ROI, and mortgage calculators.",
            subToolRoutes = listOf(
                "coin_tracker", "compound_interest", "currency_trends", "dividend_calc", "expense_tracker",
                "gst_calc", "inflation_calc", "loan_calc", "mortgage_calc", "nft_viewer", "retirement_planner",
                "roi_calc", "salary_calc", "sip_calc", "stock_profit", "tax_calc", "wallet_explorer", "cagr_calc", "dcf_calc"
            )),

        // 14. Food & Drink
        Tool("Food & Drink Kitchen", Icons.Default.Restaurant, "food_drink_group", "Food & Drink", Color(0xFFFF9800),
            description = "Interactive recipes scaling, hydration track, and culinary conversions.",
            subToolRoutes = listOf("recipe_scaler", "water", "food_drink_companion")),

        // 15. Games
        Tool("Game Hub", Icons.Default.Gamepad, "game_group", "Games", Color(0xFFFF9800),
            description = "Enjoy simple offline classic and fun games.",
            subToolRoutes = listOf(
                "coin_flip", "dice_roller", "memory_game", "number_guessing", "random", "tic_tac_toe",
                "snake", "ludo", "carroms", "game_of_life", "clash_deck", "roulette",
                "dino_jump", "2048", "sudoku", "minesweeper"
            )),

        // 16. GIF & Animation
        Tool("GIF & Animation Studio", Icons.Default.Animation, "gif_tools_group", "GIF & Animation", Color(0xFF9C27B0),
            description = "Create, split, and optimize highly responsive animations.",
            subToolRoutes = listOf(
                "apng_to_images", "apng_to_jxl", "gif_to_images", "gif_to_jxl", "gif_to_webp",
                "image_to_apng", "images_to_apng", "images_to_gif", "images_to_jxl", "jpeg_to_jxl",
                "jxl_to_images", "jxl_to_jpeg"
            )),

        // 17. Health & Fitness
        Tool("Health & Fitness Center", Icons.Default.Favorite, "health_group", "Health & Fitness", Color(0xFFE91E63),
            description = "Track workouts, calories, daily habits, and active progress.",
            subToolRoutes = listOf(
                "bmi", "bmr", "calorie_calc", "macro_splitter", "habit_tracker", "meditation",
                "sleep_tracker", "step_counter", "stretch_guide", "water_reminder", "yoga_guide",
                "heart_rate", "blood_pressure", "blood_sugar", "eye_exercise", "medication_tracker",
                "period_tracker", "posture_check"
            )),

        // 18. Home
        Tool("Home & DIY Companion", Icons.Default.Home, "home_diy_group", "Home", Color(0xFF795548),
            description = "Maintain household plants, track fuel consumption, and manage vehicle logs.",
            subToolRoutes = listOf("car_maintenance", "fuel", "fuel_consumption", "plant_care", "speedometer")),

        // 19. Image Lab
        Tool("Image Processing Lab", Icons.Default.PhotoLibrary, "image_tools_group", "Image Lab", Color(0xFF2196F3),
            description = "Powerful single-edit, format converter, draw, collage, and metadata viewer.",
            subToolRoutes = listOf(
                "batch_img_pro_v2", "exif_viewer", "image_ai_tools", "image_base64", "image_bg_remover",
                "image_collage", "image_color_picker", "image_compare", "image_crop", "image_cutting",
                "image_delete_exif", "image_draw", "image_draw_bg", "image_edit_exif", "image_filter",
                "image_format_conv", "image_layers_bg", "image_layers_img", "image_mask_filter",
                "image_noise_gen", "image_ocr", "image_open_project", "image_palette", "image_preview",
                "image_resize_conv", "image_resize_limits", "image_resize_weight", "image_single_edit",
                "image_stacking", "image_stitching", "image_to_svg", "image_to_webp", "image_wallpapers",
                "image_watermark", "image_web_load", "multi_crop", "multi_image_resize", "pixel_art",
                "webp_to_images", "digital_magnifier", "mirror_tool"
            )),

        // 20. Lifestyle
        Tool("Lifestyle Hub", Icons.Default.Checkroom, "lifestyle_group", "Lifestyle", Color(0xFFE91E63),
            description = "Explore fashion, cultural size guides, regional heritage, and panchangam.",
            subToolRoutes = listOf(
                "clothing_sizes", "shoe_sizes", "ring_sizes", "body_measurements", "dress_guide",
                "clothes_guide", "headwear_guide", "footwear_guide", "accessories_guide", "fashion_guide",
                "traditional_fashion", "modern_fashion", "tribal_fashion", "indian_fashion", "world_fashion",
                "all_countries_sizes", "topwear_guide", "bottomwear_guide", "waistwear_guide", "size_guide", "fashion_materials"
            )),

        // 21. Navigation
        Tool("Navigation & Maps", Icons.Default.Map, "nav_group", "Navigation", Color(0xFF8BC34A),
            description = "Route mapping, coordinates tracking, and offline triangulation.",
            subToolRoutes = listOf("beacon_nav", "route_planner", "world_map", "gps_status", "triangulate", "path_tracking")),

        // 22. Maths
        Tool("Maths Suite", Icons.Default.Functions, "math_group", "Maths", Color(0xFF607D8B),
            description = "Advanced analytical, equations solver, and matrix helpers.",
            subToolRoutes = listOf("binary_calc", "fraction_calc", "matrix_calc", "sci_calc", "stats", "truth_table", "eq_solver")),

        // 24. Misc
        Tool("Miscellaneous Helper", Icons.Default.MoreHoriz, "misc_group", "Misc", Color(0xFF607D8B),
            description = "Daily quote inspiration, and customizable Quick Settings shade Tiles.",
            subToolRoutes = listOf("daily_quotes", "quick_tiles")),

        // 25. Music & Audio
        Tool("Music & Audio Hub", Icons.Default.LibraryMusic, "audio_tools_group", "Music & Audio", Color(0xFFE91E63),
            description = "Vocal remover, metronome, sound equalizer, and advanced audio processor.",
            subToolRoutes = listOf(
                "piano",
                "add_sfx", "aud_conv", "aud_eq_v2", "aud_info_v2", "audio_loop", "binaural", "bpm",
                "chord_lib", "guitar_tuner", "key_bpm_finder", "m_3d_audio", "m_8d_audio", "m_audio_compressor",
                "m_audio_cutter", "m_audio_editor", "m_audio_joiner", "m_audio_mixer", "m_audio_normalizer",
                "m_audio_pan", "m_audio_pitch", "m_audio_splitter", "m_audio_tag_editor", "m_bass_booster",
                "m_echo_effect", "m_equalizer", "m_karaoke_maker", "m_mute_audio", "m_reverse_audio",
                "m_ringtone_maker", "m_silence_remover", "m_speech_to_text", "m_speed_changer",
                "m_text_to_speech", "m_voice_changer", "m_volume_booster", "metronome", "noise_generator",
                "record_audio", "silence_generator", "sound_mastering", "voice_memo", "wave_generator",
                "ai_noise_remover", "ai_stems_splitter", "ai_voice_mimic", "aud_master_pro",
                "audio_noise_remover", "echo_remover", "reverb_remover", "vocal_autotuner", "vocal_remover",
                "whistle", "white_noise"
            )),

        // 26. Network
        Tool("Network Diagnostics", Icons.Default.NetworkCheck, "net_group", "Network", Color(0xFF00BCD4),
            description = "Speed test, LAN wake-up, DNS lookup, and HTTP protocol testing suite.",
            subToolRoutes = listOf(
                "device_discovery", "dns_lookup", "mqtt_tester", "my_ip", "network_info", "ping",
                "port_checker", "port_scanner", "speed_test", "subnet_calc", "wake_on_lan", "whois",
                "wifi_anal", "http_request", "ssh_client"
            )),

        // 27. News
        Tool("News Feed Reader", Icons.Default.Newspaper, "news_group", "News", Color(0xFF03A9F4),
            description = "Interactive feed client for technological and scientific headlines.",
            subToolRoutes = listOf("news_companion")),

        // 28. Privacy & Security
        Tool("Privacy & Security Vault", Icons.Default.Security, "security_group", "Privacy & Security", Color(0xFF607D8B),
            description = "Secure password locker, cipher tools, permission managers, and cryptographic modules.",
            subToolRoutes = listOf(
                "app_locker", "app_permissions", "cipher_tools", "password_gen", "password_manager",
                "perm_manager", "privacy_check", "security_vault"
            )),

        // 29. Productivity
        Tool("Productivity Center", Icons.Default.Task, "prod_group", "Productivity", Color(0xFF3F51B5),
            description = "Keep focused with Pomodoro, Kanban boards, checklists, and active journaling.",
            subToolRoutes = listOf("checklist", "daily_journal", "kanban", "note", "pomodoro", "task_board", "time_logger")),

        // 30. Shopping
        Tool("Shopping Assistant", Icons.Default.ShoppingCart, "shopping_group", "Shopping", Color(0xFF4CAF50),
            description = "Fully-operational shopping cart organizer and real-time total calculator.",
            subToolRoutes = listOf("shopping_companion")),

        // 31. Social
        Tool("Social Presence Hub", Icons.Default.Share, "social_media_group", "Social", Color(0xFFE91E63),
            description = "Optimize biography links and preview social media formatting.",
            subToolRoutes = listOf("bio_linker", "profile_photo_maker", "social_preview")),

        // 32. Sports
        Tool("Sports Training Coach", Icons.AutoMirrored.Filled.DirectionsRun, "sports_group", "Sports", Color(0xFFE91E63),
            description = "Precision interval timers and split laps recording.",
            subToolRoutes = listOf("sports_companion")),

        // 33. System Tools
        Tool("System Monitor Hub", Icons.Default.Dns, "system_group", "System Tools", Color(0xFF607D8B),
            description = "Continuous hardware diagnostic, memory, processor, and diagnostic tools.",
            subToolRoutes = listOf(
                "app_info", "battery", "cpu_info", "ram_info", "device", "device_id",
                "process_manager", "storage", "system_lab", "update_check", "dashboard", "power_bench", "telemetry_stats"
            )),

        // 34. Tools
        Tool("Daily Tools & Helpers", Icons.Default.Build, "tools_helper_group", "Tools", Color(0xFF4CAF50),
            description = "Handy utilities like precision rulers, flashlights, and angle measurers.",
            subToolRoutes = listOf("flashlight", "vibration", "ruler", "protractor")),

        // 35. Travel & Local
        Tool("Travel & Adventure Guides", Icons.Default.Landscape, "outdoor_group", "Travel & Local", Color(0xFF8BC34A),
            description = "Emergency SOS signaling, wilderness survival handbooks, and altitude graphing.",
            subToolRoutes = listOf(
                "survival_guide", "campfire_guide", "hiking_trails", "knots_guide", "packing_list",
                "signal_mirror", "sos", "travel_budget", "cliff_height", "altitude_graph"
            )),

        // 36. Video Lab
        Tool("Video Processing Lab", Icons.Default.VideoLibrary, "video_tools_group", "Video Lab", Color(0xFFF44336),
            description = "Compress, flip, split, merge, and extract audio tracks from recordings.",
            subToolRoutes = listOf(
                "frame_grabber", "m_video_to_audio", "mix_video_audio", "vid_annotator", "vid_edit_pro",
                "vid_thumb", "video_compress", "video_delete", "video_flip", "video_loop", "video_reverse",
                "video_sfx", "video_silence", "video_speed_changer", "video_splitter", "video_stabilizer",
                "video_to_gif", "video_trim", "video_volume_booster", "video_merger"
            )),

        // 37. Weather
        Tool("Weather & Forecast", Icons.Default.Cloud, "weather_group", "Weather", Color(0xFF03A9F4),
            description = "Live UV radiation, air metrics, tidal shifts, and lightning metrics.",
            subToolRoutes = listOf(
                "air_quality", "light_pollution", "moon_phase", "rain_radar", "uv_index",
                "weather_forecast", "weather_prediction", "tides", "lightning"
            )),

        // 38. Web Tools
        Tool("Web Search & Grabber", Icons.Default.Language, "web_tools_group", "Web Tools", Color(0xFF2196F3),
            description = "Online meta-tag visualizers and downloader assistance.",
            subToolRoutes = listOf("hub", "media_grabber", "meta_anal", "web", "youtube_utility")),

        // 39. Sensors
        Tool("Sensors & Diagnostics", Icons.Default.Sensors, "sensor_group", "Sensors", Color(0xFF673AB7),
            description = "Calibrate digital compasses, light readers, levels, and metal magnetometers.",
            subToolRoutes = listOf(
                "altimeter", "barometer", "compass", "gforce_meter", "level", "light", "metal",
                "sensor_data", "sensors_list", "spl_meter", "thermal_info", "clinometer", "solar_panel"
            )),


        // ==========================================
        // --- SUB-TOOLS (HIDDEN FROM HOME) ---
        // ==========================================

        // --- NEW COMPANIONS ---
        Tool("News Companion", Icons.Default.Newspaper, "news_companion", category = "News", isVisibleOnHome = false, isSubTool = true),
        Tool("Shopping Companion", Icons.Default.ShoppingCart, "shopping_companion", category = "Shopping", isVisibleOnHome = false, isSubTool = true),
        Tool("Sports Companion", Icons.AutoMirrored.Filled.DirectionsRun, "sports_companion", category = "Sports", isVisibleOnHome = false, isSubTool = true),
        Tool("Food & Drink Companion", Icons.Default.Restaurant, "food_drink_companion", category = "Food & Drink", isVisibleOnHome = false, isSubTool = true),

        // --- GENERAL ---
        Tool("Triangulation", Icons.Default.Explore, "triangulate", category = "Navigation", isVisibleOnHome = false, isSubTool = true),
        Tool("Water Purifier", Icons.Default.LocalDrink, "water_purify", category = "Food & Drink", isVisibleOnHome = false, isSubTool = true),
        Tool("Emergency Whistle", Icons.Default.Campaign, "whistle", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("White Noise", Icons.Default.NightsStay, "white_noise", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Lightning Distance", Icons.Default.FlashOn, "lightning", category = "Weather", isVisibleOnHome = false, isSubTool = true),
        Tool("Solar Aligner", Icons.Default.WbSunny, "solar_panel", category = "Sensors", isVisibleOnHome = false, isSubTool = true),
        Tool("Clinometer", Icons.Default.Architecture, "clinometer", category = "Sensors", isVisibleOnHome = false, isSubTool = true),

        // --- MUSIC & AUDIO ---
        Tool("3D Audio", Icons.Default.Headset, "m_3d_audio", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("8D Audio", Icons.Default.Headset, "m_8d_audio", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Binaural Beats", Icons.Default.Headset, "binaural", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Add SFX", Icons.Default.AutoAwesome, "add_sfx", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Compressor", Icons.Default.Compress, "m_audio_compressor", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Converter", Icons.Default.Transform, "aud_conv", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Cutter", Icons.Default.ContentCut, "m_audio_cutter", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Editor", Icons.Default.Edit, "m_audio_editor", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Equalizer", Icons.Default.Equalizer, "aud_eq_v2", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Info", Icons.Default.Info, "aud_info_v2", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Joiner", Icons.Default.Link, "m_audio_joiner", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Loop", Icons.Default.Loop, "audio_loop", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Mixer", Icons.Default.Tune, "m_audio_mixer", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Normalizer", Icons.AutoMirrored.Filled.VolumeUp, "m_audio_normalizer", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Pan", Icons.AutoMirrored.Filled.AltRoute, "m_audio_pan", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Splitter", Icons.AutoMirrored.Filled.AltRoute, "m_audio_splitter", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Tag Editor", Icons.AutoMirrored.Filled.Label, "m_audio_tag_editor", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Bass Booster", Icons.Default.Speaker, "m_bass_booster", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("BPM Counter", Icons.Default.Favorite, "bpm", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Chord Library", Icons.Default.LibraryMusic, "chord_lib", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Echo Effect", Icons.Default.SettingsBackupRestore, "m_echo_effect", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Equalizer", Icons.Default.Equalizer, "m_equalizer", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Guitar Tuner", Icons.Default.MusicNote, "guitar_tuner", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Karaoke Maker", Icons.Default.Mic, "m_karaoke_maker", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Key BPM Finder", Icons.Default.MusicNote, "key_bpm_finder", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Metronome", Icons.Default.AvTimer, "metronome", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Mute Audio", Icons.AutoMirrored.Filled.VolumeOff, "m_mute_audio", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Noise Generator", Icons.Default.GraphicEq, "noise_generator", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Record Audio", Icons.Default.Mic, "record_audio", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Reverse Audio", Icons.Default.History, "m_reverse_audio", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Ringtone Maker", Icons.Default.Notifications, "m_ringtone_maker", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Silence Generator", Icons.Default.DoNotDisturbOn, "silence_generator", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Silence Remover", Icons.Default.SpeakerNotesOff, "m_silence_remover", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Sound Mastering", Icons.Default.Insights, "sound_mastering", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Speech to Text", Icons.Default.Mic, "m_speech_to_text", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Speed Changer", Icons.Default.FastForward, "m_speed_changer", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Text to Speech", Icons.Default.RecordVoiceOver, "m_text_to_speech", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Voice Changer", Icons.Default.Face, "m_voice_changer", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Voice Memo", Icons.Default.SettingsVoice, "voice_memo", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Volume Booster", Icons.AutoMirrored.Filled.VolumeUp, "m_volume_booster", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Wave Generator", Icons.Default.Waves, "wave_generator", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Noise Remover", Icons.Default.BlurOff, "ai_noise_remover", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Stems Splitter", Icons.Default.MusicNote, "ai_stems_splitter", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Voice Mimic", Icons.Default.RecordVoiceOver, "ai_voice_mimic", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Master Pro", Icons.Default.SettingsVoice, "aud_master_pro", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Audio Noise Remover", Icons.Default.GraphicEq, "audio_noise_remover", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Echo Remover", Icons.Default.SettingsBackupRestore, "echo_remover", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Reverb Remover", Icons.Default.Waves, "reverb_remover", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Vocal Autotuner", Icons.Default.MusicNote, "vocal_autotuner", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),
        Tool("Vocal Remover", Icons.Default.MicOff, "vocal_remover", category = "Music & Audio", isVisibleOnHome = false, isSubTool = true),

        // --- GIF & ANIMATION ---
        Tool("APNG to images", Icons.Default.Collections, "apng_to_images", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("APNG to JXL", Icons.Default.Animation, "apng_to_jxl", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("GIF to images", Icons.Default.Collections, "gif_to_images", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("GIF to JXL", Icons.Default.Transform, "gif_to_jxl", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("GIF to WEBP", Icons.Default.Transform, "gif_to_webp", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("Image to APNG", Icons.Default.Transform, "image_to_apng", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to APNG", Icons.Default.Transform, "images_to_apng", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to GIF", Icons.Default.Animation, "images_to_gif", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to JXL", Icons.Default.Animation, "images_to_jxl", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("JPEG to JXL", Icons.Default.Transform, "jpeg_to_jxl", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("JXL to images", Icons.Default.Collections, "jxl_to_images", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),
        Tool("JXL to JPEG", Icons.Default.Transform, "jxl_to_jpeg", category = "GIF & Animation", isVisibleOnHome = false, isSubTool = true),

        // --- IMAGE LAB ---
        Tool("Background Remover", Icons.Default.LayersClear, "image_bg_remover", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Base64 Image Tools", Icons.Default.Code, "image_base64", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Batch Image Pro", Icons.Default.Collections, "batch_img_pro_v2", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Collage Maker", Icons.Default.AutoAwesomeMosaic, "image_collage", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Color Picker", Icons.Default.Palette, "image_color_picker", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Compare", Icons.Default.Compare, "image_compare", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Crop", Icons.Default.Crop, "image_crop", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Delete EXIF", Icons.Default.NoPhotography, "image_delete_exif", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Digital Magnifier", Icons.Default.ZoomIn, "digital_magnifier", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Draw", Icons.Default.Brush, "image_draw", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Draw on background", Icons.Default.Brush, "image_draw_bg", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Edit EXIF", Icons.Default.CameraAlt, "image_edit_exif", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Exif Viewer", Icons.Default.CameraAlt, "exif_viewer", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Filter", Icons.Default.FilterHdr, "image_filter", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Format Conversion", Icons.Default.Transform, "image_format_conv", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Image Cutting", Icons.Default.GridOn, "image_cutting", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Image Preview", Icons.Default.Image, "image_preview", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Image Stacking", Icons.Default.Layers, "image_stacking", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Image Stitching", Icons.Default.ViewArray, "image_stitching", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to SVG", Icons.Default.Architecture, "image_to_svg", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to WEBP", Icons.Default.Transform, "image_to_webp", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Layers on background", Icons.Default.Layers, "image_layers_bg", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Layers on image", Icons.Default.Layers, "image_layers_img", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Mask Filter", Icons.Default.Texture, "image_mask_filter", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Mirror Tool", Icons.Default.CameraFront, "mirror_tool", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Multi Crop", Icons.Default.Crop, "multi_crop", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Multi Image Resize", Icons.Default.PhotoSizeSelectLarge, "multi_image_resize", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Noise Generation", Icons.Default.BlurOn, "image_noise_gen", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("OCR", Icons.AutoMirrored.Filled.ManageSearch, "image_ocr", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Open project", Icons.Default.FolderOpen, "image_open_project", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Palette Tools", Icons.Default.ColorLens, "image_palette", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Pixel Art Maker", Icons.Default.Grid4x4, "pixel_art", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Resize and Convert", Icons.Default.PhotoSizeSelectLarge, "image_resize_conv", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Resize by Limits", Icons.Default.AspectRatio, "image_resize_limits", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Resize by Weight", Icons.Default.Scale, "image_resize_weight", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Single Edit", Icons.Default.Edit, "image_single_edit", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Smart Tools", Icons.Default.Psychology, "image_ai_tools", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Wallpapers Export", Icons.Default.Wallpaper, "image_wallpapers", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Watermarking", Icons.AutoMirrored.Filled.BrandingWatermark, "image_watermark", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Web Image Loading", Icons.Default.CloudDownload, "image_web_load", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("WEBP to images", Icons.Default.Collections, "webp_to_images", category = "Image Lab", isVisibleOnHome = false, isSubTool = true),

        // --- EDUCATION ---
        Tool("Constants Table", Icons.Default.Functions, "constants", category = "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Constellations", Icons.Default.Stars, "constellations", category = "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("DNA Visualizer", Icons.Default.Hub, "dna_viz", category = "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Formula Sheet", Icons.Default.Functions, "physics_formulas", category = "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Periodic Table", Icons.Default.GridOn, "periodic_table", category = "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Planet Finder", Icons.Default.BrightnessHigh, "planet_finder", category = "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Pokedex", Icons.Default.CatchingPokemon, "pokedex", category = "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Prime Checker", Icons.Default.Filter7, "prime", category = "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Solar System", Icons.Default.Public, "solar_system", category = "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Star Map", Icons.Default.AutoAwesome, "star_map", category = "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Unit Circle", Icons.Default.InvertColors, "unit_circle", category = "Education", isVisibleOnHome = false, isSubTool = true),

        // --- CONVERTERS ---
        Tool("Checksum Verifier", Icons.Default.VerifiedUser, "file_checksum", category = "Converters", isVisibleOnHome = false, isSubTool = true),
        Tool("Base Converter", Icons.Default.Numbers, "base_conv", category = "Converters", isVisibleOnHome = false, isSubTool = true),
        Tool("Unit Converter", Icons.Default.SwapHoriz, "converter", category = "Converters", isVisibleOnHome = false, isSubTool = true),
        Tool("Crypto Converter", Icons.Default.CurrencyExchange, "crypto_conv", category = "Converters", isVisibleOnHome = false, isSubTool = true),
        Tool("Currency Converter", Icons.Default.CurrencyExchange, "currency", category = "Converters", isVisibleOnHome = false, isSubTool = true),
        Tool("Torque Converter", Icons.Default.SyncAlt, "torque_conv", category = "Converters", isVisibleOnHome = false, isSubTool = true),
        Tool("File Format Converter", Icons.Default.Transform, "file_conv", category = "Converters", isVisibleOnHome = false, isSubTool = true),

        // --- GENERAL ---
        Tool("Nature Tiles", Icons.Default.Widgets, "tiles_widgets", category = "General", isVisibleOnHome = false, isSubTool = true),
        Tool("Wifi QR Generator", Icons.Default.Wifi, "wifi_qr", category = "General", isVisibleOnHome = false, isSubTool = true),
        Tool("QR Scanner", Icons.Default.QrCodeScanner, "qr_scanner", category = "General", isVisibleOnHome = false, isSubTool = true),
        Tool("QR Generator", Icons.Default.QrCode, "qr_gen", category = "General", isVisibleOnHome = false, isSubTool = true),

        // --- TOOLS ---
        Tool("Flashlight", Icons.Default.FlashlightOn, "flashlight", category = "Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Vibration Test", Icons.Default.Vibration, "vibration", category = "Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Ruler", Icons.Default.Straighten, "ruler", category = "Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Protractor", Icons.Default.Architecture, "protractor", category = "Tools", isVisibleOnHome = false, isSubTool = true),

        // --- DATA LAB ---
        Tool("Data Visualizer", Icons.Default.BarChart, "data_viz", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("JSON Format", Icons.Default.DataObject, "json", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("YAML to JSON", Icons.Default.Transform, "yaml_to_json", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Anomaly Detection", Icons.Default.Warning, "anomaly_detection", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Data Profiling", Icons.Default.AccountBox, "data_profiling", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Statistics", Icons.AutoMirrored.Filled.ShowChart, "data_statistics", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Visualisations", Icons.Default.PieChart, "data_visualisations", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Synthetic Data Gen", Icons.Default.Science, "synthetic_data_gen", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Data Quality", Icons.Default.CheckCircle, "data_quality", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Data Cleaning", Icons.Default.CleaningServices, "data_cleaning", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Data Transformation", Icons.Default.Transform, "data_transformation", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Web Scraper Pro", Icons.Default.Search, "web_scraper", category = "Data Lab", isVisibleOnHome = false, isSubTool = true),

        // --- DATE & TIME ---
        Tool("Clock", Icons.Default.Schedule, "clock", category = "Date & Time", isVisibleOnHome = false, isSubTool = true),
        Tool("Date Calc", Icons.Default.CalendarToday, "date_calc", category = "Date & Time", isVisibleOnHome = false, isSubTool = true),
        Tool("Stopwatch", Icons.Default.Timer, "stopwatch", category = "Date & Time", isVisibleOnHome = false, isSubTool = true),
        Tool("World Clock", Icons.Default.Public, "world_clock", category = "Date & Time", isVisibleOnHome = false, isSubTool = true),

        // --- DESIGN & CREATIVE ---
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

        // --- DEVELOPER ---
        Tool("Anagram Finder", Icons.Default.SortByAlpha, "anagram", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("ASCII Table", Icons.AutoMirrored.Filled.Notes, "ascii_table", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Base64 Tool", Icons.Default.Code, "base64", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Case Converter", Icons.Default.TextFields, "case_converter", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Crontab Gen", Icons.Default.Schedule, "crontab_gen", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Hex Viewer", Icons.Default.Numbers, "hex_viewer", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("JWT Tool", Icons.Default.Key, "jwt_tool", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Lorem Ipsum", Icons.AutoMirrored.Filled.Notes, "lorem", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Markdown Preview", Icons.Default.Description, "markdown_preview", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Morse Code", Icons.Default.Language, "morse", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Morse Decoder", Icons.Default.Language, "morse_decoder", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Regex Tester", Icons.Default.Code, "regex_tester", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Text Diff", Icons.Default.Difference, "text_diff", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("URL Encoder", Icons.Default.Link, "url_encoder", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("App Inspector", Icons.Default.Android, "app_inspector", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Terminal Emulator", Icons.Default.Terminal, "terminal", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Word Counter", Icons.Default.Abc, "word_counter", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Word Frequency", Icons.AutoMirrored.Filled.ShowChart, "word_frequency", category = "Developer", isVisibleOnHome = false, isSubTool = true),
        Tool("Developer Console", Icons.Default.Code, "developer_console", category = "Developer", isVisibleOnHome = false, isSubTool = true),

        // --- DOCUMENTS ---
        Tool("CSV to JSON", Icons.Default.Transform, "csv_to_json", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Doc Scanner", Icons.Default.Scanner, "doc_scanner", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Duplicate Finder", Icons.Default.ContentCopy, "duplicate_finder", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("File Shredder", Icons.Default.DeleteForever, "file_shredder", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("SQL Formatter", Icons.Default.Storage, "sql_format", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Storage Cleaner", Icons.Default.CleaningServices, "storage_cleaner", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Zip/Unzip", Icons.Default.FolderZip, "zip_unzip", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Online Document Tools", Icons.Default.Cloud, "docs_online", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Batch Converter", Icons.Default.Transform, "markitdown", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Images to PDF", Icons.Default.Collections, "images_to_pdf", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Compress PDF", Icons.Default.Compress, "pdf_compress", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Crop PDF", Icons.Default.Crop, "pdf_crop", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Extract Images PDF", Icons.Default.Image, "pdf_extract_images", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Flatten PDF", Icons.Default.LayersClear, "pdf_flatten", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Grayscale PDF", Icons.Default.ColorLens, "pdf_grayscale", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Merge PDF", Icons.Default.Merge, "pdf_merge", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Page Numbers", Icons.Default.FormatListNumbered, "pdf_page_numbers", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("PDF Metadata", Icons.Default.Info, "pdf_metadata", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("PDF to Text (OCR)", Icons.Default.TextFields, "pdf_ocr", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Preview PDF", Icons.Default.Preview, "pdf_preview", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Print PDF", Icons.Default.Print, "pdf_print", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Protect PDF", Icons.Default.Lock, "pdf_protect", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Rearrange PDF", Icons.Default.Reorder, "pdf_rearrange", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Remove PDF pages", Icons.Default.Delete, "pdf_remove_pages", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Repair PDF", Icons.Default.Build, "pdf_repair", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Rotate PDF", Icons.AutoMirrored.Filled.RotateRight, "pdf_rotate", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("PDF Signature", Icons.Default.Draw, "pdf_signature", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Split PDF", Icons.AutoMirrored.Filled.CallSplit, "pdf_split", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Unlock PDF", Icons.Default.LockOpen, "pdf_unlock", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Watermarking PDF", Icons.AutoMirrored.Filled.BrandingWatermark, "pdf_watermark", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Zip PDF", Icons.Default.FolderZip, "pdf_zip", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("HTML to PDF", Icons.Default.Html, "pdf_html_to_pdf", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Scan to PDF", Icons.Default.CameraAlt, "pdf_scan_to_pdf", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Fill Forms", Icons.Default.EditNote, "pdf_fill_forms", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Word to PDF", Icons.Default.Description, "pdf_word_to_pdf", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Excel to PDF", Icons.Default.TableChart, "pdf_excel_to_pdf", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Text to PDF", Icons.Default.TextFields, "pdf_text_to_pdf", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("QR to PDF", Icons.Default.QrCode, "pdf_qr_to_pdf", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Barcode to PDF", Icons.Default.QrCode, "pdf_barcode_to_pdf", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Invert PDF", Icons.Default.InvertColors, "pdf_invert", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("PDF to MDX", Icons.Default.Description, "pdf_to_mdx", category = "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("PDF to MHTML", Icons.Default.Html, "pdf_to_mhtml", category = "Documents", isVisibleOnHome = false, isSubTool = true),

        // --- FOOD & DRINK ---
        Tool("Recipe Scaler", Icons.Default.Scale, "recipe_scaler", category = "Food & Drink", isVisibleOnHome = false, isSubTool = true),
        Tool("Water Tracker", Icons.Default.LocalDrink, "water", category = "Food & Drink", isVisibleOnHome = false, isSubTool = true),

        // --- HOME ---
        Tool("Car Maintenance", Icons.Default.Build, "car_maintenance", category = "Home", isVisibleOnHome = false, isSubTool = true),
        Tool("Fuel Cost", Icons.Default.LocalGasStation, "fuel", category = "Home", isVisibleOnHome = false, isSubTool = true),
        Tool("Fuel Consumption", Icons.Default.LocalGasStation, "fuel_consumption", category = "Home", isVisibleOnHome = false, isSubTool = true),
        Tool("Plant Care", Icons.Default.Eco, "plant_care", category = "Home", isVisibleOnHome = false, isSubTool = true),
        Tool("Speedometer", Icons.Default.Speed, "speedometer", category = "Home", isVisibleOnHome = false, isSubTool = true),

        // --- GAMES ---
        Tool("Coin Flip", Icons.Default.Paid, "coin_flip", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Dice Roller", Icons.Default.Casino, "dice_roller", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Memory Game", Icons.Default.Extension, "memory_game", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Number Guessing", Icons.Default.QuestionMark, "number_guessing", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Random Gen", Icons.Default.Casino, "random", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Tic Tac Toe", Icons.Default.Close, "tic_tac_toe", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Snake", Icons.Default.Gamepad, "snake", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Ludo", Icons.Default.Gamepad, "ludo", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Carroms", Icons.Default.Gamepad, "carroms", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Game of Life", Icons.Default.Grid4x4, "game_of_life", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Clash Deck", Icons.Default.Style, "clash_deck", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Roulette", Icons.Default.Casino, "roulette", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Dino Jump", Icons.Default.Gamepad, "dino_jump", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("2048", Icons.Default.Grid4x4, "2048", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Sudoku", Icons.Default.GridOn, "sudoku", category = "Games", isVisibleOnHome = false, isSubTool = true),
        Tool("Minesweeper", Icons.Default.GridOn, "minesweeper", category = "Games", isVisibleOnHome = false, isSubTool = true),

        // --- HEALTH & FITNESS ---
        Tool("BMI Calc", Icons.Default.AccessibilityNew, "bmi", category = "Health & Fitness", isVisibleOnHome = false, isSubTool = true),
        Tool("BMR Calculator", Icons.Default.Calculate, "bmr", category = "Health & Fitness", isVisibleOnHome = false, isSubTool = true),
        Tool("Calorie Calc", Icons.Default.Restaurant, "calorie_calc", category = "Health & Fitness", isVisibleOnHome = false, isSubTool = true),
        Tool("Macro Splitter", Icons.Default.Restaurant, "macro_splitter", category = "Health & Fitness", isVisibleOnHome = false, isSubTool = true),
        Tool("Habit Tracker", Icons.Default.EventRepeat, "habit_tracker", category = "Health & Fitness", isVisibleOnHome = false, isSubTool = true),
        Tool("Meditation Timer", Icons.Default.SelfImprovement, "meditation", category = "Health & Fitness", isVisibleOnHome = false, isSubTool = true),
        Tool("Sleep Tracker", Icons.Default.Bedtime, "sleep_tracker", category = "Health & Fitness", isVisibleOnHome = false, isSubTool = true),
        Tool("Step Counter", Icons.AutoMirrored.Filled.DirectionsRun, "step_counter", category = "Health & Fitness", isVisibleOnHome = false, isSubTool = true),
        Tool("Stretching Guide", Icons.Default.SelfImprovement, "stretch_guide", category = "Health & Fitness", isVisibleOnHome = false, isSubTool = true),
        Tool("Water Reminder", Icons.Default.NotificationsActive, "water_reminder", category = "Health & Fitness", isVisibleOnHome = false, isSubTool = true),
        Tool("Yoga Guide", Icons.Default.SelfImprovement, "yoga_guide", category = "Health & Fitness", isVisibleOnHome = false, isSubTool = true),

        // --- LIFESTYLE ---
        Tool("Fashion Materials", Icons.Default.Checkroom, "fashion_materials", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Clothing Sizes", Icons.Default.Checkroom, "clothing_sizes", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Shoe Sizes", Icons.Default.DirectionsRun, "shoe_sizes", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Ring Sizes", Icons.Default.RadioButtonUnchecked, "ring_sizes", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Body Measurements", Icons.Default.Straighten, "body_measurements", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Dress Guide", Icons.Default.Checkroom, "dress_guide", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Clothes Guide", Icons.Default.Checkroom, "clothes_guide", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Headwear Guide", Icons.Default.Checkroom, "headwear_guide", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Footwear Guide", Icons.Default.DirectionsRun, "footwear_guide", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Accessories Guide", Icons.Default.Watch, "accessories_guide", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Fashion Guide", Icons.Default.AutoAwesome, "fashion_guide", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Traditional Fashion", Icons.Default.HistoryEdu, "traditional_fashion", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Modern Fashion", Icons.Default.Checkroom, "modern_fashion", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Tribal Fashion", Icons.Default.Diversity3, "tribal_fashion", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Indian Fashion", Icons.Default.Festival, "indian_fashion", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("World Fashion", Icons.Default.Public, "world_fashion", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("All Countries Sizes", Icons.Default.Language, "all_countries_sizes", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Topwear Guide", Icons.Default.Checkroom, "topwear_guide", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Bottomwear Guide", Icons.Default.Checkroom, "bottomwear_guide", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Waistwear Guide", Icons.Default.Checkroom, "waistwear_guide", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),
        Tool("Panchangam", Icons.Default.CalendarMonth, "panchangam", category = "Date & Time", isVisibleOnHome = false, isSubTool = true),
        Tool("Zodiac Finder", Icons.Default.AutoAwesome, "zodiac", category = "Date & Time", isVisibleOnHome = false, isSubTool = true),
        Tool("Fashion & Size Hub", Icons.Default.Checkroom, "size_guide", category = "Lifestyle", isVisibleOnHome = false, isSubTool = true),

        // --- NAVIGATION ---
        Tool("Beacon Navigation", Icons.Default.Explore, "beacon_nav", category = "Navigation", isVisibleOnHome = false, isSubTool = true, description = "Radar-style beacon tracking and navigation."),
        Tool("Route Planner", Icons.Default.Directions, "route_planner", category = "Navigation", isVisibleOnHome = false, isSubTool = true),
        Tool("World Map", Icons.Default.Map, "world_map", category = "Navigation", isVisibleOnHome = false, isSubTool = true),
        Tool("GPS Status", Icons.Default.GpsFixed, "gps_status", category = "Navigation", isVisibleOnHome = false, isSubTool = true),
        Tool("Path Tracking", Icons.Default.Route, "path_tracking", category = "Navigation", isVisibleOnHome = false, isSubTool = true),

        // --- MATHS ---
        Tool("Equation Solver", Icons.Default.Functions, "eq_solver", category = "Maths", isVisibleOnHome = false, isSubTool = true),

        // --- MEDICAL ---
        Tool("Heart Rate Monitor", Icons.Default.Favorite, "heart_rate", category = "Medical", isVisibleOnHome = false, isSubTool = true),
        Tool("Blood Pressure", Icons.Default.MonitorHeart, "blood_pressure", category = "Medical", isVisibleOnHome = false, isSubTool = true),
        Tool("Blood Sugar", Icons.Default.Bloodtype, "blood_sugar", category = "Medical", isVisibleOnHome = false, isSubTool = true),
        Tool("Eye Exercise", Icons.Default.Visibility, "eye_exercise", category = "Medical", isVisibleOnHome = false, isSubTool = true),
        Tool("Medication Tracker", Icons.Default.MedicalServices, "medication_tracker", category = "Medical", isVisibleOnHome = false, isSubTool = true),
        Tool("Period Tracker", Icons.Default.CalendarMonth, "period_tracker", category = "Medical", isVisibleOnHome = false, isSubTool = true),
        Tool("Posture Checker", Icons.Default.Accessibility, "posture_check", category = "Medical", isVisibleOnHome = false, isSubTool = true),
        Tool("Bra Calculator", Icons.Default.Calculate, "bra_calculator", category = "Calculators", isVisibleOnHome = false, isSubTool = true),
        Tool("Underwear Calculator", Icons.Default.Calculate, "underwear_calculator", category = "Calculators", isVisibleOnHome = false, isSubTool = true),
        Tool("Dress Calculator", Icons.Default.Calculate, "dress_calculator", category = "Calculators", isVisibleOnHome = false, isSubTool = true),
        Tool("Ring Calculator", Icons.Default.Calculate, "ring_calculator", category = "Calculators", isVisibleOnHome = false, isSubTool = true),
        Tool("Arm Calculator", Icons.Default.Calculate, "arm_calculator", category = "Calculators", isVisibleOnHome = false, isSubTool = true),
        Tool("Body Measurements Calculator", Icons.Default.Calculate, "body_calculator", category = "Calculators", isVisibleOnHome = false, isSubTool = true),
        Tool("Kids Size Calculator", Icons.Default.Calculate, "kids_calculator", category = "Calculators", isVisibleOnHome = false, isSubTool = true),
        Tool("Bangle Size Calculator", Icons.Default.Calculate, "bangle_calculator", category = "Calculators", isVisibleOnHome = false, isSubTool = true),
        Tool("Word Rank Calculator", Icons.Default.Calculate, "word_rank_calc", category = "Calculators", isVisibleOnHome = false, isSubTool = true),

        // --- MISC ---
        Tool("Daily Quotes", Icons.Default.FormatQuote, "daily_quotes", category = "Misc", isVisibleOnHome = false, isSubTool = true),
        Tool("Quick Tiles", Icons.Default.Widgets, "quick_tiles", category = "Misc", isVisibleOnHome = false, isSubTool = true),

        // --- SOCIAL ---
        Tool("Bio Linker", Icons.Default.Link, "bio_linker", category = "Social", isVisibleOnHome = false, isSubTool = true),
        Tool("Profile Photo Maker", Icons.Default.AccountCircle, "profile_photo_maker", category = "Social", isVisibleOnHome = false, isSubTool = true),
        Tool("Social Preview", Icons.Default.Share, "social_preview", category = "Social", isVisibleOnHome = false, isSubTool = true),

        // --- TRAVEL & LOCAL ---
        Tool("Survival Guide", Icons.Default.AutoStories, "survival_guide", category = "Travel & Local", isVisibleOnHome = false, isSubTool = true, description = "Comprehensive wilderness survival techniques."),
        Tool("Campfire Guide", Icons.Default.LocalFireDepartment, "campfire_guide", category = "Travel & Local", isVisibleOnHome = false, isSubTool = true),
        Tool("Hiking Trails", Icons.AutoMirrored.Filled.DirectionsRun, "hiking_trails", category = "Travel & Local", isVisibleOnHome = false, isSubTool = true),
        Tool("Knots Guide", Icons.Default.InvertColors, "knots_guide", category = "Travel & Local", isVisibleOnHome = false, isSubTool = true),
        Tool("Packing List", Icons.Default.Checklist, "packing_list", category = "Travel & Local", isVisibleOnHome = false, isSubTool = true),
        Tool("Signal Mirror", Icons.Default.FlashlightOn, "signal_mirror", category = "Travel & Local", isVisibleOnHome = false, isSubTool = true),
        Tool("Emergency SOS", Icons.Default.Sos, "sos", category = "Travel & Local", isVisibleOnHome = false, isSubTool = true),
        Tool("Travel Budgeter", Icons.Default.AttachMoney, "travel_budget", category = "Travel & Local", isVisibleOnHome = false, isSubTool = true),
        Tool("Cliff Height", Icons.Default.Landscape, "cliff_height", category = "Travel & Local", isVisibleOnHome = false, isSubTool = true),
        Tool("Altitude Graph", Icons.AutoMirrored.Filled.ShowChart, "altitude_graph", category = "Travel & Local", isVisibleOnHome = false, isSubTool = true),

        // --- VIDEO LAB ---
        Tool("Video Editor Pro", Icons.Default.Movie, "vid_edit_pro", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Editor", Icons.Default.Edit, "video_trim", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
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
        Tool("Delete Segment", Icons.Default.Delete, "video_delete", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Loop Video", Icons.Default.Loop, "video_loop", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Mix Video Audio", Icons.Default.Tune, "mix_video_audio", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Silence Video", Icons.AutoMirrored.Filled.VolumeOff, "video_silence", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Thumbnail Extractor", Icons.Default.Image, "vid_thumb", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Compressor", Icons.Default.Compress, "video_compress", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Flip", Icons.Default.Flip, "video_flip", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),
        Tool("Reverse Video", Icons.Default.History, "video_reverse", category = "Video Lab", isVisibleOnHome = false, isSubTool = true),

        // --- WEATHER ---
        Tool("Air Quality", Icons.Default.Air, "air_quality", category = "Weather", isVisibleOnHome = false, isSubTool = true),
        Tool("Light Pollution", Icons.Default.NightsStay, "light_pollution", category = "Weather", isVisibleOnHome = false, isSubTool = true),
        Tool("Moon Phase", Icons.Default.Brightness3, "moon_phase", category = "Weather", isVisibleOnHome = false, isSubTool = true),
        Tool("Rain Radar", Icons.Default.Water, "rain_radar", category = "Weather", isVisibleOnHome = false, isSubTool = true),
        Tool("UV Index", Icons.Default.WbSunny, "uv_index", category = "Weather", isVisibleOnHome = false, isSubTool = true),
        Tool("Weather Forecast", Icons.Default.WbCloudy, "weather_forecast", category = "Weather", isVisibleOnHome = false, isSubTool = true),
        Tool("Tides", Icons.Default.Water, "tides", category = "Weather", isVisibleOnHome = false, isSubTool = true),
        Tool("Weather Prediction", Icons.Default.Cloud, "weather_prediction", category = "Weather", isVisibleOnHome = false, isSubTool = true),

        // --- WEB TOOLS ---
        Tool("YouTube Companion", Icons.Default.SmartDisplay, "youtube_utility", category = "Web Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Epic Bookmarx", Icons.Default.Hub, "hub", category = "Web Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Media Grabber", Icons.Default.Download, "media_grabber", category = "Web Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Metatag Analyzer", Icons.Default.Search, "meta_anal", category = "Web Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Web Search", Icons.Default.Search, "web", category = "Web Tools", isVisibleOnHome = false, isSubTool = true),

        // --- SENSORS ---
        Tool("Altimeter", Icons.Default.Landscape, "altimeter", category = "Sensors", isVisibleOnHome = false, isSubTool = true),
        Tool("Barometer", Icons.Default.Compress, "barometer", category = "Sensors", isVisibleOnHome = false, isSubTool = true),
        Tool("Compass", Icons.Default.Explore, "compass", category = "Sensors", isVisibleOnHome = false, isSubTool = true),
        Tool("G-Force Meter", Icons.Default.Speed, "gforce_meter", category = "Sensors", isVisibleOnHome = false, isSubTool = true),
        Tool("Level", Icons.Default.Architecture, "level", category = "Sensors", isVisibleOnHome = false, isSubTool = true),
        Tool("Light Meter", Icons.Default.LightMode, "light", category = "Sensors", isVisibleOnHome = false, isSubTool = true),
        Tool("Metal Detector", Icons.Default.CompassCalibration, "metal", category = "Sensors", isVisibleOnHome = false, isSubTool = true),
        Tool("Sensor Data", Icons.Default.SettingsInputComponent, "sensor_data", category = "Sensors", isVisibleOnHome = false, isSubTool = true),
        Tool("Sensors List", Icons.AutoMirrored.Filled.List, "sensors_list", category = "Sensors", isVisibleOnHome = false, isSubTool = true),
        Tool("SPL Meter", Icons.AutoMirrored.Filled.VolumeUp, "spl_meter", category = "Sensors", isVisibleOnHome = false, isSubTool = true),
        Tool("Thermal Info", Icons.Default.DeviceThermostat, "thermal_info", category = "Sensors", isVisibleOnHome = false, isSubTool = true),

        // --- SYSTEM TOOLS ---
        Tool("App Info", Icons.Default.Apps, "app_info", category = "System Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Battery", Icons.Default.BatteryFull, "battery", category = "System Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("CPU Info", Icons.Default.Memory, "cpu_info", category = "System Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("RAM Info", Icons.Default.Memory, "ram_info", category = "System Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Device Info", Icons.Default.Info, "device", category = "System Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Hardware ID", Icons.Default.PermDeviceInformation, "device_id", category = "System Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Process Manager", Icons.Default.Dns, "process_manager", category = "System Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Storage", Icons.Default.Storage, "storage", category = "System Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("System Lab Core", Icons.Default.Science, "system_lab", category = "System Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Update Check", Icons.Default.SystemUpdate, "update_check", category = "System Tools", isVisibleOnHome = false, isSubTool = true),

        // --- WEB Dispatcher links/Perchance AI ---
        Tool("AI Image Pro", Icons.Default.Image, "per_image_pro", category = "AI", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Text Gen", Icons.Default.TextFields, "per_text_gen", category = "AI", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Text Rewriter", Icons.Default.Edit, "per_text_rewrite", category = "AI", isVisibleOnHome = false, isSubTool = true),
        Tool("NECS Story", Icons.Default.AutoAwesome, "per_necs_story", category = "AI", isVisibleOnHome = false, isSubTool = true),
        Tool("Perchance Character Maker", Icons.Default.Person, "per_character", category = "AI", isVisibleOnHome = false, isSubTool = true),
        Tool("Perchance General Hub", Icons.Default.Hub, "per_hub", category = "AI", isVisibleOnHome = false, isSubTool = true),
        Tool("Perchance Image Generator", Icons.Default.Image, "per_image", category = "AI", isVisibleOnHome = false, isSubTool = true),
        Tool("Perchance Story Writer", Icons.Default.AutoAwesome, "per_story", category = "AI", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Chat Assistant", Icons.AutoMirrored.Filled.Chat, "ai_chat", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Code Helper", Icons.Default.Code, "ai_code", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Gemini AI Hub", Icons.Default.AutoAwesome, "ai_companion", "AI Tools", Color(0xFF673AB7), isVisibleOnHome = false, isSubTool = true),
        Tool("Document Translator", Icons.Default.Translate, "ai_doc_translator", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Grammar Checker", Icons.Default.Spellcheck, "ai_grammar", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Image Generator", Icons.Default.Image, "ai_image", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Object Detector", Icons.Default.CenterFocusStrong, "ai_obj_detect", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Sentiment Analysis", Icons.Default.Mood, "ai_sentiment", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Text Summarizer", Icons.Default.Summarize, "ai_summarizer", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Text Extractor", Icons.Default.TextFields, "ai_text_ext", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("AI Translator", Icons.Default.Translate, "ai_translate", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Virtual Try-On", Icons.Default.Face, "ai_tryon", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Antenna Calc", Icons.Default.SettingsInputAntenna, "antenna_calc", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("App Locker", Icons.Default.Lock, "app_locker", "Security", isVisibleOnHome = false, isSubTool = true),
        Tool("App Permissions", Icons.Default.Security, "app_permissions", "Security", isVisibleOnHome = false, isSubTool = true),
        Tool("Area Calculator", Icons.Default.SquareFoot, "area_calc", "Travel", isVisibleOnHome = false, isSubTool = true),
        Tool("Automation", Icons.Default.SettingsInputComponent, "automation", "Utilities", Color(0xFF673AB7), isVisibleOnHome = false, isSubTool = true),
        Tool("Billing & Invoices", Icons.Default.Receipt, "billing", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Binary Calc", Icons.Default.Numbers, "binary_calc", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("CAGR Calculator", Icons.AutoMirrored.Filled.TrendingUp, "cagr_calc", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Calculator", Icons.Default.Calculate, "calculator", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Checklist", Icons.Default.Checklist, "checklist", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Cipher", Icons.Default.Security, "cipher_tools", "Security", isVisibleOnHome = false, isSubTool = true),
        Tool("Circuit Calc", Icons.Default.Memory, "circuit_calc", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Cloud Sync Hub", Icons.Default.CloudSync, "cloud_sync", "Data", Color(0xFF2196F3), isVisibleOnHome = false, isSubTool = true),
        Tool("Coin Tracker", Icons.Default.MonetizationOn, "coin_tracker", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Compound Interest", Icons.AutoMirrored.Filled.TrendingUp, "compound_interest", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Currency Trends", Icons.Default.Timeline, "currency_trends", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Daily Journal", Icons.Default.EditNote, "daily_journal", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Omni Dashboard", Icons.Default.Dashboard, "dashboard", "Device", Color(0xFF39FF14), isVisibleOnHome = false, isSubTool = true),
        Tool("DCF Calculator", Icons.Default.AccountBalanceWallet, "dcf_calc", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Device Discovery", Icons.Default.Search, "device_discovery", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("Discount Calc", Icons.Default.Percent, "discount", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Dividend Calc", Icons.Default.Payments, "dividend_calc", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("DNS Lookup", Icons.Default.Dns, "dns_lookup", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("Electronics Tools", Icons.Default.ElectricalServices, "electronics_tools", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Expense Tracker", Icons.Default.AccountBalanceWallet, "expense_tracker", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Face Swap AI", Icons.Default.Face, "face_swap", "AI Tools", Color(0xFFFF4081), isVisibleOnHome = false, isSubTool = true),
        Tool("File Explorer", Icons.Default.Folder, "file_explorer", "Documents", isVisibleOnHome = false, isSubTool = true),
        Tool("Filter Designer", Icons.Default.FilterList, "filter_design", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Force Calculator", Icons.Default.Speed, "force_calc", "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Fraction Calc", Icons.Default.Percent, "fraction_calc", "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("GST Calculator", Icons.Default.RequestQuote, "gst_calc", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("HTTP Request", Icons.Default.Http, "http_request", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("Inflation Calc", Icons.Default.MoneyOff, "inflation_calc", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Kanban Board", Icons.Default.ViewWeek, "kanban", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Loan Calculator", Icons.Default.AccountBalance, "loan_calc", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Logic Gates", Icons.Default.SettingsInputComponent, "logic_gates", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Pitch Changer", Icons.Default.Height, "m_audio_pitch", "Media", isVisibleOnHome = false, isSubTool = true),
        Tool("Matrix Calc", Icons.Default.Grid4x4, "matrix_calc", "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Mortgage Calc", Icons.Default.Home, "mortgage_calc", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("MQTT Tester", Icons.Default.NetworkCheck, "mqtt_tester", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("My IP", Icons.Default.Public, "my_ip", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("Network Details", Icons.Default.NetworkCheck, "network_info", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("NFT Viewer", Icons.Default.Token, "nft_viewer", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Note Pad", Icons.Default.NoteAlt, "note", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Ohm's Law", Icons.Default.ElectricalServices, "ohms_law", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Strong Password Gen", Icons.Default.VpnKey, "password_gen", "Security", isVisibleOnHome = false, isSubTool = true),
        Tool("Password Manager", Icons.Default.Password, "password_manager", "Security", isVisibleOnHome = false, isSubTool = true),
        Tool("PCB Trace Width", Icons.Default.Straighten, "pcb_trace", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Perchance AI Hub", Icons.Default.AutoAwesome, "perchance_tools", "Web", Color(0xFFE91E63), isVisibleOnHome = false, isSubTool = true),
        Tool("Permission Manager", Icons.Default.ManageAccounts, "perm_manager", "Security", isVisibleOnHome = false, isSubTool = true),
        Tool("Ping", Icons.Default.SettingsEthernet, "ping", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("Pomodoro", Icons.Default.HourglassEmpty, "pomodoro", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Port Checker", Icons.Default.Dns, "port_checker", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("Port Scanner", Icons.Default.Search, "port_scanner", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("PowerBench", Icons.Default.Speed, "power_bench", "Device", Color(0xFFF44336), isVisibleOnHome = false, isSubTool = true),
        Tool("Privacy Check", Icons.Default.PrivacyTip, "privacy_check", "Security", isVisibleOnHome = false, isSubTool = true),
        Tool("Resistor Color Code", Icons.Default.Architecture, "resistor_code", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Retirement Planner", Icons.Default.EventAvailable, "retirement_planner", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("ROI Calculator", Icons.AutoMirrored.Filled.ShowChart, "roi_calc", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Salary Calc", Icons.Default.Work, "salary_calc", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Scientific Calc", Icons.Default.Functions, "sci_calc", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Security Vault", Icons.Default.Lock, "security_vault", "Security", Color(0xFF607D8B), isVisibleOnHome = false, isSubTool = true),
        Tool("Signal Gen", Icons.Default.GraphicEq, "signal_gen_pro", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("SIP Calculator", Icons.Default.PieChart, "sip_calc", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Smart Hub", Icons.Default.Hub, "smart_hub", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Speed Test", Icons.Default.Speed, "speed_test", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("SSH Client", Icons.Default.Terminal, "ssh_client", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("Statistics", Icons.Default.BarChart, "stats", "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Stock Profit", Icons.AutoMirrored.Filled.TrendingUp, "stock_profit", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Subnet Calc", Icons.Default.SettingsEthernet, "subnet_calc", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("Task Board", Icons.Default.Dashboard, "task_board", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Tax Calculator", Icons.Default.MoneyOff, "tax_calc", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Telemetry & Stats", Icons.Default.Analytics, "telemetry_stats", "Device", Color(0xFF00E676), isVisibleOnHome = false, isSubTool = true),
        Tool("Time Logger", Icons.Default.HistoryToggleOff, "time_logger", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Tip Calc", Icons.Default.Receipt, "tip", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Truth Table Gen", Icons.AutoMirrored.Filled.ListAlt, "truth_table", "Education", isVisibleOnHome = false, isSubTool = true),
        Tool("Unit Price Comparison", Icons.AutoMirrored.Filled.CompareArrows, "unit_compare", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Unit Price Calc", Icons.Default.PriceCheck, "unit_price", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Video Noise Remover", Icons.Default.VideoSettings, "video_noise_remover", "AI Tools", isVisibleOnHome = false, isSubTool = true),
        Tool("Volume Calc", Icons.Default.VerticalAlignBottom, "volume_calc", "Utilities", isVisibleOnHome = false, isSubTool = true),
        Tool("Wake On LAN", Icons.Default.SettingsPower, "wake_on_lan", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("Wallet Explorer", Icons.Default.AccountBalanceWallet, "wallet_explorer", "Finance", isVisibleOnHome = false, isSubTool = true),
        Tool("Whois", Icons.Default.QuestionMark, "whois", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("WIFI Analyzer", Icons.Default.Wifi, "wifi_anal", "Network", isVisibleOnHome = false, isSubTool = true),
        Tool("Pocket Piano", Icons.Default.MusicNote, "piano", "Music & Audio", isVisibleOnHome = false, isSubTool = true),
    )

    fun sanitizeRoute(input: String): String {
        return input.lowercase().replace(Regex("[^a-z0-9_]"), "_")
    }

    private fun getCategoryIcon(category: String): ImageVector {
        return when (category) {
            "AI" -> Icons.Default.AutoAwesome
            "Games" -> Icons.Default.Gamepad
            "Tools & Utilities" -> Icons.Default.Build
            "News" -> Icons.Default.Newspaper
            "Documents" -> Icons.Default.Description
            "Shopping" -> Icons.Default.ShoppingBag
            "Privacy & Security" -> Icons.Default.Shield
            "Manga / Anime" -> Icons.Default.Movie
            "Streaming" -> Icons.Default.PlayArrow
            "Hosting" -> Icons.Default.Cloud
            "Google" -> Icons.Default.AutoAwesome
            "Windows" -> Icons.Default.Laptop
            "Android" -> Icons.Default.Android
            "Media" -> Icons.Default.PermMedia
            "Govt." -> Icons.Default.AccountBalance
            "Banking / Finance" -> Icons.Default.MonetizationOn
            "Travel" -> Icons.Default.Flight
            "Date & Time" -> Icons.Default.Schedule
            "Network" -> Icons.Default.Wifi
            "Coding" -> Icons.Default.Code
            "Search" -> Icons.Default.Search
            "Calculators" -> Icons.Default.Calculate
            "Productivity" -> Icons.Default.Work
            "Web apps" -> Icons.Default.OpenInBrowser
            "Social" -> Icons.Default.Share
            "Email" -> Icons.Default.Email
            "Storage" -> Icons.Default.Storage
            "Music" -> Icons.Default.MusicNote
            "Jobs" -> Icons.Default.WorkOutline
            "Perchance" -> Icons.Default.AutoAwesome
            else -> Icons.Default.Language
        }
    }

    private var isDynamicToolsInitialized = false

    fun initializeDynamicTools(context: Context) {
        if (isDynamicToolsInitialized) return
        val links = UrlLinksManager.getLinks(context)
        if (links.isEmpty()) return

        val grouped = links.groupBy { it.category }
        val linkTitles = links.map { it.title.lowercase() }.toSet()
        val linkRoutes = links.map { sanitizeRoute(it.title) }.toSet()

        // Hide any existing static tool that matches a dynamic link
        val filteredStaticTools = tools.map { tool ->
            val matchesName = tool.name.lowercase() in linkTitles
            val matchesRoute = tool.route in linkRoutes || tool.route.removePrefix("sec_") in linkRoutes
            if (matchesName || matchesRoute) {
                tool.copy(isVisibleOnHome = false, isSubTool = true)
            } else {
                tool
            }
        }

        val newTools = filteredStaticTools.toMutableList()

        for ((category, categoryLinks) in grouped) {
            val catSlug = sanitizeRoute(category)
            val subRoutes = categoryLinks.map { "dyn_link_${sanitizeRoute(it.title)}" }

            // Add Category Main Tool
            val catMainTool = Tool(
                name = "$category Directory",
                icon = getCategoryIcon(category),
                route = "dyn_cat_$catSlug",
                category = "Web Directory",
                color = Color(0xFF03A9F4),
                description = "Directory of curated $category web resources and utilities.",
                subToolRoutes = subRoutes,
                isVisibleOnHome = true
            )
            newTools.add(catMainTool)

            // Add Individual Subtools
            for (link in categoryLinks) {
                val linkSlug = sanitizeRoute(link.title)
                val subTool = Tool(
                    name = link.title,
                    icon = Icons.Default.Link,
                    route = "dyn_link_$linkSlug",
                    category = "Web Directory",
                    color = Color(0xFF8BC34A),
                    description = link.url,
                    isVisibleOnHome = false,
                    isSubTool = true
                )
                newTools.add(subTool)
            }
        }

        tools = newTools
        isDynamicToolsInitialized = true
    }
}
