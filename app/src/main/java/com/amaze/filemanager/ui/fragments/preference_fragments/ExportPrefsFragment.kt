package com.amaze.filemanager.ui.fragments.preference_fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.afollestad.materialdialogs.MaterialDialog
import com.amaze.filemanager.BuildConfig
import com.amaze.filemanager.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap

class ExportPrefsFragment : BasePrefsFragment() {
    override val title = R.string.export_import_settings_pref
    var sharedPrefs: SharedPreferences? = null
    var configItemsToExport = java.util.LinkedHashMap<String, Any>()


    private var exportSettingsPreference: Preference? = null
    private var importSettingsPreference: Preference? = null
    private val onClickImportSettings = Preference.OnPreferenceClickListener{

        val configItems = LinkedHashMap<String, Any>().apply {
            // appearance_prefs.xml
                sharedPrefs?.getString(PreferencesConstants.FRAGMENT_THEME, "4")
                    ?.let { it1 -> put(PreferencesConstants.FRAGMENT_THEME, it1) }
            put(PreferencesConstants.FRAGMENT_THEME, findPreference<Preference>(PreferencesConstants.FRAGMENT_THEME).toString())
            put(PreferencesConstants.PREFERENCE_USE_CIRCULAR_IMAGES, findPreference<Preference>(PreferencesConstants.PREFERENCE_USE_CIRCULAR_IMAGES).toString())
            put(PreferencesConstants.PREFERENCE_SHOW_HEADERS, findPreference<Preference>(PreferencesConstants.PREFERENCE_SHOW_HEADERS).toString())
//            put(PreferencesConstants.PREFERENCE_COLORIZE_ICONS, config.primaryColor)
//            put(PreferencesConstants.PREFERENCE_COLORED_NAVIGATION, config.accentColor)
//            put(PreferencesConstants.PREFERENCE_SELECT_COLOR_CONFIG, config.appIconColor)
//            put(PreferencesConstants.PREFERENCE_INTELLI_HIDE_TOOLBAR, config.useEnglish)
//            put(PreferencesConstants.PREFERENCE_GRID_COLUMNS, config.wasUseEnglishToggled)
//            put(PreferencesConstants.PREFERENCE_ENABLE_MARQUEE_FILENAME, config.widgetBgColor)
//            // color_prefs.xml
//            put(PreferencesConstants.PREFERENCE_SKIN, config.widgetTextColor)
//            put(PreferencesConstants.PREFERENCE_SKIN_TWO, config.showWeekNumbers)
//            put(PreferencesConstants.PREFERENCE_ACCENT, config.startWeeklyAt)
//            put(PreferencesConstants.PREFERENCE_ICON_SKIN, config.vibrateOnReminder)
//            put(PreferencesConstants.PRESELECTED_CONFIGS, config.lastEventReminderMinutes1)
//            put(PreferencesConstants.PREFERENCE_COLOR_CONFIG, config.lastEventReminderMinutes1)
//            // ui_prefs.xml
//            put(PreferencesConstants.PREFERENCE_SHOW_THUMB, config.lastEventReminderMinutes2)
//            put(PreferencesConstants.PREFERENCE_SHOW_FILE_SIZE, config.lastEventReminderMinutes3)
//            put(PreferencesConstants.PREFERENCE_SHOW_PERMISSIONS, config.displayPastEvents)
//            put(PreferencesConstants.PREFERENCE_SHOW_GOBACK_BUTTON, config.fontSize)
//            put(PreferencesConstants.PREFERENCE_SHOW_HIDDENFILES, config.listWidgetViewToOpen)
//            put(PreferencesConstants.PREFERENCE_SHOW_LAST_MODIFIED, config.reminderAudioStream)
//            put(PreferencesConstants.PREFERENCE_DRAG_AND_DROP_PREFERENCE, config.displayDescription)
//            put(PreferencesConstants.PREFERENCE_DRAG_AND_DROP_REMEMBERED, config.replaceDescription)
//            // drag and drop
//            put(PreferencesConstants.PREFERENCE_DRAG_DEFAULT, config.showGrid)
//            put(PreferencesConstants.PREFERENCE_DRAG_TO_SELECT, config.loopReminders)
//            put(PreferencesConstants.PREFERENCE_DRAG_TO_MOVE_COPY, config.dimPastEvents)
//            put(PreferencesConstants.PREFERENCE_DRAG_REMEMBER_COPY, config.allowChangingTimeZones)
//            put(PreferencesConstants.PREFERENCE_DRAG_REMEMBER_MOVE, config.usePreviousEventReminders)
//            // bookmarks_prefs.xml
//            put(PreferencesConstants.PREFERENCE_SHOW_SIDEBAR_FOLDERS, config.usePreviousEventReminders)
//            // quickaccess_prefs.xml
//            put(PreferencesConstants.PREFERENCE_SHOW_SIDEBAR_QUICKACCESSES, config.usePreviousEventReminders)
//            // behavior_prefs.xml
//            put(PreferencesConstants.PREFERENCE_ROOT_LEGACY_LISTING, config.defaultReminder1)
//            put(PreferencesConstants.PREFERENCE_ROOTMODE, config.defaultReminder2)
//            put(PreferencesConstants.PREFERENCE_CHANGEPATHS, config.defaultReminder3)
//            put(PreferencesConstants.PREFERENCE_SAVED_PATHS, config.pullToRefresh)
//            put(PreferencesConstants.PREFERENCE_ZIP_EXTRACT_PATH, config.defaultStartTime)
//            put(PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK, config.defaultDuration)
//            // security_prefs.xml
//            put(PreferencesConstants.PREFERENCE_CRYPT_FINGERPRINT, config.useSameSnooze)
//            put(PreferencesConstants.PREFERENCE_CRYPT_MASTER_PASSWORD, config.snoozeTime)
//            put(PreferencesConstants.PREFERENCE_CRYPT_MASTER_PASSWORD_DEFAULT, config.use24HourFormat)
//            put(PreferencesConstants.PREFERENCE_CRYPT_FINGERPRINT_DEFAULT, config.isSundayFirst)
//            put(PreferencesConstants.PREFERENCE_CRYPT_WARNING_REMEMBER, config.isSundayFirst)
//            put(PreferencesConstants.ENCRYPT_PASSWORD_FINGERPRINT, config.isSundayFirst)
//            put(PreferencesConstants.ENCRYPT_PASSWORD_MASTER, PreferencesConstants.ENCRYPT_PASSWORD_MASTER)
//            put(PreferencesConstants.PREFERENCE_CRYPT_WARNING_REMEMBER_DEFAULT, config.isSundayFirst)
//            // others
//            put(PreferencesConstants.PREFERENCE_CURRENT_TAB, config.highlightWeekends)
//            put(PreferencesConstants.PREFERENCE_BOOKMARKS_ADDED, config.highlightWeekendsColor)
//            put(PreferencesConstants.PREFERENCE_DIRECTORY_SORT_MODE, config.allowCreatingTasks)
//            put(PreferencesConstants.PREFERENCE_DRAWER_HEADER_PATH, config.allowCreatingTasks)
//            put(PreferencesConstants.PREFERENCE_URI, config.allowCreatingTasks)
//            put(PreferencesConstants.PREFERENCE_VIEW, config.allowCreatingTasks)
//            put(PreferencesConstants.PREFERENCE_NEED_TO_SET_HOME, config.allowCreatingTasks)
//            put(PreferencesConstants.PREFERENCE_SORTBY_ONLY_THIS, config.allowCreatingTasks)
//            put(PreferencesConstants.PREFERENCE_APPLIST_SORTBY, config.allowCreatingTasks)
//            put(PreferencesConstants.PREFERENCE_APPLIST_ISASCENDING, config.allowCreatingTasks)
        }

        exportSettings(configItems)

        true
    }

