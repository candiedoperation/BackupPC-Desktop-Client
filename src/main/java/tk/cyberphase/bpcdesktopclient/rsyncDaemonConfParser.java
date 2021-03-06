/*
    BackupPC Desktop Client
    Copyright (C) 2021  Atheesh Thirumalairajan

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package tk.cyberphase.bpcdesktopclient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 *
 * @author atheesh
 */
public class rsyncDaemonConfParser {
    
    public static Set<String> invalidModuleNames;
    
    public rsyncDaemonConfParser(String daemonConfPath) throws ConfigurationException, IOException {
        invalidModuleNames = new HashSet<>();        
        INIConfiguration iniConfig = new INIConfiguration();
        BufferedReader configReader = Files.newBufferedReader(Paths.get(daemonConfPath), StandardCharsets.UTF_8);        
        
        iniConfig.read(configReader);
        invalidModuleNames = iniConfig.getSections(); //Using getSection() will create the section (if not exists)        
    }
    
    public rsyncDaemonConfParser() {
        
    }

    public List<iniConfigData> parseDaemonConfig(String daemonConfPath) throws FileNotFoundException, ConfigurationException, IOException {
        List<iniConfigData> daemonConfigData = new ArrayList<>();
        INIConfiguration iniConfig = new INIConfiguration();
        BufferedReader configReader = Files.newBufferedReader(Paths.get(daemonConfPath), StandardCharsets.UTF_8);
        iniConfig.read(configReader);

        Set<String> sectionNames = iniConfig.getSections();
        //System.out.printf("Daemon Config Section names: %s\n", sectionNames.toString()); //Debugging
        sectionNames.forEach(sectionName -> {
            SubnodeConfiguration section = iniConfig.getSection(sectionName);
            if (section != null) {
                Iterator<String> keys = section.getKeys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = section.getString(key);

                    if (value != null) {
                        //key = key.replace("..", "."); // TODO: find a better way than this hack
                        iniConfigData parsedConfigField = new iniConfigData();
                        parsedConfigField.config_header = sectionName;
                        parsedConfigField.config_settings.put(key, value);
                        daemonConfigData.add(parsedConfigField);
                    }
                }
            }
        });

        configReader.close();
        return daemonConfigData;
    }
    
    public void addConfigHeader(Map<String,String> config_data) {
        System.out.println(config_data.toString());
    }
    
    public boolean isSectionNameInvalid(String configHeaderName) throws IOException, ConfigurationException {        
        return invalidModuleNames.stream().anyMatch(configHeaderName::equalsIgnoreCase); //INI Header Names are case-insensitive in Windows, Others may behave differently
    }
    
    public void removeConfigHeader(String configHeaderName) {
        
    }

    public void main(String[] args) {
        try {
            List<iniConfigData> data = parseDaemonConfig(new bpcConfigParser().bpcConfigData.get("rsyncd_conf_path"));
            data.forEach(data_field -> {
                System.out.println(data_field.config_header + "=>" + data_field.config_settings.toString());
            });
        } catch (FileNotFoundException e) {
            Logger.getLogger(rsyncDaemonConfParser.class.getName()).log(Level.SEVERE, null, e);
        } catch (ConfigurationException | IOException ex) {
            Logger.getLogger(rsyncDaemonConfParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
