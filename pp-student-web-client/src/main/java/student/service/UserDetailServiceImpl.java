package student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import student.entity.User;

@Service
@Qualifier("myUserDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private HttpHeaders headers;
	
	String baseUrl = "http://localhost:8888/api/user/";
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		HttpEntity<String> requestEntity = new HttpEntity<>(headers) ;
		ResponseEntity<User> response = restTemplate.exchange(baseUrl + "?email=" + username , HttpMethod.GET, requestEntity, User.class);
		User user = response.getBody();
		System.out.println("==>login : " + user);
		if(user==null)
			throw new UsernameNotFoundException("==>User not found!!!");
		return user;
	}
}
