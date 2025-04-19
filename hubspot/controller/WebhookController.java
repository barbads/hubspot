@RestController
@RequestMapping("/api/webhook")
public class GenericController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    @Autowired
    private WebhookService webhookService;

    
    /**
     * Endpoint to receive any JSON object
     * 
     * @param requestBody The JSON request body
     * @return ResponseEntity with status and echo of received data
     */
    @PostMapping("/receive")
    public ResponseEntity<Object> receiveJson(@RequestBody GenericRequestDTO requestBody) {
        // Log or process the received JSON data
        System.out.println("Received JSON data: " + requestBody.getProperties());
        
        // You can perform any operations with the received data here
        
        // Return the received data as confirmation
        return new ResponseEntity<>(requestBody.getProperties(), HttpStatus.OK);
    }
}