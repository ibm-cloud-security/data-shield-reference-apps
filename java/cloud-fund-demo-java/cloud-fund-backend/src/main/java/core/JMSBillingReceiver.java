package core;

import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import crypto.CryptoService;
import crypto.ICrypto;
import utils.Constants;

@Component
public class JMSBillingReceiver {
	
	private static Logger LOGGER = Logger.getLogger(JMSBillingReceiver.class.getName());
	
	@Autowired 
	private TransactionRepository transactionRepository;
	
	private static ICrypto cryptoImplementation = new CryptoService();
	private String ENCRYPTION_KEY = System.getenv(Constants.ENCRYPTION_KEY_ENV);

    @JmsListener(destination = Constants.BILLING_SERVICE, containerFactory = Constants.JMS_FACTORY)
    public void receiveMessage(BillingInfo billingInfo) throws Exception {
    	
    	SecretKeySpec key = cryptoImplementation.generateKey(ENCRYPTION_KEY);
        LOGGER.info("JMSBillingReceiver.receiveMessage(): Billing information received for transaction: "+billingInfo.getId());
        LOGGER.info("JMSBillingReceiver.receiveMessage(): Processing billing information......");
        String decryptedCreditCardNumber = cryptoImplementation.decrypt(billingInfo.getCreditCardNumber(), key);
        LOGGER.debug("JMSBillingReceiver.receiveMessage(): Decrypted "+decryptedCreditCardNumber);
        Thread.sleep(30000);
        Optional<Transaction> transaction = transactionRepository.findById(billingInfo.getId());
        LOGGER.info("JMSBillingReceiver.receiveMessage(): Billing for transaction "+transaction.get().getId()+" was processed successfully.");
        transaction.get().setStatus(Constants.PROCESSED);
        transactionRepository.save(transaction.get());
        
    }

}
