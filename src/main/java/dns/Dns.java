package dns;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Dns {
    private Map<AdresseIP, DnsItem> ipToItemMap = new HashMap<>();
    private Map<NomMachine, DnsItem> nameToItemMap = new HashMap<>();
    private String databaseFile;

    public Dns(String propertiesFile) throws IOException {
        loadProperties(propertiesFile);
        loadDatabase();
    }

    private void loadProperties(String propertiesFile) throws IOException {
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Paths.get(propertiesFile)));
        databaseFile = properties.getProperty("databaseFile");
        if (databaseFile == null || databaseFile.isEmpty()) {
            throw new IllegalArgumentException("Le fichier de base de données n'est pas spécifié dans le fichier de propriétés.");
        }
    }

    private void loadDatabase() throws IOException {
        Path path = Paths.get(databaseFile);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            if (parts.length == 2) {
                AdresseIP ip = new AdresseIP(parts[1]);
                NomMachine machine = new NomMachine(parts[0]);
                DnsItem item = new DnsItem(ip, machine);
                addItem(item, false);
            }
        }
    }

    public DnsItem getItem(AdresseIP ip) {
        return ipToItemMap.get(ip);
    }

    public DnsItem getItem(NomMachine machineName) {
        return nameToItemMap.get(machineName);
    }

    public List<DnsItem> getItems(String domain) {
        return nameToItemMap.values().stream()
                .filter(item -> item.getMachineName().getDomainName().equals(domain))
                .collect(Collectors.toList());
    }

    public void addItem(DnsItem item) throws IOException {
        addItem(item, true);
    }

    private void addItem(DnsItem item, boolean updateFile) throws IOException {
        if (ipToItemMap.containsKey(item.getIpAddress()) || nameToItemMap.containsKey(item.getMachineName())) {
            throw new IllegalArgumentException("L'adresse IP ou le nom de machine existe déjà !");
        }
        ipToItemMap.put(item.getIpAddress(), item);
        nameToItemMap.put(item.getMachineName(), item);

        if (updateFile) {
            saveItemToFile(item);
        }
    }

    private void saveItemToFile(DnsItem item) throws IOException {
        String entry = item.getMachineName() + " " + item.getIpAddress() + "\n";
        Files.write(Paths.get(databaseFile), entry.getBytes(), StandardOpenOption.APPEND);
    }

    public void removeItem(AdresseIP ip) throws IOException {
        DnsItem item = ipToItemMap.remove(ip);
        if (item != null) {
            nameToItemMap.remove(item.getMachineName());
            saveDatabase();
        }
    }

    public void removeItem(NomMachine machineName) throws IOException {
        DnsItem item = nameToItemMap.remove(machineName);
        if (item != null) {
            ipToItemMap.remove(item.getIpAddress());
            saveDatabase();
        }
    }

    private void saveDatabase() throws IOException {
        List<String> entries = ipToItemMap.values().stream()
                .map(DnsItem::toString)
                .collect(Collectors.toList());
        Files.write(Paths.get(databaseFile), entries);
    }

    public Map<AdresseIP, DnsItem> getIpToItemMap() {
        return ipToItemMap;
    }

    public Map<NomMachine, DnsItem> getNameToItemMap() {
        return nameToItemMap;
    }
}
