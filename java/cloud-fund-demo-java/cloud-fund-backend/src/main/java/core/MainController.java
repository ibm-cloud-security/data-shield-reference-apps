package core;

import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;

import crypto.CryptoService;
import crypto.ICrypto;
import utils.Constants;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/") // This means URL's start with /demo (after Application path)
public class MainController {
	
	private static Logger LOGGER = LogManager.getLogger(MainController.class.getName());
	Gson gson = new Gson();

	@Autowired 
	private TransactionRepository transactionRepository;
	
	@Autowired
	private ApplicationContext appContext;
	
	private static ICrypto cryptoService = new CryptoService();
	private String ENCRYPTION_KEY = System.getenv(Constants.ENCRYPTION_KEY_ENV);
	
	
	@GetMapping(path="/")
	public @ResponseBody String getInfo() {
		
		LOGGER.info("getInfo(): / ");
		return "Welcome to cloud fund backend.";
	}
	
	//Donate
	@RequestMapping(path = "/api/v1/transactions", method = RequestMethod.POST)
	public ResponseEntity<String> donateOneTime(@RequestBody String body) throws Exception {
		
		LOGGER.info("donateOneTime(): /api/v1/transactions ");
		LOGGER.debug("donateOneTime(): "+body);
		Transaction transaction = gson.fromJson(body, Transaction.class);
		SecretKeySpec key = cryptoService.generateKey(ENCRYPTION_KEY);
		String encryptedCardNumber = cryptoService.encrypt(transaction.getCreditCard().getNumber(), key);
		LOGGER.debug("donateMonthly(): Encrypted number: "+encryptedCardNumber);
		transaction.getCreditCard().setNumber(encryptedCardNumber);
		transactionRepository.save(transaction);
		
		LOGGER.info("donateOneTime(): Sending transaction information to billing service.");
		JmsTemplate jmsTemplate = appContext.getBean(JmsTemplate.class);
        jmsTemplate.convertAndSend("billingService", new BillingInfo(transaction.getId(), transaction.getName(), transaction.getCreditCard().getNumber(), transaction.getCreditCard().getExpiry(), transaction.getCreditCard().getCvv() ));
		
		return ResponseEntity.ok("");
	}
	
	//Donate encrypted
	@RequestMapping(path = "/api/v1/store_card", method = RequestMethod.POST)
	public ResponseEntity<String> donateMonthly(@RequestBody String body) throws Exception {

		LOGGER.info("donateMonthly(): /api/v1/store_card ");
		LOGGER.debug("donateMonthly(): "+body);
		Transaction transaction =gson.fromJson(body, Transaction.class);
		SecretKeySpec key = cryptoService.generateKey(ENCRYPTION_KEY);
		String encryptedCardNumber = cryptoService.encrypt(transaction.getCreditCard().getNumber(), key);
		LOGGER.debug("donateMonthly(): Encrypted number: "+encryptedCardNumber);
		transaction.getCreditCard().setNumber(encryptedCardNumber);
		transactionRepository.save(transaction);
		
		LOGGER.info("donateOneTime(): Sending transaction information to billing service.");
		JmsTemplate jmsTemplate = appContext.getBean(JmsTemplate.class);
        jmsTemplate.convertAndSend("billingService", new BillingInfo(transaction.getId(), transaction.getName(), transaction.getCreditCard().getNumber(), transaction.getCreditCard().getExpiry(), transaction.getCreditCard().getCvv() ));

		return ResponseEntity.ok("");
	}
	
	//Get
	@RequestMapping(path = "/api/v1/transactions", method = RequestMethod.GET)
	public @ResponseBody Iterable<Transaction> getOneTimeTransactions(@RequestParam(value="email") String email){
		
		LOGGER.info("getOneTimeTransactions(): /api/v1/transactions "+email);
		List<Transaction> transaction = transactionRepository.findByEmailAndType(email, "One-Time");
		LOGGER.info("getOneTimeTransactions(): "+transaction);
		return transaction;
	}
	
	//Delete
	@RequestMapping(path = "/api/v1/transactions/{transactionId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTransaction(@PathVariable("transactionId") int transactionId) {
		
		LOGGER.info("deleteTransaction(): /api/v1/transactions/"+transactionId);
		transactionRepository.deleteById(transactionId);
		return ResponseEntity.ok("");
	}
	
	//Get cards
	@RequestMapping(path = "/api/v1/get_cards", method = RequestMethod.GET)
	public @ResponseBody Iterable<Transaction> getMonthlyTransactions(@RequestParam(value="email") String email) throws Exception {
		
		LOGGER.info("getMonthlyTransactions(): /api/v1/get_cards "+email);
		List<Transaction> transactions = transactionRepository.findByEmailAndType(email, "Monthly");
		LOGGER.info("getMonthlyTransactions(): "+transactions);
		return transactions;
	}

	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Transaction> getAllTransactions() {
		
		LOGGER.info("getAllTransactions(): /all ");
		return transactionRepository.findAll();
	}
	
    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}