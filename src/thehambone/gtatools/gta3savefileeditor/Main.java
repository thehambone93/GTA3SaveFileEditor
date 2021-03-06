package thehambone.gtatools.gta3savefileeditor;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import thehambone.gtatools.gta3savefileeditor.gxt.GXT;
import thehambone.gtatools.gta3savefileeditor.util.GUIUtilities;
import thehambone.gtatools.gta3savefileeditor.util.Logger;
import thehambone.gtatools.gta3savefileeditor.util.ResourceLoader;
import thehambone.gtatools.gta3savefileeditor.util.UncaughtExceptionHandler;


/**
 * This class handles program initialization and contains general program
 * information as constants.
 * <p>
 * Created on Feb 6, 2015.
 * 
 * @author thehambone
 */
public final class Main
{
    public static final String PROGRAM_TITLE = "GTA III Save File Editor";
    public static final String PROGRAM_VERSION = "0.2";
    public static final String PROGRAM_AUTHOR = "thehambone";
    public static final String PROGRAM_AUTHOR_EMAIL = "thehambone93@gmail.com";
    public static final String PROGRAM_AUTHOR_URL
            = "http://gtaforums.com/index.php?showuser=907241";
    public static final String PROGRAM_UPDATE_URL
            = "http://www.gtagarage.com/mods/show.php?id=27101";
    public static final String PROJECT_PAGE_URL
            = "http://gtaforums.com/index.php?showtopic=784598";
    public static final String PROGRAM_COPYRIGHT
            = "Copyright (C) 2015-2016 thehambone";
    
    private static final String BUILD_PROPERTIES_PATH
            = "META-INF/build.properties";
    private static final String PROGRAM_ICON_PATH
            = "META-INF/res/icon.png";
    private static final String GXT_PATH
            = "META-INF/res/mobile/american.gxt";
    
    private static final Logger.Level DEFAULT_LOGGING_LEVEL = Logger.Level.INFO;
    
    private static Date buildDate = new Date(0);
    private static int buildNumber = 0; 
    
    private static OperatingSystem os = OperatingSystem.UNKNOWN;
    
    private static boolean isDebugModeEnabled = false;
    private static boolean showHelpMessage = false;
    
    /**
     * Checks whether debug mode is enabled.
     * 
     * @return {@code true} if debug mode is enabled, {@code false} otherwise
     */
    public static boolean isDebugModeEnabled()
    {
        return isDebugModeEnabled;
    }
    
    /**
     * Returns the {@code OperatingSystem} constant that identifies the current
     * OS.
     * 
     * @return the current operating system as an {@code OperatingSystem}
     * constant
     */
    public static OperatingSystem getOperatingSystem()
    {
        return os;
    }
    
    /**
     * Gets the program's build date as a {@code Date} object.
     * 
     * @return the program build date
     */
    public static Date getBuildDate()
    {
        return buildDate;
    }
    
    /**
     * Gets the program build number.
     * 
     * @return the current build number
     */
    public static int getBuildNumber()
    {
        return buildNumber;
    }
    
    /**
     * The program entry point.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args)
    {
        /* TODO (0.3):
         * -Generate crash dumps when a fatal error occurs, include info about
         *  the state of the program and a save file varaible dump
         * -Document savefile package
         */
        
        initLogger();
        parseCommandLineArgs(args);
        initLookAndFeel();
        initUncaughtExceptionHandler();
        determineOS();
        readBuildProperties();
        
        if (showHelpMessage) {
            showHelpMessage();
        }
        
        Logger.info("%s\n", PROGRAM_TITLE);
        Logger.info("%s <%s>\n", PROGRAM_COPYRIGHT, PROGRAM_AUTHOR_EMAIL);
        Logger.info("Version %s build %d\n", PROGRAM_VERSION, buildNumber);
        Logger.info("Compiled on %s\n",
                new SimpleDateFormat("MMM. dd, yyyy").format(buildDate));
        
        Logger.info("os.name = %s\n", System.getProperty("os.name"));
        Logger.info("os.version = %s\n", System.getProperty("os.version"));
        Logger.info("os.arch = %s\n", System.getProperty("os.arch"));
        Logger.info("java.version = %s\n", System.getProperty("java.version"));
        Logger.info("sun.arch.data.model = %s\n",
                System.getProperty("sun.arch.data.model"));
        
