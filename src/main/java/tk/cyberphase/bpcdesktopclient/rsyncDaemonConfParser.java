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
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
        iniConfigData parsedConfigField = new iniConfigData();        
        INIConfiguration iniConfig = new INIConfiguration();
        BufferedReader configReader = Files.newBufferedReader(Paths.get(daemonConfPath), StandardCharsets.UTF_8);
        iniConfig.read(configReader);

        Set<String> sectionNames = iniConfig.getSections();
        String currentIteratorSection = null;
        int currentIteratorSectionID = 0;
        //System.out.printf("Daemon Config Section names: %s\n", sectionNames.toString()); //Debugging
        
        for (String sectionName : sectionNames) {
            SubnodeConfiguration section = iniConfig.getSection(sectionName);
            
            if (sectionName != null && currentIteratorSection == null) {
                parsedConfigField = new iniConfigData();
                daemonConfigData.add(parsedConfigField);
                currentIteratorSection = sectionName;
                
                System.out.println("First Non-Global Section of Configuration File: " + currentIteratorSection);
            } else if (sectionName == null && currentIteratorSection == null) {
                parsedConfigField = new iniConfigData();
                daemonConfigData.add(parsedConfigField);
                currentIteratorSection = null;
                
                System.out.println(String.format("Global Section of the Configuration File: %s", currentIteratorSection));
            } else if (!sectionName.equals(currentIteratorSection)) {                
                parsedConfigField = new iniConfigData();
                daemonConfigData.add(parsedConfigField);
                currentIteratorSection = sectionName;
                currentIteratorSectionID++;
                
                System.out.println(String.format("Non-Global Section %d of the Configuration File: %s", (currentIteratorSectionID + 1), currentIteratorSection));  //+1 is for Human Readability. It lists length instead of index.                
            }
            
            if (section != null) {
                Iterator<String> keys = section.getKeys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = section.getString(key);

                    if (value != null) {
                        //key = key.replace("..", "."); // TODO: find a better way than this hack
                        parsedConfigField.config_header = sectionName;
                        parsedConfigField.config_settings.put(key, value);
                        daemonConfigData.set(currentIteratorSectionID, parsedConfigField);
                    }
                }
            }
        }
        
        //Debugging
        daemonConfigData.forEach(daemonConfiguration -> {
            System.out.println(daemonConfiguration.convertToString());
        });

        configReader.close();
        return daemonConfigData;
    }
    
    public List<iniConfigData> getNonGlobalFormattedParsedData (List<iniConfigData> parsedINIConfigData) {
        ListIterator parsedINIConfigDataIterator = parsedINIConfigData.listIterator();
        while (parsedINIConfigDataIterator.hasNext()) { parsedINIConfigDataIterator.next(); }
        
        for (iniConfigData internalParsedINIConfigData : parsedINIConfigData) {
            if (internalParsedINIConfigData.config_header == null) {
                parsedINIConfigData.remove(internalParsedINIConfigData);
                break;
            }
        }
        
        return parsedINIConfigData;
    }
    
    public void addConfigHeader(Map<String,String> config_data) {
        System.out.println(config_data.toString());
    }
    
    public boolean isSectionNameInvalid(String configHeaderName) throws IOException, ConfigurationException {        
        return invalidModuleNames.stream().anyMatch(configHeaderName::equalsIgnoreCase); //INI Header Names are case-insensitive in Windows, Others may behave differently
    }
    
    public void removeConfigHeader(String configHeaderName, String daemonConfPath) throws FileNotFoundException, IOException, ConfigurationException {
        INIConfiguration iniConfig = new INIConfiguration();
        BufferedReader configReader = Files.newBufferedReader(Paths.get(daemonConfPath), StandardCharsets.UTF_8);
        iniConfig.read(configReader);     
        
        SubnodeConfiguration deletableConfigHeader = iniConfig.getSection(configHeaderName);
        deletableConfigHeader.clear();
        
        configReader.close();
        
        BufferedWriter configWriter = new BufferedWriter(new FileWriter(daemonConfPath));
        iniConfig.write(configWriter);
        
        configWriter.close();
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
