package com.ftn.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ftn.controller.AgentController;
import com.ftn.dto.AgentDTO;
import com.ftn.model.Agent;
import com.ftn.model.Permission;
import com.ftn.model.Role;
import com.ftn.model.User;
import com.ftn.model.UserToken;
import com.ftn.repository.AccomodationRepository;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.RoomRepository;
import com.ftn.security.AgentSecurity;
import com.ftn.security.TokenUtils;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllAgentsRequest;
import com.ftn.webservice.files.GetAllAgentsResponse;
import com.ftn.webservice.files.GetAllCitiesRequest;
import com.ftn.webservice.files.GetAllCitiesResponse;

@Service
public class AgentService implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(AgentService.class);

	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private AccomodationRepository accomodationRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private RatingService ratingService;
	@Autowired
	private CityService cityService;
	@Autowired
	private TypeAccomodationService typeAccomodationService;
	@Autowired
	private AdditionalServicesService additionalServicesService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private AccomodationService accomodationService;
	@Autowired
	private PriceService priceService;
	@Autowired 
	private ReservationService reservationService;
	@Autowired 
	private ReservationAgentService reservationAgentService;
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	public ArrayList<Agent> getAllAgents() {
		
		permissionService.getAllPermissions();
		roleService.getAllRoles();
		
		GetAllAgentsRequest request = new GetAllAgentsRequest();
		request.setRequest("Agent request: 'Get all existing agents'");
		
		GetAllAgentsResponse response = (GetAllAgentsResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<Agent> agents = new ArrayList<Agent>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all existing agents'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getAgentslist().size(); i++) {
			
			Agent a = new Agent();
			a.setId(response.getAgentslist().get(i).getId());
			a.setUsername(response.getAgentslist().get(i).getUsername());
			a.setPassword(response.getAgentslist().get(i).getPassword());
			a.setFirstName(response.getAgentslist().get(i).getFirstName());
			a.setLastName(response.getAgentslist().get(i).getLastName());
			a.setAddress(response.getAgentslist().get(i).getAddress());
			a.setMbr(response.getAgentslist().get(i).getMbr());	
			a.setEnabled(response.getAgentslist().get(i).isEnabled());
			a.setNonLocked(response.getAgentslist().get(i).isNonLocked());
			
			List<Role> roles = new ArrayList<Role>();
			for(int j=0; j<response.getAgentslist().get(i).getAgentRoles().size(); j++) {
				Long roleId = response.getAgentslist().get(i).getAgentRoles().get(j).getId();
				Role role = roleRepository.getOne(roleId);
				roles.add(role);
			}
			
			Set<Role> setRoles = new HashSet<Role>(roles);
			a.setRoles(setRoles);
			
			agentRepository.save(a);
			agents.add(a);
			
		}
		

		return (ArrayList<Agent>) agents;
	}
	
	public UserToken loginAgent(AgentDTO agentDTO) throws Exception{
		
		Agent agent = agentRepository.findOneByUsername(agentDTO.getUsername());
		//log.info("User id: "+ agent.getId()+" LOG"); 
		
		//System.out.println("username: " + agentDTO.getUsername());
		//System.out.println("lozinka: " + agentDTO.getPassword());
		
		if (agent == null) {
			//log.error("User id: "+ agent.getId()+" LOGFAIL");
			return null;
		}

		if (BCrypt.checkpw(agentDTO.getPassword(), agent.getPassword())) {
			System.out.println("jednake sifre");
			UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(agentDTO.getUsername(),
					agentDTO.getPassword());
			Authentication auth = authManager.authenticate(authReq);
			String username = authReq.getName();
			String token = tokenUtils.generateToken(auth);
			long expiresIn = tokenUtils.getExpiredIn();
			
			Agent a = agentRepository.findOneByUsername(username);
			
			//Sinhronizacija baze
			countryService.getAllCountries();
			cityService.getAllCities();
			typeAccomodationService.getAllTypes();
			additionalServicesService.getAllAdditionalServices();
			categoryService.getAllCategories();
			accomodationService.getAllAccomodation(token);
			
			for(int i=0; i<accomodationRepository.findAll().size(); i++) {
				roomService.getAllRooms(accomodationRepository.findAll().get(i).getId(),token);
			}
			
			for(int i=0; i<roomRepository.findAll().size(); i++) {
				priceService.getAllPrices(roomRepository.findAll().get(i).getId(),token);
			}	
			
			userService.getAllUsers();
			reservationService.getAllReservations(token);	
			reservationAgentService.getAllReservations(token);
			
			//PRIVREMENO
			//ratingService.getAllRatings();
			
			log.info("User id: "+ agent.getId()+" LOGSUCCESS");

			return new UserToken(token, expiresIn);
			
		} else {
			
			log.error("User id: "+ agent.getId()+" LOGFAIL");

			return null;
		}
			
		
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Agent agent = agentRepository.findOneByUsername(username);
	       
        return getAgentSecurity(agent);
	}
	
	private AgentSecurity getAgentSecurity(Agent agent) {
		
		Set<Role> roles = agent.getRoles();
		
		Set<String> perm = new HashSet<String>();
		
		for(Role r: roles) {
			
			for(Permission p: r.getPermissions()) {
				
				
				perm.add(p.getName());
			}
		}
		
		List<GrantedAuthority> authorites = new ArrayList<GrantedAuthority>();
		for(String s: perm) {
		
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(s);
			authorites.add(authority);
			
		}
		
		return new AgentSecurity(agent.getId(), agent.getPassword(), agent.getUsername(), agent.isEnabled(), authorites, agent.isNonLocked());
	}

}
