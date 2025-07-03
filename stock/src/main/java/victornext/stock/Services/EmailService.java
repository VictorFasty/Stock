package victornext.stock.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${alert.recipient.email}")
    private String emailDestino;

    public void enviarAlertaEstoque(String mensagem) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailDestino);
        email.setSubject("⚠️ Alerta de Estoque Baixo");
        email.setText(mensagem);
        javaMailSender.send(email);
    }
}


