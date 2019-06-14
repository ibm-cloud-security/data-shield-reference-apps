# Cloud Fund Java Demo

## How it works?

![Cloud Fund Demo Diagram](Cloud-fund-demo-diagram.png?raw=true "Cloud Fund Demo Diagram")

1.	User submits transaction in the UI and gets sent to the Controller.
2.	After getting the transaction info, the Controller encrypts the credit card number using the Crypto Service.
3.	After encrypting, Crypto Service sends back the encrypted credit card number.
4.	The Controller stores the transaction (including the encrypted credit card number) in the Transactions DB. The transaction is stored with the status “IN PROGRESS”.
5.	The Controller sends the billing info from the transaction, to the Billing Service.
6.	After receiving credit card information, Billing Service uses the Crypto Service to decrypt the credit card number.
7.	After decrypting, Crypto Service sends the credit card number back to the Billing Service.
8.	Billing Service processes the billing information and updates the transaction in the DB with the status “PROCESSED”.

## Libraries used

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
    import org.springframework.context.annotation.Bean;
    import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
    import org.springframework.jms.config.JmsListenerContainerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.jms.annotation.JmsListener;
    import org.springframework.stereotype.Component;
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
    import org.springframework.data.repository.CrudRepository;
    
    import javax.persistence.ElementCollection;
    import javax.persistence.Embeddable;
    import javax.persistence.Embedded;
    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.GenerationType;
    import javax.persistence.Id;
    import javax.jms.ConnectionFactory;
    
    import javax.crypto.spec.SecretKeySpec;
    import javax.crypto.spec.SecretKeySpec;
    import javax.crypto.BadPaddingException;
    import javax.crypto.Cipher;
    import javax.crypto.IllegalBlockSizeException;
    import javax.crypto.NoSuchPaddingException;
    import javax.crypto.SecretKey;
    import javax.crypto.SecretKeyFactory;
    import javax.crypto.spec.PBEKeySpec;
    import javax.crypto.spec.SecretKeySpec;


# Setting up Cloud Fund Java demo

## Project Structure

* Backend: This project runs a Spring, Java, JPA backend, which connects to a Mariadb instance.
    * image: `<registry_path>/cloud-fund-backend`
    * deployment: `cloud-fund-backend`. This deployment has two containers: `java` and `mariadb`
    * service: `cloud-fund-backend-service`
    
* Backend-for-frontend: This projects runs on nodejs. It serves as the backend for the frontend UI.
    * image: `<registry_path>/cloud-fund-bff`
    * deployment: `cloud-fund-bff`
    * service: `cloud-fund-bff-service`
 
* Frontend: The frontend runs on react - redux.
    * image: `<registry_path>/cloud-fund-frontend`
    * deployment: `cloud-fund-frontend`
    * service: `cloud-fund-frontend-service`

## Steps to install

_Make sure you have yq installed._

1. Populate values in `./setup/scripts/values.sh`. The set of values belong to the following categories:
    * SGX Cluster where we want to deploy this demo
    * Account and registry where we want to push/pull images to/from
    * App ID configuration (a predefined instance is already populated)
    * App URLs (it is recommended to use defaults)
    
2. CD to `./setup/scripts` and execute`setup.sh`
3. Check for the following pods, services and ingress to be up and running:
    * cloud-fund-backend
    * cloud-fund-frontend
    * cloud-fund-bff
    * cloud-fund-backend-service
    * cloud-fund-bff-service
    * cloud-fund-frontend-service
    * cloud-fund-ingress
    
4. Once all pods are up and running, the demo app can be accessed through the ingress host. For example `<cluster_name>.<region>.containers.appdomain.cloud`.

_Make sure your App ID instance has the proper ingress URL defined as redirect URL, for example `https://<cluster_name>.<region>.containers.appdomain.cloud/*`. 
This can be found at the App ID instance configuration, under `Manage Identity Providers` -> `Authentication Settings` -> `Add web redirect URLs`._

## What does the `setup.sh` script run? (All scripts found under `./setup/scripts`)

1. Exports cluster configuration: `export_cluster_config.sh`
2. Logs in to ibmcloud and container registry `cloud_login.sh`
3. Builds and pushes to the registry, backend, backend-for-frontend and frontend images `build_and_push.sh`
4. Creates secrets, using the values from `values.sh`, `create_secrets.sh`
5. Deploys pods, services and ingress `deploy.sh`

## Useful scripts (Found under `./setup/scripts`)
    
* `cloud_login.sh` Logs to ibmcloud and container registry
* `create_secrets.sh` Creates secrets, based on defined values in `values.sh`
* `deploy.sh` Redeploys all pods, services and ingress for this demo
* `export_cluster_config.sh` Exports cluster configuration (basically connects to IKS cluster)
* `setup.sh` Does the main setup
* `use_sgx_backend.sh` Changes `cloud-fund-backend` to use SGX protected image 
* `use_unconverted_backend.sh` Changes `cloud-fund-backend` to use unconverted image
* `build_and_push.sh` Builds and pushes each one of the components to the registry specified in `values.sh`