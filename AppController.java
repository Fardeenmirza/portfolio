package portfolio.restapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Controller
public class AppController {

	@Autowired
	private CustomerDAO dao;
	@GetMapping("/")
	public String viewHomePage(Model model) {
		List<Customer> customer = dao.list(); 
		model.addAttribute("customer",customer);
		return "index";
	}
	@GetMapping("/welcome")
	public String home() {
		return "welcome";
	}
	@PostMapping(path = "/sendMessage",consumes = "application/json")
	public String sendMessage(@RequestBody Customer customer) {
		System.out.println(customer);
		dao.save(customer);
		return "redirect:/";

	}
	@PostMapping("/pdf/Fardeen_Mirza")
	public ResponseEntity<InputStreamResource> downloadPDFResource() throws IOException{

		BasicAWSCredentials awsCreds = new BasicAWSCredentials("here you will put your key", "here id");
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("here ypur location").withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.build();
		String bucketName = "downloadcv";
		String folderName = "cv";
		String fileNameInS3 = "Fardeen_Mirza.pdf";
		String fileNameInLocalPC = "Fardeen_Mirza.pdf";
		S3Object fullObject;
		fullObject = s3Client.getObject(new GetObjectRequest(bucketName, folderName + "/" + fileNameInS3));
		System.out.println("--Downloading file done");
		InputStream is = fullObject.getObjectContent();
		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_PDF)
				.cacheControl(CacheControl.noCache()).header("Content-Disposition", "attachment; filename=" + "Fardeen_Mirza.pdf")
				.body(new InputStreamResource(is));
	}}


