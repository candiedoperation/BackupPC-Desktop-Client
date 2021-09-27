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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class bpcConfigParser {
    public Map<String, String> bpcConfigData;
    
    public bpcConfigParser () throws ConfigurationException, IOException {
        bpcConfigData = new HashMap<>();
        bpcConfigData.put("rsyncd_conf_path", "/home/atheesh/rsyncd.conf");
    }
}
