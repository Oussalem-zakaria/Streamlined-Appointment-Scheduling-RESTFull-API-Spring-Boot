package com.ucd.CenterRDV.services;

import com.ucd.CenterRDV.models.Creneau;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.antlr.v4.runtime.misc.Utils.readFile;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailFromTemplate(String to, String name, String date, String center, Creneau creneau) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("mailtrap@demomailtrap.com\n"));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("Nouvelle réservation de rendez-vous confirmée"); // Modifiez le sujet ici

        // Modifiez le chemin du fichier HTML
        String templatePath = "/Users/ai/Documents/web semantique/SPRING BOOT APP/Center-RDV/src/main/resources/static/template.html";

        // Read the HTML template into a String variable
        String htmlTemplate = null;
        try {
            htmlTemplate = readFileAsString(templatePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Replace placeholders in the HTML template with dynamic values
        htmlTemplate = htmlTemplate.replace("${name}", name);
        htmlTemplate = htmlTemplate.replace("${date}", date);
        htmlTemplate = htmlTemplate.replace("${center}", center);
        htmlTemplate = htmlTemplate.replace("${heureDebut}", creneau.getHeureDebut().toString());
        htmlTemplate = htmlTemplate.replace("${heureFin}", creneau.getHeureFin().toString());

        // Set the email's content to be the HTML template
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    private String readFileAsString(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public void sendPasswordResetEmail(String email, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("mailtrap@demomailtrap.com\n")); // Replace with your sender email
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("MyRDV - Réinitialisation de mot de passe");

        // Customize the email body with a link to reset the password
        String resetUrl = "https://5fc7-105-66-134-221.ngrok-free.app/auth/reset-password?token=" + token + "&email="+email;
        String messageBody = "Bonjour,\n\nVous avez demandé la réinitialisation de votre mot de passe sur MyRDV.\n\n" +
                "Cliquez sur le lien suivant pour réinitialiser votre mot de passe :\n" + resetUrl + "\n\n" +
                "Ce lien est valide pendant 1 heure.\n\n" +
                "Cordialement,\nL'équipe MyRDV";

        message.setText(messageBody);

        mailSender.send(message);
    }

}