        initShutdownHooks();
        loadSettings();
        loadGXT();
        Image icon = loadIcon();
        createAndShowGUI(icon);
    }
    
    /**
     * Sets up the status logger.
     */
    private static void initLogger()
    {
        Logger.newInstance(DEFAULT_LOGGING_LEVEL);
    }
    
    /**
     * Analyzes command-line arguments.
     */
    private static void parseCommandLineArgs(String[] args)
    {
        if (args.length == 0) {
            return;
        }
        
        Logger.debug("Parsing command-line options...");
        for (String arg : args) {
            if (!arg.startsWith("--")) {
                continue;
            }
            
            Logger.debug("Parsing argument: %s\n", arg);
            
            String[] tokens = arg.split("=");   // format: --argument=option
            switch (tokens[0].substring(2)) {
                case "debug":
                    isDebugModeEnabled = true;
                    Logger.info("Debug mode enabled!");
                    break;
                case "help":
                    showHelpMessage = true;
                    break;
                case "log-level":
                    if (tokens.length < 2) {
                        Logger.warn("Option not specified for 'log-level' "
                                + "- argument will be ignored");
                    } else {
                        try {
                            Logger.Level l = Logger.Level.valueOf(tokens[1]);
                            Logger.getLogger().setLevel(l);
                            Logger.info("Logging level set to %s\n", l.name());
                        } catch (IllegalArgumentException ex) {
                            Logger.warn("Invalid logging level. Using default "
                                    + "logging level (%s) [%s: %s]\n",
                                    DEFAULT_LOGGING_LEVEL,
                                    ex.getClass().getName(), ex.getMessage());
                        }
                    }
                    break;
                case "log-to-file":
                    try {
                        Logger.getLogger().logToFile(true);
                        Logger.info("Logging to file enabled!");
                    } catch (FileNotFoundException ex) {
                        Logger.error("Unable to create log file.", ex);
                    }
                    break;
                default:
                    Logger.warn("Unknown command-line option: " + arg);
            }
        }
    }
    
    /**
     * Sets the application look and feel to match the system's look and feel.
     */
    private static void initLookAndFeel()
    {
        try {
            Logger.debug("Initializing system look and feel...");
            String lafClassName = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lafClassName);
            Logger.debug("Look and feel set to \"" + lafClassName + "\"");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.warn("Failed to initialize UI look and feel. "
                    + "Using default look and feel.", ex);
            Logger.stackTrace(ex);
        }
    }
    
    /**
     * Sets the default uncaught exception handler for all threads.
     */
    private static void initUncaughtExceptionHandler()
    {
        Logger.debug("Setting default uncaught exception handler...");
        Thread.setDefaultUncaughtExceptionHandler(
                new UncaughtExceptionHandler());
    }
    
    /**
     * Determines on which operating system the program is running by analyzing
     * the OS name.
     */
    private static void determineOS()
    {
        Logger.debug("Determining operating system...");
        
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            os = OperatingSystem.WINDOWS;
        } else if (osName.contains("mac")) {
            os = OperatingSystem.MAC_OS_X;
        } else if (osName.contains("nix") || osName.contains("nux")
                || osName.contains("aix")) {
            os = OperatingSystem.LINUX_FAMILY;
        } else {
            os = OperatingSystem.UNKNOWN;
            Logger.warn("Unknown operating system");
        }
        
        Logger.debug("Operating system: " + os.name());
    }
    
    /**
     * Reads build information from a file embedded in the JAR.
     */
    private static void readBuildProperties()
    {
        Logger.debug("Reading build properties...");
        
        Properties buildProperties = new Properties();
        InputStream in = null;
        try {
            in = ResourceLoader.getResourceStream(BUILD_PROPERTIES_PATH);
            if (in == null) {
                throw new IOException("file not found - "
                        + BUILD_PROPERTIES_PATH);
            }
            buildProperties.load(in);
        } catch (IOException ex) {
            Logger.error("Failed to read build properties!", ex);
            Logger.stackTrace(ex);
        }
            
        String buildDateFormat;
        String buildDateRaw;
        String buildNumberRaw;

        buildDateFormat = buildProperties.getProperty("build.date.format");
        buildDateRaw = buildProperties.getProperty("build.date");
        buildNumberRaw = buildProperties.getProperty("build.number");

        if (buildDateFormat != null && buildDateRaw != null) {
            try {
                buildDate = new SimpleDateFormat(buildDateFormat)
                        .parse(buildDateRaw);
            } catch (ParseException ex) {
                Logger.error("Failed to parse build date.");
                Logger.stackTrace(ex);
            }
        }
        if (buildNumberRaw != null) {
            buildNumber = Integer.parseInt(buildNumberRaw);
        }
        
        Logger.debug("Build properties loaded successfully!");
    }
    
    /**
     * Displays a message box containing valid command-line arguments.
     */
    private static void showHelpMessage()
    {
        String helpString = "Command-line options:\n\n"
                + "--debug\n"
                + "--help\n"
                + "--log-level=&#60;DEBUG | INFO | WARN | ERROR | FATAL&#62;\n"
                + "--log-to-file";
        GUIUtilities.showInformationMessageBox(null, helpString, "Help", 350);
    }
    
    /**
     * Defines actions to be performed when the JVM closes.
     */
    private static void initShutdownHooks()
    {
        Logger.debug("Initializing shutdown hooks...");
        Runnable saveSettingsHook = new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    Logger.info("Saving program settings...");
                    Settings.save();
                } catch (IOException ex) {
                    Logger.error("Failed to save program settings!", ex);
                    Logger.stackTrace(ex);
                }
            }
        };
        
        Runtime.getRuntime().addShutdownHook(
                new Thread(saveSettingsHook, "Save-Settings"));
        Logger.debug("\"Save-Settings\" hook initialized!");
    }
    
    /**
     * Loads program settings from a file.
     */
    private static void loadSettings()
    {
        Logger.info("Loading program settings...");
        
        try {
            Settings.load();
        } catch (IOException ex) {
            String errMsg = "Failed to load program settings. "
                    + "Default settings will be used.";
            Logger.warn(errMsg, ex);
            Logger.stackTrace(ex);
            GUIUtilities.showErrorMessageBox(null, errMsg, "IO Error", ex);
            Settings.loadDefaults();
            return;
        }
        
        String debugLogging = Settings.get(Settings.Key.DEBUG_LOGGING);
        if (!Boolean.parseBoolean(debugLogging)) {
            return;
        }
        
        try {
            Logger.getLogger().logToFile(true);
            Logger.getLogger().setLevel(Logger.Level.DEBUG);
            Logger.info("Debug logging enabled!");
        } catch (FileNotFoundException ex) {
            Logger.error("Unable to enable debug logging!", ex);
            Logger.stackTrace(ex);
            GUIUtilities.showErrorMessageBox(null,
                    "Unable to enable debug logging!", "Error", ex);
        }
    }
    
    /**
     * Loads a GXT table from a GXT file embedded in the JAR.
     */
    private static void loadGXT()
    {
        Logger.info("Loading GXT table...");
        try {
            InputStream gxtStream = ResourceLoader.getResourceStream(GXT_PATH);
            GXT.loadGXTTable(gxtStream);
        } catch (IOException ex) {
            String errMsg = "Failed to load GXT table. "
                    + "Some features will be unavailable.";
            Logger.error(errMsg, ex);
            Logger.stackTrace(ex.fillInStackTrace());
            GUIUtilities.showErrorMessageBox(null, errMsg, "IO Error", ex);
        }
    }
      
    /**
     * Loads and returns the program icon from an image embedded in the JAR.
     */
    private static Image loadIcon()
    {
        Logger.debug("Loading icon from %s...\n", PROGRAM_ICON_PATH);
        Image icon = null;

        try {
            icon = ResourceLoader.loadImageResource(PROGRAM_ICON_PATH);
        } catch (IOException ex) {
            Logger.warn("Failed to load icon resource.");
            Logger.stackTrace(ex);
        }
        
        return icon;
    }
    
    /**
     * Initializes and displays the main GUI frame.
     */
    private static void createAndShowGUI(final Image icon)
    {
        Logger.info("Loading user interface...");
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                EditorWindow frame = new EditorWindow();
                frame.setTitle(PROGRAM_TITLE + " " + PROGRAM_VERSION);
                frame.setIconImage(icon);
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frame.setSize(630, 515);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
    
    /**
     * Defines all of the supported operating systems.
     */
    public static enum OperatingSystem
    {
        LINUX_FAMILY,
        MAC_OS_X,
        WINDOWS,
        UNKNOWN;
    }
}
