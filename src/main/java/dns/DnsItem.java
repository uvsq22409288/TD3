package dns;

public class DnsItem {
    private final AdresseIP ipAddress;
    private final NomMachine machineName;

    public DnsItem(AdresseIP ipAddress, NomMachine machineName) {
        this.ipAddress = ipAddress;
        this.machineName = machineName;
    }

    public AdresseIP getIpAddress() {
        return ipAddress;
    }

    public NomMachine getMachineName() {
        return machineName;
    }

    @Override
    public String toString() {
        return machineName + " " + ipAddress;
    }
}
