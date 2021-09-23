package com.messi.DockerJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

@SpringBootApplication
@RestController
public class DockerJavaApplication {
	private String connectionString;

	@GetMapping("/")
	public String home() {
		return "FFFFFFFFF***********************Hello Welcome in LNPDT Moderinzation*********************";
	}


	public static void main(String[] args) {
		SpringApplication.run(DockerJavaApplication.class, args);
	}

	@GetMapping("/getSecret")
	public String get() {

		try {
			ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
					.clientId("13351341-0198-4307-9e55-3b34c23933aa").clientSecret("nf1X01RA-aVB2W-cG-oi7sE1_HlZ~KV315").tenantId("d6114c59-ce50-4c5e-a3ec-27858c9547f8")
					.build();
			// Azure SDK client builders accept the credential as a parameter
			SecretClient secretClient = new SecretClientBuilder().vaultUrl("https://keyvaultpoonamyadav.vault.azure.net")
					.credential(clientSecretCredential).buildClient();
			KeyVaultSecret secret = secretClient.getSecret("MYSQL-DATABASE-ADMIN-LOGIN-NAME");
			System.out.printf("Retrieved secret with name \"%s\" and value \"%s\"%n", secret.getName(),
					secret.getValue());
			connectionString = (String) secret.getName() + (String) secret.getValue();
			return connectionString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

	}

	

	public void run(String... varl) throws Exception {
		System.out.println(String.format("\nConnection String stored in Azure Key Vault:\n%s\n", connectionString));
	}
	
	@GetMapping("/getDB")
	public String getDataBase() {
		try {
			return MYSQLApplications.getDataBaseConnectionCheck();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

}
