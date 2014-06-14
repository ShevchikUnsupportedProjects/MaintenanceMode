/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package mmode.bungee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class YamlConfiguration {

	private static final char SEPARATOR = '.';
	private Map<String, Object> self = new LinkedHashMap<String, Object>();

	@SuppressWarnings("unchecked")
	public void load(InputStream is) {
		self = yaml.loadAs(is, LinkedHashMap.class);
	}

	public void load(File file) throws FileNotFoundException {
		load(new FileInputStream(file));
	}

	public void save(OutputStream os) {
		yaml.dump(self, new OutputStreamWriter(os));
	}

	public void save(File file) throws IOException {
		yaml.dump(self, new FileWriter(file));
	}

	private Yaml yaml;

	public YamlConfiguration() {
		initYAML();
	}

	private YamlConfiguration(Map<String, Object> section) {
		self = section;
		initYAML();
	}

	private void initYAML() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);
	}

	@SuppressWarnings("unchecked")
	private YamlConfiguration getSectionFor(String path) {
		int index = path.indexOf(SEPARATOR);
		if (index == -1) {
			return this;
		}

		String root = path.substring(0, index);
		Object section = self.get(root);
		if (section == null) {
			section = new LinkedHashMap<String, Object>();
			self.put(root, section);
		}
		if (section instanceof YamlConfiguration) {
			return (YamlConfiguration) section;
		}

		return new YamlConfiguration((Map<String, Object>) section);
	}

	private String getChild(String path) {
		int index = path.indexOf(SEPARATOR);
		return (index == -1) ? path : path.substring(index + 1);
	}


	@SuppressWarnings("unchecked")
	public <T> T get(String path, T def) {
		YamlConfiguration section = getSectionFor(path);
		Object val;
		if (section == this) {
			val = self.get(path);
		} else {
			val = section.get(getChild(path), def);
		}

		return (val != null) ? (T) val : def;
	}

	public Object get(String path) {
		return get(path, null);
	}

	public void set(String path, Object value) {
		YamlConfiguration section = getSectionFor(path);
		if (section == this) {
			self.put(path, value);
		} else {
			section.set(getChild(path), value);
		}
	}

	public byte getByte(String path) {
		return getByte(path, (byte) 0);
	}

	public byte getByte(String path, byte def) {
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).byteValue() : def;
	}

	public List<Byte> getByteList(String path) {
		List<?> list = getList(path);
		List<Byte> result = new ArrayList<Byte>();

		for (Object object : list) {
			if (object instanceof Number) {
				result.add(((Number) object).byteValue());
			}
		}

		return result;
	}

	public short getShort(String path) {
		return getShort(path, (short) 0);
	}

	public short getShort(String path, short def) {
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).shortValue() : def;
	}

	public List<Short> getShortList(String path) {
		List<?> list = getList(path);
		List<Short> result = new ArrayList<Short>();

		for (Object object : list) {
			if (object instanceof Number) {
				result.add(((Number) object).shortValue());
			}
		}

		return result;
	}

	public int getInt(String path) {
		return getInt(path, 0);
	}

	public int getInt(String path, int def) {
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).intValue() : def;
	}

	public List<Integer> getIntList(String path) {
		List<?> list = getList(path);
		List<Integer> result = new ArrayList<Integer>();

		for (Object object : list) {
			if (object instanceof Number) {
				result.add(((Number) object).intValue());
			}
		}

		return result;
	}

	public long getLong(String path) {
		return getLong(path, 0);
	}

	public long getLong(String path, long def) {
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).longValue() : def;
	}

	public List<Long> getLongList(String path) {
		List<?> list = getList(path);
		List<Long> result = new ArrayList<>();

		for (Object object : list) {
			if (object instanceof Number) {
				result.add(((Number) object).longValue());
			}
		}

		return result;
	}

	public float getFloat(String path) {
		return getFloat(path, 0);
	}

	public float getFloat(String path, float def) {
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).floatValue() : def;
	}

	public List<Float> getFloatList(String path) {
		List<?> list = getList(path);
		List<Float> result = new ArrayList<Float>();

		for (Object object : list) {
			if (object instanceof Number) {
				result.add(((Number) object).floatValue());
			}
		}

		return result;
	}

	public double getDouble(String path) {
		return getDouble(path, 0);
	}

	public double getDouble(String path, double def) {
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).doubleValue() : def;
	}

	public List<Double> getDoubleList(String path) {
		List<?> list = getList(path);
		List<Double> result = new ArrayList<Double>();

		for (Object object : list) {
			if (object instanceof Number) {
				result.add(((Number) object).doubleValue());
			}
		}

		return result;
	}

	public boolean getBoolean(String path) {
		return getBoolean(path, false);
	}

	public boolean getBoolean(String path, boolean def) {
		Object val = get(path, def);
		return (val instanceof Boolean) ? (Boolean) val : def;
	}

	public List<Boolean> getBooleanList(String path) {
		List<?> list = getList(path);
		List<Boolean> result = new ArrayList<Boolean>();

		for (Object object : list) {
			if (object instanceof Boolean) {
				result.add((Boolean) object);
			}
		}

		return result;
	}

	public char getChar(String path) {
		return getChar(path, '\u0000');
	}

	public char getChar(String path, char def) {
		Object val = get(path, def);
		return (val instanceof Character) ? (Character) val : def;
	}

	public List<Character> getCharList(String path) {
		List<?> list = getList(path);
		List<Character> result = new ArrayList<Character>();

		for (Object object : list) {
			if (object instanceof Character) {
				result.add((Character) object);
			}
		}

		return result;
	}

	public String getString(String path) {
		return getString(path, "");
	}

	public String getString(String path, String def) {
		Object val = get(path, def);
		return (val instanceof String) ? (String) val : def;
	}

	public List<String> getStringList(String path) {
		List<?> list = getList(path);
		List<String> result = new ArrayList<String>();

		for (Object object : list) {
			if (object instanceof String) {
				result.add((String) object);
			}
		}

		return result;
	}

	public List<?> getList(String path) {
		return getList(path, Collections.EMPTY_LIST);
	}

	public List<?> getList(String path, List<?> def) {
		Object val = get(path, def);
		return (val instanceof List<?>) ? (List<?>) val : def;
	}

}
