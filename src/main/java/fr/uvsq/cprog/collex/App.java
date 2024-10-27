package fr.uvsq.cprog.collex;

import dns.AdresseIP;
import dns.Dns;
import dns.NomMachine;
import dns.DnsItem;

import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        try {
            Dns dns = new Dns("config.properties");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Bienvenue dans le simulateur de DNS !");
            String command;

            while (true) {
                System.out.print("Entrez une commande : ");
                command = scanner.nextLine().trim();

                if (command.equalsIgnoreCase("quit")) {
                    break;
                } else if (command.startsWith("resolve ")) {
                    handleResolveCommand(dns, command);
                } else if (command.startsWith("ls")) {
                    handleListCommand(dns);
                } else if (command.startsWith("add ")) {
                    handleAddCommand(dns, command);
                } else {
                    System.out.println("Commande non reconnue !");
                }
            }

            System.out.println("Au revoir !");
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    private static void handleResolveCommand(Dns dns, String command) {
        String[] parts = command.split("\\s+");
        if (parts.length == 2) {
            try {
                DnsItem item = dns.getItem(new NomMachine(parts[1]));
                if (item != null) {
                    System.out.println("Résultat : " + item);
                } else {
                    System.out.println("Nom de machine introuvable !");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        } else {
            System.out.println("Format de commande invalide !");
        }
    }

    private static void handleListCommand(Dns dns) {
        dns.getNameToItemMap().values().forEach(System.out::println);
    }

    private static void handleAddCommand(Dns dns, String command) {
        String[] parts = command.split("\\s+");
        if (parts.length == 3) {
            try {
                AdresseIP ip = new AdresseIP(parts[1]);
                NomMachine machineName = new NomMachine(parts[2]);
                DnsItem item = new DnsItem(ip, machineName);
                dns.addItem(item);
                System.out.println("Ajouté : " + item);
            } catch (IllegalArgumentException | IOException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        } else {
            System.out.println("Format de commande invalide !");
        }
    }
}
