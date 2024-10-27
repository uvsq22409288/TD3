package dns;

public class AdresseIP {
    private final String ip;

    public AdresseIP(String ip) {
        if (!ip.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b")) {
            throw new IllegalArgumentException("Format d'adresse IP invalide : " + ip);
        }
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return ip;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AdresseIP adresseIP = (AdresseIP) obj;
        return ip.equals(adresseIP.ip);
    }

    @Override
    public int hashCode() {
        return ip.hashCode();
    }
}