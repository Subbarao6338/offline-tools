import re

# More granular mapping
route_to_hub = {
    # System Monitor
    "dashboard": "System Monitor", "power_bench": "System Monitor", "telemetry_stats": "System Monitor",
    "battery": "System Monitor", "ram_info": "System Monitor", "cpu_info": "System Monitor",
    "device": "System Monitor", "device_id": "System Monitor", "storage": "System Monitor",
    "system_lab": "System Monitor", "update_check": "System Monitor", "process_manager": "System Monitor",
    "app_info": "System Monitor",

    # Developer Tools
    "developer_console": "Developer Tools", "terminal": "Developer Tools", "app_inspector": "Developer Tools",
    "ascii_table": "Developer Tools", "base64": "Developer Tools", "crontab_gen": "Developer Tools",
    "hex_viewer": "Developer Tools", "jwt_tool": "Developer Tools", "markdown_preview": "Developer Tools",
    "regex_tester": "Developer Tools", "url_encoder": "Developer Tools", "base_conv": "Developer Tools",
    "anagram": "Developer Tools", "case_converter": "Developer Tools", "lorem": "Developer Tools",
    "morse": "Developer Tools", "morse_decoder": "Developer Tools", "text_diff": "Developer Tools",
    "word_counter": "Developer Tools", "word_frequency": "Developer Tools", "word_rank_calc": "Developer Tools",

    # Audio Lab
    "audio_tools_group": "Audio Lab", "m_3d_audio": "Audio Lab", "m_8d_audio": "Audio Lab",
    "binaural": "Audio Lab", "add_sfx": "Audio Lab", "m_audio_compressor": "Audio Lab",
    "aud_conv": "Audio Lab", "m_audio_cutter": "Audio Lab", "m_audio_editor": "Audio Lab",
    "aud_eq_v2": "Audio Lab", "aud_info_v2": "Audio Lab", "m_audio_joiner": "Audio Lab",
    "audio_loop": "Audio Lab", "m_audio_mixer": "Audio Lab", "m_audio_normalizer": "Audio Lab",
    "m_audio_pan": "Audio Lab", "m_audio_pitch": "Audio Lab", "m_audio_splitter": "Audio Lab",
    "m_audio_tag_editor": "Audio Lab", "m_bass_booster": "Audio Lab", "bpm": "Audio Lab",
    "chord_lib": "Audio Lab", "guitar_tuner": "Audio Lab", "karaoke_maker": "Audio Lab",
    "key_bpm_finder": "Audio Lab", "metronome": "Audio Lab", "m_mute_audio": "Audio Lab",
    "noise_generator": "Audio Lab", "record_audio": "Audio Lab", "m_reverse_audio": "Audio Lab",
    "m_ringtone_maker": "Audio Lab", "silence_generator": "Audio Lab", "m_silence_remover": "Audio Lab",
    "sound_mastering": "Audio Lab", "m_speech_to_text": "Audio Lab", "m_speed_changer": "Audio Lab",
    "m_text_to_speech": "Audio Lab", "m_voice_changer": "Audio Lab", "m_volume_booster": "Audio Lab",
    "wave_generator": "Audio Lab", "ai_noise_remover": "Audio Lab", "ai_stems_splitter": "Audio Lab",
    "ai_voice_mimic": "Audio Lab", "aud_master_pro": "Audio Lab", "audio_noise_remover": "Audio Lab",
    "echo_remover": "Audio Lab", "reverb_remover": "Audio Lab", "vocal_autotuner": "Audio Lab",
    "vocal_remover": "Audio Lab", "voice_memo": "Audio Lab",

    # Video Lab
    "video_tools_group": "Video Lab", "frame_grabber": "Video Lab", "m_video_to_audio": "Video Lab",
    "mix_video_audio": "Video Lab", "vid_annotator": "Video Lab", "vid_edit_pro": "Video Lab",
    "vid_thumb": "Video Lab", "video_compress": "Video Lab", "video_delete": "Video Lab",
    "video_flip": "Video Lab", "video_loop": "Video Lab", "video_reverse": "Video Lab",
    "video_sfx": "Video Lab", "video_silence": "Video Lab", "video_speed_changer": "Video Lab",
    "video_splitter": "Video Lab", "video_stabilizer": "Video Lab", "video_to_gif": "Video Lab",
    "video_trim": "Video Lab", "video_volume_booster": "Video Lab", "video_merger": "Video Lab",

    # Image Studio
    "image_tools_group": "Image Studio", "batch_img_pro_v2": "Image Studio", "exif_viewer": "Image Studio",
    "image_ai_tools": "Image Studio", "image_base64": "Image Studio", "image_bg_remover": "Image Studio",
    "image_collage": "Image Studio", "image_color_picker": "Image Studio", "image_compare": "Image Studio",
    "image_crop": "Image Studio", "image_cutting": "Image Studio", "image_delete_exif": "Image Studio",
    "image_draw": "Image Studio", "image_draw_bg": "Image Studio", "image_edit_exif": "Image Studio",
    "image_filter": "Image Studio", "image_format_conv": "Image Studio", "image_layers_bg": "Image Studio",
    "image_layers_img": "Image Studio", "image_mask_filter": "Image Studio", "image_noise_gen": "Image Studio",
    "image_ocr": "Image Studio", "image_open_project": "Image Studio", "image_palette": "Image Studio",
    "image_preview": "Image Studio", "image_resize_conv": "Image Studio", "image_resize_limits": "Image Studio",
    "image_resize_weight": "Image Studio", "image_single_edit": "Image Studio", "image_stacking": "Image Studio",
    "image_stitching": "Image Studio", "image_to_svg": "Image Studio", "image_to_webp": "Image Studio",
    "image_wallpapers": "Image Studio", "image_watermark": "Image Studio", "image_web_load": "Image Studio",
    "multi_crop": "Image Studio", "multi_image_resize": "Image Studio", "pixel_art": "Image Studio",
    "profile_photo_maker": "Image Studio", "webp_to_images": "Image Studio", "file_conv": "Image Studio",
    "digital_magnifier": "Image Studio", "mirror_tool": "Image Studio",

    # GIF & Animation
    "gif_tools_group": "GIF & Animation", "apng_to_images": "GIF & Animation", "apng_to_jxl": "GIF & Animation",
    "gif_to_images": "GIF & Animation", "gif_to_jxl": "GIF & Animation", "gif_to_webp": "GIF & Animation",
    "image_to_apng": "GIF & Animation", "images_to_apng": "GIF & Animation", "images_to_gif": "GIF & Animation",
    "images_to_jxl": "GIF & Animation", "jpeg_to_jxl": "GIF & Animation", "jxl_to_images": "GIF & Animation",
    "jxl_to_jpeg": "GIF & Animation",

    # AI Companion
    "ai_companion": "AI Companion", "ai_group": "AI Companion", "face_swap": "AI Companion",
    "ai_chat": "AI Companion", "ai_code": "AI Companion", "ai_doc_translator": "AI Companion",
    "ai_grammar": "AI Companion", "ai_image": "AI Companion", "ai_obj_detect": "AI Companion",
    "ai_sentiment": "AI Companion", "ai_summarizer": "AI Companion", "ai_text_ext": "AI Companion",
    "ai_translate": "AI Companion", "video_noise_remover": "AI Companion", "ai_tryon": "AI Companion",
    "perchance_tools": "AI Companion", "per_character": "AI Companion", "per_hub": "AI Companion",
    "per_image": "AI Companion", "per_image_pro": "AI Companion", "per_necs_story": "AI Companion",
    "per_story": "AI Companion", "per_text_gen": "AI Companion", "per_text_rewrite": "AI Companion",

    # Data Science
    "web_scraper": "Data Science", "cloud_sync": "Data Science", "data_tools_group": "Data Science",
    "data_viz": "Data Science", "json": "Data Science", "yaml_to_json": "Data Science",
    "anomaly_detection": "Data Science", "data_profiling": "Data Science", "data_statistics": "Data Science",
    "data_visualisations": "Data Science", "synthetic_data_gen": "Data Science", "data_quality": "Data Science",
    "data_cleaning": "Data Science", "data_transformation": "Data Science",

    # Science Lab
    "science_group": "Science Lab", "constants": "Science Lab", "constellations": "Science Lab",
    "dna_viz": "Science Lab", "periodic_table": "Science Lab", "planet_finder": "Science Lab",
    "pokedex": "Science Lab", "prime": "Science Lab", "solar_system": "Science Lab",
    "star_map": "Science Lab", "unit_circle": "Science Lab", "ballistics": "Science Lab",

    # Math Hub
    "math_group": "Math Hub", "binary_calc": "Math Hub", "fraction_calc": "Math Hub",
    "matrix_calc": "Math Hub", "sci_calc": "Math Hub", "stats": "Math Hub", "truth_table": "Math Hub",
    "eq_solver": "Math Hub", "calculator": "Math Hub", "discount": "Math Hub", "tip": "Math Hub",
    "unit_compare": "Math Hub", "unit_price": "Math Hub", "volume_calc": "Math Hub", "billing": "Math Hub",

    # Daily Helpers
    "util_group": "Daily Helpers", "clock": "Daily Helpers", "date_calc": "Daily Helpers",
    "flashlight": "Daily Helpers", "protractor": "Daily Helpers", "qr_gen": "Daily Helpers",
    "qr_scanner": "Daily Helpers", "ruler": "Daily Helpers", "stopwatch": "Daily Helpers",
    "vibration": "Daily Helpers", "wifi_qr": "Daily Helpers", "tiles_widgets": "Daily Helpers",

    # Productivity
    "prod_group": "Productivity", "checklist": "Productivity", "daily_journal": "Productivity",
    "daily_quotes": "Productivity", "kanban": "Productivity", "note": "Productivity",
    "pomodoro": "Productivity", "task_board": "Productivity", "time_logger": "Productivity",
    "docs_group": "Productivity", "csv_to_json": "Productivity", "doc_scanner": "Productivity",
    "duplicate_finder": "Productivity", "file_explorer": "Productivity", "file_shredder": "Productivity",
    "sql_format": "Productivity", "storage_cleaner": "Productivity", "zip_unzip": "Productivity",
    "docs_online": "Productivity", "markitdown": "Productivity", "pdf_tools_group": "Productivity",

    # Engineering Lab
    "engineering_group": "Engineering Lab", "antenna_calc": "Engineering Lab", "circuit_calc": "Engineering Lab",
    "electronics_tools": "Engineering Lab", "filter_design": "Engineering Lab", "logic_gates": "Engineering Lab",
    "ohms_law": "Engineering Lab", "pcb_trace": "Engineering Lab", "resistor_code": "Engineering Lab",
    "signal_gen_pro": "Engineering Lab", "smart_hub": "Engineering Lab", "force_calc": "Engineering Lab",

    # Games & Fun
    "game_group": "Games & Fun", "coin_flip": "Games & Fun", "dice_roller": "Games & Fun",
    "memory_game": "Games & Fun", "number_guessing": "Games & Fun", "random": "Games & Fun",
    "tic_tac_toe": "Games & Fun", "snake": "Games & Fun", "ludo": "Games & Fun", "carroms": "Games & Fun",
    "chess": "Games & Fun", "game_of_life": "Games & Fun", "clash_deck": "Games & Fun",
    "roulette": "Games & Fun", "dino_jump": "Games & Fun", "2048": "Games & Fun", "sudoku": "Games & Fun",
    "minesweeper": "Games & Fun",

    # Network Lab
    "net_group": "Network Lab", "device_discovery": "Network Lab", "dns_lookup": "Network Lab",
    "mqtt_tester": "Network Lab", "my_ip": "Network Lab", "network_info": "Network Lab",
    "ping": "Network Lab", "port_checker": "Network Lab", "port_scanner": "Network Lab",
    "speed_test": "Network Lab", "subnet_calc": "Network Lab", "wake_on_lan": "Network Lab",
    "whois": "Network Lab", "wifi_anal": "Network Lab", "http_request": "Network Lab",
    "ssh_client": "Network Lab",

    # Security Vault
    "security_vault": "Security Vault", "security_group": "Security Vault", "app_locker": "Security Vault",
    "app_permissions": "Security Vault", "cipher_tools": "Security Vault", "password_gen": "Security Vault",
    "password_manager": "Security Vault", "perm_manager": "Security Vault", "privacy_check": "Security Vault",
    "sec_adguard": "Security Vault", "sec_nextdns": "Security Vault", "sec_bitwarden": "Security Vault",
    "sec_ente": "Security Vault",

    # Finance Hub
    "finance_group": "Finance Hub", "coin_tracker": "Finance Hub", "compound_interest": "Finance Hub",
    "currency_trends": "Finance Hub", "dividend_calc": "Finance Hub", "expense_tracker": "Finance Hub",
    "gst_calc": "Finance Hub", "inflation_calc": "Finance Hub", "loan_calc": "Finance Hub",
    "mortgage_calc": "Finance Hub", "nft_viewer": "Finance Hub", "retirement_planner": "Finance Hub",
    "roi_calc": "Finance Hub", "salary_calc": "Finance Hub", "sip_calc": "Finance Hub",
    "stock_profit": "Finance Hub", "tax_calc": "Finance Hub", "wallet_explorer": "Finance Hub",
    "cagr_calc": "Finance Hub", "dcf_calc": "Finance Hub",

    # Health & Vitality
    "health_group": "Health & Vitality", "bmi": "Health & Vitality", "bmr": "Health & Vitality",
    "calorie_calc": "Health & Vitality", "macro_splitter": "Health & Vitality", "eye_exercise": "Health & Vitality",
    "habit_tracker": "Health & Vitality", "medication_tracker": "Health & Vitality", "meditation": "Health & Vitality",
    "period_tracker": "Health & Vitality", "posture_check": "Health & Vitality", "sleep_tracker": "Health & Vitality",
    "step_counter": "Health & Vitality", "stretch_guide": "Health & Vitality", "water": "Health & Vitality",
    "water_reminder": "Health & Vitality", "yoga_guide": "Health & Vitality", "heart_rate": "Health & Vitality",
    "blood_pressure": "Health & Vitality", "blood_sugar": "Health & Vitality",

    # Weather Center
    "weather_group": "Weather Center", "air_quality": "Weather Center", "light_pollution": "Weather Center",
    "moon_phase": "Weather Center", "rain_radar": "Weather Center", "uv_index": "Weather Center",
    "weather_forecast": "Weather Center", "weather_prediction": "Weather Center", "tides": "Weather Center",

    # Outdoor & Adventure
    "outdoor_group": "Outdoor & Adventure", "beacon_nav": "Outdoor & Adventure", "path_tracking": "Outdoor & Adventure",
    "altitude_graph": "Outdoor & Adventure", "area_calc": "Outdoor & Adventure",
    "campfire_guide": "Outdoor & Adventure", "gps_status": "Outdoor & Adventure", "hiking_trails": "Outdoor & Adventure",
    "knots_guide": "Outdoor & Adventure", "route_planner": "Outdoor & Adventure", "signal_mirror": "Outdoor & Adventure",
    "world_clock": "Outdoor & Adventure", "world_map": "Outdoor & Adventure", "cliff_height": "Outdoor & Adventure",
    "altimeter": "Outdoor & Adventure", "barometer": "Outdoor & Adventure", "compass": "Outdoor & Adventure",
    "gforce_meter": "Outdoor & Adventure", "level": "Outdoor & Adventure", "light": "Outdoor & Adventure",
    "metal": "Outdoor & Adventure", "sensor_data": "Outdoor & Adventure", "sensors_list": "Outdoor & Adventure",
    "spl_meter": "Outdoor & Adventure", "thermal_info": "Outdoor & Adventure",

    # Survival Guide
    "survival_guide": "Survival Guide", "sos": "Survival Guide",

    # Design & Creative
    "design_tools_group": "Design & Creative", "color_conv_pro": "Design & Creative",
    "color_harmonies": "Design & Creative", "color_info": "Design & Creative", "color_mixing": "Design & Creative",
    "color_shading": "Design & Creative", "edit_palette": "Design & Creative", "generate_palette": "Design & Creative",
    "image_histogram": "Design & Creative", "material_you_palette": "Design & Creative",
    "drawing_board": "Design & Creative", "signature_maker": "Design & Creative",

    # DIY & Home
    "diy_home_group": "DIY & Home", "car_maintenance": "DIY & Home", "fuel": "DIY & Home",
    "fuel_consumption": "DIY & Home", "plant_care": "DIY & Home", "recipe_scaler": "DIY & Home",
    "speedometer": "DIY & Home",

    # Fashion & Lifestyle
    "size_fit_group": "Fashion & Lifestyle", "clothing_sizes": "Fashion & Lifestyle",
    "shoe_sizes": "Fashion & Lifestyle", "ring_sizes": "Fashion & Lifestyle", "bra_calculator": "Fashion & Lifestyle",
    "body_measurements": "Fashion & Lifestyle", "dress_guide": "Fashion & Lifestyle",
    "clothes_guide": "Fashion & Lifestyle", "headwear_guide": "Fashion & Lifestyle",
    "footwear_guide": "Fashion & Lifestyle", "accessories_guide": "Fashion & Lifestyle",
    "fashion_guide": "Fashion & Lifestyle", "traditional_fashion": "Fashion & Lifestyle",
    "modern_fashion": "Fashion & Lifestyle", "tribal_fashion": "Fashion & Lifestyle",
    "indian_fashion": "Fashion & Lifestyle", "world_fashion": "Fashion & Lifestyle",
    "all_countries_sizes": "Fashion & Lifestyle", "topwear_guide": "Fashion & Lifestyle",
    "bottomwear_guide": "Fashion & Lifestyle", "waistwear_guide": "Fashion & Lifestyle",
    "panchangam": "Fashion & Lifestyle", "zodiac": "Fashion & Lifestyle", "size_guide": "Fashion & Lifestyle",

    # Social Presence
    "social_media_group": "Social Presence", "bio_linker": "Social Presence", "social_preview": "Social Presence",
}

# New logical order for the tools list (Top-level tools)
main_tool_routes = [
    "dashboard", "developer_console", "security_vault", "ai_companion", "web_scraper", "web",
    "audio_tools_group", "video_tools_group", "image_tools_group", "gif_tools_group",
    "science_group", "math_group", "util_group", "prod_group", "calc_group", "conv_group", "engineering_group",
    "game_group", "system_group", "sensor_group", "net_group", "security_group", "pdf_tools_group", "docs_group",
    "finance_group", "health_group", "weather_group", "outdoor_group", "design_tools_group", "diy_home_group",
    "size_fit_group", "social_media_group"
]

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
for line in lines:
    if 'Tool(' in line:
        # Extract route
        route_match = re.search(r'"([^"]*)"', line.split(',')[2])
        if route_match:
            route = route_match.group(1)
            if route in route_to_hub:
                new_hub = route_to_hub[route]
                # Surgical replacement of category
                parts = line.split(',')
                # category is parts[3]
                parts[3] = f' "{new_hub}"'
                line = ','.join(parts)
    new_lines.append(line)

with open('app/src/main/java/omni/toolbox/model/ToolProvider.kt', 'w') as f:
    f.writelines(new_lines)
