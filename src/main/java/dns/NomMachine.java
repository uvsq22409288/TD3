package dns;



public class NomMachine {
    private final String qualifiedName;

    public NomMachine(String qualifiedName) {
        if (!qualifiedName.contains(".")) {
            throw new IllegalArgumentException("Format de nom qualifié invalide : " + qualifiedName);
        }
        this.qualifiedName = qualifiedName;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public String getDomainName() {
        return qualifiedName.substring(qualifiedName.indexOf('.') + 1);
    }

    public String getMachineName() {
        return qualifiedName.substring(0, qualifiedName.indexOf('.'));
    }

    @Override
    public String toString() {
        return qualifiedName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NomMachine that = (NomMachine) obj;
        return qualifiedName.equals(that.qualifiedName);
    }

    @Override
    public int hashCode() {
        return qualifiedName.hashCode();
    }
}
