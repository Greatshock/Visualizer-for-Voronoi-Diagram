/*
 * @author Nikita Marinosyan
 * @date 17.01.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

package hse.utilities;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;

/**
 * Utility class with some handy routine
 */
public class Utilities {

    /**
     * Opens webpage in browser if it is supported
     * @param uri - web address to be opened
     * @param component - GUI component where the link is placed
     */
    public static void openWebpage(URI uri, JComponent component)
    {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
        {
            try
            {
                desktop.browse(uri);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(component, "Browser is not supported on you system!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Reads the entire resource text file.
     * * @param resourceName Name of the resource text file
     * * @return Content of the resource text file.
     */

    public static String readAllTextFromResource(String resourceName)
    {
        String s = "";;
        InputStream inputStream =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            s = result.toString("UTF-8");
        } catch (Exception e) {
            // Omitted.
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // Omitted.
            }
        }
        return s;
    }
}