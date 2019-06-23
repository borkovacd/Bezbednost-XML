package com.ftn.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ftn.dto.AgentDTO;
import com.ftn.model.Agent;
import com.ftn.model.Permission;
import com.ftn.model.Role;
import com.ftn.model.UserToken;
import com.ftn.repository.AccomodationRepository;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.RoomRepository;
import com.ftn.security.TokenUtils;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllAgentsRequest;
import com.ftn.webservice.files.GetAllAgentsResponse;
import com.ftn.webservice.files.GetAllCitiesRequest;
import com.ftn.webservice.files.GetAllCitiesResponse;

@Service
public class AgentService {

	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private AccomodationRepository accomodationRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	//@Autowired
//	private AuthenticationManager authManager;
	
	//@Autowired
	//TokenUtils tokenUtils;
	
	@Autowired
	private CategoryService categoryService;
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
	
	public Long loginAgent(AgentDTO agentDTO){
		
		
		Agent agent = agentRepository.findOneByUsername(agentDTO.getUsername());
		
		
		if (agent == null) {
			throw new IllegalArgumentException("Agent not found!");
		}

		if (agent.getPassword().equals(agentDTO.getPassword())) {
			
			
			//OVDE IDE SINHRONIZACIJA
			countryService.getAllCountries();
			cityService.getAllCities();
			typeAccomodationService.getAllTypes();
			additionalServicesService.getAllAdditionalServices();
			categoryService.getAllCategories();
			accomodationService.getAllAccomodation(agent.getId());
			
			
			for(int i=0; i<accomodationRepository.findAll().size(); i++) {
				roomService.getAllRooms(accomodationRepository.findAll().get(i).getId());
			}
			
			for(int i=0; i<roomRepository.findAll().size(); i++) {
				priceService.getAllPrices(roomRepository.findAll().get(i).getId());
			}	
			
			userService.getAllUsers();
			reservationService.getAllReservations(agent.getId());	
			//permissionService.getAllPermissions();
			//roleService.getAllRoles();
			
			
			
			// ovde ide deo za logovanje
			
			UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(agentDTO.getUsername(),
					agentDTO.getPassword());
			
		//	Authentication auth = authManager.authenticate(authReq);
			
		//	String username = authReq.getName();
			
		//	String token = tokenUtils.generateToken(auth);

		//	long expiresIn = tokenUtils.getExpiredIn();
			
		//	Agent a = agentRepository.findOneByUsername(username);
			
			//return new UserToken(token, expiresIn);
			return agent.getId();
		}else{
			return null;
		}
			
		
	}

}
