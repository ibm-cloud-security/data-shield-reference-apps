package core;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
	
	List<Transaction> findByEmailAndType(String email, String type);

}
