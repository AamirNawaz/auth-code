**Authentication and Authorization completed in Spring boot with Spring version 3.1.7 **
Having User Table and Role Table which create User_roles table in junction ,using Postgress Database.

## Installation

Steps to clone and run the project in local enviroments.

### step1:

Git clone the repository.

### Step2:

Make sure to update the databse crendentials in resource/applicatin.properties file.

### step3:

```
 Run the project.
```

## Postman signup payload:

```sh
http://localhost:9090/customer/signup
{
    "name": "aamir",
    "email": "aamir@gmail.com",
    "password": "aamir",
    "roles": ["ROLE_CUSTOMER","ROLE_ADMIN","ROLE_SP"]
}

Curl Request For signup:

curl --location 'http://localhost:9090/customer/signup' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772' \
--data-raw '{
    "name": "aamir",
    "email": "aamir@gmail.com",
    "password": "aamir",
    "roles": ["ROLE_CUSTOMER","ROLE_ADMIN","ROLE_SP"]
}'
```

```sh
http://localhost:9090/customer/login
{
    "email": "aamir@gmail.com",
    "password": "aamir"
}

Curl request:

curl --location 'http://localhost:9090/customer/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772' \
--data-raw '{
    "email": "aamir@gmail.com",
    "password": "aamir"
}'
```

```sh
you can pass token in Bearer inside Authorization.

curl --location 'http://localhost:9090/customer/list' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYW1pckBnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9DVVNUT01FUiIsIlJPTEVfU1AiXSwiaWF0IjoxNzA1NDA2MDMzLCJleHAiOjE3MDU0MDc4MzN9.cDWg01Vbi-NqBKn1UviUozsRrHjIrEBMLVc9zzKjRuM' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772'


For Customer dashboard to access only with customer role

Curl Request Customer Dashboard:

curl --location 'http://localhost:9090/api/dashboard' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YWhpckBnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0NVU1RPTUVSIl0sImlhdCI6MTcwNTQwNTk3MiwiZXhwIjoxNzA1NDA3NzcyfQ.-7-4mnGqYXcP_lj_2geEBCmUnLThKfUgRuukL2M7EzY' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772'


```

```sh
Role Curd curl with Admin role access:
##CreateRole 

curl --location 'http://localhost:9090/roles/create' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772' \
--data '{
    "name":"ROLE_SP"
}'

##Get All roles
curl --location 'http://localhost:9090/roles' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYW1pckBnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9DVVNUT01FUiIsIlJPTEVfU1AiXSwiaWF0IjoxNzA1NDA1ODg1LCJleHAiOjE3MDU0MDc2ODV9.v7YaqkTIqWg9rqlrxljORot2wlg0Fkr8zUC4U4qqDdc' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772'

##Delete role by id
curl --location 'http://localhost:9090/roles/delete/4' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772'

```

## Entites  Role & User whic create junction table user_roles

### User Entity:

```java
//I have used name Customer class you can update it User etc. 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private final Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
```

## Role Entity

```java

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }
}
```

### Spring Security Config file

``` java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/roles/**").hasAnyRole("ADMIN")
                        .requestMatchers("customer/list").hasAnyRole("SP", "ADMIN")
                        .requestMatchers("/api/dashboard").hasRole("CUSTOMER")
                        .requestMatchers("/customer/signup", "/customer/login").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
```

### JWT Util class

```java

@Component
public class JwtUtil {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    public String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("roles", getAuthorities(userDetails))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private List<String> getAuthorities(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
```

## Jwt filer class which act as middleware

```java

@Configuration
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomerServiceImpl customerService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(CustomerServiceImpl customerService, JwtUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customerService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

````

## CustomerServiceImpl class

``` java
@Service
public class CustomerServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found!"));

        Set<Role> roles = customer.getRoles();
        System.out.println("Roles for user " + email + ": " + roles);

        Collection<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new User(customer.getEmail(), customer.getPassword(), authorities);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}

```

## CreateCustomer serviec class

```java

@Service
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(CustomerRepository customerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer creatCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = new Customer();
        customer.setName(customerRequestDto.getName());
        customer.setEmail(customerRequestDto.getEmail());
        String hashPassword = passwordEncoder.encode(customerRequestDto.getPassword());
        customer.setPassword(hashPassword);


        Set<Role> roles = new HashSet<>();
        for (String roleName : customerRequestDto.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new EntityNotFoundException("Role with name " + roleName + " not found"));
            roles.add(role);
        }

        customer.setRoles(roles);

        return customerRepository.save(customer);
    }
}
```

```sh
 # Authoer - Aamir Nawaz
 # Principal Software Engineer - Java | Kotline | Spring boot | Node.js GraphQl | Golang Gogin, GoFiber| Aws ,GCP
 # Contact Details: aamirnawaz.dev@gmail.com
 # Portfolio url: aamirnawaz@netlify.app
```