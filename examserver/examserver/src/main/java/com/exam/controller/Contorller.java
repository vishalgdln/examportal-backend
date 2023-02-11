package com.exam.controller;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class Contorller {
	
	@Autowired
	private UserService userService;
	
	String text="{\r\n" + 
			"    \"regionType\":1,\r\n" + 
			"    \"regionCd\":\"24\",\r\n" + 
			"    \"dateType\":1,\r\n" + 
			"    \"fromDate\":\"02032015\",\r\n" + 
			"    \"toDate\":\"01062015\",\r\n" + 
			"    \"locationType\":1,\r\n" + 
			"    \"pageNo\":0,\r\n" + 
			"    \"pageSize\":10,\r\n" + 
			"    \"sortBy\":\"schoolName\"\r\n" + 
			"}";

	@PostMapping("/")
	public User saveUser(@RequestBody User user ) throws Exception
	{
		Set<UserRole> roles= new HashSet<UserRole>();
		Role role = new Role();
		role.setRoleId(45);
		role.setRoleName("Normal");
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		roles.add(userRole);
		return this.userService.createUser(user, roles);
	}
	
	@GetMapping(value = "/{userName}")
	public User getUser(@PathVariable("userName") String userName)
	{
		return this.userService.getUser(userName);
	}
	
	@GetMapping(value = "enc/")
	public byte[] getenc() throws Exception
	{
		String secretKey= "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkZNufsaiibInDFWJXgOWMlnACSedFbEgspukdxrqaIJkadGFNc4Fx5cO1UxoMJU4fUAlt40WKj3iEsrnvn12QWRkNsPuVvdVZsRFLjIkC3B+39fbpjYrGEweb4MNXjzEC5pyPeNOHl/1yEbqS8II6kxq+tqoguSxBk1//HA2410+4lG78PWQW2//UEWWvpWgmX0MSFtJ3UwErNkmqoKFVWtDpLCiXwL6jfCqSzSjWW6e9y4O8La0l2U1g20dexyCHpL9yfb8uykDCVoraYbozWfzbe9EGpCz24SNwJ1zA0ILIgTp9YzLs7RNIXfMzP5DYnH1DaJKk+ZANy8jT9JumwIDAQAB";
//		String base64 = "ew0KICJjbGllbnRJZCI6Im5pbHAiLA0KICJjbGllbnRTZWNyZXQiOiJOaWxwQE5pbHAzNTkiLA0KICJhcHBLZXkiOiJpZEpGZGdXWUNZWnU2Q2dwN2llL1U2THVMUWJJaW5IRjdjQ0M1dWRnSFF3PSINCn0NCg==";
		String base64 = "ew0KICJjbGllbnRJZCI6Im5pbHAiLA0KICJjbGllbnRTZWNyZXQiOiJOaWxwQE5pbHAzNTkiLA0KICJhcHBLZXkiOiIwSm1HaitYcFpBRkM1MFdpa2FLNE1raitObE81cHlHZVpJQVVwdzRUQW5BPSINCn0=";
//		String base64 = "VGVzdEAxMjMjJCUqXjU4MTIzJCQlKiExMg==";

		FileInputStream fis = new FileInputStream("D:/udiseplusapi.cer");
		CertificateFactory cf = CertificateFactory.getInstance("X509");
		X509Certificate crt = (X509Certificate) cf.generateCertificate(fis);
		PublicKey publicKey = crt.getPublicKey();
		System.out.println(publicKey);
		return this.userService.encrypt1(base64, publicKey);
	}
	
	@GetMapping(value = "dec/")
	public String getdec() throws Exception
	{
		//String sekKey=null;
		String decoceInHex64Text="032ef9df8861d737599e2dd5f3766c1599cc7f2b47ff1c90e9f71abb2a736b634dc90bddcf94b97fcf0c39f08caef23b90994424c6430697c889d37c1c21b66c719679fbfc2b816a";
		//byte[] decodedKey = Base64.getDecoder().decode(hexText);
		return this.userService.decrypt(decoceInHex64Text, "UIA8FtDP4DT6CMqH4lSfjiYNALKjsmv85dU4wH6zqxM=");
	}
	
	@GetMapping(value = "frequentApiEnc/")
	public String frequentApiEnc(@RequestBody String paylod) throws Exception
	{
		String str= userService.encrypt(paylod, "ZA9QJkd3mMr1YOqeTcdKGnn49XKb+cWeme3nmowWvQQ=");
		ApprenticeshipApiAuthDto enc=new ApprenticeshipApiAuthDto();
		enc.setData(Base64.getEncoder().encodeToString(str.getBytes()));
		System.out.println(Base64.getEncoder().encodeToString(str.getBytes()));
	    RestTemplate rest= new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJhbGxvd2VkQXBpSWRzIjpbMF0sInN1YiI6Im5pbHAiLCJyb2xlSWQiOjEwLCJleHAiOjE2NzM0NTI0MzksImlhdCI6MTY3MzQzNDQzOSwidXNlclJlZ2lvbklkIjoxMTZ9.40yxFyJqMan2e1Z2Qq5eVbLsZkXu01a6U54LMuXEkHLnO2KUTFSwxlNmNNgVkInQhF_C8OKK9pqesFDa_nua0g");
	    HttpEntity<ApprenticeshipApiAuthDto> entity = new HttpEntity<ApprenticeshipApiAuthDto>(enc,
				headers);
	    String abc=rest.postForObject("https://api.udiseplus.gov.in/school/v1.1/school-info-basic/by-region-date", entity, String.class);
	    System.out.println(abc);
	    return abc;
	}
	
}
