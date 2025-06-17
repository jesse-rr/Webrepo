package com.example.cacophony.model.helper;

public class Permission { // long = 64 bit, int = 32 bit (more restrict)
    // ===== CORE PERMISSIONS =====
    public static final long VIEW_CHANNELS       = 1L << 0;   // 1
    public static final long SEND_MESSAGES       = 1L << 1;   // 2
    public static final long MANAGE_MESSAGES     = 1L << 2;   // 4 (Delete/Edit others' messages)
    public static final long KICK_MEMBERS        = 1L << 3;   // 8
    public static final long BAN_MEMBERS         = 1L << 4;   // 16
    public static final long MANAGE_ROLES        = 1L << 5;   // 32
    public static final long MANAGE_CHANNELS     = 1L << 6;   // 64
    public static final long MANAGE_SERVER       = 1L << 7;   // 128 (Edit server name/icon)
    public static final long CREATE_INVITES      = 1L << 8;   // 256
    public static final long MENTION_EVERYONE    = 1L << 9;   // 512
    public static final long MUTE_MEMBERS        = 1L << 10;  // 1024 (Voice chat)
    public static final long MOVE_MEMBERS        = 1L << 11;  // 2048 (Voice chat)
    public static final long ATTACH_FILES        = 1L << 12;  // 4096
    public static final long CONNECT_VOICE       = 1L << 13;  // 8192
    public static final long SPEAK               = 1L << 14;  // 16384 (Voice chat)

    // ===== SPECIAL PERMISSIONS =====
    public static final long ADMINISTRATOR       = 1L << 15;  // 32768 (Bypass all checks)
    public static final long PRIORITY_SPEAKER    = 1L << 16;  // 65536 (Voice priority)
    public static final long STREAM              = 1L << 17;  // 131072 (Go live in voice)
    public static final long USE_EXTERNAL_EMOJIS = 1L << 18;  // 262144

    // ===== PERMISSION GROUPS =====
    public static final long DEFAULT_MEMBER = VIEW_CHANNELS | SEND_MESSAGES | ATTACH_FILES | CREATE_INVITES;
    public static final long DEFAULT_MODERATOR = DEFAULT_MEMBER | KICK_MEMBERS | MANAGE_MESSAGES | MUTE_MEMBERS;
    public static final long DEFAULT_ADMIN = DEFAULT_MODERATOR | BAN_MEMBERS | MANAGE_CHANNELS | MANAGE_ROLES | MOVE_MEMBERS;
    public static final long OWNER = DEFAULT_ADMIN | MENTION_EVERYONE;

    // ===== HELPER METHODS =====
    public static boolean hasPermission(int bitmask, int permission) {
        return (bitmask & ADMINISTRATOR) != 0 || (bitmask & permission) == permission;
    }
    public static int addPermission(int bitmask, int permission) {
        return bitmask | permission;
    }
    public static int removePermission(int bitmask, int permission) {
        return bitmask & ~permission;
    }
}