    private fun getCurrentFormattedDateTime(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        return simpleDateFormat.format(Date(System.currentTimeMillis()))
    }

    private fun getExportSettingsFilename(): String {
        val appName = BuildConfig.APPLICATION_ID.removeSuffix(".debug").removePrefix("com.amaze.")
        return "$appName-settings_${getCurrentFormattedDateTime()}.txt"
    }
    fun getInternalStoragePath() =
        if (File("/storage/emulated/0").exists()) "/storage/emulated/0" else Environment.getExternalStorageDirectory().absolutePath.trimEnd('/')

    fun String.getFilenameFromPath() = substring(lastIndexOf("/") + 1)

    fun String.isAValidFilename(): Boolean {
        val ILLEGAL_CHARACTERS = charArrayOf('/', '\n', '\r', '\t', '\u0000', '`', '?', '*', '\\', '<', '>', '|', '\"', ':')
        ILLEGAL_CHARACTERS.forEach {
            if (contains(it))
                return false
        }
        return true
    }

    private fun exportSettings(configItems: java.util.LinkedHashMap<String, Any>) {
        val exportSettingsDialogBuilder = MaterialDialog.Builder(activity)
        exportSettingsDialogBuilder.title(
            "Export Setting"
        )

        exportSettingsDialogBuilder.input(
            null,
            getExportSettingsFilename(),
            false
        ) { _, _ -> }

        exportSettingsDialogBuilder.theme(
            activity.utilsProvider.appTheme.getMaterialDialogTheme(requireContext())
        )
        exportSettingsDialogBuilder.positiveText(resources.getString(R.string.ok))
        exportSettingsDialogBuilder.negativeText(resources.getString(R.string.cancel))
        exportSettingsDialogBuilder.positiveColor(activity.accent)
        exportSettingsDialogBuilder.negativeColor(activity.accent)

        exportSettingsDialogBuilder.onPositive{dialog,_ ->
            val filename = dialog.inputEditText!!.text.toString()
            if (filename.isEmpty()) {
                Toast.makeText(activity,"File name cannot be empty",Toast.LENGTH_SHORT)
                return@onPositive
            }

            val newPath = "${getInternalStoragePath().trimEnd('/')}/$filename"
            if (!newPath.getFilenameFromPath().isAValidFilename()) {
                Toast.makeText(activity,"File name cannot contain invalid characters",Toast.LENGTH_SHORT)
                return@onPositive
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            configItemsToExport = configItems
            ExportSettingsDialog(this, getExportSettingsFilename(), true) { path, filename ->
                Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TITLE, filename)
                    addCategory(Intent.CATEGORY_OPENABLE)

                    startActivityForResult(this, SELECT_EXPORT_SETTINGS_FILE_INTENT)
                }
            }
        } else {
            handlePermission(PERMISSION_WRITE_STORAGE) {
                if (it) {
                    ExportSettingsDialog(this, getExportSettingsFilename(), false) { path, filename ->
                        val file = File(path)
                        getFileOutputStream(file.toFileDirItem(this), true) {
                            exportSettingsTo(it, configItems)
                        }
                    }
                }
            }
        }
    }

    private val onClickExportSettings = Preference.OnPreferenceChangeListener{}


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.export_settings_prefs, rootKey)

        exportSettingsPreference = findPreference(
            PreferencesConstants.PREFERENCE_EXPORT_SETTINGS
        )

        importSettingsPreference = findPreference(
            PreferencesConstants.PREFERENCE_IMPORT_SETTINGS
        )


        // Fragments are created before the super call returns, so we must
        // initialize sharedPrefs before the super call otherwise it cannot be used by fragments
//        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

    }
}
